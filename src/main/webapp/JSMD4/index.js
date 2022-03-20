let id_calendar = 0;

getCalenderToday();

function getCalenderToday() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/calendar/list_today`,
        success: function (data) {
            let display_calendar_today = '';
            for (let i = 0; i < data.length; i++) {
                display_calendar_today += displayCalendarToday(data[i]);
            }
            document.getElementById("CalenderTodayList").innerHTML = display_calendar_today;
        }
    });
}

function displayCalendarToday(lists) {
    return `<tr>
            <td>${lists.content}</td>
            <td><a href="${lists.urlEvent}" target="_blank">${lists.urlEvent}</a></td>
            <td>${lists.dateStart} - ${lists.dateFinish}</td>
            <td><button class="btn btn-light btn-round px-3" onclick="eventCalendar(${lists.id})">Edit</button> 
            <button class="btn btn-light btn-round px-3" onclick="deleteCalendar(${lists.id})">Delete</button></td>
            </tr>`
        ;
}

function eventCalendar(id){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/calendar/event_calendar/${id}`,
        success: function (data) {
            topFunction();
            $('#title-new').val(data.content);
            $('#url-new').val(data.urlEvent);
            $('#date-start').val(data.dateStart);
            $('#date-finish').val(data.dateFinish);
            id_calendar = data.id;
            document.getElementById("forms_calender").hidden = false;
            document.getElementById("form-calendar").onclick = function () {
                editCalendar();
            };
        }
    });
}

function editCalendar(){
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
    if (confirm("Are you sure you want to edit event ?")){
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "PUT",
            data: JSON.stringify(data),
            url: `http://localhost:8080/api/calendar/edit_calendar/${id_calendar}`,
            success: function () {
                forms_calender();
                getCalenderToday();
            }
        });
    } else {
        forms_calender();
    }
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function deleteCalendar(id) {
    if (confirm("Are you sure you want to delete event ?")){
        $.ajax({
            type: "DELETE",
            url: `http://localhost:8080/api/calendar/delete_calendar/${id}`,
            success: function () {
                getCalenderToday();
            }
        });
    }
}

function displayTotalCoachSalary(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/totalCoachSalary`,
        success: function (data) {
            console.log(data);
            document.getElementById("totalCoachSalary").innerHTML = totalCoachSalary(data)
        }
    });
}

function displayTotalCoachBonus(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/totalCoachBonus`,
        success: function (data) {
            console.log(data);
            document.getElementById("totalCoachBonus").innerHTML = totalCoachBonus(data)
        }
    });
}


function totalCoachSalary(totalCoachSalary){
    return `<h5 class="text-white mb-0">$${totalCoachSalary} <span class="float-right">
                <i class="fa fa-usd"></i></span>
            </h5>`;
}

function totalCoachBonus(totalCoachBonus){
    return `<p class="mb-0 text-white small-font">Total coach salary<span class="float-right">bonus $${totalCoachBonus}
                <i class="zmdi zmdi-long-arrow-up"></i></span>
            </p>`;
}

displayTotalCoachSalary();
displayTotalCoachBonus();

function displayTotalPlayerSalary(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/totalPlayerSalary`,
        success: function (data) {
            console.log(data);
            document.getElementById("totalPlayerSalary").innerHTML = totalPlayerSalary(data)
        }
    });
}

function totalPlayerSalary(totalPlayerSalary){
    return `<h5 class="text-white mb-0">$${totalPlayerSalary} <span class="float-right">
                <i class="fa fa-usd"></i></span>
            </h5>`;
}

function displayTotalPlayerBonus(){
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/player/totalPlayerBonus`,
        success: function (data) {
            console.log(data);
            document.getElementById("totalPlayerBonus").innerHTML = totalPlayerBonus(data)
        }
    });
}

function totalPlayerBonus(totalPlayerBonus){
    return `<p class="mb-0 text-white small-font">Total player salary<span class="float-right">bonus $${totalPlayerBonus}
                <i class="zmdi zmdi-long-arrow-up"></i></span>
            </p>`;
}

displayTotalPlayerSalary();
displayTotalPlayerBonus();