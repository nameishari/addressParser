package com.friday.addressparser.util;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

public final class AddressUtils {

    private static final String COMMA = ",";

    public static String cleanAddressString(String addressString) {
        return isBlank(addressString) ? null : normalizeSpace(addressString.replaceAll(COMMA, SPACE));
    }

    public static boolean hasAllDigitsWithOnlyOneCharacter(String value) {
        if (isBlank(value) || value.length() == 1) {
            return false;
        }

        int charCount = 0;
        int digitCount = 0;
        for (char ch : value.toCharArray()) {
            if (Character.isLetter(ch)) {
                charCount++;
            }
            if (Character.isDigit(ch)) {
                digitCount++;
            }
        }
        return charCount == 1 && digitCount == value.length() - 1;
    }
}
