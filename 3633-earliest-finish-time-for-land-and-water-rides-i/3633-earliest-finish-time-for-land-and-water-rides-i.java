class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int ans =Integer.MAX_VALUE;
        for(int i =0; i<landStartTime.length; i++){
            for (int j = 0; j < waterStartTime.length; j++) {

                int landFinish = landStartTime[i] + landDuration[i];
                int waterStart = Math.max(waterStartTime[j], landFinish);
                ans = Math.min(ans, waterStart + waterDuration[j]);

             
                int waterFinish = waterStartTime[j] + waterDuration[j];
                int landStart = Math.max(landStartTime[i], waterFinish);
                ans = Math.min(ans, landStart + landDuration[i]);
            }

        }
        return ans;
    }
}