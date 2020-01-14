package com.friday.addressparser.unittest.util;


import com.friday.addressparser.util.AddressUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


class AddressUtilsTest {

    @Test
    void testShouldCleanTheAddressString() {
        getCleanAddressStringTestCases().forEach((addressString, expectedResult) -> {
            final String cleanAddressString = AddressUtils.cleanAddressString(addressString);
            assertThat(cleanAddressString, is(expectedResult));
        });
    }

    @Test
    void testHasAllDigitsWithOnlyOneCharacter() {
        getHasAllDigitsWithOnlyOneCharacterTestCases().forEach((value,  expectedResult) -> {
            final boolean hasAllDigitsWithOnlyOneCharacter = AddressUtils.hasAllDigitsWithOnlyOneCharacter(value);
            assertThat(hasAllDigitsWithOnlyOneCharacter, is(expectedResult));
        });
    }

    private Map<String,String> getCleanAddressStringTestCases() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put(null, null);
        testCases.put(StringUtils.EMPTY, null);
        testCases.put(StringUtils.SPACE, null);
        testCases.put(" Winterallee   3 ", "Winterallee 3");
        testCases.put("4,  rue de la revolution", "4 rue de la revolution");
        return testCases;
    }

    private Map<String, Boolean> getHasAllDigitsWithOnlyOneCharacterTestCases() {
        Map<String, Boolean> testCases = new HashMap<>();
        testCases.put(null, false);
        testCases.put(StringUtils.EMPTY, false);
        testCases.put(StringUtils.SPACE, false);
        testCases.put("223", false);
        testCases.put("2335B", true);
        testCases.put("2335b", true);
        testCases.put("233eb", false);
        return testCases;
    }
}
