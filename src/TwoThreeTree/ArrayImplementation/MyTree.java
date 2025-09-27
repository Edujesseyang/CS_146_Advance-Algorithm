package TwoThreeTree.ArrayImplementation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTree {
    class Node {
        List<Integer> keys;
        List<Node> children;
        Node parent;
        int keyCount;

        public Node() {
        }

        public Node(Integer integer) {
            this.keys = new ArrayList<>(3);
            this.children = new ArrayList<>(4);
            this.keyCount = 0;
        }

        public void addKey(Integer key) {
            this.keys.add(key);
            keyCount++;
            Collections.sort(keys);
        }

        public boolean isLeaf() {
            for (Node n : children) {
                if (n != null) {
                    return false;
                }
            }
            return true;
        }

        public Node goToLeaf(Integer key) {
            Node current = this;
            while (!current.isLeaf()) {
                if (current.keys.contains(key)) {
                    return current;
                }

                if (this.keyCount == 1) {
                    if (key < current.keys.getFirst()) {
                        current = current.children.getFirst();
                    } else {
                        current = current.children.get(2);
                    }
                } else if (this.keyCount == 2) {
                    if (key < current.keys.get(0)) {
                        current = current.children.getFirst();
                    } else if (key < current.keys.get(1)) {
                        current = current.children.get(2);
                    } else {
                        current = current.children.get(1);
                    }
                }

            }
            return current;
        }

        public boolean keyExists(Integer key) {
            if (key != null) {
                return key.equals(this.keys.getFirst()) || key.equals(this.keys.get(1));
            }
            return false;
        }

    }

    Node root;
    int size;

    class Promotion {
        Integer key;
        Node newLeft;
        Node newRight;

        private Promotion() {
        }
    }

    public MyTree() {
        this.root = new Node();
        this.size = 0;
    }

    public MyTree(ArrayList<Integer> keys) {
        root = new Node(keys.getFirst());
        for (int i = 1; i < keys.size(); i++) {
            this.insert(keys.get(i));
        }
    }

    public void insert(Integer key) {
        // check if input is valid
        if (key == null) {
            throw new InvalidParameterException("null parameter");
        }

        // case 1: empty tree
        if (root == null) {
            Node newNode = new Node();
            newNode.addKey(key);
            this.size++;
            return;
        }

        // case 2: go to leaf
        Node leaf = root.goToLeaf(key);
        if (leaf.keyExists(key)) {
            return;  // check if key exists in leaf
        }

        // insert leaf
        // case 1: leaf has 1 key
        if (leaf.keyCount == 1) {
            leaf.addKey(key);
            size++;
            return;
        }

        // case 2: leaf has 2 keys, split needs
        Promotion sendToParent;
        if (leaf.keyCount == 2) {
            leaf.addKey(key);
            sendToParent = splitLeaf(leaf);
        }

        // loop to root, 2keys node split and passing up, 1key node absorb.
        Node current = leaf;
        while(root != current){

        }


    }

    private Promotion splitLeaf(Node leaf) {
        Node newLeft = new Node(leaf.keys.getFirst());
        Node newRight = new Node(leaf.keys.get(2));
        Promotion toParent = new Promotion();
        toParent.key = leaf.keys.get(1);
        toParent.newLeft = newLeft;
        toParent.newRight = newRight;
        return toParent;
    }


}
