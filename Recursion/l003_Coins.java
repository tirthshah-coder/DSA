public class l003_Coins{
    // coins -> [2, 3, 5, 7]   tar = 10

    // permutation Infinite coins
    public static int permutationInfi(int[] arr, int tar, String ans){
        if(tar == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = 0; i < arr.length; i++){
            if(tar - arr[i] >= 0)
                count += permutationInfi(arr, tar - arr[i], ans + arr[i]);
        }
        return count;
    }

    // combination Infinite coins
    public static int combinationInfi(int[] arr, int tar, int idx, String ans){
        if(tar == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = idx; i < arr.length; i++){
            if(tar - arr[i] >= 0)
                count += combinationInfi(arr, tar - arr[i], i, ans + arr[i]);
        }
        return count;
    }

    // combination single coin
    public static int combinationSingle(int[] arr, int tar, int idx, String ans){
        if(tar == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = idx; i < arr.length; i++){
            if(tar - arr[i] >= 0)
                count += combinationSingle(arr, tar - arr[i], i + 1, ans + arr[i]);
        }

        return count;
    }

    // permutation single coin
    // soln 1 : using boolean array
    public static int permutationSingle_01(int[] arr, int tar, String ans, boolean[] vis){
        if(tar == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = 0; i < arr.length; i++){
            if(!vis[i] && tar - arr[i] >= 0){
                vis[i] = true;
                count += permutationSingle_01(arr, tar - arr[i], ans + arr[i], vis);
                vis[i] = false;
            }
        }
        return count;
    }
    
    // soln 2 : without using boolean array
    public static int permutationSingle_02(int[] arr, int tar, String ans){
        if(tar == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = 0; i < arr.length; i++){
            // not visited then only
            if(arr[i] > 0 && tar - arr[i] >= 0){
                int val = arr[i];
                arr[i] = -val;      // visited
                count += permutationSingle_02(arr, tar - val, ans + val);
                arr[i] = val;       // unvisited
            }
        }
        return count;
    }

    // ===================================================
    // coins using subsequence

    // permutation Infinite
    public static int permutationInfi_sub(int[] arr, int tar, int idx, String ans){
        if(tar == 0 || idx == arr.length){
            if(tar == 0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(tar - arr[idx] >= 0){
            count += permutationInfi_sub(arr, tar - arr[idx], 0, ans + arr[idx]);
        }
         count += permutationInfi_sub(arr, tar, idx + 1, ans);
         return count;
    }

    // combination Infinite
    public static int combinationInfi_sub(int[] arr, int tar, int idx, String ans){
        if(tar == 0 || idx == arr.length){
            if(tar == 0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(tar - arr[idx] >= 0){
            count += combinationInfi_sub(arr, tar - arr[idx], idx, ans + arr[idx]);
        }
        count += combinationInfi_sub(arr, tar, idx + 1, ans);
        return count;
    }

    // combination Single
    public static int combinationSingle_sub(int[] arr, int tar, int idx, String ans){
        if(tar == 0 || idx == arr.length){
            if(tar == 0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(tar - arr[idx] >= 0){
            count += combinationSingle_sub(arr, tar - arr[idx], idx + 1, ans + arr[idx]);
        }    
        count += combinationSingle_sub(arr, tar, idx + 1, ans);
        return count;
    }

    // permutation single
    public static int permutationSingle_sub(int[] arr, int tar, int idx, String ans){
        if(tar == 0 || idx == arr.length){
            if(tar == 0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        if(arr[idx] > 0 && tar - arr[idx] >= 0){
            int val = arr[idx];
            arr[idx] = -val;     // visited 
            count += permutationSingle_sub(arr, tar - val, 0, ans + val);
            arr[idx] = val;      // unvisited
        }
        count += permutationSingle_sub(arr, tar, idx + 1, ans);
        return count;
    }


    public static void main(String[] args){
        int[] arr = {2,3,5,7};
        int tar = 10;
        // boolean[] vis = new boolean[arr.length];
        int res = permutationSingle_sub(arr, tar, 0, "");
        System.out.println(res);
    }
}