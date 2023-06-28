USE `root`.`music`;

CREATE TABLE IF NOT EXISTS User (
    id INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(2048) NOT NULL,
    description VARCHAR(100),
    imgHref VARCHAR(100) DEFAULT "/images/usernoimage.jpeg",
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Playlist (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    imgHref VARCHAR(100) DEFAULT "/images/noimage.png",
    userOwnerId INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(userOwnerId) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS Soundtrack (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(70) NOT NULL,
    artist VARCHAR(70) NOT NULL,
    trackHref VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    imgHref VARCHAR(100) DEFAULT "/images/noimage.png",
    PRIMARY KEY(id),
    FOREIGN KEY(playlistId) REFERENCES Playlist(id)
);

CREATE TABLE IF NOT EXISTS SoundList (
     id INT AUTO_INCREMENT NOT NULL,
     playlistId INT NOT NULL,
     soundtrackId INT NOT NULL,
     PRIMARY KEY(id),
     FOREIGN KEY(playlistId) REFERENCES Playlist(id),
     FOREIGN KEY(soundtrackId) REFERENCES Soundtrack(id)
);

CREATE TABLE IF NOT EXISTS Dialog (
    id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(40) NOT NULL,
    user1Id INT NOT NULL,
    user2Id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(user1Id) REFERENCES User(id),
    FOREIGN KEY(user2Id) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS Message (
    id INT AUTO_INCREMENT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    dialogId INT NOT NULL,
    userId INT NOT NULL,
    date DATETIME NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(dialogId) REFERENCES Dialog(id),
    FOREIGN KEY(userId) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS FileList (
    id INT AUTO_INCREMENT NOT NULL,
    messageId INT NOT NULL,
    fileHref VARCHAR(200) NOT NULL,
    originalName VARCHAR(200) NOT NULL,
    type VARCHAR(10) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(messageId) REFERENCES Message(id)
);

CREATE TABLE IF NOT EXISTS Contact (
    id INT AUTO_INCREMENT NOT NULL,
    userOwnerId INT NOT NULL,
    userRefererId INT NOT NULL,
    status VARCHAR(20),
    PRIMARY KEY(id),
    FOREIGN KEY(userOwnerId) REFERENCES User(id),
    FOREIGN KEY(userRefererId) REFERENCES User(id)
);