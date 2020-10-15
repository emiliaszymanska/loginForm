const logoutButton = document.querySelector("#logout-button");

logoutButton.addEventListener('click', () => {
    eraseCookie("user");
    window.location.href = "index.html";
});

if (!getCookie("user")) {
    window.location.href = "index.html";
}

function getCookie(name) {
    return document.cookie.split(';').some(c => {
        return c.trim().startsWith(name + '=');
    });
}

function eraseCookie(name) {
    document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}