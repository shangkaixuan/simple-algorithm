package com.simple.algorithm.sort.tmp;

/**
 * 几大排序的工具类
 *
 * @author srh
 * @date 2019-09-28
 */
public class SortUtil {

    private SortUtil() {
    }

    /**
     * 冒泡排序
     */
    public static void bubblingSort(int[] arr, int begin, int end, boolean asc) {
        for (int i = begin + 1; i < end; i++) {
            for (int k = begin; k < end - i; k++) {
                if (asc == (arr[k] - arr[k + 1] > 0)) {
                    int temp = arr[k];
                    arr[k] = arr[k + 1];
                    arr[k + 1] = temp;
                }
            }
        }
    }

    /**
     * 归并排序
     */
    public static void mergeSort(int[] arr, int begin, int end, boolean asc) {
        if (begin >= end) {
            return;
        }
        int mid = (begin + end) / 2;
        mergeSort(arr, begin, mid, asc);
        mergeSort(arr, mid + 1, end, asc);
        merge(arr, begin, mid, end, asc);
    }

    private static void merge(int[] arr, int begin, int mid, int end, boolean asc) {
        int[] temp = new int[end - begin];
        int k = 0;
        int i = begin;
        int j = mid + 1;
        while (i <= mid && j < end) {
            if (asc == (arr[i] - arr[j] < 0)) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j < end) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, begin, temp.length);
    }

    /**
     * 快速排序
     */
    public static void quickSort(int[] arr, int begin, int end, boolean asc) {
        if (end > begin) {
            int pivotIndex = partition(arr, begin, end, asc);
            quickSort(arr, begin, pivotIndex - 1, asc);
            quickSort(arr, pivotIndex + 1,end ,asc);
        }
    }

    private static int partition(int[] arr, int begin, int end, boolean asc) {
        int pivot = arr[begin]; //  choose the first elements as the pivot
        int low = begin + 1;    // forward search
        int high = end;         // backward search

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