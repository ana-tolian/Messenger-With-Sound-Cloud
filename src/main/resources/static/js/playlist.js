'use strict';

function Playlist() {
    this.list = [];
    this.current = 0;
    this.length = 0;
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
        this.current = this.length - 1;
        return this.list[this.current];
    }
}

Playlist.prototype.add = function (soundtrack) {
    this.list[length++] = soundtrack;
}

Playlist.prototype.getCurrent = function () {
    return this.list[this.current];
}

Playlist.prototype.setCurrent = function (soundtrackId) {
    for (let i = 0; i < length; i++)
        if (this.list[i].id === soundtrackId)
            this.current = i;
}

Playlist.prototype.getSoundtrackById = function (soundtrackId) {
    let soundtrack;

    for (let i = 0; i < length; i++)
        if (this.list[i].id === soundtrackId)
            soundtrack = this.list[i];
    return soundtrack;
}

Playlist.prototype.reset = function () {
    this.getCurrent().stop();
    this.getCurrent().unmute();
    this.getCurrent().setTime(0);
}