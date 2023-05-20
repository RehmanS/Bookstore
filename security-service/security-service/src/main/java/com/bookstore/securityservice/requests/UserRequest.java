package com.example.jwttoken.requests;

import lombok.Data;

@Data
public class UserRequest {
    String username;
    String password;
}
