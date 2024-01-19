package com.szogunn.demonextdoorbook.servicesImpl;

import com.szogunn.demonextdoorbook.model.User;
import com.szogunn.demonextdoorbook.payloads.SignupRequest;
import com.szogunn.demonextdoorbook.repositories.OwnerRepository;
import com.szogunn.demonextdoorbook.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final OwnerRepository ownerRepository;

    public UserServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public ResponseEntity<String> signUp(SignupRequest signupRequest) {
        if (ownerRepository.existsOwnerByEmail(signupRequest.email())){
            return new ResponseEntity<>("Error: Email is already taken", HttpStatus.BAD_REQUEST);
        }

        ownerRepository.save(new User(signupRequest.login(),signupRequest.password(),signupRequest.email(), signupRequest.address()));
        return new ResponseEntity<>("Successfully Registered", HttpStatus.OK);
    }

}
