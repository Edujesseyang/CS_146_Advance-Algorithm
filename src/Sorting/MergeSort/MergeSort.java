package Sorting.MergeSort;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(5, 3, 7, 9, 1, 3, 6, 5, 7, 2));
        mergeSort(test1, 0, test1.size() - 1);
        System.out.println(test1);
    }

    private static void mergeSort(ArrayList<Integer> targetList, int start, int end) {
        if (start >= end) {
            return;                                  // base case: stop the recursion
        }
        int mid = start + (end - start) / 2;         // find the mid-point
        mergeSort(targetList, start, mid);           // sort left side
        mergeSort(targetList, mid + 1, end);    // sort right side

        merge(targetList, start, mid, end);          // merge both sorted sides
    }

    private static void merge(ArrayList<Integer> targetList, int start, int mid, int end) {
        ArrayList<Integer> temp = new ArrayList<>(end - start + 1);     // temp list

        int s = start, m = mid + 1;                         // init iterating pointers: first one for both side
        while (s <= mid && m <= end) {                      // stop when any side has done
            if (targetList.get(s) <= targetList.get(m)) {   // compare elements of both side, and
                temp.add(targetList.get(s++));              // keep putting the smaller one in temp list
            } else {
                temp.add(targetList.get(m++));
            }
        }

        while (s <= mid) {                                  // if left side has left over, copy all of them into temp
            temp.add(targetList.get(s++));
        }

        while (m <= end) {                                  // if right side has left over, copy all of them into temp
            temp.add(targetList.get(m++));
        }

        for (int i = 0; i < temp.size(); i++) {             // copy the temp back to origin segment
            targetList.set(start + i, temp.get(i));
        }
    }
}
