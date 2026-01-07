/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    private long totalSum = 0;
    private long maxProduct = 0;
    private static final int MOD = 1_000_000_007;

    public int maxProduct(TreeNode root) {
        totalSum = getTotalSum(root);
        computeSubtreeSum(root);

        return (int)(maxProduct % MOD);
    }
    private long getTotalSum(TreeNode node) {
        if (node == null) return 0;
        return node.val + getTotalSum(node.left) + getTotalSum(node.right);
    }
    private long computeSubtreeSum(TreeNode node) {
        if (node == null) return 0;
        long leftSum = computeSubtreeSum(node.left);
        long rightSum = computeSubtreeSum(node.right);
        long subtreeSum = node.val + leftSum + rightSum;
        long product = subtreeSum * (totalSum - subtreeSum);
        maxProduct = Math.max(maxProduct, product);
        return subtreeSum;
    }
}
