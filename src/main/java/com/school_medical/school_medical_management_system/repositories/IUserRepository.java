package com.school_medical.school_medical_management_system.repositories;

import java.util.List;
import java.util.Optional;

import com.school_medical.school_medical_management_system.repositories.entites.Parent;
import org.springframework.security.core.userdetails.UserDetails;

import com.school_medical.school_medical_management_system.repositories.entites.Appuser;

public interface IUserRepository {
    UserDetails findUserByEmail(String email);
    Optional<Appuser> findByAccountNumber(String accountNumber);
    Appuser getUserByEmail(String username);
    List<Appuser> getAllNurses();
    List<String> getAllUserEmails();
    List<Appuser> getAllUsers();
    Appuser saveUser(Appuser user);
    void saveParent(Parent parent);
}
