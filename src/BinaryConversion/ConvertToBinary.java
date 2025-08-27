package BinaryConversion;

import java.util.ArrayList;
import java.util.List;

public class ConvertToBinary {
    public static void main(String[] args) {
        int test = 102;
        String result = convertToBinary(test).toString();
        System.out.println(result);

        int test2 = 102;
        String result2 = convertToBinary2(test2).toString();
        System.out.println(result2);

        int test3 = 10201;
        String result3 = convertToBinary(test3).toString();
        System.out.println(result3);
        int test4 = 10201;

        String result4 = convertToBinary2(test4).toString();
        System.out.println(result4);
    }

    private static List<Integer> convertToBinary(int n) {

        int tmp = 1, i = 0;
        while (tmp * 2 <= n) { // find largest num that is a power of 2 but less or equal than n
            tmp *= 2;
            i++; // at the same time, updating i to the rightmost position
        }

        List<Integer> result = new ArrayList<>(i + 1); // prepare the list
        for (int j = 0; j <= i; j++) {
            result.add(0);
        }

        while (n > 0) { // this step is for putting digits in the position
            if (n >= tmp) { // tmp is largest power of 2, if n >= tmp, means we should put 1 at that position
                result.set(i, 1); // put 1 in i position
                n -= tmp; // update n, we need take care the remain part
            }
            i -= 1; // update i to one left
            tmp /= 2; // update tmp
        }
        //Collections.reverse(result);
        return result;
    }

    private static List<Integer> convertToBinary2(int n) {
        List<Integer> ans = new ArrayList<>();
        while (n > 0) {
            if (n % 2 == 0) {
                ans.add(0);
            } else {
                ans.add(1);
            }
            n /= 2;
        }
        //Collections.reverse(ans);
        return ans;
    }
}
