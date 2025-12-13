import java.util.*;

class Solution {
    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        
        // Define priority order for business lines
        Map<String, Integer> priority = new HashMap<>();
        priority.put("electronics", 0);
        priority.put("grocery", 1);
        priority.put("pharmacy", 2);
        priority.put("restaurant", 3);

        List<Coupon> validCoupons = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {
            if (!isActive[i]) continue;

            if (!priority.containsKey(businessLine[i])) continue;
            if (code[i] == null || code[i].isEmpty() || !code[i].matches("[a-zA-Z0-9_]+")) {
                continue;
            }

            validCoupons.add(new Coupon(code[i], businessLine[i]));
        }
        Collections.sort(validCoupons, (a, b) -> {
            int cmp = priority.get(a.businessLine) - priority.get(b.businessLine);
            if (cmp != 0) return cmp;
            return a.code.compareTo(b.code);
        });
        List<String> result = new ArrayList<>();
        for (Coupon c : validCoupons) {
            result.add(c.code);
        }

        return result;
    }
    static class Coupon {
        String code;
        String businessLine;

        Coupon(String code, String businessLine) {
            this.code = code;
            this.businessLine = businessLine;
        }
    }
}
