'use strict';

function Soundtrack(id, artist, name, src) {
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
    this.isEnded = 0;
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
    console.log("current time2222 : " + this.currentTime + " " + aud.currentTime);
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

    document.getElementById("dur_" + this.id).textContent = (Math.floor(this.currentTime / 60)).toFixed() + ":" +
        ((Math.floor(this.currentTime % 60) >= 10) ? Math.floor(this.currentTime % 60).toFixed() : "0" + Math.floor(this.currentTime % 60).toFixed());

    this.seekbar = this.currentTime / Number(this.duration) * 100.0;

    if (this.seekbar > 100) {
        this.seekbar = 0;
        this.isEnded = 1;
        this.stop();
    }
    this.redrawProgressbar(this.seekbar);
}

Soundtrack.prototype.redrawProgressbar = function (seek) {
    document.getElementById('progress_' + this.id).style["width"] = seek + "%";
}