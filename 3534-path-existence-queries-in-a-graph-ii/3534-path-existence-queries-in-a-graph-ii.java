import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int[] value = new int[n];
        int[] pos = new int[n];
        int[] comp = new int[n];

        for (int i = 0; i < n; i++) {
            value[i] = arr[i][0];
            pos[arr[i][1]] = i;
        }

      
        int cid = 0;
        comp[0] = 0;
        for (int i = 1; i < n; i++) {
            if (value[i] - value[i - 1] > maxDiff) cid++;
            comp[i] = cid;
        }

     
        int[] next = new int[n];
        int r = 0;
        for (int i = 0; i < n; i++) {
            while (r + 1 < n && value[r + 1] - value[i] <= maxDiff) r++;
            next[i] = r;
        }

      
        int[] prev = new int[n];
        int l = 0;
        for (int i = 0; i < n; i++) {
            while (value[i] - value[l] > maxDiff) l++;
            prev[i] = l;
        }

        int LOG = 17;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];
        int[][] down = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
            down[0][i] = prev[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
                down[k][i] = down[k - 1][down[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int u = pos[queries[qi][0]];
            int v = pos[queries[qi][1]];

            if (u == v) {
                ans[qi] = 0;
                continue;
            }

            if (comp[u] != comp[v]) {
                ans[qi] = -1;
                continue;
            }

            if (u < v) {
                int cur = u;
                int steps = 0;
                for (int k = LOG - 1; k >= 0; k--) {
                    if (up[k][cur] < v) {
                        steps += 1 << k;
                        cur = up[k][cur];
                    }
                }
                ans[qi] = steps + 1;
            } else {
                int cur = u;
                int steps = 0;
                for (int k = LOG - 1; k >= 0; k--) {
                    if (down[k][cur] > v) {
                        steps += 1 << k;
                        cur = down[k][cur];
                    }
                }
                ans[qi] = steps + 1;
            }
        }

        return ans;
    }
}