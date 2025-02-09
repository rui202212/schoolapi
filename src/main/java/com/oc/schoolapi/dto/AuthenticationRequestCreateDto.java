package com.oc.schoolapi.dto;

import com.oc.schoolapi.model.UserType;

import java.util.List;

/**
 * Authentication request DTO record
 */
public record AuthenticationRequestCreateDto(
        String firstName,
        String lastName,
        String email,
        String password,
        List<UserType> roles,
        UserType userType
) {
}
