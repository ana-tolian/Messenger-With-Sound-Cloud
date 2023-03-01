var playing = false;
var muted = false;
var aud;
var id = 0;

window.setInterval(refresh, 1000);

function play(event) {
    var id = event.target.id;

    if (playing && id != this.id) {
        playing = false;
        document.getElementById(this.id).style = "background-position: 6px 5px";
        document.getElementById('seek_' + getId(this.id)).value = 0;
        aud.pause();
    }

    if (id != this.id) {
        aud = new Audio(document.getElementById('source_' + getId(id)).src);
    }

    this.id = id;

    console.log(id);
    console.log('seek_' + getId(id));

    if (!playing) {
        aud.play();
        playing = true;
        document.getElementById(id).style = "background-position: -32px 5px";
        document.getElementById('seek_' + getId(id)).max = aud.duration;

    } else  {
        aud.pause();
        playing = false;
        document.getElementById(id).style = "background-position: 6px 5px";

    }
}

function mute(event) {
    var id = event.target.id;

    if (!muted) {
        aud.volume = 0;
        muted = true;
        document.getElementById(id).style = "background-position: -115px 5px";

    } else {
        aud.volume = 1;
        muted = false;
        document.getElementById(id).style = "background-position: -60px 5px";
    }
}

function refresh () {
    if (playing) {
        document.getElementById('seek_' + getId(id)).value++;

        if (aud.currentTime >= aud.duration) {
            playing = false;
            aud.currentTime = 0;

            document.getElementById('seek_' + getId(id)).value = 0;
            document.getElementById(id).style = "background-position: 6px 5px";
        }
    }
}

function move () {
    console.log("New value " + document.getElementById('seek_' + getId(id)).value);
    aud.currentTime = document.getElementById('seek_' + getId(id)).value;
    console.log(aud.currentTime);
}

function getId (id) {
    return id.substring(id.lastIndexOf('_') + 1);
}