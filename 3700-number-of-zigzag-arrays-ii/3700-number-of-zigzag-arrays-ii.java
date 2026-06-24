class Solution {
    static final long MOD = 1_000_000_007L;

    private long[][] multiply(long[][] A, long[][] B) {
        int n = A.length;
        long[][] res = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (A[i][k] == 0) continue;

                long val = A[i][k];
                for (int j = 0; j < n; j++) {
                    if (B[k][j] == 0) continue;

                    res[i][j] = (res[i][j] + val * B[k][j]) % MOD;
                }
            }
        }
        return res;
    }

    private long[] multiply(long[][] A, long[] v) {
        int n = A.length;
        long[] res = new long[n];

        for (int i = 0; i < n; i++) {
            long cur = 0;
            for (int j = 0; j < n; j++) {
                if (A[i][j] == 0) continue;
                cur = (cur + A[i][j] * v[j]) % MOD;
            }
            res[i] = cur;
        }

        return res;
    }

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        int states = 2 * m;

        long[] base = new long[states];

        // Length = 2 states
        for (int b = 0; b < m; b++) {
            base[b] = b;                  // up[b]
            base[m + b] = (m - 1 - b);   // down[b]
        }

        long[][] trans = new long[states][states];

        // up[c] <- down[b] where b < c
        for (int c = 0; c < m; c++) {
            for (int b = 0; b < c; b++) {
                trans[c][m + b] = 1;
            }
        }

        // down[c] <- up[b] where b > c
        for (int c = 0; c < m; c++) {
            for (int b = c + 1; b < m; b++) {
                trans[m + c][b] = 1;
            }
        }

        long power = n - 2;

        long[][] result = new long[states][states];
        for (int i = 0; i < states; i++) {
            result[i][i] = 1;
        }

        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiply(result, trans);
            }
            trans = multiply(trans, trans);
            power >>= 1;
        }

        long[] finalState = multiply(result, base);

        long ans = 0;
        for (long x : finalState) {
            ans = (ans + x) % MOD;
        }

        return (int) ans;
    }
}