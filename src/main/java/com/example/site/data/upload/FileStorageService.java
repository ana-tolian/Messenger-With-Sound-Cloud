package com.example.site.data.upload;

import com.example.site.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    void store(MultipartFile[] files, List<String> list, User user);
    Resource loadAsResource(String filename);
    long getFileLength (String filename);
}
