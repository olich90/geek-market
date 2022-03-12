package ru.gb.springbootdemoapp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springbootdemoapp.model.RegistrationToken;
import ru.gb.springbootdemoapp.repository.AuthorityRepository;
import ru.gb.springbootdemoapp.repository.RegistrationTokenRepository;
import ru.gb.springbootdemoapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityRepository authorityRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final EmailService emailService;

    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthorityRepository authorityRepository, RegistrationTokenRepository registrationTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityRepository = authorityRepository;
        this.registrationTokenRepository = registrationTokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void sighUp(String email, String password) {
        ru.gb.springbootdemoapp.model.User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            if (user.getEnabled()){
                throw new IllegalStateException("Пользователь уже существует");
            }
            createAndSendToken(user, email);
            return;
        }
        var newUser = new ru.gb.springbootdemoapp.model.User();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setEnabled(false);
        newUser.setAuthorities(Set.of(authorityRepository.findByName("ROLE_USER")));
        userRepository.save(newUser);

        createAndSendToken(newUser, email);
    }

    private void createAndSendToken (ru.gb.springbootdemoapp.model.User user, String email) {
        String tokenUid = UUID.randomUUID().toString();
        registrationTokenRepository.save(new RegistrationToken(tokenUid, LocalDateTime.now().plusMinutes(1), user));
        emailService.sendVarificationLink(email, tokenUid);
    }

    @Transactional
    public boolean confirmRegistration(String token) {
        var user = registrationTokenRepository.findUserByToken(LocalDateTime.now(), token);
        if (user.isEmpty()) {
            throw new IllegalStateException("Ссылка для подтверждения регистрации устарела...");
        }
        user.ifPresent(u -> u.setEnabled(true));
        return true;
    }
}
