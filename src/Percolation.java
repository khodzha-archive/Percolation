import java.lang.IndexOutOfBoundsException;

public class Percolation {
    private int size;
    private int[] array;
    private boolean[] opened;
    private int[] sz;
    private boolean percolates = false;
    
    public Percolation(int N) {
        size = N;
        // N*N + 1 - beginning of percolation system
        // N*N + 2 - end of percolation system
        array = new int[N * N + 2];
        opened = new boolean[N * N + 2];
        sz = new int[N * N + 2];
        for (int i = 0; i < array.length; i++) {
            array[i] = -i;
            opened[i] = false;
            sz[i] = 1;
        }
        array[N * N] = N * N;
        array[N * N + 1] = N * N + 1;
        opened[N * N] = true;
        opened[N * N + 1] = true;
    }

    public void open(int i, int j) {
        int index = convertToIndex(i, j);
        int c;
        if (!opened[index]) {
            array[index] = index;
            opened[index] = true;
        }
        if (i == 1)
            union(index, size * size);
        if (i < size && isOpen(i + 1, j)) {
            c = convertToIndex(i + 1, j);
            union(index, c);
        }
        if (i > 1 && isOpen(i - 1, j)) {
            c = convertToIndex(i - 1, j);
            union(index, c);
        }
        if (j < size && isOpen(i, j + 1)) {
            c = convertToIndex(i, j + 1);
            union(index, c);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            c = convertToIndex(i, j - 1);
            union(index, c);
        }

    }

    public boolean isOpen(int i, int j) {
        return opened[convertToIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        int index = convertToIndex(i, j);
        return connected(index, size * size);
    }

    public boolean percolates() {
        if (!percolates) {
            for (int j = 1; j <= size; j++) {
                if(connected(convertToIndex(size, j), size*size)) {
                    percolates = true;
                    break;
                }
            }
        }
        return percolates;
    }

    private int convertToIndex(int i, int j) throws IndexOutOfBoundsException {
        if (i > size || j > size || i < 1 || j < 1)
            throw new IndexOutOfBoundsException();
        return (i - 1) * size + j - 1;
    }

    private int root(int index) {
        int i = index;
        while (i >= 0 && i != array[i])
            i = array[i];
        return i;
    }

    private void union(int i, int j) {
        int p = root(i);
        int q = root(j);
        if (p == q) return;
        if (sz[p] < sz[j]) {
            array[p] = q;
            sz[q] += sz[p];
        } else {
            array[q] = p;
            sz[p] += sz[q];
        }
    }

    private boolean connected(int i, int j) {
        return (!opened[i] || !opened[j]) ? false : root(i) == root(j);
    }
}