package com.example.site.data;

import com.example.site.entity.Playlist;
import com.example.site.entity.Soundtrack;
import com.example.site.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@Repository
public class JdbcPlaylistRepository implements PlaylistRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public JdbcPlaylistRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }


    @Override
    public List<Playlist> getUserPlaylists (User user) {
        List<Playlist> playlists = findPlaylists(user);

        String sql = "SELECT count(*) AS numberOfSoundtracks " +
                     "FROM SoundList " +
                     "INNER JOIN Playlist ON SoundList.playlistId = Playlist.id " +
                     "WHERE Playlist.id = ?";

        for (Playlist playlist : playlists) {
            List<Integer> d = jdbcTemplate.query(sql, new Integer[] {playlist.getId()}, this::mapRowToInteger);
            playlist.setCount(d.get(0));
        }
        return playlists;
    }

    private List<Playlist> findPlaylists (User user) {
        return jdbcTemplate.query(
                "SELECT id, name, imgHref, userOwnerId FROM Playlist WHERE userOwnerId=" + user.getId(),
                this::mapRowToPlaylist);
    }

    @Override
    public Playlist getMainPlaylist(User user) {
        List<Playlist> resultSet = jdbcTemplate.query(
                    "SELECT id, name, imgHref, userOwnerId " +
                        "FROM Playlist " +
                        "WHERE (userOwnerId=" + user.getId() + " AND name='Основной')",
                this::mapRowToPlaylist);
        return (resultSet.isEmpty() ? null : resultSet.remove(0));
    }

    @Override
    public void addToPlaylist(Playlist playlist, Soundtrack soundtrack) {
        jdbcTemplate.update("INSERT INTO SoundList(playlistId, soundtrackId) VALUE (?,?)",
                playlist.getId(), soundtrack.getId());
    }

    @Override
    public Playlist save (Playlist playlist) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "INSERT INTO playlist(name, imgHref, userOwnerId) VALUE (?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, playlist.getName());
            ps.setString(2, playlist.getImgHref());
            ps.setInt   (3, playlist.getUser().getId());

            return ps;
        }, keyHolder);

        playlist.setId(keyHolder.getKey().intValue());
        return playlist;

    }

    private Playlist mapRowToPlaylist(ResultSet row, int rowNum) throws SQLException {
        return new Playlist(
                Integer.parseInt(row.getString("id")),
                row.getString("name"),
                row.getString("imgHref"),
                userRepository.findById(row.getInt("userOwnerId")));
    }

    private int mapRowToInteger(ResultSet row, int rowNum) throws SQLException {
        return row.getInt("numberOfSoundtracks");
    }

}
