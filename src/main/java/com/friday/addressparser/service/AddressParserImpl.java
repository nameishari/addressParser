package com.friday.addressparser.service;

import com.friday.addressparser.dto.response.AddressDTO;
import com.friday.addressparser.util.AddressUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static java.util.Arrays.asList;
import static com.friday.addressparser.util.ListUtils.indexOfIgnoreCase;

@Service
public class AddressParserImpl implements AddressParser {

    @Override
    public AddressDTO parseAddress(final String addressString) {
        List<String> addressWords = new ArrayList<>(asList(addressString.split(SPACE)));
        final String houseNumber = getHouseNumber(addressWords);
        final String street = getStreet(addressWords);
        return new AddressDTO(street, houseNumber);
    }

    private String getHouseNumber(List<String> addressWords) {
        final int numberStringIndex = indexOfIgnoreCase(addressWords, "No");
        List<String> houseNumberWords = new ArrayList<>();
        if (numberStringIndex > 0) {
            houseNumberWords.add(addressWords.get(numberStringIndex));
            houseNumberWords.add(addressWords.get(numberStringIndex + 1));
        } else {
            houseNumberWords.addAll(findHouseNumbers(addressWords));
        }
        houseNumberWords.forEach(addressWords::remove);
        return String.join(SPACE, houseNumberWords);
    }

    private List<String> findHouseNumbers(List<String> addressWords) {
        List<String> houseNumberWords = new ArrayList<>();
        String houseNumber = addressWords.stream()
                .filter(NumberUtils::isDigits)
                .filter(addressWord -> !isPostalCode(addressWord))
                .findFirst()
                .orElseGet(() ->
                        addressWords.stream()
                                .filter(AddressUtils::hasAllDigitsWithOnlyOneCharacter)
                                .findFirst()
                                .orElse(EMPTY)
                );
        houseNumberWords.add(houseNumber);
        final int index = addressWords.indexOf(houseNumber);
        if (!AddressUtils.hasAllDigitsWithOnlyOneCharacter(houseNumber) && index + 1 < addressWords.size()) {
            String maybeSubHouseNumber = addressWords.get(index + 1);
            if (maybeSubHouseNumber.length() == 1 && Character.isLetter(maybeSubHouseNumber.charAt(0))) {
                houseNumberWords.add(maybeSubHouseNumber);
            }
        }
        return houseNumberWords;
    }

    private String getStreet(List<String> addressWords) {
        return addressWords.stream()
                .filter(addressWord -> !isCountryOrStateOrCityOrDistrict(addressWord))
                .collect(Collectors.joining(SPACE));
    }

    /**
     * In real time address may contain postal code as well.
     * assuming it wont exist in the input string now.
     */
    private boolean isPostalCode(String val) {
        return false;
    }

    /**
     * In real time address may contain country, state, city and district
     * assuming it wont exist in the input string now.
     */
    private boolean isCountryOrStateOrCityOrDistrict(String val) {
        return false;
    }
}
