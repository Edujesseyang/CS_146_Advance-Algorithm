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

        private int getKeyPosition(Integer key) {
            int i = 0;
            while (i < keyCount && key.compareTo(keys[i]) > 0) i++;
            return i;
        }

        private void addKey(int position, Integer key) {
            for (int i = keyCount; i > position; i--) {
                keys[i] = keys[i - 1];
            }
            keys[position] = key;
            keyCount++;
        }

        private Node goToLeaf(Integer key) {
            Node current = this;
            while (!current.isLeaf()) {
                int i = current.getKeyPosition(key);
                if (i < current.keyCount && key.equals(current.keys[i])) {
                    return null; // found duplicate when passing
                }

                current = current.children[i];
            }

            int i = current.getKeyPosition(key);
            if (i < current.keyCount && key.equals(current.keys[i])) {
                return null; // found duplicate in the leaf
            }
            return current;
        }

        private void setChild(int childInd, Node child) {
            children[childInd] = child;
            if (child != null) {
                child.parent = this;
            }
        }

        private void splitUp() {
            Node current = this;
            while (current.keyCount == 3) {
                Integer key1 = current.keys[0], key2 = current.keys[1], key3 = current.keys[2];

                Node left = new Node();
                left.addKey(0, key1);// key1 is left's key
                left.setChild(0, current.children[0]); // set left children,  cur's child1, child2 are the left's children
                left.setChild(1, current.children[1]);

                Node right = new Node();
                right.addKey(0, key3);// key3 is right's key
                right.setChild(0, current.children[2]); // set right children,  cur's child3, child4 are the right's children
                right.setChild(1, current.children[3]);

                if (current.parent == null) { // current is root
                    Node newRoot = new Node();
                    newRoot.addKey(0, key2);
                    newRoot.setChild(0, left);
                    newRoot.setChild(1, right);
                    root = newRoot;
                    return;
                }

                // handle current's parent
                Node parentOfCurrent = current.parent;

                int childInd = 0; // childInd is also keyInd
                while (parentOfCurrent.children[childInd] != current) childInd++; // find the correct childInd


                // make space for new key
                for (int i = parentOfCurrent.keyCount; i > childInd; i--) {
                    parentOfCurrent.keys[i] = parentOfCurrent.keys[i - 1];
                }
                parentOfCurrent.keys[childInd] = key2; // put key into space

                // make space for new child( childInd + 1)
                for (int i = parentOfCurrent.keyCount + 1; i > childInd + 1; i--) {
                    parentOfCurrent.children[i] = parentOfCurrent.children[i - 1];
                }

                // link the left and right child
                parentOfCurrent.setChild(childInd, left);
                parentOfCurrent.setChild(childInd + 1, right);

                parentOfCurrent.keyCount++;
                current = parentOfCurrent; // keep handling its parent.
            }
        }

        private Node findNode(Integer target) {
            for (Integer key : keys) { // check all keys
                if (Objects.equals(key, target)) { // found it, return this
                    return this;
                }
            }

            if (!this.isLeaf()) {
                for (Node child : children) { // recursively check each child
                    if (child != null) {
                        child.findNode(target);
                    }

                }
            }

            return null;
        }

        private int subtreeSize() {

            int result = keyCount; // init keys counts

            if (!this.isLeaf()) {
                for (Node child : children) {
                    if (child != null) {
                        result += child.subtreeSize();
                    }
                }
            }
            return result;
        }


        private Integer findKeyByIndex(int index) {
            for (int i = 0; i < keyCount; i++) {
                if (children[i] != null) {
                    int leftSubtreeSize = children[i].subtreeSize();
                    if (index < leftSubtreeSize) {
                        return children[i].findKeyByIndex(index);
                    }

                    index -= leftSubtreeSize;
                }

                if (index == 0) {
                    return keys[i];
                }
                index--;
            }
            int rightSize = children[keyCount].subtreeSize();
            if (index < rightSize) {
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

        // case 2: go to leaf
        Node leaf = root.goToLeaf(key);
        if (leaf == null) {
            return false;  // key exists in leaf || key has been added to any node on the path || found duplicate
        }

        int correctInd = leaf.getKeyPosition(key);
        leaf.addKey(correctInd, key);
        leaf.splitUp();
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
        if (root == null) return 0;
        Node keyNode = root.findNode(key);
        return keyNode == null ? 0 : keyNode.subtreeSize();
    }

    public Integer get(int index) {
        if (root == null) throw new IllegalStateException("empty tree");
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }

        return root.findKeyByIndex(index);
    }

    public List<Integer> toList() {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) return list;
        inorder(root, list, null);
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inorder(root, null, sb);
        if (sb.length() >= 2) sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    private void inorder(Node node, ArrayList<Integer> list, StringBuilder sb) {
        if (node == null) return;
        if (sb == null) {
            for (int i = 0; i < node.keyCount; i++) {
                inorder(node.children[i], list, null); // go to each child
                list.add(node.keys[i]); // add key to list
            }
            inorder(node.children[node.keyCount], list, null); // go to the last one
        } else if (list == null) {
            for (int i = 0; i < node.keyCount; i++) {
                inorder(node.children[i], null, sb); // go to each child
                sb.append(node.keys[i]).append(", "); // append to string builder
            }
            inorder(node.children[node.keyCount], null, sb); // go to the last one
        }
    }
}
