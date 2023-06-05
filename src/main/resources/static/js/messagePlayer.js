

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