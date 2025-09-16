package Sorting.MergeSort;

import java.util.Arrays;

public class BottomUpMergeSort {

    public static void main(String[] args) {
        int[] test1 = {4, 3, 6, 8, 9, 2, 1, 0, 6, 3};
        System.out.println(Arrays.toString(test1));

        btmUpMergeSort(test1);
        System.out.println(Arrays.toString(test1));


    }

    private static void btmUpMergeSort(int[] arr) {
        int size = arr.length;
        int[] temp = new int[size]; // create a workspace

        for (int step = 1; step < size; step *= 2) { // step is half of the size of current merging section
            for (int start = 0; start < size - step; start += step * 2) { // start is starting index of current merging section
                int mid = start + step - 1;  // mid-point between start and end
                int end = Math.min(size - 1, start + step * 2 - 1); // end is sart + double step - 1, or the last element
                merge(arr, temp, start, mid, end);
            }
        }
    }

    private static void merge(int[] arr, int[] temp, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int count = start; // use the entire temp arr to merge, but only use the same section

        while (i <= mid && j <= end) {
            if (arr[i] <= arr[j]) {
                temp[count++] = arr[i++];
            } else {
                temp[count++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[count++] = arr[i++];
        }

        while (j <= end) {
            temp[count++] = arr[j++];
        }

        for (int k = start; k <= end; k++) {
            arr[k] = temp[k]; // copy the temp back to origin
        }
    }
}
