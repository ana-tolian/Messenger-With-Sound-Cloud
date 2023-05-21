package com.example.site.data;

import com.example.site.entity.Playlist;
import com.example.site.entity.Soundtrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSoundtrackRepository implements SoundtrackRepository {
     private final JdbcTemplate jdbcTemplate;
     private final UserRepository userRepository;

    @Autowired
    public JdbcSoundtrackRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }


    public List<Playlist> findAllPlaylists () {
        List<Playlist> playlists = findAllPlaylist();

        String sql = "SELECT count(*) AS numberOfSoundtracks FROM Soundtrack INNER JOIN Playlist ON Soundtrack.playlistId = Playlist.id WHERE Playlist.id = ?";

        for (Playlist playlist : playlists) {
            List<Integer> d = jdbcTemplate.query(sql, new Integer[] {playlist.getId()}, this::mapRowToInteger);
            playlist.setCount(d.get(0));
        }
        return playlists;
    }


    private List<Playlist> findAllPlaylist () {
        return jdbcTemplate.query(
                "SELECT id, name, imgHref, userOwnerId FROM Playlist",
                this::mapRowToPlaylist);
    }

    public Playlist savePlaylist (Playlist playlist) {
        jdbcTemplate.update("INSERT INTO playlist(name, imgHref, userOwnerId) VALUE (?,?,?)",
                playlist.getName(), playlist.getImgHref(), playlist.getUser().getId());
        return playlist;
    }

    @Override
    public List<Soundtrack> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, artist, trackHref, duration, playlistId, imgHref FROM Soundtrack",
                this::mapRowToSoundtrack);
    }

    @Override
    public List<Soundtrack> findByPlaylistId(int id) {
       return jdbcTemplate.query(
                "SELECT id, name, artist, trackHref, duration, playlistId, imgHref FROM Soundtrack WHERE playlistId=?",
                this::mapRowToSoundtrack, id);
    }

    @Override
    public Soundtrack save(Soundtrack soundtrack) {
        jdbcTemplate.update("INSERT INTO Soundtrack(name, artist, trackHref, duration, playlistId) VALUE (?,?,?,?,?)",
                        soundtrack.getName(), soundtrack.getArtist(), soundtrack.getTrackHref(),
                        soundtrack.getDuration(), soundtrack.getPlaylistId());
        return soundtrack;
    }

    private Soundtrack mapRowToSoundtrack(ResultSet row, int rowNum) throws SQLException {
        return new Soundtrack(
                Integer.parseInt(row.getString("id")),
                row.getString("name"),
                row.getString("artist"),
                row.getString("trackHref"),
                row.getInt("duration"),
                row.getString("playlistId"),
                row.getString("imgHref"));
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
