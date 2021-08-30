import java.util.ArrayList;

public class l001 {

    public static void pppppp(int a, int b) {
        if (a > b)
            return;
    }

    public static void ppppp(int a, int b) {
        System.out.println(a);
        pppppp(a + 1, b);
    }

    public static void pppp(int a, int b) {
        System.out.println(a);
        ppppp(a + 1, b);
    }

    public static void ppp(int a, int b) {
        System.out.println(a);
        pppp(a + 1, b);
    }

    public static void pp(int a, int b) {
        System.out.println(a);
        ppp(a + 1, b);
    }

    public static void p(int a, int b) {
        System.out.println(a);
        pp(a + 1, b);
    }

    // Print Increasing
    public static void printIncreasing(int a, int b){
        if(a > b) return;
        System.out.println(a);
        printIncreasing(a + 1, b);
    }

    // Print Decreasing
    public static void printDecreasing(int a, int b){
        if(a > b) return;
        printDecreasing(a + 1, b);
        System.out.println(a);
    }

    // Print Increasing Decreasing
    public static void printIncreasingDecreasing(int a, int b){
        if(a > b) return;
        System.out.println(a);
        printIncreasingDecreasing(a + 1, b);
        System.out.println(a);

    }

    // Print Odd Even
    public static void printOddEven(int a, int b){
        if(a > b) return;

        if(a % 2 != 0){
            System.out.println(a);
        }

        printOddEven(a + 1, b);
        if(a % 2 == 0){
            System.out.println(a);
        }
    }

    // Display Arr
    public static void displayArr(int[] arr, int idx){
        if(idx == arr.length) return;
        System.out.println(arr[idx]);
        displayArr(arr, idx + 1);
    }

    // Display Arr Reverse
    public static void displayArrReverse(int[] arr, int idx){
        if(idx < 0) return;
        System.out.println(arr[idx]);
        displayArrReverse(arr, idx - 1);
    }

    // max
    public static int max(int[] arr, int idx){
        if(idx == arr.length) return -(int)1e9;
        int recAns = max(arr, idx + 1);
        if(arr[idx] > recAns){
            return arr[idx];
        }
        return recAns;
    }

    // min
    public static int min(int[] arr, int idx){
        if(idx == arr.length) return (int)1e9;
        int recAns = max(arr, idx + 1);
        if(arr[idx] < recAns){
            return arr[idx];
        }
        return recAns;
    }

    // find
    public static boolean find(int[] arr, int idx, int data){
        if(idx == arr.length) return false;
        if(arr[idx] == data) return true;
        boolean recAns = find(arr, idx + 1, data);
        return recAns;
    }

    // first index
    public static int fi(int[] arr, int idx, int data){
        if(idx == arr.length) return -1;
        if(arr[idx] == data)  return idx;
        return fi(arr, idx + 1, data);
    }

    // last index
    public static int li(int[] arr, int idx, int data){
        if(idx == arr.length) return -1;
        int i =  li(arr, idx + 1, data);
        if(arr[idx] == data && i == -1)  return idx;
        return i;
    }

    // all index
    public static int[] allIndex(int[] arr, int idx, int data, int count) {
        if(idx == arr.length){
            int[] base = new int[count];
            return base;
        }
        if(arr[idx] == data) count++;
        int[] res = allIndex(arr, idx + 1, data, count);
        if(arr[idx] == data) res[count - 1] = idx;
        return res;
    }

    // Subsequence --> top to bottom approach (bottom pe ans banta hai)
    public static ArrayList<String> subSeq(String str, int idx){
        if(idx == str.length()){
            ArrayList<String> base = new ArrayList<>();
            base.add("");
            return base;
        } 

        char ch = str.charAt(idx);
        ArrayList<String> recAns = subSeq(str, idx + 1);
        ArrayList<String> ans = new ArrayList<>(recAns);
        for(int i = 0; i < recAns.size(); i++){
            ans.add(ch + recAns.get(i));
        }

        return ans;
    }

