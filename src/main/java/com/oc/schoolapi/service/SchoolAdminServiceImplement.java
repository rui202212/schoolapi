package com.oc.schoolapi.service;

import com.oc.schoolapi.model.SchoolAdmin;
import com.oc.schoolapi.repository.SchoolAdminRepository;
import org.springframework.stereotype.Service;

@Service
public class SchoolAdminServiceImplement extends UserServiceImplement<SchoolAdmin, SchoolAdminRepository> {
    protected SchoolAdminServiceImplement(SchoolAdminRepository repository) {
        super(repository);
    }
}
