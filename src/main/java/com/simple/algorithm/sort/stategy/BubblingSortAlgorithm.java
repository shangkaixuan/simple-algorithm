package com.simple.algorithm.sort.stategy;

/**
 *  冒泡排序
 *
 * @author srh
 * @date 2019/10/25
 **/
public class BubblingSortAlgorithm implements SortAlgorithm {

    @Override
    public int[] sort(int[] arr, int begin, int end, boolean asc) {
        for (int i = begin + 1; i < end; i++) {
            for (int k = begin; k < end - i; k++) {
                if (asc == (arr[k] - arr[k + 1] > 0)) {
                    int temp = arr[k];
                    arr[k] = arr[k + 1];
                    arr[k + 1] = temp;
                }
            }
        }
        return arr;
    }

}
