package com.oc.schoolapi.dto;

import com.oc.schoolapi.model.UserType;

import java.time.LocalDate;
import java.util.List;

/**
 * Student Data Transfert Object to get data from user
 * @param firstName
 * @param lastName
 * @param email
 * @param password
 * @param birthDate
 * @param enrolledSchoolClassesIds
 */
public record StudentDto(
        String firstName,
        String lastName,
        String email,
        String password,
        List<UserType> roles,
        LocalDate birthDate,
        List<Long> enrolledSchoolClassesIds
) {
}
