package Heap;

import java.util.ArrayList;

public class MyMaxHeap {
    private ArrayList<Integer> list;

    public MyMaxHeap() {
        list = new ArrayList<>();
    }

    public MyMaxHeap(ArrayList<Integer> input) {
        // new a list
        list = new ArrayList<>();
        if (input.isEmpty()) {
            return;
        }

        // add every element to list from input, check if its father is smaller
        for (int i = 0; i < input.size(); i++) {
            list.add(input.get(i));
            int childInd = i;
            // while father is smaller, keeping shifting new add to the root
            while (childInd > 0) { // use childInd for to stop
                int fatherInd = (childInd - 1) / 2;

                if (list.get(fatherInd) >= list.get(childInd)) {
                    break; // when father > child, break
                }

                swap(list, fatherInd, childInd);
                childInd = fatherInd; // child is new father now
            }
        }
    }

    public MyMaxHeap(ArrayList<Integer>... inputs) {
        // create list
        list = new ArrayList<>();
        for (ArrayList<Integer> input : inputs) {
            list.addAll(input);
        }

        heapify();
    }


    private void heapify() {
        int size = list.size(); // get Size
        for (int i = getParentInd(size - 1); i >= 0; i--) {// loop backward from the father of last element
            int current = i;
            while (true) {
                int leftInd = getLeftChildInd(current); // def left child index
                int rightInd = getRightChildInd(current); // def right child index
                int largerChildInd = current; // assume father is largest
                if (leftInd < size && list.get(leftInd) > list.get(largerChildInd)) {
                    largerChildInd = leftInd; // compare father with left, update largest is left is larger
                }
                if (rightInd < size && list.get(rightInd) > list.get(largerChildInd)) {
                    largerChildInd = rightInd; // compare father with right, update largest is left is larger
                }

                if (largerChildInd == current) { // if father is still the largest, go ahead to next iteration
                    break;
                }

                swap(list, current, largerChildInd); // swap father and larger child
                current = largerChildInd; // key, now the cur goes to child position, moving down to check child's children
            }
        }
    }

    private int getParentInd(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChildInd(int i) {
        return 2 * i + 1;
    }

    private int getRightChildInd(int i) {
        return 2 * i + 2;
    }

    private void swap(ArrayList<Integer> list, int i, int j) {
        int tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    public int size() {
        return list.size();
    }

    public int getHeight() {
        if (list.size() == 0) {
            return 0;
        }
        return (int) (Math.log(list.size()) / Math.log(2)) + 1;
    }

    public int getMaxVal() {
        return list.get(0);
    }

    public void insert(int num) {
        list.add(num); // add to the end first
        int currentInd = list.size() - 1; //
        int parentInd = getParentInd(currentInd);

        while (parentInd >= 0) { // when there is parent
            if (list.get(currentInd) <= list.get(parentInd)) {
                return; // if cur smaller or equal to its father, return
            }
            swap(list, currentInd, parentInd); // if not, swap them
            currentInd = parentInd; // cur in on its father's position
            parentInd = getParentInd(currentInd); // find new parent's index
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int nodesPerLevel = 1;
        int lvl = getHeight();
        while (i < list.size()) {
            while (i < list.size() && i + 1 < nodesPerLevel) {
                sb.append(list.get(i));
                for (int k = 0; k <= lvl; k++) {
                    sb.append("  ");
                }
                i++;
            }
            nodesPerLevel *= 2;
            sb.append("\n");
            for (int j = 0; j <= lvl; j++) {
                sb.append("  ");
            }
            lvl--;
        }
        return sb.toString();
    }

}
