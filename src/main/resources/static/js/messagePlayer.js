let prevId;
let curr_soundtrack;
let aud;

Soundtrack = function (id, artist, name, src) {
    this.id = id;
    this.artist = artist;
    this.name = name;
    this.src = src;
    this.duration = 10;
    this.currentTime = 0;
    this.seekbar = 0;
    this.volume = 100;
    this.isMuted = 0;
    this.isPaused = 1;
}

Soundtrack.prototype.play = function () {
    timer = setInterval(refresh, 100)
    aud.play();
    this.setCurrentTime();
    this.isPaused = 0;

    if (this.isMuted)
        this.mute();

    console.log("currentTime: " + aud.currentTime + " stored: " + this.currentTime);

    document.getElementById("play_" + this.id).style = "background-position: -32px 5px";
}

Soundtrack.prototype.stop = function () {
    clearInterval(timer);

    this.isPaused = 1;
    aud.pause();
    document.getElementById("play_" + this.id).style = "background-position: 6px 5px";
}

Soundtrack.prototype.setCurrentTime = function () {
    aud.currentTime = this.currentTime;
}

Soundtrack.prototype.setVolume = function (volume) {
    this.volume = volume;
}

Soundtrack.prototype.switchMute = function () {
    if (!this.isMuted)
        this.mute()
    else
        this.unmute();
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

Soundtrack.prototype.setTime = function () {
    this.currentTime = aud.currentTime;

    document.getElementById("dur_" + this.id).textContent = (this.currentTime / 60).toFixed() + ":" +
        ((this.currentTime % 60 >= 10) ? (this.currentTime % 60).toFixed() : "0" + (this.currentTime % 60).toFixed());

    this.seekbar = this.currentTime / Number(this.duration) * 100.0;

    if (this.seekbar > 100) {
        this.seekbar = 0;
        this.stop();
    }
    this.redrawProgressbar(this.seekbar);
}

Soundtrack.prototype.redrawProgressbar = function (seek) {
    document.getElementById('progress_' + this.id).style["width"] = seek + "%";
}

function play(tempId) {
    let id = getId(tempId);
    let name = document.getElementById("name_" + id).innerHTML;
    let artist = document.getElementById("artist_" + id).innerHTML;
    let src = document.getElementById("src_" + id).src;

    if (curr_soundtrack != null)
        curr_soundtrack.stop();

    curr_soundtrack = new Soundtrack(id, artist, name, src);
    let isJustStopped = 0;

    if (aud != null && curr_soundtrack.isPaused) {
        curr_soundtrack.stop();
        isJustStopped = 1;
    }

    $(document).ready(function() {
        aud = new Audio(curr_soundtrack.src);
        aud.load();
    });

    if (!isJustStopped && prevId == id || prevId != id) {
        curr_soundtrack.play();
    }
    prevId = curr_soundtrack.id;
}

function mute(tempId) {
    let id = getId(tempId);

    curr_soundtrack.switchMute();
}

function move (event, tempId) {
    let id = getId(tempId);
    let rect = document.getElementById('seek_' + id).getBoundingClientRect();
    let seek = (event.clientX - rect.x) / rect.width;

    curr_soundtrack.currentTime = curr_soundtrack.duration * seek;
    curr_soundtrack.setCurrentTime();
    curr_soundtrack.redrawProgressbar(seek * 100);
}

function refresh() {
    curr_soundtrack.setTime();
}