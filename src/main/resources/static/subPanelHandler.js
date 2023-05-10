
function openZpane () {
    document.getElementById("zpane").style.display = "flex";
}

function closeZpane () {
    document.getElementById("zpane").style.display = "none";
}

function searchDialog () {
    let xhr = new XMLHttpRequest();
    let body = document.getElementById("search").value;
    xhr.withCredentials = true;

    xhr.open("GET", "http://localhost:8080/search");
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("mes").insertAdjacentHTML('beforeend', this.responseText);
        }
    });

    xhr.send(body);
}

function getCsrfToken () {
    return document.getElementById("csrf").content;
}

function getCsrfHeader () {
    return document.getElementById("csrf_header").content;
}