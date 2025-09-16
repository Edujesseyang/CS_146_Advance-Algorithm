package Sorting.QuickSort;

public class QuickSort {
    public static void main(String[] args) {

    }

    private static void quickSort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return;
        }

        int separation = partition(arr, begin, end);

        quickSort(arr, begin, separation);
        quickSort(arr, separation + 1, end);
    }

    private static int partition(int[] arr, int first, int last) {
        int pivot = findAPivot(arr, first, last);
        swap(arr, pivot, last);

        int separator = first;
        for (int i = first; i < last - 1; i++) {
            if (arr[i] < arr[last]) {
                swap(arr, i, separator);
                separator++;
            }
        }
        swap(arr, separator, last);
        return separator;

    }

    private static void swap(int[] arr, int from, int to) {
        int temp = arr[to];
        arr[to] = arr[from];
        arr[from] = temp;
    }

    private static int findAPivot(int[] arr, int first, int last) {


    }
}
