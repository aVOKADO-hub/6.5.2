package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.IntConsumer;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class Main {
    static class MyArray{
        private final int SIZE=1000;
        int[]intArr=new int[SIZE];
        public MyArray() {
            fill();
        }
        void fill(){
            int count=0;
            for (int item:intArr){
                item = count++;
            }
        }
        public int[] getIntArr() {
            return intArr;
        }
    }
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        int[]arr = new MyArray().getIntArr();
        Callable<int[]> taskSelectionSort = ()->selectionSort(arr);
        Callable<int[]> taskBubbleSort=()->bubbleSort(arr);
        Callable<int[]> taskHeapSort=()->heapSort(arr);
        List<Callable<int[]>> tasks = List.of(taskSelectionSort,taskBubbleSort,taskHeapSort);
        try {
            service.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
   static int[] selectionSort(int[]arr)
    {
        long start = System.currentTimeMillis();
        int pos;
        int temp;
        for (int i = 0; i < arr.length; i++)
        {
            pos = i;
            for (int j = i+1; j < arr.length; j++)
            {
                if (arr[j] < arr[pos])                  //find the index of the minimum element
                {
                    pos = j;
                }
            }
            temp = arr[pos];            //swap the current element with the minimum element
            arr[pos] = arr[i];
            arr[i] = temp;
        }
        System.out.println("Selection sorting algorithm "+(System.currentTimeMillis()-start));
        return arr;
    }
    static int[] bubbleSort(int arr[])
    { long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i]>arr[j]){
                    var tmp = arr[i];
                    arr[i]= arr[j];
                    arr[j]=tmp;
                }
            }
        }
        System.out.println("Bubble sorting algorithm "+(System.currentTimeMillis()-start));
        return arr;
    }
    static int[]heapSort(int arr[])
    {
        long start = System.currentTimeMillis();
        int temp;

        for (int i = arr.length / 2 - 1; i >= 0; i--)                //build the heap
        {
            heapify(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i > 0; i--)                            //extract elements from the heap
        {
            temp = arr[0];                                                  //move current root to end (since it is the largest)
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);                                             //recall heapify to rebuild heap for the remaining elements
        }
        System.out.println("Heap sorting algorithm "+(System.currentTimeMillis()-start));
        return arr;
    }
    static void heapify(int arr[], int n, int i)
    {
        int MAX = i; // Initialize largest as root
        int left = 2 * i + 1; //index of the left child of ith node = 2*i + 1
        int right = 2 * i + 2; //index of the right child of ith node  = 2*i + 2
        int temp;

        if (left < n && arr[left] > arr[MAX])            //check if the left child of the root is larger than the root
        {
            MAX = left;
        }

        if (right < n && arr[right] > arr[MAX])            //check if the right child of the root is larger than the root
        {
            MAX = right;
        }

        if (MAX != i)
        {                                               //repeat the procedure for finding the largest element in the heap
            temp = arr[i];
            arr[i] = arr[MAX];
            arr[MAX] = temp;
            heapify(arr, n, MAX);
        }
    }
}