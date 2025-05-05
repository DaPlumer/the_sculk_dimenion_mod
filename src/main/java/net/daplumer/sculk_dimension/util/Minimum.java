package net.daplumer.sculk_dimension.util;

import java.util.Arrays;
@SuppressWarnings("All")
public class Minimum {
    @SafeVarargs
    public static <T extends Comparable<T>> T of (T... vals){
        return Arrays.stream(vals).reduce((a,b)->a.compareTo(b)<0?a:b).get();
    }
}
