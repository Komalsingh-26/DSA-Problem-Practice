class Solution {
    public long sumAndMultiply(int n) {
        if (n == 0) return 0;

        StringBuilder sb = new StringBuilder();
        int sum = 0;

        while (n > 0) {
            int digit = n % 10;
            if (digit != 0) {
                sb.append(digit);
                sum += digit;
            }
            n /= 10;
        }

        sb.reverse();

        long x = 0;
        for (int i = 0; i < sb.length(); i++) {
            x = x * 10 + (sb.charAt(i) - '0');
        }

        return x * sum;
    }
}