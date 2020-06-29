package union_find;


public class quickFind {
    private int[] id;

    public quickFind(int N) {
        this.id = new int[N];
        for(int i = 0; i < N; i++)
            this.id[i] = i;
    }
    public void union(int p, int q) {
        int pID = this.id[p]; // get the id for p
        int qID = this.id[q]; // get the id for q
        for (int i = 0; i < this.id.length; i++)  // loop through the list, changing anything with the qID to the pID
            if (this.id[i] == qID)
                this.id[i] = pID;
    }

    public boolean connected(int p, int q) { return this.id[p] == this.id[q]; } // see if the groups of p and q are equal

    public static void main(String[] args)
    {
        int N = 10;
        int[] pArray = new int[] {4, 3, 6, 9, 2, 8, 5, 7, 6, 1, 6};
        int[] qArray = new int[] {3, 8, 5, 4, 1, 9, 0, 2, 1, 0, 7};
        quickFind uf = new quickFind(N);
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
