CREATE TABLE IF NOT EXISTS Playlist (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    imgHref VARCHAR(100) DEFAULT "/images/noimage.png",
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Soundtrack (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    artist VARCHAR(50) NOT NULL,
    trackHref VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    playlistId INT,
    imgHref VARCHAR(100) DEFAULT "/images/noimage.png",
    PRIMARY KEY(id),
    FOREIGN KEY(playlistId) REFERENCES Playlist(id)
);

CREATE TABLE IF NOT EXISTS User (
    id INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(2048) NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY(id)
);