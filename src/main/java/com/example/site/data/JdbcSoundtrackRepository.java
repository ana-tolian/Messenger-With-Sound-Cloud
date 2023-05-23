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
import java.util.Optional;

@Repository
public class JdbcSoundtrackRepository implements SoundtrackRepository {
     private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcSoundtrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Soundtrack> findSoundtracksFromPlaylist(Playlist playlist) {
        return jdbcTemplate.query(
                    "SELECT Soundtrack.id, name, artist, trackHref, duration, imgHref " +
                        "FROM Soundtrack " +
                        "INNER JOIN SoundList ON SoundList.soundtrackId=Soundtrack.id " +
                        "WHERE SoundList.playlistId=" + playlist.getId(),
                this::mapRowToSoundtrack);
    }

    @Override
    public List<Soundtrack> findByPlaylistId(int id) {
        List<Soundtrack> list = jdbcTemplate.query(
                    "SELECT Soundtrack.id, name, artist, trackHref, duration, imgHref " +
                        "FROM Soundtrack " +
                        "INNER JOIN SoundList ON SoundList.soundtrackId=Soundtrack.id " +
                        "WHERE SoundList.playlistId=" + id,
                this::mapRowToSoundtrack);

        return  list;
    }

    @Override
    public Soundtrack save(Soundtrack soundtrack) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(
                            "INSERT INTO Soundtrack(name, artist, trackHref, duration) VALUE (?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, soundtrack.getName());
            ps.setString(2, soundtrack.getArtist());
            ps.setString(3, soundtrack.getTrackHref());
            ps.setInt   (4, soundtrack.getDuration());

            return ps;
        }, keyHolder);

        soundtrack.setId(keyHolder.getKey().intValue());
        return soundtrack;
    }

    @Override
    public Soundtrack update(Soundtrack soundtrack) {
        return null;
    }

    private Soundtrack mapRowToSoundtrack(ResultSet row, int rowNum) throws SQLException {
        return new Soundtrack(
                Integer.parseInt(row.getString("id")),
                row.getString("name"),
                row.getString("artist"),
                row.getString("trackHref"),
                row.getInt("duration"),
                row.getString("imgHref"));
    }

}
