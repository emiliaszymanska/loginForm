const form = document.querySelector("#login-form");

form.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = `email=${this.email.value}&password=${this.password.value}`;
    console.log(data);
    login(data);
});

function login(data) {
    fetch("http://localhost:8010/login",
        {
            // credentials: 'same-origin',
            credentials: 'include',
            method: "POST",
            body: data
        })
        .then(function (response) {
            if (!response.ok) { // OR !response.status
                throw Error(response.statusText);
            }
            return response.text();
        })
        .then(function (message) { // user is authenticated (cookie send by server is set in browser)
            console.log(message);
        })
        .then(function (user) {
            console.log(user);
            window.location.href="../html/hello.html"
        })
        .catch(function (error) { // user NOT authenticated (server returns different status than 200-299)
            console.log(error);
        });
}