    // Subsequence --> bottom to top approach (top pe ans banta hai)
    public static int subSeq(String str, String ans, ArrayList<String> res, int idx){
        if(idx == str.length()){
            res.add(ans);
            return 1;
        }

        int count = 0;
        count += subSeq(str, ans + str.charAt(idx), res, idx + 1);
        count += subSeq(str, ans, res, idx + 1);
        return count;
    }

    // Maze Path --> Top to down
    public static ArrayList<String> mazePath_01(int sr, int sc, int dr, int dc){
        if(sr == dr && sc == dc){
            ArrayList<String> base = new ArrayList<>();
            base.add("");
            return base;
        }

        ArrayList<String> ans = new ArrayList<>();

        if(sr + 1 <= dr){
            ArrayList<String> recAns = mazePath_01(sr + 1, sc, dr, dc);
            for(int i = 0; i < recAns.size(); i++){
                ans.add("V" + recAns.get(i));
            }
        }

        if(sr + 1 <= dr && sc + 1 <= dc){
            ArrayList<String> recAns = mazePath_01(sr + 1, sc + 1, dr, dc);
            for(int i = 0; i < recAns.size(); i++){
                ans.add("D" + recAns.get(i));
            }
        }

        if(sc + 1 <= dc){
            ArrayList<String> recAns = mazePath_01(sr, sc + 1, dr, dc);
            for(int i = 0; i < recAns.size(); i++){
                ans.add("H" + recAns.get(i));
            }
        }

        return ans;
    }

    // Maze Path --> bottom to top
    public static int mazePath_01(int sr, int sc, int dr, int dc, ArrayList<String> res, String psf){
        if(sr == dr && sc == dc){
            res.add(psf);
            return 1;
        }

        int count = 0;
        if(sr + 1 <= dr){
            count += mazePath_01(sr + 1, sc, dr, dc, res, psf + "V");
        }
        if(sr + 1 <= dr && sc + 1 <= dc){
            count += mazePath_01(sr + 1, sc + 1, dr, dc, res, psf + "D");
        }
        if(sc + 1 <= dc){
            count += mazePath_01(sr, sc + 1, dr, dc, res, psf + "H");
        }
        return count;
    }

    // Maze Path multiple jumps 
    public static ArrayList<String> mazePath_02(int sr, int sc, int dr, int dc){
        if(sr == dr && sc == dc){
            ArrayList<String> base = new ArrayList<>();
            base.add("");
            return base;
        }

        ArrayList<String> ans = new ArrayList<>();
        for(int jump = 1; sr + jump <= dr; jump++){
            ArrayList<String> recAns = mazePath_02(sr + jump, sc, dr, dc);
            for(int i = 0; i < recAns.size(); i++){
                ans.add("V" + jump + recAns.get(i));
            }
        }
        
        for(int jump = 1; sr + jump <= dr && sc + jump <= dc; jump++){
            ArrayList<String> recAns = mazePath_02(sr + jump, sc + jump, dr, dc);
            for(int i = 0; i < recAns.size(); i++){
                ans.add("D" + jump + recAns.get(i));
            }
        }

        for(int jump = 1; sc + jump <= dc; jump++){
            ArrayList<String> recAns = mazePath_02(sr, sc + jump, dr, dc);
            for(int i = 0; i < recAns.size(); i++){
                ans.add("H" + jump + recAns.get(i));
            }
        }

        return ans;
    }

