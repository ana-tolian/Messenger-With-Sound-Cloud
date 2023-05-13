let dialogId;
let messageUpdate;
let dialogUpdate;

function send () {
    let xhr = new XMLHttpRequest();
    let body = document.getElementById("text").value;
    let url = "http://localhost:8080/messages/post?dl=" + dialogId;
    xhr.withCredentials = true;

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("mes").insertAdjacentHTML('beforeend', this.responseText);
            document.getElementById("text").value = "";
            document.getElementById("mes").scrollTo(0, document.getElementById("mes").scrollHeight);
        }
    });

    xhr.send(body);
}

function setChat(tempId) {
    document.getElementById("allChats").style.display = "none";
    document.getElementById("dialog").style.display = "block";
    document.getElementById("dialog_controls").style.display = "none";

    dialogId = getId(tempId);

    messageUpdate = setInterval(upload, messageRefreshFrequence);
    clearInterval(dialogUpdate)

    upload();
}