let index = 0;

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
            $('#title-new').val(data.content);
            $('#url-new').val(data.urlEvent);
            $('#date-start').val(data.dateStart);
            $('#date-finish').val(data.dateFinish);
            index = data.id;
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
            url: `http://localhost:8080/api/calendar/edit_calendar/${index}`,
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