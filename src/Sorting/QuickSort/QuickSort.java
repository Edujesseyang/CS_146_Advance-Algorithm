package Sorting.QuickSort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {

        int[] test1 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        System.out.println(Arrays.toString(test1));
        quickSort(test1, 0, test1.length - 1);
        System.out.println(Arrays.toString(test1));

        int[] test2 = {75, 62, 32, 53, 99, 90, 23, 81, 31, 55,
                13, 80, 19, 3, 67, 95, 8, 11, 88, 44,
                84, 25, 86, 98, 68, 89, 0, 76, 14, 24, 3, 34, 6, 1, 5, 56, 3, 1,
                12, 57, 51, 21, 22, 15, 52, 77, 30, 41, 46, 34, 57, 234, 76,
                70, 20, 38, 83, 58, 2, 42, 63, 54, 40, 34, 45, 57, 67, 86, 45, 2,
                29, 7, 73, 78, 39, 48, 59, 87, 10, 16, 4, 4, 1, 4, 6, 7, 7,
                85, 46, 71, 93, 35, 64, 97, 65, 49, 74,
                60, 28, 36, 4, 9, 43, 61, 47, 56, 96,
                69, 66, 82, 18, 50, 17, 1, 72, 26, 27,
                45, 5, 6, 94, 92, 79, 33, 91, 37, 34, 45, 57, 343, 7682, 52, -1};

        System.out.println(Arrays.toString(test2));
        quickSort(test2, 0, test2.length - 1);
        System.out.println(Arrays.toString(test2));


    }

    private static void quickSort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return; // base case
        }

        if (end - begin <= 10) { // if the section is too small, use insertSort
            insertSort(arr, begin, end);
            return;
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

    // there are 3 ways to find the pivot point:
    //      1. Lomuto: use the last element.
    //      2. Hoare: use the middle element.
    //      3. 3 randoms: find 3 randoms, use the value that in the middle.
    private static int findAPivot(int[] arr, int first, int last) {
        if (last - first > 20) {
            return medianOfThreeRandoms(arr, first, last);
        }

        return first + (last - first) / 2;
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[b];
        arr[b] = arr[a];
        arr[a] = temp;
    }

    /**
     * randomly pick 3 elements, find the mid one for the pivot
     */
    private static int medianOfThreeRandoms(int[] arr, int first, int last) {
        int midInd = first + (last - first) / 2;
        int firstNum = arr[first];
        int lastNum = arr[last];
        int midNum = arr[midInd];

        if ((firstNum > lastNum && firstNum < midNum) || (firstNum < lastNum && firstNum > midNum)) {
            return first;
        }
        if ((lastNum > firstNum && lastNum < midNum) || (lastNum < firstNum && lastNum > midNum)) {
            return last;
        }

        return midInd;
    }

    private static void insertSort(int[] arr, int begin, int end) {
        for (int i = begin + 1; i < end; i++) { // do the shifting from the second one
            shiftBigToRight(arr, i, arr[i]);
        }
    }

    private static void shiftBigToRight(int[] arr, int currentInd, int currentVal) {
        int prevOfCorrectPlace = currentInd - 1;  // check all element in the front of the current
        while (prevOfCorrectPlace >= 0 && arr[prevOfCorrectPlace] > currentVal) { // if current is smaller,
            arr[prevOfCorrectPlace + 1] = arr[prevOfCorrectPlace--];  // shif them to right
        }
        arr[prevOfCorrectPlace + 1] = currentVal; // insert the current to the right place
    }
}


