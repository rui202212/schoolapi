package com.oc.schoolapi.dto;

import java.util.Set;

public record SchoolSubjectDto(
        String subjectName,
        String description,
        Set<Long> teachersIds
) {
}
