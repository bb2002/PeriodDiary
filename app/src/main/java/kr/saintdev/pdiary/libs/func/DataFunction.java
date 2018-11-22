package kr.saintdev.pdiary.libs.func;

import java.util.ArrayList;
import java.util.List;

public class DataFunction {
    public static List<Integer> getAllYears() {
        List<Integer> years = new ArrayList<Integer>();
        for(int i = 1970; i <= 2099; i ++) {
            years.add(i);
        }

        return years;
    }

    public static List<Integer> getAllMonths() {
        List<Integer> years = new ArrayList<Integer>();
        for(int i = 1; i <= 12; i ++) {
            years.add(i);
        }

        return years;
    }
}
