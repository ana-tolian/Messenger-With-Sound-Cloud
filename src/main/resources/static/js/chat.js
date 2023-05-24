let dialogId;
let messageUpdate;
let dialogUpdate;


function send () {
    let xhr = new XMLHttpRequest();
    let body = document.getElementById("text").value;
    let url = "http://localhost:8080/messages/post?dl=" + dialogId;
    let fileParam = "";

    if (body === "" && fileNames.length < 1)
        return;

    for (const filename of fileNames)
        fileParam += "&file=" + filename;
    url += fileParam;

    console.log(url);

    xhr.open("POST", url);
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());

    xhr.addEventListener("readystatechange", function() {
        if(this.readyState === 4) {
            document.getElementById("mes").insertAdjacentHTML('beforeend', this.responseText);
            document.getElementById("text").value = "";
            document.getElementById("input-info").innerHTML = "";
            scrollDown(document.getElementById("mes"));
            fileNames = [];
        }
    });

    xhr.send(body);
}

function setChat(tempId) {
    document.getElementById("allChats").style.display = "none";
    document.getElementById("dialog").style.display = "block";
    document.getElementById("dialog_controls").style.display = "none";
    scrollDownMessages();

    dialogId = getId(tempId);

    messageUpdate = setInterval(upload, messageRefreshFrequence);
    clearInterval(dialogUpdate)

    upload();
}