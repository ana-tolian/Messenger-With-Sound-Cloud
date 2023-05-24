package com.example.site.web.controller;

import com.example.site.data.JdbcDialogRepository;
import com.example.site.entity.Dialog;
import com.example.site.entity.FileRow;
import com.example.site.entity.Message;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/messages")
public class MessageLoadController {

    private final JdbcDialogRepository dialogRepository;

    @Autowired
    public MessageLoadController (JdbcDialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @PostMapping
    public String loadMessages (@RequestParam(value = "dl", required = true) Integer id,
                                Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        Dialog dialog = dialogRepository.getDialogById(id);

        if (isBelongsTo(user, dialog)) {
            model.addAttribute("thisUser", user);
            model.addAttribute("allMessages", dialogRepository.loadMessages(dialog));
            return "messages";
        } else
            return "redirect:/error";
    }

    @PostMapping("/post")
    public String postMessages (@RequestBody(required = false) String content,
                                @RequestParam(value = "dl", required = true) Integer id,
                                @RequestParam(value = "file", required = false) String[] fileHref,
                                Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        Dialog dialog = dialogRepository.getDialogById(id);

        if (fileHref == null && content == null)
            return "error";

        if (content == null)
            content = "";

        if (isBelongsTo(user, dialog)) {
            Message message = new Message(content, getRows(fileHref), dialog, user, java.time.LocalDateTime.now());
            dialogRepository.saveMessage(message);
            model.addAttribute("message", message);
            return "message";
        }
        return "error";
    }

    private boolean isBelongsTo (User user, Dialog dialog) {
        if (user.equals(dialog.getUser1()) || user.equals((dialog.getUser2())))
            return true;
        return false;
    }

    private List<FileRow> getRows (String[] fileHref) {
        List<FileRow> rows = new ArrayList<>();

        if (fileHref == null)
            return rows;

        for (String href : fileHref) {
            String type = href.substring(href.lastIndexOf('.') + 1);

            if (type.equalsIgnoreCase("jpeg")
                    || type.equalsIgnoreCase("jpg")
                    || type.equalsIgnoreCase("png"))
                type = "image";
            else
                type = "file";

            rows.add(new FileRow(href, type));
        }

        return rows;
    }
}
