class Solution {
    
    private static class Node {
        long count;
        long sum;
        
        Node(long count, long sum) {
            this.count = count;
            this.sum = sum;
        }
    }
    
    private char[] digits;
    private Node[][][][][][] memo;
    private boolean[][][][][][] seen;
    
    public long totalWaviness(long num1, long num2) {
        return calc(num2) - calc(num1 - 1);
    }
    
    private long calc(long n) {
        if (n <= 0) return 0;
        
        digits = String.valueOf(n).toCharArray();
        int m = digits.length;
        
        memo = new Node[m + 1][2][17][11][11][2];
        seen = new boolean[m + 1][2][17][11][11][2];
        
        return dfs(0, 1, 0, 0, 10, 10).sum;
    }
    
    private Node dfs(int pos, int tight, int started,
                     int len, int prev2, int prev1) {
        
        if (pos == digits.length) {
            return new Node(1L, 0L);
        }
        
        if (tight == 0 && seen[pos][started][len][prev2][prev1][0]) {
            return memo[pos][started][len][prev2][prev1][0];
        }
        
        int limit = (tight == 1) ? digits[pos] - '0' : 9;
        
        long totalCount = 0;
        long totalSum = 0;
        
        for (int d = 0; d <= limit; d++) {
            int newTight = (tight == 1 && d == limit) ? 1 : 0;
            
            if (started == 0 && d == 0) {
                Node child = dfs(pos + 1, newTight, 0, 0, 10, 10);
                totalCount += child.count;
                totalSum += child.sum;
            } else {
                
                int contribution = 0;
                
                if (started == 1 && len >= 2) {
                    boolean peak = (prev1 > prev2 && prev1 > d);
                    boolean valley = (prev1 < prev2 && prev1 < d);
                    if (peak || valley) contribution = 1;
                }
                
                int newLen;
                int newPrev2;
                int newPrev1;
                
                if (started == 0) {
                    newLen = 1;
                    newPrev2 = 10;
                    newPrev1 = d;
                } else if (len == 1) {
                    newLen = 2;
                    newPrev2 = prev1;
                    newPrev1 = d;
                } else {
                    newLen = len + 1;
                    newPrev2 = prev1;
                    newPrev1 = d;
                }
                
                Node child = dfs(pos + 1, newTight, 1,
                                 newLen, newPrev2, newPrev1);
                
                totalCount += child.count;
                totalSum += child.sum + (long) contribution * child.count;
            }
        }
        
        Node ans = new Node(totalCount, totalSum);
        
        if (tight == 0) {
            seen[pos][started][len][prev2][prev1][0] = true;
            memo[pos][started][len][prev2][prev1][0] = ans;
        }
        
        return ans;
    }
}