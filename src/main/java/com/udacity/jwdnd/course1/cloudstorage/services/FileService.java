package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

@Service
public class FileService {

    private final UserService userService;
    private final FileMapper fileMapper;

     public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public boolean isFilenameAvailable(String filename, String username){
        User user = userService.findUserByUsername(username);
        return fileMapper.getFileByFilename(filename,user.getUser_id()) == null;
    }
    public File getFileByFilename(String filename,String username){
        User user = userService.findUserByUsername(username);
        return fileMapper.getFileByFilename(filename,user.getUser_id());
    }
    public File getFileByFileId(Integer file_id,String username){
        User user = userService.findUserByUsername(username);
        return fileMapper.getFileByFileId(file_id,user.getUser_id());
    }

    public List<File> findAllFiles(String username){
        User user = userService.findUserByUsername(username);
        return fileMapper.getAll(user.getUser_id());
    }
    public Integer createFile(MultipartFile file, String username){
        byte[] file_data = null;
        User user = userService.findUserByUsername(username);

        try {
            file_data = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileMapper.insertFile(new File(
                null,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                user.getUser_id(),
                file_data));
    }

    public Integer deleteFile(Integer file_id){
         return fileMapper.deleteFile(file_id);
    }


}
