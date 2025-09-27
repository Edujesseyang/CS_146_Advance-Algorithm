package TwoThreeTree.ArrayImplementation;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class MyTree {
    class Node {
        Integer[] keys;
        Node[] children;
        int keyCount;

        public Node() {
        }

        public Node(Integer integer) {
            this.keys = new Integer[2];
            this.children = new Node[3];
            this.keyCount = 0;
        }

        public void addKey(Integer key) {
            if (this.keys[0] == null) {
                this.keys[0] = key;
                this.keyCount = 1;
            } else if (this.keys[1] == null) {
                this.keys[1] = key;
                this.keyCount = 2;
            }
        }

        public boolean isLeaf() {
            for (Node n : children) {
                if (n != null) {
                    return false;
                }
            }
            return true;
        }

    }

    Node root;
    int size;

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

        // case 2: root is

    }
}
