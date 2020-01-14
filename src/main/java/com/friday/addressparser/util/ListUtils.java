package com.friday.addressparser.util;

import java.util.List;

public final class ListUtils {

    public static int indexOfIgnoreCase(List<String> list, String indexToBeFound) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).equalsIgnoreCase(indexToBeFound)) {
                return index;
            }
        }
        return -1;
    }
}
