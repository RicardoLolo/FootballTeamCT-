//COACH
//Hàm lấy list Coach
function displayCoach(coach) {
    return `<tr><td>${coach.name}</td>
                <td>${coach.birthday}</td>
                <td>${coach.country}</td>
                <td>${coach.salary}</td>
                <td>${coach.bonus}</td>
                <td>${coach.introduction}</td>
                <td>${coach.gmail}</td>
                <td>${coach.password}</td>
                <td>${coach.coachType.type}</td>
                <td><button class="btn btn-light btn-round px-3" onclick="deleteCoach(${coach.id})">Delete</button></td>
                <td><button class="btn btn-light btn-round px-3" onclick="editCoach(${coach.id})">Edit</button></td></tr>`;

}
function getCoach() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach`,
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
        url: `http://localhost:8080/api/coach/page?page=${page}`,
        success: function (data) {
            let array = data.content
            let display_coach = '';
            for (let i = 0; i < array.length; i++) {
                display_coach += displayCoach(array[i]);
            }
            document.getElementById("coachList").innerHTML = display_coach;
            document.getElementById("displayPage").innerHTML = displayPage(data)
            document.getElementById("form").hidden = true;
            //điều kiện bỏ nút previous
            if (data.pageable.pageNumber === 0) {
                document.getElementById("backup").hidden = true
            }
            //điều kiện bỏ nút next
            if (data.pageable.pageNumber + 1 === data.totalPages) {
                document.getElementById("next").hidden = true
            }
        }
    });
}

//hàm hiển thị phần chuyển page
function displayPage(data){
    return `<button class="btn btn-primary" id="backup" onclick="isPrevious(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber+1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNext(${data.pageable.pageNumber})">Next</button>`
}

//hàm lùi page
function isPrevious(pageNumber) {
    getCoachByPage(pageNumber-1)
}

//hàm tiến page
function isNext(pageNumber) {
    getCoachByPage(pageNumber+1)
}

function searchCoach() {
    let search = document.getElementById("search").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/search?search=${search}`,
        success: function (data) {
            let display_coach = '';
            for (let i = 0; i < data.length; i++) {
                display_coach += displayCoach(data[i]);
            }
            document.getElementById('productList').innerHTML = display_coach;
            document.getElementById("searchForm").reset()
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
                getCoach();
            }
        });
    }
}
getCoach()
//end coach