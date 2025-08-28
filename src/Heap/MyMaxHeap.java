package Heap;

import java.util.ArrayList;
import java.util.List;

public class MyMaxHeap {
    private List<Integer> list;

    public MyMaxHeap() {
        list = new ArrayList<>();
    }

    public MyMaxHeap(ArrayList<Integer> input) {
        // new a list
        list = new ArrayList<>();
        list.add(input.get(0));

        // add every element to list from input, check if it father is smaller
        for (int i = 1; i < input.size(); i++) {
            list.add(input.get(i));
            int fatherInd = (i - 1) / 2;

            // while father is smaller, keeping shifting new add to the root
            while (fatherInd >= 0 && list.get(fatherInd) < list.get(i)) {
                int tmp = list.get(i);
                list.set(i, list.get(fatherInd));
                list.set(fatherInd, tmp);
                fatherInd = (fatherInd - 1) / 2;
            }
        }
    }

    public int size() {
        return list.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer integer : list) {
            sb.append(integer).append(", ");
        }
        return sb.toString();
    }


}
