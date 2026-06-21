class Solution {
    public int maxIceCream(int[] costs, int coins) {
        int maxCost = 0;

        for (int cost : costs) {
            maxCost = Math.max(maxCost, cost);
        }

        int[] freq = new int[maxCost + 1];

        for (int cost : costs) {
            freq[cost]++;
        }

        int bars = 0;

        for (int cost = 1; cost <= maxCost && coins >= cost; cost++) {
            if (freq[cost] == 0) continue;

            int canBuy = Math.min(freq[cost], coins / cost);

            bars += canBuy;
            coins -= canBuy * cost;
        }

        return bars;
    }
}