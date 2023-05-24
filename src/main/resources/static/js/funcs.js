function getCsrfToken () {
    return document.getElementById("csrf").content;
}

function getCsrfHeader () {
    return document.getElementById("csrf_header").content;
}

function getId (id) {
    if (id.toString().includes("_"))
        return Number(id.substring(id.lastIndexOf('_') + 1));
    return Number(id);
}

function getName (id) {
    if (id.toString().includes("_"))
        return id.substring(0, id.lastIndexOf('_'))
    return id;
}

function scrollDownMessages () {
    scrollDown(document.getElementById("dialog"));
}

function scrollDown (element) {
    element.scrollTo(0, element.scrollHeight);
}