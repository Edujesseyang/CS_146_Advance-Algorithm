package Heap.MyHeap;

import java.util.ArrayList;

public class MyHeap {
    ArrayList<Integer> heapList;

    public MyHeap() {
        heapList = new ArrayList<>();
    }

    public MyHeap(ArrayList<Integer>... lists) {
        heapList = new ArrayList<>();

        for (ArrayList<Integer> list : lists) {
            heapList.addAll(list);
        }

        heapify();
    }

    public int size() {
        return this.heapList.size();
    }

    public int getMax() {
        return this.heapList.get(0);
    }

    public String toString() {
        return this.heapList.toString();
    }

    


    // helper methods:

    private void heapify() {
        for (int i = size() / 2; i >= 0; i--) {
            shiftDown(i, size());
        }
    }

    private void shiftUp(int i) {
        while (i > 0) {
            int parentInd = getParent(i);
            if (parentInd < 0 || heapList.get(parentInd) > heapList.get(i)) {
                return;
            }
            swap(parentInd, i);
            i = parentInd;
        }
    }

    private void shiftDown(int i, int endAt) {
        while (i < endAt) {
            int leftChildInd = getLeftChild(i);
            int rightChildInd = getRightChild(i);
            int largest = i;

            if (leftChildInd < endAt && heapList.get(largest) < heapList.get(leftChildInd)) {
                largest = leftChildInd;
            }
            if (rightChildInd < endAt && heapList.get(largest) < heapList.get(rightChildInd)) {
                largest = rightChildInd;
            }

            if (largest == i) {
                return;
            }

            swap(largest, i);
            i = largest;
        }
    }

    private void swap(int a, int b) {
        int tmp = heapList.get(a);
        heapList.set(a, heapList.get(b));
        heapList.set(b, tmp);
    }

    private int getParent(int i) {
        return (i - 1) / 2;
    }

    private int getLeftChild(int i) {
        return i * 2 + 1;
    }

    private int getRightChild(int i) {
        return i * 2 + 2;
    }


}
