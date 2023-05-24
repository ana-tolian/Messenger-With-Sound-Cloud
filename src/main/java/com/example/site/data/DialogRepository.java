package com.example.site.data;

import com.example.site.entity.Dialog;
import com.example.site.entity.FileRow;
import com.example.site.entity.Message;
import com.example.site.entity.User;

import java.util.List;

public interface DialogRepository {

    List<Message> loadMessages(Dialog dialog);
    Message loadLastMessage(Dialog dialog);
    Message saveMessage(Message message);
    List<Message> getDialogsForModel(List<Dialog> dialogs);
    List<FileRow> getFiles(int fileListId);

    List<Dialog> getDialogs(User user);
    List<Dialog> getDialogsByTitle(User user, String title);
    Dialog getDialogById(int id);
    int saveDialog(Dialog dialog);
}
