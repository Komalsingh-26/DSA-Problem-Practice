class Solution {
    public int countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int[] pref = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + (nums[i] == target ? 1 : -1);
        }

        int[] vals = pref.clone();
        java.util.Arrays.sort(vals);

        Fenwick bit = new Fenwick(vals.length + 2);
        long ans = 0;

        for (int p : pref) {
            int idx = lowerBound(vals, p) + 1;

            ans += bit.query(idx - 1); 
            bit.add(idx, 1);
        }

        return (int) ans;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr[m] < target) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    static class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n];
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
}