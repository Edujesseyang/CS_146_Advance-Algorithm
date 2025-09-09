package Sorting.InsertSort;

public class InsertSort {
    public static void main(String[] args) {
        int[] test1 = {5, 3, 6, 9, 7, 4, 1, 2, 8, 2, 2, 0};
        System.out.println("Test case: ");
        printArr(test1);
        System.out.println("After sort:");
        int[] sorted1 = insertSort(test1);
        printArr(sorted1);
        System.out.println("Reversed order:");
        int[] sorted2 = reverseOrder(test1);
        printArr(sorted2);
    }

    private static int[] insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // get the key
            int key = arr[i];
            // point the one in front of the key
            int j = i - 1;
            while (j >= 0 && key < arr[j]) { // default boundary j; if key is smaller than the one before
                // put the one before into key's slot, update j
                arr[j + 1] = arr[j--];
            } // this while loop will shift all numbers who are larger than the key, until the right slot

            // put the key into right slot
            arr[j + 1] = key;
        }
        return arr;
    }

    private static int[] reverseOrder(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && key > arr[j]) { // only difference is whether is key greater or smaller than the one in the front
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = key;
        }
        return arr;
    }


    private static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
    }
}
