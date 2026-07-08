class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        final int MOD = 1_000_000_007;
        int n = s.length();

        int[] prefCnt = new int[n + 1];
        int[] temp = new int[n];
        int k = 0;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            prefCnt[i + 1] = prefCnt[i];
            if (c != '0') {
                temp[k++] = c - '0';
                prefCnt[i + 1]++;
            }
        }
        long[] pow10 = new long[k + 1];
        pow10[0] = 1;
        for (int i = 1; i <= k; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }
        long[] hash = new long[k + 1];
        long[] digitSum = new long[k + 1];

        for (int i = 0; i < k; i++) {
            hash[i + 1] = (hash[i] * 10 + temp[i]) % MOD;
            digitSum[i + 1] = digitSum[i] + temp[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = prefCnt[l];
            int right = prefCnt[r + 1] - 1;

            if (left > right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left + 1;

            long x = (hash[right + 1] - (hash[left] * pow10[len]) % MOD + MOD) % MOD;
            long sum = digitSum[right + 1] - digitSum[left];

            ans[i] = (int) ((x * (sum % MOD)) % MOD);
        }

        return ans;
    }
}