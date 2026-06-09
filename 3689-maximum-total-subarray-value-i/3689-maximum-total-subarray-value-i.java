class Solution {
    public long maxTotalValue(int[] nums, int k) {
        int mn = Integer.MAX_VALUE;
        int mx = Integer.MIN_VALUE;
        for(int x : nums){
            mn = Math.min(mn, x);
            mx = Math.max(mx, x);
        }
        return (long)(mx- mn)*k;
        
    }
}