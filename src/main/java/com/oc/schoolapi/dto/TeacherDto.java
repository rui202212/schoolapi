package com.oc.schoolapi.dto;

import com.oc.schoolapi.model.UserType;

import java.util.List;
import java.util.Set;

public record TeacherDto(
        String firstName,
        String lastName,
        String email,
        String password,
        List<UserType> roles,
        Set<Long> subjectsTaughtIds,
        Set<Long> assignedSchoolClassesIds
) {
}
