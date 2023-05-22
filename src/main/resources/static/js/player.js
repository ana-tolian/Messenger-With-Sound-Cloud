let playlist;
let aud;
let timer;

let initialisation = window.setInterval(init, 1000);


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
        this.src = src;
        this.duration = 10;
        this.currentTime = 0;
        this.seekbar = 0;
        this.volume = 100;
        this.isMuted = 0;
        this.isPaused = 1;
        // this.timer = 0;
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
        // document.getElementById(this.id + "").currentTime = this.currentTime;
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



    function init() {
        $(document).ready(function() {
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
                            src = child3.childNodes[1].src;
                        }
                    }
                }

                if (id != null) {
                    let soundtrack = new Soundtrack(id, artist, name, src);
                    let time = document.getElementById("dur_" + id).attributes["value"].value;
                    soundtrack.duration = Number(time);

                    playlist.add(soundtrack);
                }
            }

            clearInterval(initialisation);
        });
    }


    function play(tempId) {
        let id = getId(tempId);
        let soundtrack = playlist.list[id - 1];
        let isJustStopped = 0;
        let prevId = playlist.getCurrent().id;

        if (aud != null && !playlist.getCurrent().isPaused) {
            playlist.getCurrent().stop();
            isJustStopped = 1;
        }

        playlist.setCurrent(id - 1);
        aud = new Audio(soundtrack.src);
        aud.load();

        if (!isJustStopped && prevId == id || prevId != id) {
            soundtrack.play();
        }

    }

    function mute(tempId) {
        let id = getId(tempId);
        let soundtrack = playlist.list[id - 1];

        soundtrack.switchMute();
    }

    function move (event, tempId) {
        let id = getId(tempId);
        let rect = document.getElementById('seek_' + id).getBoundingClientRect();
        let seek = (event.clientX - rect.x) / rect.width;

        playlist.list[id - 1].currentTime = playlist.list[id - 1].duration * seek;
        console.log("change " + playlist.list[id - 1].duration * seek);
        playlist.list[id - 1].setCurrentTime();
        playlist.list[id - 1].redrawProgressbar(seek * 100);
    }

    function refresh() {
        playlist.getCurrent().setTime();
    }

    function getId(id) {
        return Number(id.substring(id.lastIndexOf('_') + 1));
    }
