package Sorting.QuickSort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {

        int[] test1 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        System.out.println(Arrays.toString(test1));
        quickSort(test1, 0, 9);
        System.out.println(Arrays.toString(test1));


    }

    private static void quickSort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return; // base case
        }

        int separation = partition(arr, begin, end); // find a separating point

        quickSort(arr, begin, separation - 1); // quicksort left half
        quickSort(arr, separation + 1, end); // quicksort right half
    }

    private static int partition(int[] arr, int first, int last) {
        int pivot = findAPivot(arr, first, last); // find a pivot
        swap(arr, pivot, last); // swap the pivot to the end

        int separator = first; // a pointer holding the position for pivot
        for (int i = first; i < last; i++) { // i pointer moving to the end
            if (arr[i] < arr[last]) { // if i smaller than pivot:
                swap(arr, i, separator); // swap i and separator,
                separator++; // move the separator one right
            }
        }
        swap(arr, separator, last); // move the pivot to the spot where it supposes to be
        return separator;
    }

    // there are many ways to find the pivot point, here is just simply grab the middle
    private static int findAPivot(int[] arr, int first, int last) {
        return first + (last - first) / 2;
    }

    private static void swap(int[] arr, int from, int to) {
        int temp = arr[to];
        arr[to] = arr[from];
        arr[from] = temp;
    }
}
