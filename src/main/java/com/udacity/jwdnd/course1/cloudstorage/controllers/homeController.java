package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;

@Controller
@RequestMapping(path = "/home")
public class homeController {

    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public homeController(FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homeView(Authentication auth, Model model){
        model.addAttribute("allFiles", fileService.findAllFiles(auth.getName()));
        model.addAttribute("allNotes", noteService.findAll(auth.getName()));
        model.addAttribute("allCredentials", credentialService.findAllCredentials(auth.getName()));
        model.addAttribute("decoder", Base64.getDecoder());
        model.addAttribute("encryptionService", encryptionService);
        //model.addAttribute("isNotesActive",false);
        //model.addAttribute("isCredentialsActive",false);
        return "home";
    }

    @PostMapping(path = "/upload-file")
    public String uploadFile(@RequestParam(name = "fileUpload")MultipartFile fileUpload,
                             Authentication auth,
                             RedirectAttributes redirectAttributes){
        String fileError = null;
        String username = auth.getName();
        if(!fileService.isFilenameAvailable(fileUpload.getOriginalFilename(), username))
            fileError = "Two files cannot have the same name, please change the name of your file";

        if (fileUpload.isEmpty())
            fileError = "You must select a file.";

        if (fileError == null){
            int rows_added = fileService.createFile(fileUpload,username);
            if (rows_added < 0)
                fileError = "There was an error while uploading the file, please try again";
        }
        if (fileError != null)
            redirectAttributes.addFlashAttribute("fileUploadError",fileError);


        return "redirect:/home";
    }

    @GetMapping(path = "/view-file/{file_id}")
    public ResponseEntity viewFile(@PathVariable Integer file_id,Authentication auth){

        File file = fileService.getFileByFileId(file_id,auth.getName());

        HttpHeaders response_headers = new HttpHeaders();
        if (file == null)
            return  new ResponseEntity("That file doesn´t exist",null, HttpStatus.NOT_FOUND);

        response_headers.add("content-disposition", "inline; filename="+file.getFilename());
        response_headers.add("Content-Type", file.getContent_type());

        return new ResponseEntity(file.getFile_data(),response_headers, HttpStatus.OK);
    }

    @GetMapping(path = "/delete-file/{file_id}")
    public String deleteFile(@PathVariable Integer file_id, RedirectAttributes redirectAttributes){

        String delete_error = null;
        Integer rows_deleted = fileService.deleteFile(file_id);
        System.out.println(rows_deleted);
        if (rows_deleted < 0)
            delete_error = "Something happened while deleting the file, try again please.";

        if (delete_error != null)
            redirectAttributes.addFlashAttribute("deleteFileError",delete_error);

        return "redirect:/home";
    }
}
