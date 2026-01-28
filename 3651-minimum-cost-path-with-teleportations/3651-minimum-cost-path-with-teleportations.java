class Solution {
    public int minCost(int[][] grid, int k) {
        int n = grid.length, m = grid[0].length, max = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) max = Math.max(max, grid[i][j]); 
        }

        int[][] dp = new int[n][m]; 
        int[] temp = new int[max + 1], best = new int[max + 1]; 
        Arrays.fill(temp, Integer.MAX_VALUE);
        temp[grid[n - 1][m - 1]] = 0;

        for(int i = m - 2; i >= 0; i--) { 
            dp[n - 1][i] = dp[n - 1][i + 1] + grid[n - 1][i + 1];
            temp[grid[n - 1][i]] = Math.min(temp[grid[n - 1][i]], dp[n - 1][i]);
        }
        for(int i = n - 2; i >= 0; i--) {
            dp[i][m - 1] = dp[i + 1][m - 1] + grid[i + 1][m - 1]; 
            temp[grid[i][m - 1]] = Math.min(temp[grid[i][m - 1]], dp[i][m - 1]);
            for(int j = m - 2; j >= 0; j--) {
                dp[i][j] = Math.min(dp[i + 1][j] + grid[i + 1][j], dp[i][j + 1] + grid[i][j + 1]); 
                temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]); 
            }
        }

        for(int x = 0; x < k; x++) { 

            best[0] = temp[0];
            for(int i = 1; i <= max; i++) best[i] = Math.min(best[i - 1], temp[i]); 
            
            for(int i = m - 2; i >= 0; i--) { 
                dp[n - 1][i] = Math.min(best[grid[n - 1][i]], dp[n - 1][i + 1] + grid[n - 1][i + 1]);
                temp[grid[n - 1][i]] = Math.min(temp[grid[n - 1][i]], dp[n - 1][i]);
            }
            for(int i = n - 2; i >= 0; i--) {
                dp[i][m - 1] = Math.min(best[grid[i][m - 1]], dp[i + 1][m - 1] + grid[i + 1][m - 1]); 
                temp[grid[i][m - 1]] = Math.min(temp[grid[i][m - 1]], dp[i][m - 1]);
                for(int j = m - 2; j >= 0; j--) {
                    dp[i][j] = Math.min(Math.min(dp[i + 1][j] + grid[i + 1][j], dp[i][j + 1] + grid[i][j + 1]), best[grid[i][j]]);
                    temp[grid[i][j]] = Math.min(temp[grid[i][j]], dp[i][j]);
                }
            }
        }
        return dp[0][0]; 
    }
}