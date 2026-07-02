class Solution {
    public int[] getStrongest(int[] arr, int k) {
        Arrays.sort(arr);
        int n = arr.length;
        int median = arr[(n-1)/2];
        int[] ans = new int[k];
        int left =0;
        int right = n-1;
        int idx = 0;
        while(idx <k){
            int leftStrength = Math.abs(arr[left] - median);
            int rightStrength = Math.abs(arr[right] - median);
            if(rightStrength >= leftStrength){
                ans[idx++] = arr[right--];

            }else{
                ans[idx++] = arr[left++];
            }
        }
        return ans;
    }
}