    public static int mazePath_02(int sr, int sc, int dr, int dc, ArrayList<String> res, String psf){
        if(sr == dr && sc == dc){
            res.add(psf);
            return 1;
        }

        int count = 0;
        for(int jump = 1; sr + jump <= dr; jump++){
            count += mazePath_02(sr + jump, sc, dr, dc, res, psf + "V" + jump);
        }
        
        for(int jump = 1; sr + jump <= dr && sc + jump <= dc; jump++){
            count += mazePath_02(sr + jump, sc + jump, dr, dc, res, psf + "D" + jump);
        }
        
        for(int jump = 1; sc + jump <= dc; jump++){
            count += mazePath_02(sr, sc + jump, dr, dc, res, psf + "H" + jump);
        }

        return count;
    }

    // Using Direction vector
    public static int mazePath_03(int sr, int sc, int dr, int dc, int[][] dir, String[] dirS, ArrayList<String> res, String psf){
        if (sr == dr && sc == dc) {
            res.add(psf);
            return 1;
        }

        int count = 0;
        for (int d = 0; d < dir.length; d++) {
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];

            if (r >= 0 && c >= 0 && r <= dr && c <= dc)
                count += mazePath_03(r, c, dr, dc, dir, dirS, res, psf + dirS[d]);
        }

