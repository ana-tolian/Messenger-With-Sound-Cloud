package com.example.site.data;

import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(int id) {
        List<User> user = jdbcTemplate.query(
                "SELECT id, username, password, description, imgHref FROM User WHERE id='" + id + "';",
                this::mapRowToUser);
        return (user.isEmpty() ? null : user.remove(0));
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
                "SELECT User.id, username, password, description, imgHref " +
                        "FROM User " +
                        "LEFT JOIN Contact ON Contact.userRefererId=User.id " +
                        "WHERE ((Contact.userOwnerId IS NULL) AND (lower(username) LIKE '%" + username + "%'));",
                this::mapRowToUser);
    }


    @Override
    public List<User> findByUsernameStartsWithFromContacts(String contactname) {
        return jdbcTemplate.query(
                "SELECT User.id, username, password, description, imgHref " +
                        "FROM User " +
                        "RIGHT JOIN Contact ON Contact.userRefererId=User.id " +
                        "WHERE (lower(username) LIKE '%" + contactname + "%');",
                this::mapRowToUser);
    }

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "INSERT INTO User(username, password, description) VALUES(?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getDescription());

            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("UPDATE User SET imgHref='" + user.getImgHref() + "' WHERE id=" + user.getId());
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
