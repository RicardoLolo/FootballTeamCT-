
//COACH
//Hàm lấy list Coach
function displayCoach(coach) {
    return `<tr><td>${coach.name}</td>
                <td>${coach.birthday}</td>
                <td>${coach.country}</td>
                <td>$ ${coach.salary}</td>
                <td>$ ${coach.bonus}</td>
                <td>${coach.gmail}</td>
                <td>${coach.coachType.type}</td>
                <td>
                <button class="btn btn-light btn-round px-3" id="edit-find-coach" onclick="onclickEditCoach(${coach.id})">View</button>
                <button class="btn btn-light btn-round px-3" id="delete-find-coach" onclick="deleteCoach(${coach.id})">Delete</button>
                </td>
                </tr>`;

}

function onclickEditCoach(id){
    localStorage.setItem('id_coach', id);
    findCoachById();
    window.location.href = "profile_coach.html";
}

function getCoach() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/list-coach`,
        success: function (data) {
            // hiển thị danh sách
            let display_coach = '';
            for (let i = 0; i < data.length; i++) {
                display_coach += displayCoach(data[i]);
            }
            document.getElementById("coachList").innerHTML = display_coach;

        }
    });
}

//hàm lấy list coach theo page
function getCoachByPage(page) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/page-coach?page=${page}`,
        success: function (data) {
            let array = data.content
            let display_coach = '';
            for (let i = 0; i < array.length; i++) {
                display_coach += displayCoach(array[i]);
            }
            document.getElementById("coachList").innerHTML = display_coach;
            document.getElementById("displayPageCoach").innerHTML = displayPageCoach(data)
            document.getElementById("form").hidden = true;
            //điều kiện bỏ nút previous
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup-coach").hidden = true;
            }
            //điều kiện bỏ nút next
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next-coach").hidden = true;
            }
        }
    });
}

//hàm hiển thị phần chuyển page
function displayPageCoach(data){
    return `<button class="btn btn-light btn-round px-3" id="backup-coach" onclick="isPreviousCoach(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-light btn-round px-3" id="next-coach" onclick="isNextCoach(${data.pageable.pageNumber})">Next</button>`
}

//hàm lùi page
function isPreviousCoach(pageNumber) {
    getCoachByPage(pageNumber-1)
}

//hàm tiến page
function isNextCoach(pageNumber) {
    getCoachByPage(pageNumber+1)
}

function searchCoach() {
    let search = document.getElementById("search-coach").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/search-coach?search=${search}`,
        success: function (data) {
            let display_coach = '';
            for (let i = 0; i < data.length; i++) {
                display_coach += displayCoach(data[i]);
            }
            document.getElementById('coachList').innerHTML = display_coach;
            // document.getElementById("searchForm").reset();
        }
    });
    event.preventDefault();
}

//Delete
function deleteCoach(id) {
    if (confirm("Are you sure you want to delete coach ?")){
        $.ajax({
            type: "DELETE",
            url: `http://localhost:8080/api/coach/delete-coach/${id}`,
            success: function () {
                getCoachByPage(0);
            }
        });
    }
}

// getCoach();
getCoachByPage(0);
//end coach

// Player-list
//Hàm lấy list player
function display_player(player) {
    return `<tr><td>${player.name}</td>
                <td>${player.birthday}</td>
                <td>${player.country}</td>
                <td>${player.height}</td>
                <td>${player.weight}</td>
                <td>$ ${player.salary}</td>
                <td>$ ${player.bonus}</td>
                <td>${player.gmail}</td>
                <td>${player.position.name}</td>
                <td>${player.performance.name}</td>
                <td>${player.status.state}</td>
                <td>
                <button class="btn btn-light btn-round px-3" id="edit-find-player" onclick="onclickEditPlayer(${player.id})">View</button>
                <button class="btn btn-light btn-round px-3" id="delete-find-player" onclick="delete_player(${player.id})">Delete</button>
                </td>
                </tr>`;
}

function onclickEditPlayer(id){
    localStorage.setItem('id_player', id);
    findPlayerById();
    window.location.href = "profile_player.html";
}

function getPlayer() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/list-player`,
        success: function (data) {
            // hiển thị danh sách
            let list_player = '';
            for (let i = 0; i < data.length; i++) {
                list_player += display_player(data[i]);
            }
            document.getElementById("list-player").innerHTML = list_player;

        }
    });
}

function getPlayerByPage(page) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/page-player?page=${page}`,
        success: function (data) {
            let array = data.content
            let display_page = '';
            for (let i = 0; i < array.length; i++) {
                display_page += display_player(array[i]);
            }
            document.getElementById("list-player").innerHTML = display_page;
            document.getElementById("display-page-player").innerHTML = displayPagePlayer(data);
            document.getElementById("form").hidden = true;
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup-player").hidden = true
            }
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next-player").hidden = true
            }
        }
    });
}

getPlayerByPage(0);

function displayPagePlayer(data){
    return `<button class="btn btn-light btn-round px-3" id="backup-player" onclick="isPreviousPlayer(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-light btn-round px-3" id="next-player" onclick="isNextPlayer(${data.pageable.pageNumber})">Next</button>`
}

function isPreviousPlayer(pageNumber) {
    getPlayerByPage(pageNumber-1)
}

function isNextPlayer(pageNumber) {
    getPlayerByPage(pageNumber+1)
}

function delete_player(id) {
    if (confirm("Are you sure you want to delete player ?")){
        $.ajax({
            type: "DELETE",
            url: `http://localhost:8080/api/player/delete-player/${id}`,
            success: function () {
                getPlayerByPage(0);
            }
        });
    }
}

function searchPlayer() {
    let search = document.getElementById("search-player").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/search-player?search=${search}`,
        success: function (data) {
            let display_page = '';
            for (let i = 0; i < data.length; i++) {
                display_page += display_player(data[i]);
            }
            document.getElementById('list-player').innerHTML = display_page;
            // document.getElementById("searchForm").reset();
        }
    });
    event.preventDefault();
}

// switch (role_start){
//     case "ADMIN" :
//         document.getElementById("delete-find-coach").hidden = false;
//         document.getElementById("edit-find-coach").hidden = false;
//         document.getElementById("delete-find-player").hidden = false;
//         document.getElementById("edit-find-player").hidden = false;
//         break;
// }
