
function openZpane () {
    document.getElementById("zpane").style.display = "flex";
}

function closeZpane () {
    document.getElementById("zpane").style.display = "none";
}

function profileImageClick (tempId) {
    let xhr = new XMLHttpRequest();
    let userId = getId(tempId);
    let url = "http://localhost:8080/profile/show?user=" + userId;

    console.log("profileImageClick");

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("prof-z-pane").innerHTML = this.responseText;
            document.getElementById("prof-z-pane").style.display = "flex";
            document.getElementById("shad").style.display = "flex";

        }
    });

    xhr.send();
}

function hideProfile () {
    document.getElementById("shad").style.display = "none";
    document.getElementById("prof-z-pane").style.display = "none";
}

function openFileChooser (tempId) {
    console.log("openFileChooser");

    if (tempId === "prof-img")
        document.getElementById("choose-image-file").click();
    else
        document.getElementById("choose-file").click();
}

function uploadFile () {
    console.log("uploadFile");

    let files = document.getElementById("choose-file").files;

    console.log("len: " + files.length);

    if (files.length) {
        for (let i = 0; i < files.length; i++) {

            console.log("name " + files[i].name);

            console.log("submit");
            document.getElementById("submit-file").click();
        }
    }
}

function uploadImage () {
    console.log("uploadImage");

    let file = document.getElementById("choose-image-file").files[0];

    if (file) {
        let image = new Image();

        image.onload = function() {
            if (this.width) {
                document.getElementById("submit-image").click();

            }
        };

        image.src = URL.createObjectURL(file);
        console.log(image.src);
    }
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