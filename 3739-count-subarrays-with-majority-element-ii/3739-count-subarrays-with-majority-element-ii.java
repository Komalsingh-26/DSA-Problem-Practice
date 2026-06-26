class Solution {
    static class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n + 2];
        }

        void add(int idx, int val) {
            while (idx < bit.length) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            int sum = 0;
            while (idx > 0) {
                sum += bit[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
    }

    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int offset = n + 1;
        Fenwick fw = new Fenwick(2 * n + 5);

        long ans = 0;
        int pref = 0;

    
        fw.add(offset + pref, 1);

        for (int x : nums) {
            if (x == target)
                pref++;
            else
                pref--;

            
            ans += fw.query(offset + pref - 1);

            fw.add(offset + pref, 1);
        }

        return ans;
    }
}