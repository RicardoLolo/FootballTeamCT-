function displayPlayer(player) {
    return `<tr><td>${player.name}</td>
                <td>${player.birthday}</td>
                <td>${player.country}</td>
                <td>${player.salary}</td>
                <td>${player.bonus}</td>
                <td>${player.introduction}</td>
                <td>${player.gmail}</td>
                <td>${player.password}</td>
                <td>${player.position.name}</td>
                <td>${player.performance.name}</td>
                <td>${player.status.state}</td>
                <td><button class="btn btn-light btn-round px-3" onclick="deletePlayer(${player.id})">Delete</button></td>
                <td><button class="btn btn-light btn-round px-3" onclick="editPlayer(${player.id})">Edit</button></td></tr>`;
}

function getPlayer() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player`,
        success: function (data) {
            let display_player = '';
            for (let i = 0; i < data.length; i++) {
                display_player += displayPlayer(data[i]);
            }
            document.getElementById("playerList").innerHTML = display_player;

        }
    });
}

function getPlayerByPage(page) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/page?page=${page}`,
        success: function (data) {
            let array = data.content
            let display_player = '';
            for (let i = 0; i < array.length; i++) {
                display_player += displayPlayer(array[i]);
            }
            document.getElementById("playerList").innerHTML = display_player;
            document.getElementById("displayPage").innerHTML = displayPage(data)
            document.getElementById("form").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next").hidden = true
            }
        }
    });
}

function displayPage(data) {
    return `<button class="btn btn-primary" id="backup" onclick="isPrevious(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber + 1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNext(${data.pageable.pageNumber})">Next</button>`
}

function isPrevious(pageNumber) {
    getPlayerByPage(pageNumber - 1)
}

function isNext(pageNumber) {
    getPlayerByPage(pageNumber + 1)
}

function searchPlayer() {
    let search = document.getElementById("search").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/search?search=${search}`,
        success: function (data) {
            let display_player = '';
            for (let i = 0; i < data.length; i++) {
                display_player += displayPlayer(data[i]);
            }
            document.getElementById('productList').innerHTML = display_player;
            document.getElementById("searchForm").reset()
        }
    });
    event.preventDefault();
}

function deletePlayer(id) {
    if (confirm("Are you sure you want to delete player ?")) {
        $.ajax({
            type: "DELETE",
            url: `http://localhost:8080/api/player/delete-player/${id}`,
            success: function () {
                getPlayer();
            }
        });
    }
}

function editPlayer(id) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/player/${id}`,
        success: function (data) {
            $('#name').val(data.name);
            $('#birthday').val(data.birthday);
            $('#country').val(data.country);
            $('#salary').val(data.salary);
            $('#bonus').val(data.bonus);
            $('#introduction').val(data.introduction);
            $('#avatarURL').val(data.avatarURL);
            $('#avatarBackGround').val(data.avatarBackGround);
            $('#gmail').val(data.gmail);
            $('#password').val(data.password);
            index = data.id;
            document.getElementById("form").hidden = false;
            document.getElementById("form-button").onclick = function () {
                editPlayerPost()
            };
            getPosition();
            getPerformance();
            getStatus();
        }
    });
}

function editPlayerPost() {
    let name = $('#name').val();
    let birthday = $('#birthday').val();
    let country = $('#country').val();
    let salary = $('#salary').val();
    let bonus = $('#bonus').val();
    let introduction = $('#introduction').val();
    let avatarURL = $('#avatarURL').val();
    let avatarBackGround = $('#avatarBackGround').val();
    let gmail = $('#gmail').val();
    let password = $('#password').val();
    let position = $('#position').val()
    let performance = $('#performance').val()
    let status = $('#status').val()
    let newPlayer = {
        name: name,
        birthday: birthday,
        country: country,
        salary: salary,
        bonus: bonus,
        introduction: introduction,
        avatarURL: avatarURL,
        avatarBackGround: avatarBackGround,
        gmail: gmail,
        password: password,
        position: {
            id: position,
        },
        performance: {
            id: performance,
        },
        status: {
            id: status,
        }
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(newPlayer),
        url: `http://localhost:8080/api/player/${index}`,
        success: function () {
            getPlayer()
        }
    });
    event.preventDefault();
}

getPlayer()