package com.oc.schoolapi.dto;

import java.util.Set;

public record SchoolClassDto(
        String className,
        Long schoolSubjectId,
        Long teacherId,
        Set<Long> studentsIds
) {
}
