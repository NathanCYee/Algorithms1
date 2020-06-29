package union_find;


public class quickUnion {
    private int[] id;

    public quickUnion(int N) {
        this.id = new int[N];
        for(int i = 0; i < N; i++)
            this.id[i] = i;
    }
    public void union(int p, int q) {
        int pRoot = root(p); // find the top root of p
        int qRoot = root(q); // find the top root of q
        this.id[pRoot] = qRoot;  // attach the top root of p to q (top root of p becomes a branch of q)
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q); // see if the top roots of p and q are equal
    }

    public int root(int i) {
        while(i != id[i]) // chase parent pointers until the pointer value is equal to the point (indicating top of root)
            i = id[i];
        return i;
    }

    public static void main(String[] args)
    {
        int N = 10;
        int[] pArray = new int[] {4, 3, 6, 9, 2, 8, 5, 7, 6, 1, 6};
        int[] qArray = new int[] {3, 8, 5, 4, 1, 9, 0, 2, 1, 0, 7};
        quickUnion uf = new quickUnion(N);
        for(int i = 0; i < 11; i++) {
            int p = pArray[i];
            int q = qArray[i];
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.println(p + " " + q);
            }
        }
    }
}
