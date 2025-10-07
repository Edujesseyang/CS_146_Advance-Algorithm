package TwoThreeTree;

import java.util.*;

public class Tree {
    private class Node {
        Integer[] keys = new Integer[3];
        Node[] children = new Node[4];
        Node parent = null;
        int keyCount = 0;

        private Node() {
        }

        private boolean isLeaf() {
            return children[0] == null;
        }

        private int findInsertPosition(Integer key) {
            int i = 0;
            while (i < keyCount && key.compareTo(keys[i]) > 0) i++;
            return i;
        }

        private void addKey(int index, Integer key) {
            for (int i = keyCount; i > index; i--) {
                keys[i] = keys[i - 1]; // make space
            }
            keys[index] = key;
            keyCount++;
        }

        private boolean contain(Integer target) {
            for (int i = 0; i < keyCount; i++) {
                if (Objects.equals(target, keys[i])) {
                    return true;
                }
            }
            return false;
        }

        private Node getTargetLeaf(Integer key) {
            if (this.contain(key)) {
                return null; // duplicate
            }
            int i = findInsertPosition(key); // find the correct child index
            return (children[i] == null) ? this : children[i].getTargetLeaf(key); // children[i] == null, meas it is the leaf
        }

        private void setChild(int childInd, Node child) {
            children[childInd] = child; // link child
            if (child != null) { // link parent
                child.parent = this;
            }
        }

        private Node addAndSplit(Node pack) {
            if (this.keyCount < 2) {
                absorbNode(pack);
                return new Node(); // dummy, newRoot case = node w/ 1key 2 children, absorbed somewhere case = empty dummy node
            }
            // unpack input, which is received from bottom
            int insertKey = pack.keys[0];
            Node leftHandle = pack.children[0];
            Node rightHandle = pack.children[1];

            // find the index to insert
            int index = findInsertPosition(insertKey);
            addKey(index, insertKey);

            // they are going to up
            Node newLeft = new Node();
            newLeft.addKey(0, keys[0]);// pack left key to return package
            Node newRight = new Node();
            newRight.addKey(0, keys[2]);// pack left key to return package

            switch (index) {
                case 0: // new key add to left
                    newLeft.setChild(0, leftHandle);
                    newLeft.setChild(1, rightHandle);
                    newRight.setChild(0, children[1]);
                    newRight.setChild(1, children[2]);
                    break;
                case 1: // add to mid
                    newLeft.setChild(0, children[0]);
                    newLeft.setChild(1, leftHandle);
                    newRight.setChild(0, rightHandle);
                    newRight.setChild(1, children[2]);
                    break;
                case 2: // add to right
                    newLeft.setChild(0, children[0]);
                    newLeft.setChild(1, children[1]);
                    newRight.setChild(0, leftHandle);
                    newRight.setChild(1, rightHandle);
                    break;
            }

            // handle if this is root
            if (parent == null) {
                Node newRoot = new Node();
                newRoot.addKey(0, keys[1]);
                newRoot.setChild(0, newLeft);
                newRoot.setChild(1, newRight);
                return newRoot; // return newRoot
            }

            // pack returning node
            Node packToUp = new Node();
            packToUp.addKey(0, keys[1]);
            packToUp.setChild(0, newLeft);
            packToUp.setChild(1, newRight);

            // send to the parent
            return this.parent.addAndSplit(packToUp);
        }


        private void absorbNode(Node keyPackage) {
            // unpackage
            Integer key = keyPackage.keys[0];
            Node leftHandle = keyPackage.children[0];
            Node rightHandle = keyPackage.children[1];

            // add key to correct position
            int index = findInsertPosition(key);
            addKey(index, key);

            switch (index) {
                case 0:
                    setChild(2, children[1]); // make space for incoming children
                    setChild(0, leftHandle);
                    setChild(1, rightHandle);
                    break;
                case 1:
                    setChild(1, leftHandle);
                    setChild(2, rightHandle);
                    break;
            }
        }

        private Node findNodeContains(Integer target) {
            if (contain(target)) return this;
            int index = findInsertPosition(target);

            return children[index] == null ? null : children[index].findNodeContains(target);
        }


        private int subtreeSize() {
            int result = this.keyCount; // init keys counts
            if (!this.isLeaf()) {
                for (int i = 0; i <= keyCount; i++) {
                    result += children[i].subtreeSize();
                }
            }
            return result;
        }


        private Integer findKeyByIndex(int index) {
            for (int i = 0; i <= keyCount; i++) {
                // prevent null child
                int leftSideSize = (children[i] == null) ? 0 : children[i].subtreeSize();
                if (index < leftSideSize) {
                    return children[i].findKeyByIndex(index);
                }
                index -= leftSideSize;

                if (index == 0) {
                    return keys[i];
                }
                index--;
            }

            return null;
        }
    }

    Node root;
    int size;

    public Tree() {
        this.root = null;
        this.size = 0;
    }

    public Tree(Collection<Integer> input) {
        this();
        for (Integer i : input) {
            insert(i);
        }
    }

    public boolean insert(Integer key) {
        // check if input is valid
        if (key == null) {
            return false;
        }

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

        // make a package
        Node sendingPackage = new Node();
        sendingPackage.addKey(0, key);

        // get a return package
        Node returningPackage = leaf.addAndSplit(sendingPackage);

        if (returningPackage.keyCount == 0) { // if returning package is a dummy node
            // means added absorbed by one of the nodes
            size++;
            return true; // done
        }

        // a meaningful package is the new root
        root = returningPackage;
        size++;
        return true;
    }

    public void insert(Collection<Integer> input) {
        for (Integer i : input) {
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
        Integer res = root.findKeyByIndex(index);
        return res;  // unboxing
    }

    public List<Integer> toList() {
        ArrayList<Integer> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(Node node, ArrayList<Integer> list) {
        if (node == null) return;
        for (int i = 0; i < node.keyCount; i++) {
            inorder(node.children[i], list); // go to each child
            list.add(node.keys[i]); // add key to list
        }
        inorder(node.children[node.keyCount], list); // go to the last one
    }
}

