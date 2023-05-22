
let addedSoundtracks = [];

function addSoundtrackIntoStack(tempId) {
    document.getElementById(tempId).value = "Добавлен";
    document.getElementById(tempId).disabled = true;
    document.getElementById(tempId).className = "create_chat_button bhover_disabled";

    let id = getId(tempId);

    addedSoundtracks.push(id);
}

function createPlaylist () {
    let name = document.getElementById("album_name").value;
    let url = "http://localhost:8080/creation?playlist=" + name;

    if (name === "") {
        document.getElementById("album_name").style.border = "solid 1 px red;"
        return;
    }

    for (const soundtrackId of addedSoundtracks) {
        url += "&id=" + soundtrackId;
    }

    let xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            window.location.assign("http://localhost:8080/albums");
        }
    });

    xhr.send();
}

function cancelCreation() {
    addedSoundtracks = [];
    let url = "http://localhost:8080/soundtracks";
    loadPage(url);
}

function openAlbum (tempId) {
    let id = getId(tempId);
    let url = "http://localhost:8080/soundtracks/playlist?id=" + id;
    loadPage(url);
}

function loadPage (url) {
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            window.location.assign(url);
        }
    });

    xhr.send();
}