public static class l001{
    public static class TreeNode{
        int val = 0;
        TreeNode left = null, right = null;
        TreeNode(int val){
            this.val = val;
        }
    }

    public static int size(TreeNode root){
        return root == null ? 0 : size(root.left) + size(root.right) + 1;
    }

    public static int height(TreeNode root){
        return root == null ? -1 : Math.max(height(root.left), height(root.right)) + 1;
    }

    public static int max(TreeNode root){
        return root == null ? -(int)1e9 : Math.max(root.val, Math.max(max(root.left), max(root.right)));
    }

    public static int min(TreeNode root){
        return root == null ? (int)1e9 : Math.min(root.val, Math.min(min(root.left), min(root.right)));
    }

    public static boolean find(TreeNode root, int data){
        if(root == null) return false;
        if(root.val == data) return true;
        boolean res = find(root.left, data) || find(root.right, data);
        return res;
    }

    // 1st method ntrp
    public static boolean ntrp_1(TreeNode root, int data, ArrayList<TreeNode> list){
        if(root == null) return false;
        
        if(root.val == data){
            list.add(data);
            return true;
        }

        boolean res = ntrp_1(root.left, data, list) || ntrp_1(root.right, data, list);
        if(res){
            list.add(root);
        }
        return res;
    }

    // 2nd method ntrp
    public static ArrayList<TreeNode> ntrp_2(TreeNode root, int data){
        if(root == null) return new ArrayList<>();
        if(root.val == data){
            ArrayList<TreeNode> base = new ArrayList<>();
            base.add(data);
            return base;
        }

        ArrayList<TreeNode> left = ntrp_2(root.left, data);
        if(left.size() > 0){
            left.add(root);
            return left;
        }

        ArrayList<TreeNode> right = ntrp_2(root.right, data);
        if(right.size() > 0){
            right.add(root);
            return right;
        }

        return new ArrayList<>();
    }

    public static void rootToLeafAllPath(TreeNode root, ArrayList<Integer> smallAns, ArrayList<ArrayList<Integer>> ans){
        if(root == null) return;
        if(root.left == null && root.right == null){
            // copy smallAns to base bcoz we are performing add/remove in smallAns
            ArrayList<Integer> base = new ArrayList<>(smallAns);
            base.add(root.val);
            ans.add(base);
            return;
        }

        smallAns.add(root.val);
        rootToLeafAllPath(root.left, smallAns, ans);
        rootToLeafAllPath(root.right, smallAns, ans);
        smallAns.remove(smallAns.size() - 1);
    }

    public static ArrayList<ArrayList<Integer>> rootToAllLeafPath(TreeNode root) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> smallAns = new ArrayList<>();

        rootToAllLeafPath(root, smallAns, ans);
        return ans;
    }

    public static void exactlyOnlyOneChild(TreeNode root, ArrayList<Integer> ans){
        if(root == null || (root.left == null && root.right == null)) return;

        if(root.left == null || root.right == null){
            ans.add(root.val);   // don't return bcoz (15 will also be come as per given tree)
        }

        exactlyOnlyOneChild(root.left, ans);
        exactlyOnlyOneChild(root.right, ans);
    }

    public static int countExactlyOnlyOneChild(TreeNode root){
        if(root == null || (root.left == null && root.right == null)) return 0;
        
        int leftSingleChildCount = countExactlyOnlyOneChild(root.left); 
        int rightSingleChildCount = countExactlyOnlyOneChild(root.right); 
        int ans = leftSingleChildCount + rightSingleChildCount;
        if(root.left == null || root.right == null) ans++;
        return ans;
    }

    // Distance k away
    public static void kDown(TreeNode root, int k, TreeNode blocker, List<Integer> ans){
        if(root == null || k < 0 || root == blocker) return;
        if(k == 0){
            ans.add(root.val);
            return;
        }

        kDown(root.left, k - 1, blocker, ans);
        kDown(root.right, k - 1, blocker, ans);
    }

    // Meth 1:
    public static List<Integer> distanceK(TreeNode root, TreeNode tar, int k){
        ArrayList<TreeNode> path = new ArrayList<>();
        ntrp_1(root, tar.val, path);
        TreeNode blocker = null;
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < path.size(); i++){
            kDown(path.get(i), k - i, blocker, ans);
            blocker = path.get(i); 
        }
        
        return ans;
    }

    // Meth 2: Optimize ntrp space (without extra space)
    public static int distanceK(TreeNode root, TreeNode tar, int k, List<Integer> ans){
        if(root == null) return -1;
        if(root == tar){
            kDown(root, k, null, ans);
            return 1;
        }

        int ld = distanceK(root.left, tar, k, ans);
        if(ld != -1) {
            kDown(root, k - ld, root.left, ans);
            return ld + 1;
        }
        
        int rd = distanceK(root.right, tar, k, ans);
        if(rd != -1) {
            kDown(root, k - rd, root.right, ans);
            return rd + 1;
        }
        return -1;
    }

}