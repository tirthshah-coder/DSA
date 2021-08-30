public class l004_Queens{
    // Queen Combination
    public static int queenComb(int tnb, int tnq, int bno, int qno, String ans){
        if(qno == tnq){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = bno; i < tnb; i++){
            count += queenComb(tnb, tnq, i + 1, qno + 1, ans + "b" + i + "q" + qno + " ");
        }
        return count;
    }

    // Queen Permutation
    public static int queenPermu(int tnb, int tnq, int bno, int qno, String ans, boolean[] vis){
        if(qno == tnq){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = bno; i < tnb; i++){
            if(!vis[i]){
                vis[i] = true;
                count += queenPermu(tnb, tnq, 0, qno + 1, ans + "b" + i + "q" + qno + " ", vis);
                vis[i] = false;
            }
        }
        return count;
    }

    // Queen Combination 2D
    public static int queenComb2D(boolean[][] board, int tnq, int bno, String ans){
        if(tnq == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0, n = board.length, m = board[0].length;
        for(int i = bno; i < n * m; i++){
            int r = i % m;
            int c = i / m;
            count += queenComb2D(board, tnq - 1, i + 1, ans + "(" + r + "," + c + ")");
        }
        return count;
    }

    // Queen Permutation 2D
    public static int queenPermu2D(boolean[][] board, int tnq, int bno, String ans){
        if(tnq == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0, n = board.length, m = board[0].length;
        for(int i = bno; i < n * m; i++){
            int r = i % m;
            int c = i / m;
            if(!board[r][c]){
                board[r][c] = true;
                count += queenPermu2D(board, tnq - 1, 0, ans + "(" + r + "," + c + ")");
                board[r][c] = false;
            }
        }
        return count;
    }

    // ================================= N Queens ==================================
    // Combination
    public static boolean isSafeToPlaceQueen(boolean[][] board, int row, int col){
        int[][] dir = {{0,-1},{-1,-1},{-1,0},{-1,1}};
        int n = board.length, m = board[0].length;
        for(int d = 0; d < dir.length; d++){
            for(int rad = 1; rad < Math.max(n,m); rad++){
                int r = row + rad * dir[d][0];
                int c = col + rad * dir[d][1];
                if(r >= 0 && c >= 0 && r < n && c < m){
                    if(board[r][c]){
                        return false;
                    }
                }else{
                    break;
                }
            }
        }
        return true;
    }

    public static int nqueen_comb(boolean[][] board, int tnq, int idx, String ans){
        if(tnq == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0, n = board.length, m = board[0].length;
        for(int i = idx; i < n * m; i++){
            int r = i / m;
            int c = i % m;
            if(isSafeToPlaceQueen(board, r, c)){
                board[r][c] = true;
                count += nqueen_comb(board, tnq - 1, i + 1, ans + "(" + r + "," + c + ")");
                board[r][c] = false;
            }
        }
        return count;
    }

    // Nqueen Comb with subsequence
    public static int nqueen_comb_sub(boolean[][] board, int tnq, int idx, String ans){
        int n = board.length, m = board[0].length;
        if(tnq == 0 || idx == n * m){
            if(tnq == 0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        int r = idx / m;
        int c = idx % m;
        if(isSafeToPlaceQueen(board, r, c)){
            board[r][c] = true;
            count += nqueen_comb_sub(board, tnq - 1, idx + 1, ans + "(" + r + "," + c + ")");
            board[r][c] = false;
        }
        count += nqueen_comb_sub(board, tnq, idx + 1, ans);
        return count;
    }

    public static int nqueen_permu(boolean[][] board, int tnq, int idx, String ans){
        if(tnq == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0, n = board.length, m = board[0].length;
        for(int i = idx; i < n * m; i++){
            int r = i / m;
            int c = i % m;
            // !board[r][c] bcoz humne rad = 1 se start kiya hai 0 se nai 
            if(isSafeToPlaceQueen(board, r, c) && !board[r][c]){
                board[r][c] = true;
                count += nqueen_permu(board, tnq - 1, 0, ans + "(" + r + "," + c + ")");
                board[r][c] = false;
            }
        }
        return count;
    }

    // Optimize isSafeToPlaceQueen time (4n -> O(1))
    public static int nqueen_comb_02(int n, int m, int tnq, int idx, String ans, boolean[] row, boolean[] col, boolean[] diag, boolean[] adiag){
        if(tnq == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = idx; i < n * m; i++){
            int r = i / m;
            int c = i % m;
            if(!row[r] && !col[c] && !diag[r + c] && !adiag[r - c + m - 1]){
                row[r] = col[c] = diag[r + c] = adiag[r - c + m - 1] = true;
                count += nqueen_comb_02(n, m, tnq - 1, i + 1, ans + "(" + r + "," + c + ")", row, col, diag, adiag);
                row[r] = col[c] = diag[r + c] = adiag[r - c + m - 1] = false;
            }
        }
        return count;
    }

    // Optimize recursion call (floor)
    public static int nqueen_comb_03(int n, int m, int tnq, int r, String ans, boolean[] row, boolean[] col, boolean[] diag, boolean[] adiag){
        if(tnq == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int c = 0; c < m; c++){
            if(!row[r] && !col[c] && !diag[r + c] && !adiag[r - c + m - 1]){
                row[r] = col[c] = diag[r + c] = adiag[r - c + m - 1] = true;
                count += nqueen_comb_03(n, m, tnq - 1, r + 1, ans + "(" + r + "," + c + ")", row, col, diag, adiag);
                row[r] = col[c] = diag[r + c] = adiag[r - c + m - 1] = false;
            }
        }

        return count;
    }

    // permutation 
    public static int nqueen_permu_03(int n, int m, int tnq, int r, String ans, boolean[] row, boolean[] col, boolean[] diag, boolean[] adiag){
        if(tnq == 0 || r == n){
            if(tnq == 0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        for(int c = 0; c < m; c++){
            if(!row[r] && !col[c] && !diag[r + c] && !adiag[r - c + m - 1]){
                row[r] = col[c] = diag[r + c] = adiag[r - c + m - 1] = true;
                count += nqueen_permu_03(n, m, tnq - 1, 0, ans + "(" + r + "," + c + ")", row, col, diag, adiag);
                row[r] = col[c] = diag[r + c] = adiag[r - c + m - 1] = false;
            }
        }
        
        // Queen decided to place on other floor
        count += nqueen_permu_03(n, m, tnq, r + 1, ans, row, col, diag, adiag);  
        return count;
    }

    // Leetcode 51: NQueens
    boolean[] rows;
    boolean[] cols;
    boolean[] diag;
    boolean[] adiag;
    List<List<String>> ans = new ArrayList<>();
    
    public int nQueen_Comb(boolean[][] boxes, int tnq, int idx){
        int n = boxes.length, m = boxes[0].length;
        if(tnq == 0){
            ArrayList<String> smallAns = new ArrayList<>();
            for(int i = 0; i < n; i++){
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < m; j++){
                    sb.append(boxes[i][j] ? "Q" : "."); // true then place 'Q ', else '.'
                }
                
                smallAns.add(sb.toString());
            }
            ans.add(smallAns);
            return 1;
        }
        
        int count = 0;
        for(int i = idx; i < n * m; i++){
            int r = i / m;
            int c = i % m;
            if(!rows[r] && !cols[c] && !diag[r + c] && !adiag[r - c + m - 1]){
                rows[r] = cols[c] = diag[r + c] = adiag[r - c + m - 1] = boxes[r][c] = true;
                count += nQueen_Comb(boxes, tnq - 1, i + 1);
                rows[r] = cols[c] = diag[r + c] = adiag[r - c + m - 1] = boxes[r][c] = false;
            }
        }
        
        return count;
    }
    
    public List<List<String>> solveNQueens(int n) {
        int m = n;
        boolean[][] boxes = new boolean[n][m];
        rows = new boolean[n];
        cols = new boolean[m];
        diag = new boolean[n + m - 1];
        adiag = new boolean[n + m - 1];
        
        nQueen_Comb(boxes, n, 0);
        return ans;
    }

    // Using O(1) space bits NQueen
    public static int nqueen_comb_04(int n, int r, int col, int diag, int adiag){
        if(r == n){
            return 1;
        }

        int count = 0, m = n;
        for(int c = 0; c < m; c++){
            if((col & (1 << c)) == 0 && (diag & (1 << r + c)) == 0 && (adiag & (1 << r - c + m - 1)) == 0){
                col ^= (1 << c);  // set to 1 as true
                diag ^= (1 << r + c);
                adiag ^= (1 << r - c + m - 1);
                count += nqueen_comb_04(n, r + 1, col, diag, adiag);
                col ^= (1 << c);  // set to 0 as false
                diag ^= (1 << r + c);
                adiag ^= (1 << r - c + m - 1);
            }
        }
        return count;
    }

    public static void main(String[] args){
        int n = 4, m = 4, tnq = 4;
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[m];
        boolean[] diag = new boolean[n + m - 1];
        boolean[] adiag = new boolean[n + m - 1];
        // int ans = nqueen_permu_03(n, m, tnq, 0, "", row, col, diag, adiag);
        int ans = nqueen_comb_04(n, 0, 0, 0, 0);
        System.out.println(ans);
    }
}