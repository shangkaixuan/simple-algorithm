package com.simple.algorithm.sort.stategy;

/**
 * 快速排序算法
 *
 * @author srh
 * @date 2019/10/25
 **/
public class QuickSortAlgorithm implements SortAlgorithm {

    @Override
    public int[] sort(int[] arr, int begin, int end, boolean asc) {
        if (end > begin) {
            int pivotIndex = partition(arr, begin, end, asc);
            sort(arr, begin, pivotIndex - 1, asc);
            sort(arr, pivotIndex + 1, end, asc);
        }
        return arr;
    }

    private static int partition(int[] arr, int begin, int end, boolean asc) {
        //  choose the first elements as the pivot
        int pivot = arr[begin];
        // forward search
        int low = begin + 1;
        // backward search
        int high = end;

        while (high > low) {
            // search forward from left
            while (low <= high && arr[low] <= pivot) {
                low++;
            }

            // search backward from right
            while (low <= high && arr[high] > pivot) {
                high--;
            }

            // swap two elements in the arr
            if (high > low) {
                int temp = arr[high];
                arr[high] = arr[low];
                arr[low] = temp;
            }
        }

        //  寻找下一个对比数
        while (high > begin && arr[high] >= pivot) {
            high--;
        }
        // swap pivot with arr[hight]
        if (pivot > arr[high]) {
            arr[begin] = arr[high];
            arr[high] = pivot;
            return high;
        } else {
            return begin;
        }
    }

}
