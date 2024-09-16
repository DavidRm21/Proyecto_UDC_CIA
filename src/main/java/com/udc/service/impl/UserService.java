package com.udc.service.impl;

import com.udc.model.PersonalData;
import com.udc.model.User;
import com.udc.repository.IPersonalDetailRepository;
import com.udc.repository.IUserRepository;
import com.udc.service.IUserService;
import com.udc.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IPersonalDetailRepository personalDetailRepository;
    private final IUserRepository userRepository;
    private final EncryptionUtils encryptionUtils;

    @Override
    public ResponseEntity<String> login(User user, Model model)  {
        Optional<User> userAuth = userRepository.findByUsername(user.getUsername());
        String password = (user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
            if (userAuth.isPresent() && password.equals(userAuth.get().getPassword())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña o usuario no valida");
            }
    }

    @Override
    public ResponseEntity<String> register(User user, Model model) throws Exception {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        user.setPassword((user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("El usuario ha sido registrado exitosamente.");
    }

    @Override
    public ResponseEntity<?> savePersonalData(PersonalData personalData) throws Exception {
        PersonalData encrypData = PersonalData.builder()
                .name(encryptionUtils.encrypt(personalData.getName()))
                .surname(encryptionUtils.encrypt(personalData.getSurname()))
                .age(encryptionUtils.encrypt(personalData.getAge()))
                .city(encryptionUtils.encrypt(personalData.getCity()))
                .address(encryptionUtils.encrypt(personalData.getAddress()))
                .phone(encryptionUtils.encrypt(personalData.getPhone()))
                .idNumber(encryptionUtils.encrypt(personalData.getIdNumber()))
                .neighborhood(encryptionUtils.encrypt(personalData.getNeighborhood()))
                .build();
        personalDetailRepository.save(encrypData);
        return ResponseEntity.ok("Se ha guardado con exito " + encrypData.getName());
    }

    @Override
    public ResponseEntity<?> getPersonalData(Long id) throws Exception {
        Optional<PersonalData> personalData = personalDetailRepository.findById(id);
        if (!personalData.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        PersonalData decryptData = PersonalData.builder()
                .name(encryptionUtils.decrypt(personalData.get().getName()))
                .surname(encryptionUtils.decrypt(personalData.get().getSurname()))
                .age(encryptionUtils.decrypt(personalData.get().getAge()))
                .city(encryptionUtils.decrypt(personalData.get().getCity()))
                .address(encryptionUtils.decrypt(personalData.get().getAddress()))
                .phone(encryptionUtils.decrypt(personalData.get().getPhone()))
                .idNumber(encryptionUtils.decrypt(personalData.get().getIdNumber()))
                .neighborhood(encryptionUtils.decrypt(personalData.get().getNeighborhood()))
                .build();
        return ResponseEntity.ok(decryptData);
    }
}
