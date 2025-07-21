package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import java.util.List;

public interface IAppUserService {
    Appuser getUserByEmail(String email);
    List<Appuser> getAllNurses();
    List<Appuser> getAllUsers();
    Appuser registerUser(Appuser user);
}

