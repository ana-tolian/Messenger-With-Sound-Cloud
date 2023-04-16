let playlist;
let aud;


Playlist = function () {
    this.list = [];
    this.current = 0;
}

Playlist.prototype.next = function () {
    if (this.current < this.list.length - 1)
        return this.list[++this.current];
    else {
        this.current = 0;
        return this.list[this.current];
    }
}

Playlist.prototype.previous = function () {
    if (this.current > 1)
        return this.list[--this.current];
    else {
        this.current = this.list.length - 1;
        return this.list[this.current];
    }
}

Playlist.prototype.add = function (soundtrack) {
    this.list.push(soundtrack);
}

Playlist.prototype.getCurrent = function () {
    return this.list[this.current];
}

Playlist.prototype.setCurrent = function (curr) {
    this.current = curr;
}

Playlist.prototype.reset = function () {
    this.getCurrent().stop();
    this.getCurrent().unmute();
    this.getCurrent().setTime(0);
}

Soundtrack = function (id, artist, name, src) {
    this.id = id;
    this.artist = artist;
    this.name = name;
    this.source = src;
    this.duration = 10;
    this.currentTime = 0;
    this.seekbar = 0;
    this.volume = 100;
    this.isMuted = 0;
    this.isPaused = 1;
}

Soundtrack.prototype.play = function () {
    if (this.currentTime > 0)
        this.setTime(this.currentTime);

    this.isPaused = 0;
    aud.play();
    document.getElementById("play_" + this.id).style = "background-position: -32px 5px";
}

Soundtrack.prototype.stop = function () {
    this.isPaused = 1;
    aud.pause();
    document.getElementById("play_" + this.id).style = "background-position: 6px 5px";
}

Soundtrack.prototype.setVolume = function (volume) {
    this.volume = volume;
}

Soundtrack.prototype.mute = function () {
    this.isMuted = 1;
    aud.volume = 0;
    document.getElementById("mute_" + this.id).style = "background-position: -108px 5px";
}

Soundtrack.prototype.unmute = function () {
    this.isMuted = 0;
    aud.volume = this.volume / 100;
    document.getElementById("mute_" + this.id).style = "background-position: -58px 5px";
}

Soundtrack.prototype.setTime = function (time) {
    if (time == -1)
        this.currentTime = aud.currentTime;
    else {
        aud.currentTime = time;
        this.currentTime = time;
    }

    this.seekbar = this.currentTime / this.duration * 100;
    console.log("TEST " + this.seekbar + " curr " + this.currentTime + " dur " + this.duration);

    if (this.seekbar > 100) {
        this.seekbar = 0;
        this.stop();
    }

    console.log("progress_" + this.id + " time: " + this.currentTime);
    console.log("width: " + this.seekbar + "%;")
    document.getElementById("progress_" + this.id).style.width = (this.seekbar + "%;");
}

function init () {
    let doc = document.getElementById("main");
    playlist = new Playlist();

    for (const child1 of doc.children) {
        let id, name, artist, src;

        for (const child2 of child1.children) {
            if (child2.className === "sndImg")
                continue;

            for (const child3 of child2.children) {

                if (child3.className === "sndName") {
                    name = child3.innerHTML;
                }

                if (child3.className === "sndArtist") {
                    artist = child3.innerHTML;
                }

                if (child3.tagName == "AUDIO") {
                    id = Number(child3.id);
                    src = child3.children["source"].src;
                }
            }
        }

        if (id != null) {
            console.log(id)
            let soundtrack = new Soundtrack(id, artist, name, src)
            let audelem = document.getElementById(id);
            audelem.addEventListener("loadedmetadata", function() {
                console.log("============> " + name);
                console.log("AAAAAAAAAAAAudio data loaded");
                console.log("DDDDDDDDDDDDAudio duration: " + this.duration);
                soundtrack.duration = this.duration;
            });

            playlist.add(soundtrack);
        }
    }
    aud = new Audio(playlist.list[0].source);
}


window.setInterval(refresh, 1000);

function play(event) {
    if (playlist == null)
        init();

    let id = getId(event.target.id);
    let soundtrack = playlist.list[id - 1];
    let isJustStopped = 0;
    let prevId = playlist.getCurrent().id;

    if (aud != null && !playlist.getCurrent().isPaused) {
        playlist.getCurrent().stop();
        isJustStopped = 1;
    }

    playlist.setCurrent(id - 1);
    aud = new Audio(soundtrack.source);
    soundtrack.duration = aud.duration;

    console.log(isJustStopped + " " + id + " " + prevId);
    if (!isJustStopped && prevId == id || prevId != id) {
        console.log("play");
        soundtrack.play();
    }
    isJustStopped = 0;

}

function mute(event) {
    let id = getId(event.target.id);
    let soundtrack = playlist.list[id - 1];

    if (!soundtrack.isMuted)
        soundtrack.mute()
    else
        soundtrack.unmute();
}

function refresh () {
    if (playlist == null)
        init();

    playlist.getCurrent().setTime(-1);
}

function getId (id) {
    return Number(id.substring(id.lastIndexOf('_') + 1));
}