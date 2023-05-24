let fileNames = [];

function openZpane () {
    document.getElementById("zpane").style.display = "flex";
}

function closeZpane () {
    document.getElementById("zpane").style.display = "none";
}

function getProfile (tempId) {
    let xhr = new XMLHttpRequest();
    let userId = getId(tempId);
    let url = "http://localhost:8080/profile/show?user=" + userId;

    console.log("getProfile");

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("prof-z-pane").innerHTML = this.responseText;
            showProfile();
        }
    });

    xhr.send();
}

function showProfile () {
    document.getElementById("prof-z-pane").style.display = "flex";
    setShade(true);
}

function hideProfile () {
    document.getElementById("shad").style.display = "none";
    setShade(false);
}

function setShade (bool) {
    if (bool)
        document.getElementById("shad").style.display = "flex";
    else
        document.getElementById("prof-z-pane").style.display = "none";
}

function openFileChooser (tempId) {
    if (tempId === "prof-img")
        document.getElementById("choose-image-file").click();
    else
        document.getElementById("choose-file").click();
}

function openImage (src) {
    document.getElementById("prof-z-pane").innerHTML =
        "<div class='profile-zpane' style='width: fit-content; height: fit-content'> <img class='big_image' src=" + src + "> </div>";
    showProfile();
}

function download (uri) {
}

function uploadFile () {
    let xhr = new XMLHttpRequest();
    let files = document.getElementById("choose-file").files;

    if (files.length < 1)
        return;

    let formData = new FormData();
    let url = "http://localhost:8080/load/another/file";

    for (let i = 0; i < files.length; i++) {
        formData.append(files[i].name, files[i]);
    }

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            fileNames = this.responseText.split(" ");

            document.getElementById("input-info").innerHTML = "Загружено файлов: " + fileNames.length;
            document.getElementById("input-info").style.display = "flex";
            scrollDownMessages();
        }
    });

    xhr.send(formData);
}

function uploadImage () {
    let xhr = new XMLHttpRequest();
    let file = document.getElementById("choose-image-file").files[0];
    let formData = new FormData();
    let url = "http://localhost:8080/load/another/image";

    formData.append(file.name, file);

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if (this.readyState === 4) {
            window.location.assign("http://localhost:8080/profile");
            showProfile();
        }
    });

    xhr.send(formData);
}

function searchDialog () {
    let xhr = new XMLHttpRequest();
    let dialogName = document.getElementById("search").value;
    let url = "http://localhost:8080/search/dialog?dl=" + dialogName;

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("allChats").innerHTML = this.responseText;
        }
    });

    xhr.send();
}

function searchForUsers () {
    let xhr = new XMLHttpRequest();
    let username = document.getElementById("search").value;
    let url = "http://localhost:8080/search/contact?user=" + username;

    if (username === "")
        return;

        xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("allContacts").innerHTML = this.responseText;
        }
    });

    xhr.send();
}

function createDialog (id) {
    let username = id;
    let url = "http://localhost:8080/profile/dialogs/create?user=" + username;
    let xhr = new XMLHttpRequest();

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            closeZpane();
            setChat(Number(this.responseText));
        }
    });

    xhr.send();
}

function addNewContact (event) {
    let username = event.target.id;
    let url = "http://localhost:8080/contacts?user=" + username + "&status=friend";
    let xhr = new XMLHttpRequest();

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            event.target.value = "Добавлен";
            event.target.disabled = true;
            event.target.className = "create_chat_button bhover_disabled";
        }
    });

    xhr.send();
}