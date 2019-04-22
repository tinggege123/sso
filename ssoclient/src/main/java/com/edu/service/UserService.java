package com.edu.service;

import com.edu.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public User getUserInfo(){
        System.out.println("cc");

        return new User();
    }

}
