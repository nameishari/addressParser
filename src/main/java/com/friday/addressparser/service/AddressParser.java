package com.friday.addressparser.service;

import com.friday.addressparser.dto.response.AddressDTO;

public interface AddressParser {
    AddressDTO parseAddress(String address);
}
