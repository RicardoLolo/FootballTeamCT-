let currentUser = JSON.parse(localStorage.getItem("currentUser"));

console.log(currentUser);

if(currentUser === null){
    window.location.href = "login.html";
}

let token = localStorage.getItem("token");

let role_start = currentUser.roles[0].authority;

let email_start = currentUser.email;

console.log(email_start);

console.log(token);

console.log(role_start);

function profile() {
    if (role_start === "COACH") {
        findCoachByGmail();
    }
    if (role_start === "PLAYER"){
        findPlayerByGmail();
    }
}

function findCoachByGmail(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/find-coach-by-gmail?gmail=${email_start}`,
        success: function (data) {
            localStorage.setItem('id_coach', data.id);
            window.location.href = "profile_coach.html"
        }
    });
}

function findPlayerByGmail(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/find-player-by-gmail?gmail=${email_start}`,
        success: function (data) {
            localStorage.setItem('id_player', data.id);
            window.location.href = "profile_player.html";
        }
    });
}

function forms_calender(){
    if (role_start === "COACH" || role_start === "ADMIN"){
        document.getElementById("forms_calender").hidden = !document.getElementById("forms_calender").hidden;
        document.getElementById("forms_mail").hidden = true;
        topFunction();
        document.getElementById("form_calender").reset();
        document.getElementById("form-calendar").onclick = function () {
            save_calendar();
        };
    }
}

function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

function forms_mail(){
    document.getElementById("forms_mail").hidden = !document.getElementById("forms_mail").hidden;
    document.getElementById("forms_calender").hidden = true;
    topFunction();
}

function save_calendar() {
    let title = $('#title-new').val();
    let url = $('#url-new').val();
    let date_start = $('#date-start').val();
    let date_finish = $('#date-finish').val();
    if (title === "") {
        document.getElementById("error_from_calendar").innerHTML = "Title event cannot be blank !";
        return false;
    }
    if (date_start === "") {
        document.getElementById("error_from_calendar").innerHTML = "Date start event cannot be blank !";
        return false;
    }
    if (date_finish === "") {
        document.getElementById("error_from_calendar").innerHTML = "Date finish event cannot be blank !";
        return false;
    }
    let data = {
        content: title,
        urlEvent: url,
        dateStart: date_start,
        dateFinish: date_finish
    };
    if (confirm("Are you sure you want to create new event ?")){
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            data: JSON.stringify(data),
            url: "http://localhost:8080/api/calendar/save_calendar",
            success: function () {
                forms_calender()
                getCalenderToday();
            }
        });
    } else {
        getCalenderToday();
        forms_calender();
    }
    event.preventDefault();
}

function logout(){
    localStorage.clear();
    window.location.href = "login.html";
}
