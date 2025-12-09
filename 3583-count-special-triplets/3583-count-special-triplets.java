import java.util.HashMap;
import java.util.Map;
class Solution {
    public int specialTriplets(int[] nums) {
        final int MOD = 1_000_000_007;
        
        Map<Integer, Long> leftMap = new HashMap<>();
        Map<Integer, Long> rightMap = new HashMap<>();
        for (int num : nums) {
            rightMap.put(num, rightMap.getOrDefault(num, 0L) + 1);
        }
        
        long answer = 0;
        for (int j = 0; j < nums.length; j++) {
            int mid = nums[j];
            rightMap.put(mid, rightMap.get(mid) - 1);
            if (rightMap.get(mid) == 0) {
                rightMap.remove(mid);
            }
            
            int target = mid * 2;
            
            long leftCount = leftMap.getOrDefault(target, 0L);
            long rightCount = rightMap.getOrDefault(target, 0L);
            
            answer = (answer + (leftCount * rightCount) % MOD) % MOD;
            leftMap.put(mid, leftMap.getOrDefault(mid, 0L) + 1);
        }
        
        return (int) answer;
    }
}
