class Solution {
    
    static class Pair {
        long count;   
        long sum;     
        
        Pair(long count, long sum) {
            this.count = count;
            this.sum = sum;
        }
    }
    
    private String digits;
    private Pair[][][][] memo;
    
    public int totalWaviness(int num1, int num2) {
        long ans = solve(num2) - solve((long) num1 - 1);
        return (int) ans;
    }
    
    private long solve(long n) {
        if (n < 0) return 0;
        
        digits = String.valueOf(n);
        int len = digits.length();
        
        memo = new Pair[len + 1][2][11][11];
        
        return dfs(0, false, -1, -1, true).sum;
    }
    
    private Pair dfs(int pos, boolean started, int prev2, int prev1, boolean tight) {
        if (pos == digits.length()) {
            return new Pair(1, 0);
        }
        
        int s = started ? 1 : 0;
        
        if (!tight && memo[pos][s][prev2 + 1][prev1 + 1] != null) {
            return memo[pos][s][prev2 + 1][prev1 + 1];
        }
        
        int limit = tight ? digits.charAt(pos) - '0' : 9;
        
        long totalCount = 0;
        long totalSum = 0;
        
        for (int d = 0; d <= limit; d++) {
            boolean nextTight = tight && (d == limit);
            
            if (!started && d == 0) {
                Pair nxt = dfs(pos + 1, false, -1, -1, nextTight);
                totalCount += nxt.count;
                totalSum += nxt.sum;
            } else {
                
                int add = 0;
                int nPrev2, nPrev1;
                
                if (!started) {
                    nPrev2 = -1;
                    nPrev1 = d;
                } else if (prev2 == -1) {
                    nPrev2 = prev1;
                    nPrev1 = d;
                } else {
                    if ((prev1 > prev2 && prev1 > d) ||
                        (prev1 < prev2 && prev1 < d)) {
                        add = 1;
                    }
                    
                    nPrev2 = prev1;
                    nPrev1 = d;
                }
                
                Pair nxt = dfs(pos + 1, true, nPrev2, nPrev1, nextTight);
                
                totalCount += nxt.count;
                totalSum += nxt.sum + (long) add * nxt.count;
            }
        }
        
        Pair res = new Pair(totalCount, totalSum);
        
        if (!tight) {
            memo[pos][s][prev2 + 1][prev1 + 1] = res;
        }
        
        return res;
    }
}