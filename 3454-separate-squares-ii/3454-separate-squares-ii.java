class Solution {

    static class Event {
        long y, x1, x2;
        int type; 
        Event(long y, long x1, long x2, int type) {
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
            this.type = type;
        }
    }

    long[] xs;
    int n;
    long[] len;
    int[] cnt;

    void build(int node, int l, int r) {
        if (l + 1 == r) return;
        int m = (l + r) >> 1;
        build(node<<1, l, m);
        build(node<<1|1, m, r);
    }

    void pull(int node, int l, int r) {
        if (cnt[node] > 0) {
            len[node] = xs[r] - xs[l];
        } else if (l + 1 == r) {
            len[node] = 0;
        } else {
            len[node] = len[node<<1] + len[node<<1|1];
        }
    }

    void update(int node, int l, int r, int ql, int qr, int val) {
        if (qr <= l || r <= ql) return;
        if (ql <= l && r <= qr) {
            cnt[node] += val;
            pull(node, l, r);
            return;
        }
        int m = (l + r) >> 1;
        update(node<<1, l, m, ql, qr, val);
        update(node<<1|1, m, r, ql, qr, val);
        pull(node, l, r);
    }

    public double separateSquares(int[][] squares) {

        int m = squares.length;
        Event[] ev = new Event[m*2];
        xs = new long[m*2];

        for (int i = 0; i < m; i++) {
            long x = squares[i][0];
            long y = squares[i][1];
            long l = squares[i][2];
            ev[2*i] = new Event(y, x, x+l, 1);
            ev[2*i+1] = new Event(y+l, x, x+l, -1);
            xs[2*i] = x;
            xs[2*i+1] = x+l;
        }

        java.util.Arrays.sort(xs);
        xs = java.util.Arrays.stream(xs).distinct().toArray();
        n = xs.length;

        java.util.Arrays.sort(ev, (a,b)->Long.compare(a.y,b.y));

        len = new long[4*n];
        cnt = new int[4*n];
        build(1,0,n-1);
        long prevY = ev[0].y;
        long totalArea = 0;

        for (Event e: ev) {
            long dy = e.y - prevY;
            totalArea += len[1] * dy;

            int l = java.util.Arrays.binarySearch(xs, e.x1);
            int r = java.util.Arrays.binarySearch(xs, e.x2);
            update(1,0,n-1,l,r,e.type);

            prevY = e.y;
        }

        double half = totalArea / 2.0;
        len = new long[4*n];
        cnt = new int[4*n];
        prevY = ev[0].y;
        double area = 0;

        for (Event e: ev) {
            long dy = e.y - prevY;
            double slice = len[1] * dy;

            if (area + slice >= half) {
                double need = half - area;
                return prevY + need / len[1];
            }

            area += slice;

            int l = java.util.Arrays.binarySearch(xs, e.x1);
            int r = java.util.Arrays.binarySearch(xs, e.x2);
            update(1,0,n-1,l,r,e.type);

            prevY = e.y;
        }

        return prevY;
    }
}
