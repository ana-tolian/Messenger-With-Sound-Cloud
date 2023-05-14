
function chats () {
    document.getElementById("allChats").style.visibility = "block";
    document.getElementById("dialog").style.visibility = "none";
    document.getElementById("dialog_controls").style.display = "flex";

    clearInterval(dialogUpdate);
    dialogUpdate = setInterval(refreshDialogPreview, dialogRefreshFrequence);
    clearInterval(messageUpdate);
}

function profileImageClick () {
    let photo = document.getElementById("profilePhoto"); //TODO
}

function logout () {
    let xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("POST", "http://localhost:8080/logout");
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            alert("Выход из приложения")
        }
    });

    xhr.send();
}

