package com.simple.algorithm.sort.stategy;

/**
 * 策略模式
 *
 * @author srh
 * @date 2019/10/25
 **/
public class SortDictionary {

    private SortAlgorithm sortAlgorithm;

    public SortDictionary(SortAlgorithm sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public int[] sort(int[] arr, int begin, int end, boolean asc) {
        return sortAlgorithm.sort(arr, begin, end, asc);
    }
}
