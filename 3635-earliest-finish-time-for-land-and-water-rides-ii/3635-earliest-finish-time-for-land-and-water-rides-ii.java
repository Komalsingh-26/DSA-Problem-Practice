import java.util.*;

class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        long ans = Long.MAX_VALUE;
        int m = waterStartTime.length;
        int[][] water = new int[m][2];
        for (int i = 0; i < m; i++) {
            water[i][0] = waterStartTime[i];
            water[i][1] = waterDuration[i];
        }

        Arrays.sort(water, (a, b) -> Integer.compare(a[0], b[0]));

        int[] waterStarts = new int[m];
        long[] waterPrefixMinDur = new long[m];
        long[] waterSuffixMinOpenFinish = new long[m];

        for (int i = 0; i < m; i++) {
            waterStarts[i] = water[i][0];
        }

        waterPrefixMinDur[0] = water[0][1];
        for (int i = 1; i < m; i++) {
            waterPrefixMinDur[i] = Math.min(waterPrefixMinDur[i - 1], water[i][1]);
        }

        waterSuffixMinOpenFinish[m - 1] =
                (long) water[m - 1][0] + water[m - 1][1];
        for (int i = m - 2; i >= 0; i--) {
            waterSuffixMinOpenFinish[i] = Math.min(
                    waterSuffixMinOpenFinish[i + 1],
                    (long) water[i][0] + water[i][1]
            );
        }
        for (int i = 0; i < landStartTime.length; i++) {
            long x = (long) landStartTime[i] + landDuration[i]; 

            int pos = lowerBound(waterStarts, (int) x);

            long best = Long.MAX_VALUE;
            if (pos > 0) {
                best = Math.min(best, x + waterPrefixMinDur[pos - 1]);
            }
            if (pos < m) {
                best = Math.min(best, waterSuffixMinOpenFinish[pos]);
            }

            ans = Math.min(ans, best);
        }

        int n = landStartTime.length;
        int[][] land = new int[n][2];
        for (int i = 0; i < n; i++) {
            land[i][0] = landStartTime[i];
            land[i][1] = landDuration[i];
        }

        Arrays.sort(land, (a, b) -> Integer.compare(a[0], b[0]));

        int[] landStarts = new int[n];
        long[] landPrefixMinDur = new long[n];
        long[] landSuffixMinOpenFinish = new long[n];

        for (int i = 0; i < n; i++) {
            landStarts[i] = land[i][0];
        }

        landPrefixMinDur[0] = land[0][1];
        for (int i = 1; i < n; i++) {
            landPrefixMinDur[i] = Math.min(landPrefixMinDur[i - 1], land[i][1]);
        }

        landSuffixMinOpenFinish[n - 1] =
                (long) land[n - 1][0] + land[n - 1][1];
        for (int i = n - 2; i >= 0; i--) {
            landSuffixMinOpenFinish[i] = Math.min(
                    landSuffixMinOpenFinish[i + 1],
                    (long) land[i][0] + land[i][1]
            );
        }

        for (int j = 0; j < m; j++) {
            long x = (long) waterStartTime[j] + waterDuration[j];
            int pos = lowerBound(landStarts, (int) x);

            long best = Long.MAX_VALUE;
            if (pos > 0) {
                best = Math.min(best, x + landPrefixMinDur[pos - 1]);
            }
            if (pos < n) {
                best = Math.min(best, landSuffixMinOpenFinish[pos]);
            }

            ans = Math.min(ans, best);
        }

        return (int) ans;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}