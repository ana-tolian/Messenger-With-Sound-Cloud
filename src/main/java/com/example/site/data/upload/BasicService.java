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
            String filename = filenameToMD5(file.getOriginalFilename());
            Path fileNameAndPath = Paths.get(getDirectory(file.getOriginalFilename()), filename);
            list.add(getDirectory(file.getOriginalFilename()) + filename);

            System.out.println(getDirectory(file.getOriginalFilename()) + filename);

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

    public String filenameToMD5 (String filename) {
        return MD5(filename) + "." + getExtension(filename);
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private String getDirectory (String filename) {
        if (filename == null)
            return "/files/";

        String ext = getExtension(filename);

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

    private String getExtension (String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

}
