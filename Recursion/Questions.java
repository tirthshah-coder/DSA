import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;

public class Questions{
    // 39
    public int combinationSum(int[] coins, int tar, int idx, List<List<Integer>> ans,       List<Integer> smallAns){
        if(tar == 0){
            List<Integer> baseAns = new ArrayList<>(smallAns);
            ans.add(baseAns);
            return 1;
        }
        
        int count = 0;
        for(int i = idx; i < coins.length; i++){
            if(tar - coins[i] >= 0){
                smallAns.add(coins[i]);
                count += combinationSum(coins, tar - coins[i], i, ans, smallAns);
                smallAns.remove(smallAns.size() - 1);
            }
        }
        
        return count;
    }
    
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        combinationSum(candidates, target, 0, ans, smallAns);
        return ans;
    }

    // 40
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        Arrays.sort(candidates);  // output requires and to check for duplicates
        findSum(candidates, 0, target, ans, smallAns);
        return ans;
    }
    
    public void findSum(int[] coins, int idx, int tar, List<List<Integer>> ans, List<Integer> smallAns){
        if(tar == 0){
            List<Integer> base = new ArrayList<>(smallAns);
            ans.add(base);
            return;
        }
        
        int prev = -1;
        for(int i = idx; i < coins.length; i++){
            if(prev != coins[i] && tar - coins[i] >= 0){
                smallAns.add(coins[i]);
                findSum(coins, i + 1, tar - coins[i], ans, smallAns);
                smallAns.remove(smallAns.size() - 1);
                prev = coins[i];
            }
        }
    }

    // 322
    public int coinChange_(int[] coins, int amount) {
        if(amount == 0) return 0;
        int minAns = (int)1e9;
        for(int i = 0; i < coins.length; i++){
            if(amount - coins[i] >= 0){
                int recAns = coinChange_(coins, amount - coins[i]);
//                 bcoz we need some identification that we get ans 
                if(recAns != (int)1e9 && recAns < minAns){
                    minAns = recAns + 1;
                }
            }
        }
        
        return minAns;
    }
    
    public int coinChange(int[] coins, int amount){
        int ans = coinChange_(coins, amount);
        return ans != (int)1e9 ? ans : -1;
    }

    // 254
    public void getFactors(int n, int sp, List<List<Integer>> ans, List<Integer> smallAns){
        if(n <= 1){
            if(smallAns.size() > 1){
                List<Integer> baseAns = new ArrayList<>(smallAns);
                ans.add(baseAns);
            }

            return;
        }


        for(int i = sp; i < n; i++){
            if(n % i == 0){
                smallAns.add(i);
                getFactors(n / i, i, ans, smallAns);
                smallAns.remove(smallAns.size() - 1);
            }
        }
    }

    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        getFactors(n, 2, ans, smallAns);
        return ans;
    }

    // Sudoku Solver
    public static boolean isSafeToPlaceNumber(char[][] board, int r, int c, int num){
        // row
        for(int i = 0; i < 9; i++){
            if(board[r][i] - '0' == num){
                return false;
            }
        }

        // col
        for(int i = 0; i < 9; i++){
            if(board[i][c] - '0' == num){
                return false;
            }
        }

        // 3 * 3 matrix
        r = (r / 3) * 3;
        c = (c / 3) * 3;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[r + i][c + j] - '0' == num){
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(char[][] board, ArrayList<Integer> emptyIndex, int idx){
        if(idx == emptyIndex.size()){
            return true;     // sudoku solves
        }

        // From 1D -> 2D
        int cell = emptyIndex.get(idx);
        int r = cell / 9;
        int c = cell % 9;      

        for(int num = 1; num <= 9; num++){
            if(isSafeToPlaceNumber(board, r, c, num)){
                board[r][c] = (char)(num + '0');
                if(solveSudoku(board, emptyIndex, idx + 1)){
                    return true;          // We only want one answer
                }
                board[r][c] = '.';
            }
        }

        return false;
    }

    public static void solveSudoku(char[][] board){
        ArrayList<Integer> emptyIndex = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board[i][j] == '.'){
                    emptyIndex.add(i * 9 + j);  // From 2D -> 1D 
                }
            }
        }

        solveSudoku(board, emptyIndex, 0);
    }

    // Optimize isSafeToPlaceNumber using bits
    public static boolean solveSudoku_02(char[][] board, ArrayList<Integer> emptyIndex, int idx, int[] row, int[] col, int[][] mat){
        if(idx == emptyIndex.size()){
            return true;
        }

        int cell = emptyIndex.get(idx);
        int r = cell / 9;
        int c = cell % 9;
        for(int num = 1; num <= 9; num++){
            int mask = (1 << num);
            if((mask & row[r]) == 0 && (mask & col[c]) == 0 && (mask & mat[r / 3][c / 3]) == 0){
                row[r] ^= mask;  // set it as 1 (true)
                col[c] ^= mask;
                mat[r/3][c/3] ^= mask;

                board[r][c] = (char)(num + '0');  // place number

                if(solveSudoku_02(board, emptyIndex, idx + 1, row, col, mat)){
                    return true;
                }

                board[r][c] = '.';        // unplace number
                row[r] ^= mask;  // set it as 0 (false)
                col[c] ^= mask;
                mat[r/3][c/3] ^= mask;
            }
        }

        return false;
    }

    public static void solveSudoku_02(char[][] board){
        int[] row = new int[9];
        int[] col = new int[9];
        int[][] mat = new int[3][3];

        ArrayList<Integer> emptyIndex = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board[i][j] == '.'){
                    emptyIndex.add(i * 9 + j);  // From 2D -> 1D 
                }else{         // no: is present so we mark as true, we don't want recursive calls
                    int mask = (1 << board[i][j] - '0');
                    row[i] ^= mask;  // mark it as true
                    col[j] ^= mask;
                    mat[i/3][j/3] ^= mask; 
                }
            }
        }

        solveSudoku_02(board, emptyIndex, 0, row, col, mat);
    }

    // 36
    public static boolean isValidSudoku(char[][] board){
        int[] row = new int[9];
        int[] col = new int[9];
        int[][] mat = new int[3][3];

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board[i][j] != '.'){
                    int mask = (1 << board[i][j] - '0');
                    if((row[i] & mask) == 0 && (col[j] & mask) == 0 && (mat[i/3][j/3] & mask) == 0){
                        row[i] ^= mask;  // set as 1 (true)
                        col[j] ^= mask;
                        mat[i/3][j/3] ^= mask;
                    }else{
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // word break
    public static int wordBreak(String str, String ans, HashSet<String> dict, int longestWordLen){
        if(str.length() == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = 0; i < str.length(); i++){
            String nstr = str.substring(0, i + 1);
            if(nstr.length() > longestWordLen){
                break;
            }

            if(dict.contains(nstr)){
                count += wordBreak(str.substring(i + 1), ans + nstr + " ", dict, longestWordLen);
            }
        }

        return count;
    }   

    // Friends Pairing (String)
    public static int friendsPairing(String str, String ans, boolean[] used){
        int idx = 0;  // first unused friend
        while(idx < str.length()){
            if(!used[idx]){
                break;       // we get first unused friend
            }
            idx++;    
        }

        if(idx == str.length()){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        used[idx] = true;
        // Single
        count += friendsPairing(str, ans + "(" + str.charAt(idx) + ")", used);
        for(int i = idx + 1; i < str.length(); i++){
            if(!used[i]){
                String friend = "(" + str.charAt(idx) + " " + str.charAt(i) + ")";
                used[i] = true;
                // Pair
                count += friendsPairing(str, ans + friend, used);
                used[i] = false;
            }
        }
        used[idx] = false;
        return count;
    }

    // Friends Pairing (Number)
    public static int friendsPairing(int n, String ans, boolean[] used){
        int idx = 1;  // first unused friend
        while(idx <= n){
            if(!used[idx]){
                break;       // we get first unused friend
            }
            idx++;    
        }

        if(idx > n){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        used[idx] = true;
        // Single
        count += friendsPairing(n, ans + "(" + idx + ")", used);
        for(int i = idx + 1; i <= n; i++){
            if(!used[i]){
                String friend = "(" + idx + " " + i + ")";
                used[i] = true;
                // Pair
                count += friendsPairing(n, ans + friend, used);
                used[i] = false;
            }
        }
        used[idx] = false;
        return count;
    }

    // 179                                [3,30]
    public static String largestNumber(int[] nums){
        String[] arr = new String[nums.length];
        int idx = 0;
        for(int ele : nums){
            arr[idx++] = ele + "";
        }

        Arrays.sort(arr, (a,b) -> {
            String s1 = a + "" + b;     // 330
            String s2 = b + "" + a;     // 303
            return s2.compareTo(s1);  // other - this so big value comes first (priority)
        });
        
        // ["3","30"]
        StringBuilder sb = new StringBuilder();
        for(String ele : arr){
            sb.append(ele);    // 330
        }

        if(sb.charAt(0) == '0') return "0";
        return sb.toString();
    }

    // Crossword
    public static boolean isPossibleToPlace_H(char[][] board, String word, int r, int c){
        int m = board[0].length, l = word.length();
        if(c + l > m) return false;
        if(c == 0 && c + l < m && board[r][c + l] != '+') return false;
        if(c != 0 && c + l == m && board[r][c - 1] != '+') return false;
        if(c != 0 && c + l < m && (board[r][c + l] != '+' || board[r][c - 1] != '+')) return false;

        for(int i = 0; i < word.length(); i++){  // if we are placing char != present in board
            if(board[r][c + i] != '-' && word.charAt(i) != board[r][c + i])
                return false;
        }

        return true;
    }

    public static int place_H(char[][] board, String word, int r, int c){
        int loc = 0;  // for working as boolean
        for(int i = 0; i < word.length(); i++){
            if(board[r][c + i] == '-'){
                loc ^= (1 << i);  // mark as 1 (true) this word is placed
                board[r][c + i] = word.charAt(i);
            }
        }
        return loc;
    }

    public static void unplace_H(char[][] board, String word, int r, int c, int loc){
        for(int i = 0; i < word.length(); i++){
            int mask = (1 << i);
            if((loc & mask) != 0){ // those who are not marked as true (it ensure we place that char)
                board[r][c + i] = '-';
            }
        }
    }

    public static boolean isPossibleToPlace_V(char[][] board, String word, int r, int c){
        int n = board.length, l = word.length();
        if(r + l > n) return false;
        if(r == 0 && r + l < n && board[r + l][c] != '+') return false;
        if(r != 0 && r + l == n && board[r - 1][c] != '+') return false;
        if(r != 0 && r + l < n && (board[r + l][c] != '+' || board[r - 1][c] != '+')) return false;

        for(int i = 0; i < word.length(); i++){
            if(board[r + i][c] != '-' && board[r + i][c] != word.charAt(i))
                return false;
        }

        return true;
    }

    public static int place_V(char[][] board, String word, int r, int c){
        int loc = 0;
        for(int i = 0; i < word.length(); i++){
            if(board[r + i][c] == '-'){
                loc ^= (1 << i);
                board[r + i][c] = word.charAt(i);
            }
        }
        return loc;
    }

    public static void unplace_V(char[][] board, String word, int r, int c, int loc){
        for(int i = 0; i < word.length(); i++){
            int mask = (1 << i);
            if((loc & mask) != 0){
                board[r + i][c] = '-'; 
            }
        }
    }

    public static int crossword(char[][] board, String[] words, int idx){
        if(idx == words.length) return 1;
        String word = words[idx];
        int count = 0, n = board.length, m = board[0].length;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(board[i][j] == '-' || board[i][j] == word.charAt(0)){
                    if(isPossibleToPlace_H(board, word, i, j)){
                        int loc = place_H(board, word, i, j);
                        count += crossword(board, words, idx + 1);
                        unplace_H(board, word, i, j, loc);
                    }

                    if(isPossibleToPlace_V(board, word, i, j)){
                        int loc = place_V(board, word, i, j);
                        count += crossword(board, words, idx + 1);
                        unplace_V(board, word, i, j, loc);
                    }
                }
            }
        }

        return count;
    }

    // Gold Mine
    public static int goldMine(int[][] arr, int r, int c, int[][] dir){
        int n = arr.length, m = arr[0].length;
        if(c == m - 1) return arr[r][c];

        int myMaxAns = 0;
        for(int d = 0; d < dir.length; d++){
            int x = r + dir[d][0];
            int y = c + dir[d][1];
            if(x >= 0 && y >= 0 && x < n && y < m){
                int recMaxAns = goldMine(arr, x, y, dir);
                if(recMaxAns + arr[r][c] > myMaxAns)
                    myMaxAns = recMaxAns + arr[r][c];
            }
        }

        return myMaxAns;
    }

    public static int goldMine(int[][] arr) {
        int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
        int maxAns = 0;
        int n = arr.length, m = arr[0].length;
        for (int i = 0; i < n; i++) {
            maxAns = Math.max(maxAns, goldMine(arr, i, 0, dir));
        }

        return maxAns;
    }

    // k subset sum
    public static int kSubSetSum(int[] arr, int idx, int[] setSum, ArrayList<ArrayList<Integer>> sets){
        if(idx == arr.length){
            for(int i = 1; i < setSum.length; i++){
                if(setSum[i - 1] != setSum[i]) return 0;
            }

            for(ArrayList<Integer> set: sets){
                System.out.print(set + " ");
            }

            System.out.println();
            return 1;
        }

        int k = setSum.length, count = 0;
        for(int i = 0; i < k; i++){
            setSum[i] += arr[idx];
            sets.get(i).add(arr[idx]);

            count += kSubSetSum(arr, idx + 1, setSum, sets);

            setSum[i] -= arr[idx];
            sets.get(i).remove(sets.get(i).size() - 1);

            if(sets.get(i).size() == 0){  // he is now group leader, after that he can't perform any arrangement
                break;
            }
        }
        return count;
    }

    public static void kSubSetSum() {
        int k = 3;
        int[] arr = { 1, 2, 3, 4, 5, 6 };
        ArrayList<ArrayList<Integer>> sets = new ArrayList<>();
        for (int i = 0; i < k; i++)
            sets.add(new ArrayList<>());
        int[] setSum = new int[k];
        System.out.println(kSubSetSum(arr, 0, setSum, sets));
    }

    // K Partitions
    public static int kPartitions(String[] Character, int idx, int totalTeam, ArrayList<ArrayList<String>> ans){
        if(idx == Character.length){
            for(ArrayList<String> a : ans){
                if(a.size() == 0) return 0;
            }

            for(ArrayList<String> a : ans){
                System.out.print(a + " ");
            }

            System.out.println();
            return 1;
        }

        int count = 0;
        for(int i = 0; i < totalTeam; i++){
            ans.get(i).add(Character[idx]);
            count += kPartitions(Character, idx + 1, totalTeam, ans);
            ans.get(i).remove(ans.get(i).size() - 1);

            if(ans.get(i).size() == 0){  // group leader so he can't make arrangement
                break;
            }
        }
        return count;
    }

    public static void kPartitions() {
        int k = 3;
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        // String[] Character = { "Thor", "IronMan", "Hulk", "SpiderMan", "CaptainAmerica" };
        String[] Character = { "Thor", "IronMan", "SpiderMan"};
        for (int i = 0; i < k; i++)
            ans.add(new ArrayList<>());

        System.out.println(kPartitions(Character, 0, k, ans));
    }

    // CryptoArithmetic => send + more = money
    public static int StringToInteger(String s, int[] map){
        int res = 0;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            int mappedVal = map[ch - 'a'];
            res = res * 10 + mappedVal;
        }
        return res;
    }

    public static int crypto(String uniqueString, int idx, String s1, String s2, String s3, int[] map, boolean[] used){
        if(idx == uniqueString.length()){
            int n1 = StringToInteger(s1, map);
            int n2 = StringToInteger(s2, map);
            int n3 = StringToInteger(s3, map);

            if(n1 + n2 == n3){
                
                // if want ans in alphabetically order
                for(int i = 0; i < 26; i++){
                    if(map[i] != -1){
                        System.out.print((char) (i + 'a') + "-" + map[i] + " ");
                    }
                }
                System.out.println();
                return 1;
            }
            return 0;
        }

        int count = 0;
        char ch = uniqueString.charAt(idx);
        for(int num = 0; num <= 9; num++){  // if send (s = 0) or more (m = 0) then it's not suitable so check
            if((ch == s1.charAt(0) || ch == s2.charAt(0) || ch == s3.charAt(0)) && num == 0){
                continue;
            }

            if(!used[num]){
                used[num] = true;
                map[ch - 'a'] = num;
                count += crypto(uniqueString, idx + 1, s1, s2, s3, map, used);
                used[num] = false;
                map[ch - 'a'] = -1;
            }
        }
        return count;
    }

    public static void crypto(String s1, String s2, String s3){
        String s = s1 + s2 + s3;
        boolean[] freq = new boolean[26];  // make uniqueString bcoz at a time only one val assign to particular character
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            freq[ch - 'a'] = true;
        }   

        s = "";
        for(int i = 0; i < 26; i++){
            if(freq[i]){
                s += (char)(i + 'a');
            }
        }
       
        int[] map = new int[26];
        Arrays.fill(map,  -1);
        boolean[] isUsed = new boolean[10];
        System.out.println(crypto(s, 0, s1, s2, s3, map, isUsed));
    }

    public static void main(String[] args){
        // kPartitions();
        crypto("send", "more", "money");
    }
}