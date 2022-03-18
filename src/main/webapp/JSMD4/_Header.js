let currentUser = JSON.parse(localStorage.getItem("currentUser"));

let token = localStorage.getItem("token");

role = currentUser.roles[0].authority;

console.log(currentUser);

console.log(token);

console.log(role)

function profile() {
    if (currentUser.role === "COACH") {
        window.location.href = "profile_coach.html"
    }
    if (currentUser.role === "PLAYER"){
        window.location.href = "profile_player.html"
    }
}

function forms_calender(){
    document.getElementById("forms_calender").hidden = !document.getElementById("forms_calender").hidden;
    document.getElementById("forms_mail").hidden = true;
    document.getElementById("forms_calender").reset();
    topFunction();
    document.getElementById("form-calendar").onclick = function () {
        save_calendar();
    };
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
