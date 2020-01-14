package com.friday.addressparser.controller;

import com.friday.addressparser.dto.response.AddressDTO;
import com.friday.addressparser.exception.BadRequestException;
import com.friday.addressparser.service.AddressParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.friday.addressparser.util.AddressUtils.cleanAddressString;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressParser addressParser;

    /**
     * Parses the address string from request param.
     * Using request variable instead of path variable because we can extend the functionality like country based address parsing
     */
    @GetMapping("/parse")
    public AddressDTO parseAddress(@RequestParam("addressString") String addressString) {
        String cleanedAddressString = Optional.ofNullable(cleanAddressString(addressString))
                .orElseThrow(() -> new BadRequestException("Address string cannot be empty"));
        return addressParser.parseAddress(cleanedAddressString);
    }
}
