package com.oc.schoolapi.dto;

/**
 * Authentication request DTO record
 */
public record AuthenticationRequestDto(
        String email,
        String password
) {
}
