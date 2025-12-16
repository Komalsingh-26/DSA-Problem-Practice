import java.util.*;

class Solution {
    int budget;
    int[] present, future;
    List<Integer>[] tree;

    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        this.budget = budget;
        this.present = present;
        this.future = future;

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();

        for (int[] e : hierarchy) {
            tree[e[0] - 1].add(e[1] - 1);
        }

        int[][] res = dfs(0);
        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans, res[0][b]);
        }
        return ans;
    }

    // returns [dp0, dp1]
    private int[][] dfs(int u) {
        int[] dp0 = new int[budget + 1]; // no discount
        int[] dp1 = new int[budget + 1]; // discount available

        for (int v : tree[u]) {
            int[][] child = dfs(v);
            dp0 = merge(dp0, child[0]);
            dp1 = merge(dp1, child[1]);
        }

        int[] newDp0 = dp0.clone();
        int[] newDp1 = dp0.clone();

        // Buy u with full cost (no discount)
        int fullCost = present[u];
        int profitFull = future[u] - fullCost;
        for (int b = fullCost; b <= budget; b++) {
            newDp0[b] = Math.max(newDp0[b], dp1[b - fullCost] + profitFull);
        }

        // Buy u with half cost (discount from parent)
        int halfCost = present[u] / 2;
        int profitHalf = future[u] - halfCost;
        for (int b = halfCost; b <= budget; b++) {
            newDp1[b] = Math.max(newDp1[b], dp1[b - halfCost] + profitHalf);
        }

        return new int[][]{newDp0, newDp1};
    }

    private int[] merge(int[] A, int[] B) {
        int[] res = new int[budget + 1];
        Arrays.fill(res, Integer.MIN_VALUE / 2);

        for (int i = 0; i <= budget; i++) {
            if (A[i] < 0) continue;
            for (int j = 0; j + i <= budget; j++) {
                if (B[j] < 0) continue;
                res[i + j] = Math.max(res[i + j], A[i] + B[j]);
            }
        }
        return res;
    }
}
