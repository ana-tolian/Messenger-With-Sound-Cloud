let id;

function send () {
    let xhr = new XMLHttpRequest();
    let body = document.getElementById("text").value;
    xhr.withCredentials = true;

    xhr.open("POST", "http://localhost:8080/messages/post?dl=1");// + id);
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

function setChat(event) {
    document.getElementById("allChats").style.display = "none";
    document.getElementById("dialog").style.display = "block";
    document.getElementById("dialog_controls").style.display = "none";

    let xhr = new XMLHttpRequest();
    id = event.target.id;
    xhr.withCredentials = true;

    xhr.open("POST", "http://localhost:8080/messages?dl=1");// + id);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("mes").innerHTML = this.responseText;
            document.getElementById("mes").scrollTo(0, document.getElementById("mes").scrollHeight);
        }
    });

    xhr.send();
}

function chats () {
    document.getElementById("allChats").style.visibility = "block";
    document.getElementById("dialog").style.visibility = "none";
    document.getElementById("dialog_controls").style.display = "flex";

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

function getCsrfToken () {
   return document.getElementById("csrf").content;
}

function getCsrfHeader () {
    return document.getElementById("csrf_header").content;
}