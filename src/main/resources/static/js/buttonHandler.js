
function openZpane () {
    document.getElementById("zpane").style.display = "flex";
}

function closeZpane () {
    document.getElementById("zpane").style.display = "none";
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