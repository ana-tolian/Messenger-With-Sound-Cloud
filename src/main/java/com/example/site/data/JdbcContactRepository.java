package com.example.site.data;

import com.example.site.entity.Contact;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcContactRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public JdbcContactRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<Contact> getUserContacts(User user) {
        return jdbcTemplate.query(
                "SELECT id, userOwnerId, userRefererId, status FROM Contact WHERE userOwnerId=" + user.getId() + ";",
                this::mapRowToContact);
    }

    @Override
    public Contact saveContact(Contact contact) {
        jdbcTemplate
                .update("INSERT INTO Contact(userOwnerId, userRefererId, status) VALUES(?,?,?)",
                contact.getOwner().getId(), contact.getReferer().getId(), contact.getStatus());
        return contact;
    }

    private Contact mapRowToContact (ResultSet row, int rowNum) throws SQLException {
        return new Contact(
                row.getInt("id"),
                userRepository.findById(row.getInt("userOwnerId")),
                userRepository.findById(row.getInt("userRefererId")),
                row.getString("status"));
    }
}
