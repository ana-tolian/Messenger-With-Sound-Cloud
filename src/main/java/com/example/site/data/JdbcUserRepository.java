package com.example.site.data;

import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUsername(String username) {
        List<User> user = jdbcTemplate.query(
                "SELECT id, username, password, description, imgHref FROM User WHERE username=lower('" + username + "');",
                this::mapRowToUser);
        return (user.isEmpty() ? null : user.remove(0));
    }

    @Override
    public List<User> findByUsernameStartsWith(String username) {
        return jdbcTemplate.query(
                "SELECT id, username, password, description, imgHref FROM User " +
                        "WHERE (lower(username) LIKE '%" + username + "%');",
                this::mapRowToUser);
    }

    @Override
    public User findById(int id) {
            List<User> user = jdbcTemplate.query(
                    "SELECT id, username, password, description, imgHref FROM User WHERE id='" + id + "';",
                    this::mapRowToUser);
        return (user.isEmpty() ? null : user.remove(0));
    }

    @Override
    public User save(User user) {
        jdbcTemplate.update("INSERT INTO User(username, password, description) VALUES(?,?,?);",
                user.getUsername(), user.getPassword(), user.getDescription());
        return user;
    }

    private User mapRowToUser (ResultSet row, int rowNum) throws SQLException {
        return new User(
                Integer.parseInt(row.getString("id")),
                row.getString("username"),
                row.getString("password"),
                row.getString("description"),
                row.getString("imgHref"));
    }
}
