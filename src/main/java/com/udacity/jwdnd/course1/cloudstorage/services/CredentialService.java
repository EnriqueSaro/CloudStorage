package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final UserService userService;
    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;


    public CredentialService(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> findAllCredentials(String username){
        User user = userService.findUserByUsername(username);
        return credentialMapper.getAll(user.getUser_id());
    }
    public Integer saveCredential(Credential credential, String username) {
        User user = userService.findUserByUsername(username);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encoded_key = Base64.getEncoder().encodeToString(key);
        String encrypted_password = encryptionService.encryptValue(credential.getPassword(), key);

        return credentialMapper.insertCredential(new Credential(
                null,
                credential.getUrl(),
                credential.getUsername(),
                encoded_key,
                encrypted_password,
                user.getUser_id()
        ));
    }
    public Integer updateCredential(Credential credential, String username){
        User user = userService.findUserByUsername(username);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encoded_key = Base64.getEncoder().encodeToString(key);
        String encrypted_password = encryptionService.encryptValue(credential.getPassword(), key);

        return credentialMapper.updateCredential(new Credential(
                credential.getCredential_id(),
                credential.getUrl(),
                credential.getUsername(),
                encoded_key,
                encrypted_password,
                user.getUser_id()
        ));
    }
    public Integer deleteCredential(Integer credential_id,String username){
        User user = userService.findUserByUsername(username);
        return credentialMapper.deleteCredential(credential_id,user.getUser_id());
    }
}
