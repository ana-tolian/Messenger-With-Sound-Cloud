'use strict';

let playlist;
let timer;
let aud;

let initialisation = window.setInterval(init, 1000);

function init() {
    $(document).ready(function() {
        let soundtracks = document.getElementsByClassName("snd_name");
        playlist = new Playlist();

        for (const track of soundtracks) {
            let id, name, artist, src;

            id = getId(track.id);
            name = document.getElementById("name_" + id).innerHTML;
            artist = document.getElementById("artist_" + id).innerHTML;
            src = document.getElementById("src_" + id).src;

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
    let soundtrack = playlist.getSoundtrackById(id);
    let isJustStopped = 0;
    let prevId = playlist.getCurrent().id;

    if (aud != null && !playlist.getCurrent().isPaused) {
        playlist.getCurrent().stop();
        isJustStopped = 1;
    }

    playlist.setCurrent(id);
    aud = new Audio(soundtrack.src);
    aud.load();

    if (!isJustStopped && prevId == id || prevId != id) {
        soundtrack.play();
    }
}

function mute(tempId) {
    let id = getId(tempId);
    let soundtrack = playlist.getSoundtrackById(id);

    soundtrack.switchMute();
}

function move(event, tempId) {
    let id = getId(tempId);
    let rect = document.getElementById('seek_' + id).getBoundingClientRect();
    let seek = (event.clientX - rect.x) / rect.width;

    playlist.getCurrent().currentTime = playlist.getCurrent().duration * seek;
    playlist.getCurrent().setCurrentTime();
    playlist.getCurrent().redrawProgressbar(seek * 100);
}

function refresh() {
    playlist.getCurrent().setTime();

    if (playlist.getCurrent().isEnded) {
        playlist.getCurrent().isEnded = 0;
        play(playlist.next().id)
    }
}
