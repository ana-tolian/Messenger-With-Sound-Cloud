document.addEventListener("DOMContentLoaded", onPageLoad);
let dialogRefreshFrequence = 10000;
let messageRefreshFrequence = 5000;

function onPageLoad () {
    dialogUpdate = setInterval(refreshDialogPreview, dialogRefreshFrequence)
}

function upload () {
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/messages?dl=" + dialogId;

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("mes").innerHTML = this.responseText;

            if (justSet) {
                scrollDownMessages();
                justSet = false;
            }
        }
    });

    xhr.send();
}

function refreshDialogPreview () {
    if (document.getElementById("search").value !== "")
        return;

    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/profile/dialogs";

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("allChats").innerHTML = this.responseText;
        }
    });

    xhr.send();
}