package weightedUnionFind;

/**
 * Created by Makaronodentro on 27/11/2015.
 */
import java.util.Scanner;

public class UnionFind{

    private int[] parent;
    private int[] rank;
    private int count;

    // Constructor
    public UnionFind(int _count) {
        if (_count < 0) throw new IllegalArgumentException();
        count = _count;
        parent = new int[count];
        rank = new int[count];
        for(int i = 0; i < count; i++){
            parent[i] = i;
            rank[i] = 1;
        }
    }

    // Returns count
    public int getCount(){
        return count;
    }

    // Validate that p is a valid index
    private void validate(int p){
        int N = parent.length;
        if (p < 0 || p > N){
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));
        }
    }

    // Returns the root of component p
    public int root(int p){
        validate(p);
        while(p != parent[p]){
            parent[p] = parent[parent[p]]; // path compression
            p = parent[p];
        }
        return p;
    }

    // Connected?
    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){
        validate(p);
        validate(q);
        int rootP = root(p);
        int rootQ = root(q);

        if(rootP == rootQ){
            return;
        }

        if(rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if(rank[rootQ] < rank[rootP]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }


    /*
    ** Main
    */

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        //int i = scan.nextInt();

        int N = scan.nextInt();
        UnionFind uf = new UnionFind(N);

        while(scan.hasNextInt()){
            int p = scan.nextInt();
            int q = scan.nextInt();
            if(uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }

        System.out.println(uf.getCount() + " components");
    }
}