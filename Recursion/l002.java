public class l002{
    // Equal set
    public static int equalSet(int[] arr, int idx, int sum1, int sum2, String set1, String set2){
        if(idx == arr.length){
            if(sum1 == sum2){
                System.out.println(set1 + " = " + set2);
                return 1;
            }
            return 0;
        }

        int count = 0;
        count += equalSet(arr, idx + 1, sum1 + arr[idx], sum2, set1 + arr[idx] + " ", set2);
        count += equalSet(arr, idx + 1, sum1, sum2 + arr[idx], set1, set2 + arr[idx] + " ");
        return count;
    }

    // Permutation
    public static int permutation(String ques, String ans){
        if(ques.length() == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for(int i = 0; i < ques.length(); i++){
            char ch = ques.charAt(i);
            String ros = ques.substring(0, i) + ques.substring(i + 1);
            count += permutation(ros, ans + ch);
        }
        return count;
    }

    // Permutation Unique
    // soln 1: Using boolean arr
    public static int permutationUnique(String ques, String ans){
        if(ques.length() == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        boolean[] vis = new boolean[26];
        for(int i = 0; i < ques.length(); i++){
            char ch = ques.charAt(i);
            if(!vis[ch - 'a']){
                vis[ch - 'a'] = true;
                String ros = ques.substring(0, i) + ques.substring(i + 1);
                count += permutationUnique(ros, ans + ch);
            }
        }
        return count;
    }

    // soln 2: Using prev and curr
    public static int permutationUnique2(String ques, String ans){
        if(ques.length() == 0){
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        char prev = '$';
        for(int i = 0; i < ques.length(); i++){
            char curr = ques.charAt(i);
            if(prev != curr){
                String ros = ques.substring(0, i) + ques.substring(i + 1);
                count += permutationUnique2(ros, ans + curr);
            }

            prev = curr;
        }
        return count;
    }

    public static void main(String[] args){
        // int[] arr = {10, 20, 30, 40, 50, 60, 70, 80 };
        // int ans = equalSet(arr, 1, arr[0], 0, arr[0] + " ", "");
        // System.out.println(ans);

        int ans = permutationUnique2("aaa", "");
        System.out.println(ans);
    }
}