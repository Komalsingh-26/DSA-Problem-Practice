import java.util.Arrays;

class Solution {
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        Arrays.sort(arr);

        arr[0] = 1;

        for (int pos = 1; pos < arr.length; pos++) {
            arr[pos] = Math.min(arr[pos], arr[pos - 1] + 1);
        }

        return arr[arr.length - 1];
    }
}