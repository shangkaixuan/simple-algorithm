package com.simple.algorithm.graph;

import java.util.*;

/**
 * @author srh
 * @date 2019/12/08
 **/
public class DGGraph {

    /**
     * 数据节点
     */
    private static class Element {
        /**
         * id
         */
        Long id;
        /**
         * name
         */
        String name;
        /**
         * 深度
         */
        int depth;

        public Element(Long id, String name, int depth) {
            this.id = id;
            this.name = name;
            this.depth = depth;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", depth=" + depth +
                    '}';
        }
    }
    
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
        Element data;
        /**
         * 该顶点的第一条弧
         */
        Edge firstEdge;

        Vertex(Element data, Edge edge) {
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
     *
     * @param vexs  顶点的顺序
     * @param edges 边的结构
     */
    public DGGraph(Element[] vexs, Element[][] edges) {
        /*
         * 初始化顶点
         */
        int vlen = vexs.length;
        int elen = edges.length;
        mVexs = new ArrayList<>(vlen);
        for (Element vex : vexs) {
            Vertex vertex = new Vertex(vex, null);
            mVexs.add(vertex);
        }
        /*
         * 初始化顶点中的边
         */
        for (Element[] edge : edges) {
            // 读取每条边的起始顶点与结束顶点
            Element beginData = edge[0];
            Element endData = edge[1];
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
     *
     * @param vEdge    顶点的边
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
     *
     * @param data 字符内容
     * @return index
     */
    private int getPosition(Element data) {
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
            System.out.printf("%d(%s)：", i, mVexs.get(i).data.name);
            Edge node = mVexs.get(i).firstEdge;
            while (node != null) {
                System.out.printf("%d(%s) ", node.ivex, mVexs.get(node.ivex).data.name);
                node = node.nextEdge;
            }
            System.out.print("\n");
        }
    }

    /**
     * 拓扑排序
     *
     * @return 0 成功
     * 1 失败（有回路）
     */
    public int topologicalSort() {
        // 排序结果的角标
        int topIndex = 0;
        // 入度数组
        int[] ins = new int[mVexs.size()];
        // 拓扑排序结果数组
        Element[] tops = new Element[mVexs.size()];
        // 辅助队列进行广度遍历
        Queue<Integer> queue = new LinkedList<Integer>();

        // 统计每个顶点的入度
        for (int i = 0; i < mVexs.size(); i++) {
            Edge node = mVexs.get(i).firstEdge;
            while (node != null) {
                // 设置子顶点的层级深度（等于父节点的深度+1）
                mVexs.get(node.ivex).data.depth = mVexs.get(i).data.depth + 1;
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
            System.out.printf("%s ", tops[i]);
        }
        System.out.print("\n");
        return 0;
    }

    public static void main(String[] args) {
        // 初始化数据
        Element[] vexs = new Element[7];
        int data = 65;
        for (int i = 0; i < 7; i++) {
            char val = (char) (i + data);
            vexs[i] = new Element((long) (i * 10), String.valueOf(val), 1);
        }
        Element[][] edges = new Element[][]{
                {vexs[0], vexs[1]},
                {vexs[0], vexs[4]},
                {vexs[1], vexs[2]},
                {vexs[2], vexs[3]},
                {vexs[2], vexs[5]},
                {vexs[2], vexs[6]},
                {vexs[4], vexs[2]},
                // 回路测试
                // {vexs[3], vexs[0]}
        };

        DGGraph graph = new DGGraph(vexs, edges);
        graph.print();
        int result = graph.topologicalSort();
        System.out.printf("=== topologicalSort result: %d", result);
        System.out.print("\n");
    }

}
