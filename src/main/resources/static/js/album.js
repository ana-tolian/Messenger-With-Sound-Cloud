
function openAlbum (tempId) {
    let id = getId(tempId);
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/soundtracks/playlist?id=" + id;

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            window.location.assign(url);
        }
    });

    xhr.send();
}

function addSoundtrackIntoStack(tempId) {

}

function createPlaylist () {

}

function cancelCreation() {

}