package TwoThreeTree;

import java.util.*;

public class Tree {
    private class Node {
        int[] keys = new int[3];
        Node[] children = new Node[4];
        Node parent = null;
        int keyCount = 0;

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
            for (int i = keyCount; i > index; i--) {
                keys[i] = keys[i - 1]; // make space
            }
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
            if (child != null) { // link parent
                child.parent = this;
            }
        }

        /**
         * peel second half of a node then make it a new node and return
         * tried to implement as can be converted to tree with more than 2 keys
         */
        private Node split() {
            int n = keyCount; // now it is 3
            Node left = new Node();
            Node right = new Node();
            int mid = n / 2;  // now it is 1

            for (int i = 0; i < mid; i++) { // first half to left
                left.addKey(i, keys[i]);
                left.setChild(i, children[i]);
            }
            left.setChild(mid, children[mid]);

            int counter = 0;
            for (int i = mid + 1; i < n; i++) { // second half to right
                right.addKey(counter, keys[i]);
                right.setChild(counter, children[i]);
                counter++;
            }
            right.setChild(counter, children[n]); // take care of the last left over child

            Node pack = new Node(); // packing
            pack.addKey(0, keys[mid]);
            pack.setChild(0, left);
            pack.setChild(1, right);

            return pack;
        }

        private void passingUp(Node keyPack) {
            // un-pack
            int key = keyPack.keys[0];
            Node left = keyPack.children[0];
            Node right = keyPack.children[1];

            int index = findInsertPosition(key);
            addKey(index, key);

            // make space for incoming child
            for (int i = keyCount; i > index; i--) {
                children[i] = children[i - 1];
            }
            setChild(index, left);
            setChild(index + 1, right);

            if (keyCount < 3) {
                return; // if it's key less than 3, done
            }

            Node pack = split();

            if (parent == null) { // this is root
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


        private int subtreeSize() {
            int result = this.keyCount; // init keys counts
            if (!this.isLeaf()) {
                for (int i = 0; i <= keyCount; i++) {
                    result += children[i] == null ? 0 : children[i].subtreeSize();
                }
            }
            return result;
        }

        private int findKey(int index) {
            for (int i = 0; i < keyCount; i++) {
                int childSize = (children[i] == null) ? 0 : children[i].subtreeSize();

                if (index < childSize) {
                    return children[i].findKey(index);
                }
                index -= childSize;

                if (index == 0) {
                    return keys[i];
                }
                index--;
            }
            int lastSize = (children[keyCount] == null) ? 0 : children[keyCount].subtreeSize();
            if (index < lastSize) {
                return children[keyCount].findKey(index);
            }
            throw new IndexOutOfBoundsException("index out of bound");
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
        leaf.passingUp(keyPack);
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

        Node keyNode = root.findNodeContains(key);
        if (keyNode == null) return 0; // no key found

        return keyNode.subtreeSize();
    }

    public int get(int index) {
        if (root == null) throw new IndexOutOfBoundsException("empty tree");
        return root.findKey(index);
    }


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

