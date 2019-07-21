package com.nsv.jsmbaba;

public class StringUtil {
    public boolean checkIfStringHasDuplicateChars(String data) {
        boolean hasDuplicates = false;

        if (data == null || data.isEmpty()) {
        } else {
            char[] chars = data.toCharArray();
            for (char c : chars) {
                if (data.indexOf(c) != data.lastIndexOf(c)) {
                    hasDuplicates = true;
                    break;
                }
            }
        }

        return hasDuplicates;
    }
}
