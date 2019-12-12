import java.util.ArrayList;
import java.util.List;

/**
 * @program: testdemo->Vector
 * @description: 图
 * @author: muxiao
 * @create: 2019-12-11 21:02
 **/
public class Vector {

    public Vector(int index) {
        this.index = index;
    }

    private List<Vector> neighbors;

    private Integer index;
    //主节点是否遍历过
    private boolean visited;
    //子节点遍历次数
    private int times;

    public static void main(String[] args) {
        //图
        Vector A = new Vector(1);
        Vector B = new Vector(2);
        Vector C = new Vector(3);
        Vector D = new Vector(4);
        Vector E = new Vector(5);
        Vector F = new Vector(6);

        List<Vector> neighbors_A = new ArrayList<>();
        List<Vector> neighbors_B = new ArrayList<>();
        List<Vector> neighbors_C = new ArrayList<>();
        List<Vector> neighbors_D = new ArrayList<>();
        List<Vector> neighbors_E = new ArrayList<>();
        List<Vector> neighbors_F = new ArrayList<>();

        //A->B
        neighbors_A.add(B);
        //A->E
        neighbors_A.add(E);
        //B->C
        neighbors_B.add(C);
        //B->F
        //neighbors_B.add(F);
        //C->D
        neighbors_C.add(D);
        //D->F
        neighbors_D.add(F);
        //E->F
        neighbors_E.add(F);
        //F->B 这个回指造成闭环
        neighbors_F.add(B);


        A.setNeighbors(neighbors_A);
        B.setNeighbors(neighbors_B);
        C.setNeighbors(neighbors_C);
        D.setNeighbors(neighbors_D);
        E.setNeighbors(neighbors_E);
        F.setNeighbors(neighbors_F);
        //以上代码构造ABCDEF图结构（你画的那个图结构）

        boolean exist = judge_m(A);
        if(!exist){
            System.out.println("不存在闭环！！！");
        }
    }


    /**
     * 遍历图中所有主节点元素
     *
     * @param G
     */
    private static boolean judge_m(Vector G) {
        //保存遍历过的主元素
        List<Integer> p = new ArrayList<>();
        if (!G.getVisited()) {
            p.add(G.getIndex());
            G.setVisited(true);
            if (judge_s(G.getNeighbors(), p)) {
                System.out.println("存在闭环！！！");
                return true;
            }
            List<Vector> neighbors = G.getNeighbors();
            if (neighbors != null && neighbors.size() > 0) {
                for (Vector v : neighbors) {
                    judge_m(v);
                }
            }
        }
        return false;
    }

    /**
     * 递归每个主元素子节点下的子节点，有指向主元素则说明存在闭环
     *
     * @param neighbors
     * @param p
     * @return
     */
    private static boolean judge_s(List<Vector> neighbors, List<Integer> p) {
        if (neighbors != null && neighbors.size() > 0) {
            List<Integer> s = new ArrayList<>();
            for (Vector v : neighbors) {
                s.add(v.getIndex());
                //子节点元素回指向主元素，则存在闭环
                if (p.contains(v.getIndex())) {
                    return true;
                } else {
                    //S包含所有父级节点元素
                    s.addAll(p);
                    if (judge_s(v.getNeighbors(), s)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<Vector> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Vector> neighbors) {
        this.neighbors = neighbors;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

}
