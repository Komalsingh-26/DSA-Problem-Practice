class Solution {
    public long maximumProfit(int[] prices, int k) {
        long NEG = Long.MIN_VALUE / 4;

        long[] free = new long[k + 1];
        long[] holdLong = new long[k + 1];
        long[] holdShort = new long[k + 1];
        for (int i = 0; i <= k; i++) {
            free[i] = 0;
            holdLong[i] = NEG;
            holdShort[i] = NEG;
        }

        for (int price : prices) {
            long[] newFree = free.clone();
            long[] newLong = holdLong.clone();
            long[] newShort = holdShort.clone();

            for (int t = 0; t <= k; t++) {
                newLong[t] = Math.max(newLong[t], free[t] - price);
                newShort[t] = Math.max(newShort[t], free[t] + price);

                if (t + 1 <= k) {
                    newFree[t + 1] = Math.max(newFree[t + 1], holdLong[t] + price);
                    newFree[t + 1] = Math.max(newFree[t + 1], holdShort[t] - price);
                }
            }

            free = newFree;
            holdLong = newLong;
            holdShort = newShort;
        }

        long ans = 0;
        for (int t = 0; t <= k; t++) {
            ans = Math.max(ans, free[t]);
        }
        return ans;
    }
}
