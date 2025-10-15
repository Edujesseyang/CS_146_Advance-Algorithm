package Tree.TwoThreeTree;

import java.util.*;

public class Tree {
    private class Node {
        int[] keys = new int[3];
        Node[] children = new Node[4];
        Node parent = null;
        int keyCount = 0;
        int numOfSubtreeKeys = 0; // updating when split

        private Node() {
        }

        private boolean isLeaf() {
            return children[0] == null;
        }

        private int findInsertPosition(int key) {
            int i = 0;
            while (i < keyCount && keys[i] < key) i++;
            return i;
        }

        private void addKey(int index, int key) {
            for (int i = keyCount; i > index; i--) keys[i] = keys[i - 1]; // make space
            keys[index] = key;
            keyCount++;
        }

        private Node getTargetLeaf(int key) {
            int index = findInsertPosition(key);
            if (index < keyCount && key == keys[index]) return null; //dup
            return (children[index] == null) ? this : children[index].getTargetLeaf(key); // children[i] == null, meas it is the leaf
        }

        private void setChild(int childInd, Node child) {
            children[childInd] = child; // link child
            if (child != null) child.parent = this; // link parent
        }

        private int getNumOfSubtreeKeys(Node node) {
            return node == null ? 0 : node.numOfSubtreeKeys;
        }

        private Node split() {
            int n = keyCount; // now it is 3
            Node left = new Node();
            Node right = new Node();
            int mid = n / 2;  // now it is 1

            for (int i = 0; i < mid; i++) { // first half to left
                left.addKey(i, keys[i]);
                left.setChild(i, children[i]);
                left.numOfSubtreeKeys += getNumOfSubtreeKeys(children[i]); // update left num of subtree keys
            }
            left.setChild(mid, children[mid]);
            left.numOfSubtreeKeys += getNumOfSubtreeKeys(children[mid]);  // update the left over
            left.numOfSubtreeKeys++; // for the key

            int counter = 0;
            for (int i = mid + 1; i < n; i++) { // second half to right
                right.addKey(counter, keys[i]);
                right.setChild(counter, children[i]);
                right.numOfSubtreeKeys += getNumOfSubtreeKeys(children[i]);
                counter++;
            }
            right.setChild(counter, children[n]); // take care of the last left over child
            right.numOfSubtreeKeys += getNumOfSubtreeKeys(children[n]); // update right subtree size
            right.numOfSubtreeKeys++; // for the key

            Node pack = new Node(); // packing
            pack.numOfSubtreeKeys = this.numOfSubtreeKeys; // the size of subtree remains same, split only split
            pack.addKey(0, keys[mid]);
            pack.setChild(0, left);
            pack.setChild(1, right);

            return pack;
        }

        private void updateSubtreeSizeToRoot() { // inc 1 from the parent al the way up to root
            if (this.parent != null) {
                parent.numOfSubtreeKeys++;
                parent.updateSubtreeSizeToRoot();
            }
        }

        private void passingUp(Node keyPack) {
            // un-pack
            int key = keyPack.keys[0];
            Node left = keyPack.children[0];
            Node right = keyPack.children[1];

            int index = findInsertPosition(key);
            addKey(index, key);
            numOfSubtreeKeys -= getNumOfSubtreeKeys(children[index]); // remove the num of subtree keys from the replacing node
            numOfSubtreeKeys += getNumOfSubtreeKeys(keyPack);// because we added one key successfully in this, entire upper chain has to update
            numOfSubtreeKeys++;

            // make space to set incoming child
            for (int i = keyCount; i > index; i--) children[i] = children[i - 1];
            setChild(index, left);
            setChild(index + 1, right);

            if (keyCount < 3) {
                updateSubtreeSizeToRoot(); // finalize and update all upper numOfSubtreeKeys
                return; // if it's key less than 3, absorbed done
            }

            Node pack = split(); // else, split

            if (parent == null) { // this == root
                root = pack;
                return;
            }
            parent.passingUp(pack); // pass the middle to parent.
        }

        private Node findNodeContains(int target) {
            int index = findInsertPosition(target);
            if (index < keyCount && target == keys[index]) return this;
            return children[index] == null ? null : children[index].findNodeContains(target);
        }

        private int findKey(int index) {
            int goingToInd = keyCount; // init the destination is the last child
            int i = 0;
            while (i < keyCount) {
                int childSize = (children[i] == null) ? 0 : children[i].numOfSubtreeKeys; // check left side size

                if (index < childSize) { // go to left
                    goingToInd = i; // update the destination index
                    break;
                }

                index -= childSize; // take off the size of the child that we did

                if (index == 0) return keys[i]; // check key
                index--; // dec 1 for the check we just checked
                i++;
            }

            return children[goingToInd].findKey(index); // go to the destination
        }
    }

    Node root;
    int size;

    public Tree() {
        this.root = null;
        this.size = 0;
    }

    public Tree(int[] input) {
        this();
        for (int i : input) {
            insert(i);
        }
    }

    public boolean insert(int key) {
        // empty tree
        if (root == null) {
            root = new Node();
            root.addKey(0, key);
            size++;
            root.numOfSubtreeKeys++;
            return true;
        }

        // find the target leaf to add
        Node leaf = root.getTargetLeaf(key);
        if (leaf == null) {
            return false; // leaf == null means found a duplicate
        }

        // pack key
        Node keyPack = new Node();
        keyPack.keys[0] = key;
        leaf.passingUp(keyPack); // start passing up
        size++;
        return true;
    }

    public void insert(int[] input) {
        for (int i : input) {
            insert(i);
        }
    }

    public int size() {
        return size;
    }

    public int size(int key) {
        if (root == null) return 0; // empty tree
        Node nodeContainsKey = root.findNodeContains(key);
        return root.getNumOfSubtreeKeys(nodeContainsKey); // use root's method, because root is not null
    }

    public int get(int index) {
        if (root == null || index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bound");
        return root.findKey(index);
    }

    // ======= following for my testing purpose ======
    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    private void inorder(Node node, List<Integer> list) {
        if (node == null) return;
        for (int i = 0; i < node.keyCount; i++) {
            inorder(node.children[i], list); // go to each child
            list.add(node.keys[i]); // add key to list
        }
        inorder(node.children[node.keyCount], list); // go to the last one
    }
}

