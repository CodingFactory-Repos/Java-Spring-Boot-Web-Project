// Create Fetch API request
// @ts-ignore
var url = 'http://localhost:8080/api/users';
function realTime() {
    // Create XMLHttpRequest request GET
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Clear table
            var table = document.querySelector("table");
            table.innerHTML = '';
            for (var i = 0; i < JSON.parse(xhr.responseText).length; i++) {
                var user = JSON.parse(xhr.responseText)[i];
                table.innerHTML += "\n                    <tr>\n                        <td>".concat(user.userId, "</td>\n                        <td>").concat(user.username, "</td>\n                        <td>").concat(user.email, "</td>\n                    </tr>\n                ");
            }
        }
    };
    xhr.open('GET', url, true);
    xhr.send();
}
