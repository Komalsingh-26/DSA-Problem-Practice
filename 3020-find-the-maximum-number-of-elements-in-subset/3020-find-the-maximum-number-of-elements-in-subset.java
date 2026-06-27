class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> freq = new HashMap<>();

        for (int x : nums) {
            long v = x;
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }

        int ans = 1;

        if (freq.containsKey(1L)) {
            int c = freq.get(1L);
            ans = Math.max(ans, (c % 2 == 0) ? c - 1 : c);
        }

        for (long start : freq.keySet()) {
            if (start == 1L) continue;

            int len = 1;
            long cur = start;

            while (freq.getOrDefault(cur, 0) >= 2) {
                if (cur > 1000000000L) break;

                long next = cur * cur;
                if (next > 1000000000L) break;

                if (freq.containsKey(next)) {
                    len += 2;
                    cur = next;
                } else {
                    break;
                }
            }

            ans = Math.max(ans, len);
        }

        return ans;
    }
}