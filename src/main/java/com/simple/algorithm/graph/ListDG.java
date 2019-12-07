package com.simple.algorithm.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * directed acyclic graph 有向图的拓扑排序
 * 
 * @author srh
 * @date 2019/12/08
 **/
public class ListDG {

    /**
     * 边
     */
    private static class Edge {
        /**
         * 顶点的角标
         */
        int ivex;
        /**
         * 下一条弧d指针
         */
        Edge nextEdge;

        Edge(int ivex, Edge nextEdge) {
            this.ivex = ivex;
            this.nextEdge = nextEdge;
        }
    }

    /**
     * 顶点
     */
    private static class Vertex {
        /**
         * 顶点的标示
         */
        char data;
        /**
         * 该顶点的第一条弧
         */
        Edge firstEdge;

        Vertex(char data, Edge edge) {
            this.data = data;
            this.firstEdge = edge;
        }
    }

    /**
     * 顶点数组
     */
    private List<Vertex> mVexs;

    /**
     * 初始化顶点
     * @param vexs  顶点的顺序
     * @param edges 边的结构
     */
    public ListDG(char[] vexs, char[][] edges) {
        /*
         * 初始化顶点
         */
        int vlen = vexs.length;
        int elen = edges.length;
        mVexs = new ArrayList<>(vlen);
        for (char vex : vexs) {
            Vertex vertex = new Vertex(vex, null);
            mVexs.add(vertex);
        }
        /*
         * 初始化顶点中的边
         */
        for (char[] edge : edges) {
            // 读取每条边的起始顶点与结束顶点
            char beginData = edge[0];
            char endData = edge[1];
            // 读取每个起始顶点与结束顶点的角标
            int beginIndex = getPosition(beginData);
            int endIndex = getPosition(endData);
            // 创建第一条边
            Edge firstEdge = new Edge(endIndex, null);
            // 连接边与顶点
            if (mVexs.get(beginIndex).firstEdge == null) {
                mVexs.get(beginIndex).firstEdge = firstEdge;
            } else {
                linkLast(mVexs.get(beginIndex).firstEdge, firstEdge);
            }
        }
    }

    /**
     * 说明vertex顶点的边有多条，将顶点的另一条边拼在已有链表的最后
     * @param vEdge 顶点的边
     * @param lastEdge 顶点的另一条边
     */
    private void linkLast(Edge vEdge, Edge lastEdge) {
        Edge node = vEdge;
        while (node.nextEdge != null) {
            node = node.nextEdge;
        }
        node.nextEdge = lastEdge;
    }

    /**
     * 返回字符在所有顶点中的角标
     * @param data  字符内容
     * @return  index
     */
    private int getPosition(char data) {
        for (int i = 0; i < mVexs.size(); i++) {
            if (mVexs.get(i).data == data) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 输出图的结构
     */
    public void print() {
        System.out.print("=== List Graph: \n");

        for (int i = 0; i < mVexs.size(); i++) {
            System.out.printf("%d(%c)：", i, mVexs.get(i).data);
            Edge node = mVexs.get(i).firstEdge;
            while (node != null) {
                System.out.printf("%d(%c) ", node.ivex, mVexs.get(node.ivex).data);
                node = node.nextEdge;
            }
            System.out.print("\n");
        }
    }

    /**
     * 广度遍历
     */
    public void bsf() {
        // 队列的头尾角标
        int head,tail;
        head = tail = 0;
        // 辅助队列
        int[] queue = new int[mVexs.size()];
        // 初始化访问标记
        boolean[] visited = new boolean[mVexs.size()];
        for (int i = 0; i < mVexs.size(); i++) {
            visited[i] = false;
        }

        System.out.print("=== BSF: ");
        for (int i = 0; i < mVexs.size(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.printf("%c ", mVexs.get(i).data);
                // 入队列
                queue[tail++] = i;
            }

            while (head != tail) {
                // 出队列
                int iedge = queue[head++];
                Edge node = mVexs.get(iedge).firstEdge;
                while (node != null) {
                    int ivex = node.ivex;
                    if (!visited[ivex]) {
                        visited[ivex] = true;
                        System.out.printf("%c ", mVexs.get(ivex).data);
                        queue[tail++] = ivex;
                    }
                    node = node.nextEdge;
                }
            }
        }
        System.out.print("\n");
    }

    /**
     * 深度遍历
     */
    public void dfs() {
        // 初始化访问标记
        boolean[] visited = new boolean[mVexs.size()];
        for (int i = 0; i < mVexs.size(); i++) {
            visited[i] = false;
        }

        System.out.print("=== DFS: ");
        for (int i = 0; i < mVexs.size(); i++) {
            if (!visited[i]) {
                dfs(i, visited);
            }
        }
        System.out.print("\n");
    }

    /**
     * 深度遍历
     * @param i index
     * @param visited   visited数组
     */
    private void dfs(int i, boolean[] visited) {
        Edge node;

        visited[i] = true;
        System.out.printf("%c ", mVexs.get(i).data);
        node = mVexs.get(i).firstEdge;
        while (node != null) {
            if (!visited[node.ivex]) {
                dfs(node.ivex, visited);
            }
            node = node.nextEdge;
        }
    }

    /**
     * 拓扑排序
     * @return
     *      0 成功
     *      1 失败（有回路）
     */
    public int topologicalSort() {
        int topIndex = 0;
        // 入度数组
        int[] ins = new int[mVexs.size()];
        // 拓扑排序结果数组
        char[] tops = new char[mVexs.size()];
        // 辅助队列进行广度遍历
        Queue<Integer> queue = new LinkedList<Integer>();

        // 统计每个顶点的入度
        for (int i = 0; i < mVexs.size(); i++) {
            Edge node = mVexs.get(i).firstEdge;
            while (node != null) {
                ins[node.ivex]++;
                node = node.nextEdge;
            }
        }

        // 将所有入度为0的顶点添加到队列中
        for (int i = 0; i < mVexs.size(); i++) {
            if (ins[i] == 0) {
                queue.offer(i);
            }
        }

        // 出队列
        while (!queue.isEmpty()) {
            // 顶点的序号
            int vIndex = queue.poll();
            // tops 是排序结果
            tops[topIndex++] = mVexs.get(vIndex).data;

            // 遍历子节点，并将子节点的入度减1
            Edge node = mVexs.get(vIndex).firstEdge;
            while (node != null) {
                ins[node.ivex]--;
                if (ins[node.ivex] == 0) {
                    queue.offer(node.ivex);
                }
                node = node.nextEdge;
            }
        }

        // 如果数量不一致，说明存在回路
        if (topIndex != mVexs.size()) {
            System.out.println("graph has a cycle");
            return 1;
        }
        // 打印排序结果
        System.out.print("=== TopSort: ");
        for (int i = 0; i < mVexs.size(); i++) {
            System.out.printf("%c ", tops[i]);
        }
        System.out.print("\n");
        return 0;
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][] {
                {'A', 'B'},
                {'A', 'E'},
                {'B', 'C'},
                {'C', 'D'},
                {'C', 'F'},
                {'C', 'G'},
                {'E', 'C'}
        };

        ListDG listDG = new ListDG(vexs, edges);
        listDG.print();
        listDG.bsf();
        listDG.dfs();
        int result = listDG.topologicalSort();
        System.out.printf("=== topologicalSort result: %d", result);
        System.out.print("\n");
    }
}
