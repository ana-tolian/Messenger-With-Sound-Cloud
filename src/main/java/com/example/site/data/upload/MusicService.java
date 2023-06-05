package com.example.site.data.upload;

import com.example.site.data.PlaylistRepository;
import com.example.site.data.SoundtrackRepository;
import com.example.site.entity.Playlist;
import com.example.site.entity.Soundtrack;
import com.example.site.entity.User;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class MusicService extends BasicService {

    private final String uploadDirectory = "/uploads/";
    private final SoundtrackRepository soundtrackRepository;
    private final PlaylistRepository playlistRepository;

    @Autowired
    public MusicService(SoundtrackRepository soundtrackRepository, PlaylistRepository playlistRepository) {
        this.soundtrackRepository = soundtrackRepository;
        this.playlistRepository = playlistRepository;
    }


    @Override
    public void store(MultipartFile [] files, List<String> list, User user) {
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            list.add(fileNameAndPath.getFileName().toString());

            try {
                if (!Files.exists(fileNameAndPath.getParent()))
                    Files.createDirectories(fileNameAndPath.getParent());

                Files.write(fileNameAndPath, file.getBytes());

                String soundtrackName = fileNameAndPath.getFileName().toString();
                int duration = getAudioDuration(new File(fileNameAndPath.toUri()));

                Soundtrack soundtrack = soundtrackRepository.save(new Soundtrack(
                        FilenameParser.getSoundtrackName(soundtrackName),
                        FilenameParser.getSoundtrackArtist(soundtrackName),
                        uploadDirectory + fileNameAndPath.getFileName().toString(),
                        duration,
                        null));
                Playlist playlist = playlistRepository.getMainPlaylist(user);

                playlistRepository.addToPlaylist(playlist, soundtrack);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getAudioDuration (File file) {
        Encoder encoder = new Encoder();
        double durationInSeconds = 0.0;

        try {
            MultimediaInfo info = encoder.getInfo(file);
            durationInSeconds = info.getDuration() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) (Math.floor(durationInSeconds));
    }

}
