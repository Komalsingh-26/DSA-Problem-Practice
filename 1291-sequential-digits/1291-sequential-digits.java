class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> ans = new ArrayList<>();

        for (int start = 1; start <= 9; start++) {
            int num = start;
            int next = start + 1;

            while (next <= 9) {
                num = num * 10 + next;
                if (num >= low && num <= high) {
                    ans.add(num);
                }
                next++;
            }
        }

        Collections.sort(ans);
        return ans;
    }
}