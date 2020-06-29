package union_find;


public class weightedQuickUnion {
    private int[] id;
    private int[] sz;

    public weightedQuickUnion(int N) {
        this.id = new int[N];
        this.sz = new int[N];
        for (int i = 0; i < N; i++) {
            this.id[i] = i;
            this.sz[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pRoot = root(p); // find the top root of p
        int qRoot = root(q); // find the top root of q
        if (pRoot == qRoot)
            return;
        if (this.sz[pRoot] < this.sz[qRoot]) { // if p is smaller than q, attach to q
            this.id[pRoot] = qRoot;
            sz[qRoot] += pRoot;
        } else {
            this.id[qRoot] = pRoot;
            sz[pRoot] += qRoot;
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q); // see if the top roots of p and q are equal
    }

    public int root(int i) {
        while (i != id[i]) // chase parent pointers until the pointer value is equal to the point (indicating top of root)
            i = id[i];
        return i;
    }

    public static void main(String[] args) {
        int N = 10;
        int[] pArray = new int[]{4, 3, 6, 9, 2, 8, 5, 7, 6, 1, 6};
        int[] qArray = new int[]{3, 8, 5, 4, 1, 9, 0, 2, 1, 0, 7};
        weightedQuickUnion uf = new weightedQuickUnion(N);
        for (int i = 0; i < 11; i++) {
            int p = pArray[i];
            int q = qArray[i];
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.println(p + " " + q);
            }
        }
    }
}
