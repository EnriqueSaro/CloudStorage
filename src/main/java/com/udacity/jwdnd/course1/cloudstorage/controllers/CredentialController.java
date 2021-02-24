package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }


    @PostMapping(path = "/new-credential")
    public String createCredential(@ModelAttribute Credential credential, Authentication auth, RedirectAttributes redirectAttributes){
        String credential_error = null;
        Integer modified_rows = null;

        if (credential.getCredential_id() == null)
            modified_rows = credentialService.saveCredential(credential,auth.getName());
        else
            modified_rows = credentialService.updateCredential(credential,auth.getName());

        if (modified_rows < 1)
            credential_error = "Something happened, try again";

        if (credential_error == null)
            redirectAttributes.addFlashAttribute("credentialError", credential_error);

        redirectAttributes.addFlashAttribute("isCredentialsActive",true);
        //if (credential_error == null)
       //     redirectAttributes.addFlashAttribute("encryptionService", this.encryptionService);
        return "redirect:/home";
    }

    @GetMapping(path = "/delete-credential/{credential_id}")
    public String deleteNote(@PathVariable Integer credential_id, Authentication auth, RedirectAttributes redirectAttributes){
        String credential_error = null;
        Integer rows_deleted = credentialService.deleteCredential(credential_id, auth.getName());
        if (rows_deleted < 1)
            credential_error = "Somethimg happened, try again";

        redirectAttributes.addFlashAttribute("isCredentialsActive",true);

        return "redirect:/home";
    }
}