        return count;
    }

    // flood fill
    public static int floodFill(int sr, int sc, boolean[][] vis, int[][] dir, String[] dirS, ArrayList<String> res, String psf){
        int n = vis.length, m = vis[0].length;
        
        if(sr == n - 1 && sc == m - 1){
            res.add(psf);
            return 1;
        }

        int count = 0;
        vis[sr][sc] = true;
        for(int d = 0; d < dir.length; d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < n  && c < m && !vis[r][c]){
                count += floodFill(r, c, vis, dir, dirS, res, psf + dirS[d]);
            }
        }

        vis[sr][sc] = false;
        return count;
    }

    public static int floodFill_multi(int sr, int sc, boolean[][] vis, int[][] dir, String[] dirS,
        ArrayList<String> res, String psf){
            int n = vis.length, m = vis[0].length;
            if(sr == n - 1 && sc == m - 1){
                res.add(psf);
                return 1;
            } 

            int count = 0;
            vis[sr][sc] = true;

            for(int d = 0; d < dir.length; d++){
                for(int rad = 1; rad <= Math.max(n, m); rad++){
                    int r = sr + rad * dir[d][0];
                    int c = sc + rad * dir[d][1];
                    if(r >= 0 && c >= 0 && r < n && c < m){
                        if(!vis[r][c])
                            count += floodFill_multi(r, c, vis, dir, dirS, res, psf + dirS[d] + rad);
                    }else{
                        break;
                    }
                }
            }
            vis[sr][sc] = false;
            return count;
    }

    // https://practice.geeksforgeeks.org/problems/rat-in-a-maze-problem/1
    public static int floodFill(int sr, int sc, int[][] vis, String psf, ArrayList<String> res, int[][] dir, String[] dirS) {
        int n = vis.length, m = vis[0].length;
        if(sr == n - 1 && sc == m - 1){
            res.add(psf);
            return 1;
        }

        int count = 0;
        vis[sr][sc] = 0;
        for(int d = 0; d < dir.length; d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < n && c < m && vis[sr][sc] != 0){
                count += floodFill(r, c, vis, psf + dirS[d], res, dir, dirS);
            }
        }
        vis[sr][sc] = 1;
        return count;
    }

    // https://practice.geeksforgeeks.org/problems/special-matrix4201/1
    public int floodFill_ways(int sr, int sc, int[][] vis, int[][] dir){
        int n = vis.length, m = vis[0].length;
        if(sr == n - 1 && sc == m - 1){
            return 1;
        }

        int count = 0;
        vis[sr][sc] = 1;  // mark as true or block
        for(int d = 0; d < dir.length; d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r > 0 && c > 0 && r < n && c < m && vis[sr][sc] != 1){
                count += floodFill_ways(r, c, vis, dir);
            } 
        }
        vis[sr][sc] = 0;  // unblock
        return count;
    }

    public int FindWays(int n, int m, int[][] blocked_cells)
    {
        int[][] vis = new int[n + 1][m + 1];
        for(int[] block : blocked_cells){
            vis[block[0]][block[1]] = 1;  // mark as true or block
        }

        if(vis[1][1] == 1 || vis[n][m] == 1){
            return 0;
        }

        int[][] dir = {{0,1},{1,0}};
        return floodFill_ways(1,1,vis,dir);
    }

    // https://www.geeksforgeeks.org/rat-in-a-maze-with-multiple-steps-jump-allowed/?ref=rp
    public static int floodFill_jump(int sr, int sc, int[][] jumpMat, int[][] ans, int[][] dir){
        int n = jumpMat.length, m = jumpMat[0].length;
        if(sr == n - 1 && sc == m - 1){
            ans[sr][sc] = 1;
            // display(ans);
            ans[sr][sc] = 0;
            return 1;
        }

        int count = 0;
        int jump = jumpMat[sr][sc];
        jumpMat[sr][sc] = 0;  // block but not required as dir are (right, down) only
        ans[sr][sc] = 1;    // may be we can take jump to reach destination
        for(int d = 0; d < dir.length; d++){
            for(int rad = 1; rad <= jump; rad++){
                int r = sr + rad * dir[d][0];
                int c = sc + rad * dir[d][1];
                if(r >= 0 && c >= 0 && r < n && c < m){
                    if(jumpMat[sr][sc] != 0)
                        count += floodFill_jump(r, c, jumpMat, ans, dir);
                }else{
                    break;
                }
            }
        }

        jumpMat[sr][sc] = jump;
        ans[sr][sc] = 0;
        return count;
    }

    // longest path
    public static class Pair{
        String psf;
        int len;

        Pair(String psf, int len){
            this.psf = psf;
            this.len = len;
        }
    }

    public static Pair longestPath(int sr, int sc, boolean[][] vis, int[][] dir, String[] dirS){
        int n = vis.length, m = vis[0].length;
        if(sr == n - 1 && sc == m - 1){
            return new Pair("", 0);
        }

        vis[sr][sc] = true;
        Pair myAnsPair = new Pair("", -1);
        for(int d = 0; d < dir.length; d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < n && c < m){
                if(!vis[r][c]){
                    Pair rp = longestPath(r, c, vis, dir, dirS);
                    if(rp.len != -1 && rp.len + 1 > myAnsPair.len){
                        myAnsPair.len = rp.len + 1;
                        myAnsPair.psf = dirS[d] + rp.psf;
                    }
                }
            }
        }
        vis[sr][sc] = false;
        return myAnsPair;
    }

    // shortest path
    public static Pair shortestPath(int sr, int sc, boolean[][] vis, int[][] dir, String[] dirS){
        int n = vis.length, m = vis[0].length;
        if(sr == n - 1 && sc == m - 1){
            return new Pair("", 0);
        }

        vis[sr][sc] = true;
        Pair myAnsPair = new Pair("", (int)1e9);
        for(int d = 0; d < dir.length; d++){
            int r = sr + dir[d][0];
            int c = sc + dir[d][1];
            if(r >= 0 && c >= 0 && r < n && c < m){
                if(!vis[r][c]){
                    Pair rp = shortestPath(r, c, vis, dir, dirS);
                    if(rp.len != (int)1e9 && rp.len + 1 < myAnsPair.len){
                        myAnsPair.len = rp.len + 1;
                        myAnsPair.psf = dirS[d] + rp.psf;
                    }
                }
            }
        }
        vis[sr][sc] = false;
        return myAnsPair;
    }

    public static void main(String[] args){
        int[][] dir = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        String[] dirS = { "D", "R", "U", "L" };

        boolean[][] vis = new boolean[3][3];
        // vis[1][1] = vis[1][2] = vis[2][1] = true;

        Pair ans = longestPath(0, 0, vis, dir, dirS);
        System.out.println(ans.psf + " @ " + ans.len);
    }
}