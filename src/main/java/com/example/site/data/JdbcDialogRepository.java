package com.example.site.data;

import com.example.site.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class JdbcDialogRepository implements DialogRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private User tmpUser;

    @Autowired
    public JdbcDialogRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<Message> loadMessages(Dialog dialog) {
        return jdbcTemplate.query(
                "SELECT id, content, imgHref, dialogId, userId, date FROM Message WHERE dialogId=" + dialog.getId() + ";",
                this::mapRowToMessage);
    }

    public Message saveMessage (Message message) {
        jdbcTemplate.update(
                "INSERT INTO Message(content, imgHref, dialogId, userId, date) VALUES(?, ?, ?, ?, ?)",
                    message.getContent(), message.getImgHref(), message.getDialog().getId(),
                        message.getUser().getId(), message.getDate());
        return message;
    }

    @Override
    public Message loadLastMessage(Dialog dialog) {
        List<Message> resultSet =  jdbcTemplate.query(
                "SELECT id, content, imgHref, dialogId, userId, date " +
                        "FROM Message " +
                        "WHERE dialogId=" + dialog.getId() + " ORDER BY id DESC LIMIT 1;",
                this::mapRowToMessage);
        return (resultSet.isEmpty() ? new Message(dialog) : resultSet.remove(0));
    }

    @Override
    public List<Dialog> getDialogs(User user) {
        this.tmpUser = user;
        return jdbcTemplate.query(
                "SELECT id, title, user1Id, user2Id " +
                        "FROM Dialog " +
                        "WHERE user1Id=" + user.getId() + " OR user2Id=" + user.getId() + ";",
                this::mapRowToDialog);
    }

    @Override
    public List<Dialog> getDialogsByTitle(User user, String title) {
        return jdbcTemplate.query(
                "SELECT id, title, user1Id, user2Id " +
                        "FROM Dialog " +
                        "WHERE ((user1Id=" + user.getId() + " " +
                             "OR user2Id=" + user.getId() + ") " +
                             "AND (lower(title) LIKE '%" + title + "%'));",
                this::mapRowToDialog);
    }

    @Override
    public Dialog getDialogById(int id) {
        List<Dialog> resultSet = jdbcTemplate.query(
                            "SELECT id, title, user1Id, user2Id FROM Dialog WHERE id=" + id + ";",
                                this::mapRowToDialog);
        return (resultSet.isEmpty() ? null : resultSet.remove(0));
    }

    @Override
    public int saveDialog(Dialog dialog) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "INSERT INTO Dialog(title, user1Id, user2Id) VALUES(?,?,?)",
                                 Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, dialog.getTitle());
            ps.setInt(2, dialog.getUser1().getId());
            ps.setInt(3, dialog.getUser2().getId());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Message mapRowToMessage (ResultSet row, int rowNum) throws SQLException {
        return new Message(row.getInt("id"),
                           row.getString("content"),
                           row.getString("imgHref"),
                           getDialogById(row.getInt("dialogId")),
                           userRepository.findById(row.getInt("userId")),
                           LocalDateTime.parse(row.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public Dialog mapRowToDialog (ResultSet row, int rowNum) throws SQLException {
        User user1 = userRepository.findById(row.getInt("user1Id"));
        User user2 = userRepository.findById(row.getInt("user2Id"));

        if (tmpUser != null && tmpUser.equals(user2)) {
            user2 = user1;
            user1 = tmpUser;
        }

        return new Dialog(row.getInt("id"),
                          row.getString("title"),
                          user1, user2);
    }

    public List<Message> getDialogsForModel (List<Dialog> dialogs) {
        List<Message> messages = new ArrayList<>();

        for (Dialog d : dialogs) {
            messages.add(loadLastMessage(d));
        }

//        Comparator<Message> comparator = (o1, o2) -> {
//            if (o1 == null || o2 == null )
//                return -1;
//
//            Message m1 = (Message) o1;
//            Message m2 = (Message) o2;
//
//            if (m1.getDate().compareTo(m2.getDate()) < 0)
//                return -1;
//            else if (m1.getDate().compareTo(m2.getDate()) > 0)
//                return 1;
//            else
//                return 0;
//        };
//
//        messages.sort(comparator);

        return messages;
    }
}
