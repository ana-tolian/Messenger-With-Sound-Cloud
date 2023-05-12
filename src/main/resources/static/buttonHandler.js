
function openZpane () {
    document.getElementById("zpane").style.display = "flex";
}

function closeZpane () {
    document.getElementById("zpane").style.display = "none";
}

function searchDialog () {
    let xhr = new XMLHttpRequest();
    let dialogName = document.getElementById("search").value;
    let url = "http://localhost:8080/search/contact?dl=" + dialogName;
    xhr.withCredentials = true;

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("allChats").innerHTML = this.responseText;
        }
    });

    xhr.send(body);
}

function searchForUsers () {
    let xhr = new XMLHttpRequest();
    let username = document.getElementById("search").value;
    let url = "http://localhost:8080/search/contact?user=" + username;
    xhr.withCredentials = true;

    xhr.open("GET", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("allContacts").innerHTML = this.responseText;
        }
    });

    xhr.send();
}

function addNewContact (event) {
    let username = event.target.id;
    let url = "http://localhost:8080/contacts?user=" + username + "&status=friend";
    let xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            event.target.value = "Добавлен";
            event.target.disabled = true;
        }
    });

    xhr.send();
}

function getCsrfToken () {
    return document.getElementById("csrf").content;
}

function getCsrfHeader () {
    return document.getElementById("csrf_header").content;
}