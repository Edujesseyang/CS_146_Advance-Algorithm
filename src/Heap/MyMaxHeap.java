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

    private void swap(ArrayList<Integer> list, int i, int j) {
        int tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    public int size() {
        return list.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int nodesPerLevel = 1;
        while (i < list.size()) {
            while (i < list.size() && i + 1 < nodesPerLevel) {
                sb.append(list.get(i)).append(" ");
                i++;
            }
            nodesPerLevel *= 2;
            sb.append("\n");
        }
        return sb.toString();
    }


}
