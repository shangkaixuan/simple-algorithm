package com.simple.algorithm.sort.stategy;

/**
 * 算法接口
 *
 * @author srh
 * @date 2019/10/25
 **/
public interface SortAlgorithm {

    /**
     * 排序算法
     *
     * @param arr   原数组
     * @param begin 起始位置
     * @param end   结束位置
     * @param asc   是否升序
     * @return 排序后的数组
     */
    int[] sort(int[] arr, int begin, int end, boolean asc);

}
