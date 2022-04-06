// Register the account into the api
// @ts-ignore
var url = 'http://localhost:8080/api/register';
document.querySelector('#submit').addEventListener('click', function (e) {
    e.preventDefault();
    var username = document.querySelector('#username').value;
    var password = document.querySelector('#password').value;
    var confirmPassword = document.querySelector('#confirmPassword').value;
    var email = document.querySelector('#email').value;
    var data = 'username=' + username + '&password=' + password + '&confirmPassword' + confirmPassword + '&email=' + email;
    var xhr = new XMLHttpRequest();
    if (username && password && email && confirmPassword && (password === confirmPassword)) {
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var json = JSON.parse(xhr.responseText);
                if (json.success) {
                    window.location.href = 'http://localhost:8080/login';
                }
                else {
                    alert(json.message);
                }
            }
        };
        xhr.send(data);
    }
    else if (!username || !password || !email || !confirmPassword) {
        document.querySelector('#popUpContainer').innerHTML +=
            '<div class="popup">' +
                '<p class="popupMessage">Please fill in all the fields</p>' +
                '<button class="popupButton">OK</button>' +
                '</div>';
        // After 1.5 seconds the popup will disappear 
        setTimeout(function () {
            document.querySelector('.popup').remove();
        }, 1500);
    }
    else {
        document.querySelector('#popUpContainer').innerHTML +=
            '<div class="popup">' +
                '<p class="popupMessage">Passwords do not match</p>' +
                '<button class="popupButton">OK</button>' +
                '</div>';
        setTimeout(function () {
            document.querySelector('.popup').remove();
        }, 1500);
    }
});
document.addEventListener('click', function (e) {
    //@ts-ignore
    if (e.target.classList.contains('popupButton')) {
        //@ts-ignore
        e.target.parentNode.remove();
    }
});