package com.lirisoft.keycloak.model;

import lombok.Data;

@Data
public class User {
    private String userName;
    private String emailId;
    private String password;
    private String firstname;
    private String lastName;
}
