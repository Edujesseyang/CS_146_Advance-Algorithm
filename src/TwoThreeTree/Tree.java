package TwoThreeTree;

import java.security.InvalidParameterException;
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

        private void setChild(int i, Node child) {
            children[i] = child;
            if (child != null) {
                child.parent = this;
            }
        }

        private void splitUp() {
            Node current = this;
            while (current.keyCount == 3) {
                Integer key1 = current.keys[0], key2 = current.keys[1], key3 = current.keys[2];

                Node left = new Node();
                left.keys[0] = key1; // key1 is left's key
                left.keyCount = 1;
                left.setChild(0, current.children[0]); // set left children,  cur's child1, child2 are the left's children
                left.setChild(1, current.children[1]);


                Node right = new Node();
                right.keys[0] = key3; // key3 is right's key
                right.keyCount = 1;
                right.setChild(0, current.children[2]); // set right children,  cur's child3, child4 are the right's children
                right.setChild(1, current.children[3]);

                if (current.parent == null) { // current is root
                    Node newRoot = new Node();
                    newRoot.keys[0] = key2;
                    newRoot.keyCount = 1;
                    newRoot.setChild(0, left);
                    newRoot.setChild(1, right);
                    root = newRoot;
                    return;
                }

                // handle current's parent
                Node parentOfCurrent = current.parent;

                int childInd = 0; // childInd is also keyInd
                while (parentOfCurrent.children[childInd] != current) childInd++;


                // make space to new keys
                for (int i = parentOfCurrent.keyCount; i > childInd; i--) {
                    parentOfCurrent.keys[i] = parentOfCurrent.keys[i - 1];
                }
                parentOfCurrent.keys[childInd] = key2; // put key into space

                // make space for new child( childInd + 1)
                for (int i = parentOfCurrent.keyCount + 1; i > childInd + 1; i--) {
                    parentOfCurrent.children[i] = parentOfCurrent.children[i - 1];
                }

                // put left and right in to the space
                parentOfCurrent.setChild(childInd, left);
                parentOfCurrent.setChild(childInd + 1, right);

                parentOfCurrent.keyCount++;
                current = parentOfCurrent; // keep handling its parent.
            }
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

    public void insert(Integer key) {
        // check if input is valid
        if (key == null) {
            throw new InvalidParameterException("null parameter");
        }

        // case 1: empty tree
        if (root == null) {
            root = new Node();
            root.addKey(0, key);
            size++;
            return;
        }

        // case 2: go to leaf
        Node leaf = root.goToLeaf(key);
        if (leaf == null) {
            return;  // key exists in leaf || key has been added to any node on the path || found duplicate
        }

        int correctInd = leaf.getKeyPosition(key);
        leaf.addKey(correctInd, key);
        leaf.splitUp();
        size++;
    }

    public void insert(Collection<Integer> input) {
        for (Integer i : input) {
            insert(i);
        }
    }

    public int size() {
        return size;
    }

    public int size(int x) {
        if (root == null) return 0;
        Node n = findNode(root, x);
        return (n == null) ? 0 : subtreeSize(n);
    }

    public int get(int index) {
        if (root == null) throw new IllegalStateException("empty tree");
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        return select(root, index);
    }


    public List<Integer> toList() {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) return list;

        inorder(root, list);

        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Integer> list = toList();
        for (Integer i : list) {
            sb.append(i).append(", ");
        }
        if (sb.length() >= 2) sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    private int select(Node node, int key) {
        while (node != null) {
            int left0 = subtreeSize(node.children[0]);  // size of left

            if (node.keyCount == 1) {
                if (key < left0) {
                    node = node.children[0];
                    continue;
                }
                if (key == left0) return node.keys[0];
                // move to right, skip left and key[0]
                key -= left0 + 1;
                node = node.children[1];
            } else { // keyCount == 2
                if (key < left0) {
                    node = node.children[0];
                    continue;
                }
                if (key == left0) return node.keys[0];
                key -= left0 + 1;

                int left1 = subtreeSize(node.children[1]); // size of mid
                if (key < left1) {
                    node = node.children[1];
                    continue;
                }
                if (key == left1) return node.keys[1];
                // move to right, skip mid and key[0]
                key -= left1 + 1;
                node = node.children[2];
            }
        }
        return -1;
    }

    private void inorder(Node node, ArrayList<Integer> list) {
        if (node == null) return;
        for (int i = 0; i < node.keyCount; i++) {
            inorder(node.children[i], list); // go to each child
            list.add(node.keys[i]); // add key to list
        }
        inorder(node.children[node.keyCount], list); // go to the last one
    }

    private Node findNode(Node current, Integer key) {
        while (current != null) {
            int i = current.getKeyPosition(key); // got to the right path
            if (i < current.keyCount && key.equals(current.keys[i])) { // key is match
                return current;
            }
            if (current.isLeaf()) return null; // didn't find
            current = current.children[i];
        }
        return null;
    }

    private int subtreeSize(Node node) {
        if (node == null) return 0;
        int result = node.keyCount;         // cur node keys
        for (int i = 0; i <= node.keyCount; i++) {     // + all children's keys
            result += subtreeSize(node.children[i]);
        }
        return result;
    }

}
