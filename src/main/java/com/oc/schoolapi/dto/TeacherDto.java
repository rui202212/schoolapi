package com.oc.schoolapi.dto;

import java.util.Set;

public record TeacherDto(
        String firstName,
        String lastName,
        String email,
        String password,
        Set<Long> subjectsTaughtIds,
        Set<Long> assignedSchoolClassesIds
) {
}
