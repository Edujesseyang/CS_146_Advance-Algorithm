package TwoThreeTree;

import java.util.*;

public class Tree {
    private class Node {
        Integer small, large; // small[0] is the key, small[1] is num of the key present
        Node left, mid, right, parent;

        private Node(Integer smallKey, Integer largeKey) {
            small = smallKey;
            large = largeKey;
            if (largeKey != null) {
                sortNode();
            }
        }

        private boolean isFull() {
            return small != null && large != null;
        }

        private boolean isLeaf() {
            return left == null && mid == null && right == null;
        }

        private void addKey(Integer key) {
            if (small == null) {
                small = key;
            } else {
                large = key;
                sortNode();
            }
        }

        private void sortNode() {
            if (small != null && large != null && small > large) {
                Integer tmp = small;
                small = large;
                large = tmp;
            }
        }

        private Integer reOrderKeys(Integer newKey) {
            Integer mid; // create a mid for return
            if (newKey < small) { // if key is far left
                mid = small; // mid is the small
                small = newKey; // put key to small
            } else if (newKey > large) { // if key is far right
                mid = large; // mid is the large
                large = newKey; // put key to large
            } else {
                mid = newKey; // key is in middle
            }
            return mid;
        }
    }

    private class Result {
        // use a result class to carry the information that passed by the child to its parent
        Integer promotingKey;
        Node leftHandle;
        Node rightHandle;
        int whichChild; // 1: from left; 2. from mid; 3.from right

        Result() {
        }
    }

    // 23tree main  class
    Node root;
    int size;
    int height;

    public Tree() {
        root = null;
        size = 0;
        height = 0;
    }

    public Tree(Collection<Integer> input) {
        for (Integer i : input) {
            insert(i);
        }
    }

    public int size() {
        return size;
    }

