class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BinaryTreeBrothers {
    public boolean areBrothers(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        Pair xInfo = findNode(root, x, null, 0);
        Pair yInfo = findNode(root, y, null, 0);

        if (xInfo != null && yInfo != null && xInfo.depth == yInfo.depth && xInfo.parent != yInfo.parent) {
            return true;
        }

        return false;
    }

    private Pair findNode(TreeNode node, int target, TreeNode parent, int depth) {
        if (node == null) {
            return null;
        }

        if (node.val == target) {
            return new Pair(parent, depth);
        }

        Pair leftInfo = findNode(node.left, target, node, depth + 1);
        Pair rightInfo = findNode(node.right, target, node, depth + 1);

        return leftInfo != null ? leftInfo : rightInfo;
    }

    private static class Pair {
        TreeNode parent;
        int depth;

        Pair(TreeNode parent, int depth) {
            this.parent = parent;
            this.depth = depth;
        }
    }

    public static void main(String[] args) {
        BinaryTreeBrothers solution = new BinaryTreeBrothers();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);

        int x = 4;
        int y = 3;

        boolean result = solution.areBrothers(root, x, y);
        System.out.println("Nodes " + x + " and " + y + " are brothers: " + result);
    }
}
