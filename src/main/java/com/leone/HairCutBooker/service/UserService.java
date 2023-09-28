package com.leone.HairCutBooker.service;

import com.leone.HairCutBooker.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepo userRepo;

}
