package com.example.site.data.upload;

import com.example.site.entity.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class BasicService implements FileStorageService {

    public void store(MultipartHttpServletRequest request, List<String> list, User user) {
        Iterator<String> fileNames = request.getFileNames();
        List<MultipartFile> files = new ArrayList<>();

        while(fileNames.hasNext()) {
            files.add(request.getFile(fileNames.next()));
        }

        MultipartFile[] filesArray = new MultipartFile[files.size()];

        store(files.toArray(filesArray), list, user);
    }

    @Override
    public void store(MultipartFile[] files, List<String> list, User user) {
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(getDirectory(file.getOriginalFilename()), file.getOriginalFilename());
            list.add(getDirectory(file.getOriginalFilename()) + fileNameAndPath.getFileName().toString());

            try {
                if (!Files.exists(fileNameAndPath.getParent()))
                    Files.createDirectories(fileNameAndPath.getParent());

                Files.write(fileNameAndPath, file.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        File file = new File(getDirectory(filename) + filename);
        InputStreamResource resource;

        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resource;
    }

    @Override
    public long getFileLength(String filename) {
        File file = new File(getDirectory(filename) + filename);
        return file.length();
    }

    private String getDirectory (String filename) {
        if (filename == null)
            return "/files/";

        String ext = filename.substring(filename.lastIndexOf('.') + 1);

        if (ext != null
                && (ext.equalsIgnoreCase("jpg")
                || ext.equalsIgnoreCase("jpeg")
                || ext.equalsIgnoreCase("png")
                || ext.equalsIgnoreCase("gif")))
            return  "/images/";
        else if (ext.equalsIgnoreCase("mp3")
                || ext.equalsIgnoreCase("ogg"))
            return "/uploads/";
        else
            return "/files/";
    }
}
