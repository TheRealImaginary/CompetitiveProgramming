package Topcoder;

public class SRM_728_D2_250_HalvingEasy {

    public static int count(int[] a, int t) {
        int res = 0;
        for (int x : a) {
            while (x > t)
                x >>= 1;
            if (x == t)
                res++;
        }
        return res;
    }
}
