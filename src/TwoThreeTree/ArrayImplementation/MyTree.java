package TwoThreeTree.ArrayImplementation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyTree {
    private class Node {
        ArrayList<Integer> keys;
        ArrayList<Node> children;
        Node parent;
        int keyCount;

        private Node() {
        }

        private Node(Integer integer) {
            this.keys = new ArrayList<>(3);
            this.children = new ArrayList<>(4);
            this.keyCount = 0;
        }

        private void addKey(Integer key) {
            this.keys.add(key);
            keyCount++;
            Collections.sort(keys);
        }

        private boolean isLeaf() {
            for (Node n : children) {
                if (n != null) {
                    return false;
                }
            }
            return true;
        }

        private Node goToLeaf(Integer key) {
            Node current = this;
            while (!current.isLeaf()) {
                if (current.keys.contains(key)) { // find duplicated key
                    return null;
                }
                if (current.keyCount == 1) { // find a 1key node to add
                    current.addKey(key);
                    return null;
                }

                if (this.keyCount == 1) {
                    if (key < current.keys.get(0)) {
                        current = current.children.get(0);
                    } else {
                        current = current.children.get(2);
                    }
                } else if (this.keyCount == 2) {
                    if (key < current.keys.get(0)) {
                        current = current.children.get(0);
                    } else if (key < current.keys.get(1)) {
                        current = current.children.get(2);
                    } else {
                        current = current.children.get(1);
                    }
                }

            }
            return current;
        }

        private boolean isKeyExists(Integer key) {
            if (key != null) {
                return key.equals(this.keys.get(0)) || key.equals(this.keys.get(1));
            }
            return false;
        }

        /**
         * Find which child is this node
         *
         * @return 1, it's left
         * 2, it's mid
         * 3, it's right
         * 4, new added, (won't happen)
         */
        private int whichChild() {
            return parent.children.indexOf(this);
        }

        private void absorb(Node promotion) {
            promotion.children.add(promotion.children.get(2)); // copy mid-child to right to make enough space for 3 children
            this.addKey(promotion.keys.get(0));
            switch (whichChild()) {
                case 0:
                    this.children.set(0, promotion.children.get(0));
                    this.children.set(1, promotion.children.get(1));
                case 2:
                    this.children.set(1, promotion.children.get(0));
                    this.children.set(2, promotion.children.get(1));
            }
            for (Node child : promotion.children) {
                child.parent = this;
            }
        }

        private Node splitLeaf() {
            Node newLeft = new Node(this.keys.get(0));
            Node newRight = new Node(this.keys.get(2));
            Node promotionToParent = new Node();
            promotionToParent.keys.add(this.keys.get(1));
            promotionToParent.children.add(newLeft);
            promotionToParent.children.add(newRight);
            return promotionToParent;
        }


        private Node absorbAndSplit(Node promotion) {
            Node left = new Node();
            Node right = new Node();
            Node toParent = new Node();

            this.addKey(promotion.keys.get(0)); // add new key and sort

            // add key to the 3 nodes
            left.addKey(this.keys.get(0));
            right.addKey(this.keys.get(2));
            toParent.addKey(this.keys.get(1));

            // link them
            left.parent = toParent;
            right.parent = toParent;
            toParent.children.add(left);
            toParent.children.add(right);

            // split this to left and right
            switch (whichChild()) {
                case 0:
                    left.children.add(promotion.children.get(0));
                    left.children.add(promotion.children.get(1));
                    promotion.children.get(0).parent = left;
                    promotion.children.get(1).parent = left;
                    right.children.add(this.children.get(1));
                    right.children.add(this.children.get(2));
                case 1:
                    left.children.add(this.children.get(0));
                    left.children.add(promotion.children.get(0));
                    right.children.add(promotion.children.get(1));
                    promotion.children.get(0).parent = left;
                    promotion.children.get(1).parent = right;
                    right.children.add(this.children.get(2));
                case 2:
                    left.children.add(this.children.get(0));
                    left.children.add(this.children.get(1));
                    right.children.add(promotion.children.get(0));
                    right.children.add(promotion.children.get(1));
                    promotion.children.get(0).parent = right;
                    promotion.children.get(1).parent = right;
            }

            return toParent;
        }
    }

    Node root;
    int size;


    public MyTree() {
        this.root = new Node();
        this.size = 0;
    }

    public MyTree(ArrayList<Integer> keys) {
        root = new Node(keys.get(0));
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
        if (leaf == null || leaf.keys.contains(key)) {
            return;  // key exists in leaf || key has been added to any node on the path || found duplicate
        }

        // insert leaf
        // case 1: leaf has 1 key
        if (leaf.keyCount == 1) {
            leaf.addKey(key);
            size++;
            return;
        }

        // case 2: leaf has 2 keys, split
        Node promoting;
        leaf.addKey(key);
        promoting = leaf.splitLeaf();


        // loop to root, 2keys node split and passing up, 1key node absorb.
        Node current = leaf;
        while (root != current) {
            if (current.keyCount == 1) {
                current.absorb(promoting);
            } else if (current.keyCount == 2) {
                promoting = current.absorbAndSplit(promoting);
            }
            current = current.parent;
        }

        // now current is root
        if (current.keyCount == 1) {
            current.absorb(promoting);
        } else if (current.keyCount == 2) {
            root = current.absorbAndSplit(promoting);
        }
    }


}
