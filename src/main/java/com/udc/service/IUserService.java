package com.udc.service;

import org.springframework.ui.Model;
import com.udc.model.PersonalData;
import com.udc.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<String> login(User user, Model model) throws Exception;
    ResponseEntity<String> register(User user, Model model) throws Exception;

    ResponseEntity<?> savePersonalData(PersonalData personalData) throws Exception;
    ResponseEntity<?> getPersonalData(Long id) throws Exception;
}
