package com.simple.algorithm.sort.stategy;

import static com.simple.algorithm.sort.tmp.SortUtil.mergeSort;

/**
 * 归并排序算法
 *
 * @author srh
 * @date 2019/10/25
 **/
public class MergeSortAlgorithm implements SortAlgorithm {

    @Override
    public int[] sort(int[] arr, int begin, int end, boolean asc) {

        if (begin >= end) {
            return arr;
        }
        int mid = (begin + end) / 2;
        mergeSort(arr, begin, mid, asc);
        mergeSort(arr, mid + 1, end, asc);
        merge(arr, begin, mid, end, asc);
        return arr;
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
}
