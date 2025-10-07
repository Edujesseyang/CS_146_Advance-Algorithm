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
                keys[i] = keys[i - 1];
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
            return (children[i] == null) ? this : children[i].getTargetLeaf(key);
        }

        private void setChild(int childInd, Node child) {
            children[childInd] = child;
            if (child != null) {
                child.parent = this;
            }
        }

        private Node addAndSplit(Node pack) {
            if (this.keyCount < 2) {
                absorbNode(pack);
                return new Node(); // dummy,
            }
            // unpackage input, they are received from bottom
            int insertKey = pack.keys[0];
            Node leftHandle = pack.children[0];
            Node rightHandle = pack.children[1];

            // find the index to insert
            int index = findInsertPosition(insertKey);
            addKey(index, insertKey);

            // they are going to up
            Node newLeft = new Node();
            newLeft.addKey(0, keys[0]);// key1 is left's key
            Node newRight = new Node();
            newRight.addKey(0, keys[2]);// key3 is right's key

            switch (index) {
                case 0: // new key add to left
                    newLeft.setChild(0, leftHandle);
                    newLeft.setChild(1, rightHandle);
                    newRight.setChild(0, children[1]);
                    newRight.setChild(1, children[2]);
                    break;
                case 1:
                    newLeft.setChild(0, children[0]);
                    newLeft.setChild(1, leftHandle);
                    newRight.setChild(0, rightHandle);
                    newRight.setChild(1, children[2]);
                    break;
                case 2:
                    newLeft.setChild(0, children[0]);
                    newLeft.setChild(1, children[1]);
                    newRight.setChild(0, leftHandle);
                    newRight.setChild(1, rightHandle);
                    break;
            }

            if (parent == null) { // current is root
                Node newRoot = new Node();
                newRoot.addKey(0, keys[1]);
                newRoot.setChild(0, newLeft);
                newRoot.setChild(1, newRight);
                return newRoot;
            }

            // pack returning node
            Node packToUp = new Node();
            packToUp.addKey(0, keys[1]);
            packToUp.setChild(0, newLeft);
            packToUp.setChild(1, newRight);
            return this.parent.addAndSplit(packToUp);
        }


        private void absorbNode(Node keyPackage) {
            // unpackage
            Integer key = keyPackage.keys[0];
            Node leftHandle = keyPackage.children[0];
            Node rightHandle = keyPackage.children[1];

            int index = findInsertPosition(key);
            addKey(index, key);

            if (leftHandle == null && rightHandle == null) {
                return;
            }

            switch (index) {
                case 0:
                    setChild(2, children[1]);
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
                    if (children[i] != null) {
                        result += children[i].subtreeSize();
                    }
                }
            }
            return result;
        }


        private Integer findKeyByIndex(int index) {
            for (int i = 0; i < keyCount; i++) {
                // prevent null child
                int leftSubtreeSize = (children[i] == null) ? 0 : children[i].subtreeSize();
                if (index < leftSubtreeSize) {
                    assert children[i] != null; // index never less than 0
                    return children[i].findKeyByIndex(index);
                }
                index -= leftSubtreeSize;

                if (index == 0) {
                    return keys[i];
                }
                index--;
            }
            // prevent null child
            int rightSize = (children[keyCount] == null) ? 0 : children[keyCount].subtreeSize();
            if (index < rightSize) {
                assert children[keyCount] != null;
                return children[keyCount].findKeyByIndex(index);
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

        // case 1: empty tree
        if (root == null) {
            root = new Node();
            root.addKey(0, key);
            size++;
            return true;
        }

        // find the target leaf to add
        Node leaf = root.getTargetLeaf(key);
        if (leaf == null) {
            return false; // find duplicate
        }

        Node keyPackage = new Node();
        keyPackage.addKey(0, key);
        Node dummy = leaf.addAndSplit(keyPackage);
        if (dummy.keyCount == 0) {
            size++;
            return true;
        } else {
            root = dummy;
        }
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

        if (keyNode.isLeaf()) {
            return 1;
        }

        int index = keyNode.findInsertPosition(key);
        return keyNode.children[index].subtreeSize() + keyNode.children[index + 1].subtreeSize() + 1;
    }

    public int get(int index) {
        if (root == null) throw new IllegalStateException("empty tree");
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        Integer res = root.findKeyByIndex(index);
        if (res == null) {
            throw new IndexOutOfBoundsException("Index out of bound"); // for unboxing Integer to int
        }
        return res;
    }

    public List<Integer> toList() {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) return list;
        inorder(root, list);
        return list;
    }

    @Override
    public String toString() {
        return toList().toString();
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

