let id_calendar = 0;

function displayCalendar(lists) {
    return `<tr>
            <td>${lists.content}</td>
            <td><a href="${lists.urlEvent}" target="_blank">${lists.urlEvent}</a></td>
            <td>${lists.dateStart}</td>
            <td>${lists.dateFinish}</td>
            <td><button class="btn btn-light btn-round px-3" onclick="eventCalendar(${lists.id})">Edit</button> 
            <button class="btn btn-light btn-round px-3" onclick="deleteCalendar(${lists.id})">Delete</button></td>
            </tr>`
        ;
}

function getListCalender() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/calendar/list`,
        success: function (data) {
            let display_list_calendar = '';
            for (let i = 0; i < data.length; i++) {
                display_list_calendar += displayCalendar(data[i]);
            }
            document.getElementById("CalenderList").innerHTML = display_list_calendar;
        }
    });
}

getListCalender();

function getCalendarByPage(page) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/calendar/pageCalendar?page=${page}`,
        success: function (data) {
            let array = data.content
            let display_calendar = '';
            for (let i = 0; i < array.length; i++) {
                display_calendar += displayCalendar(array[i]);
            }
            document.getElementById("list-calendar").innerHTML = display_calendar;
            document.getElementById("displayPageCalendar").innerHTML = displayPageCalendar(data)
            document.getElementById("form").hidden = true;
            //điều kiện bỏ nút previous
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup-calendar").hidden = true;
            }
            //điều kiện bỏ nút next
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next-calendar").hidden = true;
            }
        }
    });
}

function displayPageCalendar(data){
    return `<button class="btn btn-light btn-round px-3" id="backup-calendar" onclick="isPreviousCalendar(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-light btn-round px-3" id="next-calendar" onclick="isNextCalendar(${data.pageable.pageNumber})">Next</button>`
}

function isPreviousCalendar(pageNumber) {
    getCoachByPage(pageNumber-1)
}

function isNextCalendar(pageNumber) {
    getCoachByPage(pageNumber+1)
}

getCalendarByPage(0);

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
                getCalendarByPage(0);
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
                getCalendarByPage(0);
            }
        });
    }
}