    public int size(int key) {
        Node targetNode = findTheNode(key);
        int count = 0;
        if (targetNode == null) {
            return count;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(targetNode);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.mid != null) {
                stack.push(current.mid);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.small != null) {
                count++;
            }
            if (current.large != null) {
                count++;
            }
        }
        return count;
    }

    public void insert(Collection<Integer> input) {
        for (Integer i : input) {
            insert(i);
        }
    }

    public void insert(int key) {
        if (root == null) { // case 1:  tree is empty
            root = new Node(key, null);
            root.sortNode();
            height++;
            size++;
            return;
        }

        // find the node that the key should to go
        Node leaf = goToLeaf(root, key); // if there is duplicate, handle it and return
        if (leaf == null) { // found dup
            return;
        }

        // now current is a leaf
        if (!leaf.isFull()) {
            leaf.addKey(key);
            size++;
            return; // current is not full, adding success
        }

        // current is full
        Result result = splitLeaf(leaf, key);
        Node child = leaf;
        Node parent = leaf.parent;
        while (true) { // handle the path from the leaf to root
            if (parent == null) { // child is root
                if (child.isFull()) {
                    Node newRoot = new Node(result.promotingKey, null);
                    newRoot.left = result.leftHandle;
                    newRoot.left.parent = newRoot;
                    newRoot.right = result.rightHandle;
                    newRoot.right.parent = newRoot;
                    root = newRoot;
                    height++;
                } else { // if root is 2 node, just add key
                    child.addKey(key);
                }
                size++;
                return;
            }

            // determine which child sent the result
            if (child == parent.left) {
                result.whichChild = 0;
            } else if (child == parent.mid) {
                result.whichChild = 1;
            } else if (child == parent.right) {
                result.whichChild = 2;
            }

            if (!parent.isFull()) { // current is not leaf nor root
                absorbResult(parent, result); // absorb the result
                size++;
                break;
            } else {
                result = fullNodeSplit(parent, result); // pass to up
                child = parent;
                parent = parent.parent;
            }
        }
    }

    public String toString() {
        List<Integer> list = new ArrayList<>();
        getList(root, list);
        return list.toString();
    }

    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        getList(root, list);
        return list;
    }

    public int get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        if (root == null) {
            throw new NoSuchElementException("empty tree");
        }

        class Thing {
            Node node;
            Integer key;
            boolean isKey = false;

            Thing(Node node) {
                this.node = node;  // node for recursive
            }

            Thing(Integer key) { // key for value
                this.key = key;
                this.isKey = true;
            }
        }

        Stack<Thing> stk = new Stack<>();
        stk.push(new Thing(root));
        while (!stk.isEmpty()) {
            Thing curThing = stk.pop();

            if (curThing.isKey) { // cur thing is key
                if (index == 1) {
                    return curThing.key;
                }
                index--;
                continue;
            }

            Node curNode = curThing.node;
            if (curNode == null) { // cur thing is key, don't worry, next round
                continue;
            }

            if (curNode.isFull()) { // for 3 node
                if (curNode.right != null) {
                    stk.push(new Thing(curNode.right)); // push right
                }
                stk.push(new Thing(curNode.large));  // push large
                if (curNode.mid != null) {
                    stk.push(new Thing(curNode.mid)); // push mid
                }
                stk.push(new Thing(curNode.small)); // push small
                if (curNode.left != null) {
                    stk.push(new Thing(curNode.left)); // push left
                }
            } else { // for 2 node
                if (curNode.right != null) {
                    stk.push(new Thing(curNode.right)); // push right
                }
                stk.push(new Thing(curNode.small)); // push small
                if (curNode.left != null) {
                    stk.push(new Thing(curNode.left));// push left
                }
            }
        }
        return -1; // should not be reach
    }

    private Node findTheNode(int key) {
        if (root == null) {
            return null;
        }
        Node current = root;
        while (current != null) {
            if (current.isFull()) { // 3 node
                if (current.small == key || current.large == key) {
                    return current; // found it , return
                }
                if (key < current.small) {
                    current = current.left; // go to left
                } else if (key > current.large) {
                    current = current.right; // go to right
                } else {
                    current = current.mid; // go to mid
                }

            } else { // 2 node
                if (current.small == key) {
                    return current; // found it , return
                }
                if (key < current.small) {
                    current = current.left; // go to left
                } else {
                    current = current.right; // go to right
                }
            }
        }
        return null; // can not find
    }

    private Result splitLeaf(Node node, Integer key) {
        Result result = new Result(); // create result

        result.promotingKey = node.reOrderKeys(key);
        Node left = new Node(node.small, null);
        Node right = new Node(node.large, null);

        result.leftHandle = left;
        result.rightHandle = right;

        return result;
    }

    private void absorbResult(Node node, Result result) {
        node.addKey(result.promotingKey);
        if (result.whichChild == 0) { // node is left child
            node.mid = result.rightHandle;
            node.left = result.leftHandle;
        } else if (result.whichChild == 2) { // node is right child
            node.mid = result.leftHandle;
            node.right = result.rightHandle;
        }
        node.left.parent = node;
        node.mid.parent = node;
        node.right.parent = node;
    }

    private Result fullNodeSplit(Node node, Result result) {
        Result resultToUp = new Result();
        Integer middleKey = node.reOrderKeys(result.promotingKey);
        Node left = new Node(node.small, null);
        Node right = new Node(node.large, null);
        if (result.whichChild == 0) { // if result came from left child
            // link left
            left.left = result.leftHandle;
            left.left.parent = left;
            left.right = result.rightHandle;
            left.right.parent = left;
            // link right
            right.left = node.mid;
            right.left.parent = right;
            right.right = node.right;
            right.right.parent = right;

        } else if (result.whichChild == 1) { // if result came from middle child
            // link left
            left.left = node.left;
            left.left.parent = left;
            left.right = result.leftHandle;
            left.right.parent = left;
            // link right
            right.right = node.right;
            right.right.parent = right;
            right.left = result.rightHandle;
            right.left.parent = right;

        } else if (result.whichChild == 2) { // if result came from right child
            // link left
            left.left = node.left;
            left.left.parent = left;
            left.right = node.mid;
            left.right.parent = left;
            // link right
            right.right = result.rightHandle;
            right.right.parent = right;
            right.left = result.leftHandle;
            right.left.parent = right;
        }

        // packing result to return to node's parent
        resultToUp.promotingKey = middleKey;
        resultToUp.leftHandle = left;
        resultToUp.rightHandle = right;
        return resultToUp;
    }

    private Node goToLeaf(Node current, int key) { // return null is fail to reach leaf
        while (!current.isLeaf()) {
            if (duplicateKey(current, key)) {
                return null; // find dup
            }
            if (current.isFull()) { // 3 node
                if (key < current.small) {
                    current = current.left; // go to left
                } else if (key > current.large) { // go to right
                    current = current.right;
                } else {
                    current = current.mid; // go to mid
                }
            } else { // 2 node
                if (key < current.small) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
        }

        // now current is leaf, check if is dup again
        return duplicateKey(current, key) ? null : current; // if it is true, current reached leaf
    }

    private boolean duplicateKey(Node current, int key) {
        if (current.small != null && current.small == key) {
            return true; // small is dup
        }
        if (current.large != null && current.large == key) {
            return true; // large is dup
        }
        return false; // return true if there is no dup key
    }

    private void getList(Node node, List<Integer> list) {
        if (node == null) return;
        if (!node.isFull()) { // 2 node
            if (node.left != null) getList(node.left, list);
            addKeyToList(node.small, list);
            if (node.right != null) getList(node.right, list);
        } else { // 3 node
            if (node.left != null) getList(node.left, list);
            addKeyToList(node.small, list);
            if (node.mid != null) getList(node.mid, list);
            addKeyToList(node.large, list);
            if (node.right != null) getList(node.right, list);
        }
    }

    private void addKeyToList(Integer key, List<Integer> list) {
        if (key == null) return;
        list.add(key);
    }

}
