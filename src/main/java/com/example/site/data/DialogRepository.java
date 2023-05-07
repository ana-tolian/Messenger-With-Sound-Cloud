package com.example.site.data;

import com.example.site.entity.Dialog;
import com.example.site.entity.Message;
import com.example.site.entity.User;

import java.util.List;

public interface DialogRepository {

    List<Message> loadMessages(Dialog dialog);
    Message loadLastMessage(Dialog dialog);
    List<Dialog> getDialogs(User user);
    Dialog getDialogById(int id);
    public List<Message> getDialogsForModel(User user);
}
