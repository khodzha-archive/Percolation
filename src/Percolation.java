import java.lang.IndexOutOfBoundsException;

public class Percolation {
    private int size;
    private boolean[] opened;
    private boolean percolates = false;
    private WeightedQuickUnionUF data;
    
    public Percolation(int N) {
        size = N;
        // N*N + 1 - beginning of percolation system
        // N*N + 2 - end of percolation system
        data = new WeightedQuickUnionUF(N*N + 2);
        opened = new boolean[N * N + 2];
        for (int i = 0; i < opened.length; i++) {
            opened[i] = false;
        }
        opened[N * N] = true;
        opened[N * N + 1] = true;
    }

    public void open(int i, int j) {
        int index = convertToIndex(i, j);
        int c;
        if (!opened[index]) {
            opened[index] = true;
        }
        if (i == 1)
            data.union(index, size * size);
        if (i < size && isOpen(i + 1, j)) {
            c = convertToIndex(i + 1, j);
            data.union(index, c);
        }
        if (i > 1 && isOpen(i - 1, j)) {
            c = convertToIndex(i - 1, j);
            data.union(index, c);
        }
        if (j < size && isOpen(i, j + 1)) {
            c = convertToIndex(i, j + 1);
            data.union(index, c);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            c = convertToIndex(i, j - 1);
            data.union(index, c);
        }

    }

    public boolean isOpen(int i, int j) {
        return opened[convertToIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        int index = convertToIndex(i, j);
        return data.connected(index, size * size);
    }

    public boolean percolates() {
        if (!percolates) {
            for (int j = 1; j <= size; j++) {
                if(data.connected(convertToIndex(size, j), size*size)) {
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
}