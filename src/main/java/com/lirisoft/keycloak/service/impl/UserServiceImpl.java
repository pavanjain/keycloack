package com.lirisoft.keycloak.service.impl;

import com.lirisoft.adapter.model.UserModel;
import com.lirisoft.adapter.service.AdapterService;
import com.lirisoft.keycloak.model.User;
import com.lirisoft.keycloak.model.UserResponse;
import com.lirisoft.keycloak.service.UserService;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String serverURL;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.authorization-grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.provider.keycloak.user-uri}")
    private String userApiURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AdapterService adapterService;

    @Override
    public ResponseEntity<UserResponse> addUser(User user, String token) throws HttpClientErrorException {
        CredentialRepresentation credential = createPasswordCredentials(user.getPassword());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUserName());
        userRepresentation.setFirstName(user.getFirstname());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getEmailId());
        userRepresentation.setCredentials(Collections.singletonList(credential));
        userRepresentation.setEnabled(true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        fetchUser("abc");
        HttpEntity<UserRepresentation> httpEntity = new HttpEntity<>(userRepresentation, headers);
        return restTemplate.postForEntity(userApiURL, httpEntity, UserResponse.class);
    }

    @Override
    public void assignRoleToUserByUsername(String username, String roleName) {

    }

//    @Override
//    public void assignRoleToUserByUsername(String username, String roleName) {
//        RealmResource realmResource = keyCloakClient.getInstance().realm(realm);
//        UsersResource usersResource = realmResource.users();
//
//        // Retrieve the user by username
//        List<UserRepresentation> users = usersResource.search(username, true);
//        if (users.isEmpty()) {
//            throw new IllegalArgumentException("User not found.");
//        }
//
//        // Retrieve the role by rolename
//        RoleResource roleResource = realmResource.roles().get(roleName);
//        if (roleResource == null) {
//            throw new IllegalArgumentException("Role not found.");
//        }
//
//        // Assign the role to the user
//        UserResource userResource = usersResource.get(users.get(0).getId());
//        userResource.roles().realmLevel().add(Arrays.asList(roleResource.toRepresentation()));
//    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private void fetchUser(String userName) {
        UserModel userModel = new UserModel();
        userModel.setEmail("test@gmail.com");
        userModel.setUserName("testuser");
        userModel.setAddress("testaddress");
        userModel.setEnable(true);
        userModel.setOnboardDate(new Date());
        System.out.println("going to add user ::");
        adapterService.addUser(userModel);
        System.out.println("going to fetch user ::");
        Optional<UserModel> userByUserName = adapterService.findUserByUserName("testuser");
        System.out.println("userName  :"+userByUserName);

    }

}
