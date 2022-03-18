let index = 0;


function getCoachType() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/coaches/type`,
        //xử lý khi thành công
        success: function (data) {
            let content = `<select id="coaches">`
            for (let i = 0; i < data.length; i++) {
                content += displayCoachType(data[i]);
            }
            content += '</select>'
            document.getElementById('div-coachType').innerHTML = content;
        }
    });
}

//hàm hiển thị select list coach type
function displayCoachType(coachType) {
    return `<option id="${coachType.id}" value="${coachType.id}">${coachType.type}</option>`;
}

function displayCoach(coach) {
    return `<tr><td>${coach.name}</td><td>${coach.birthday}</td><td>${coach.country}</td>
            <td>${coach.salary}</td><td>${coach.bonus}</td><td>${coach.introduction}</td>
            <td>${coach.avatarURL}</td><td>${coach.avatarBackGround}</td>
            <td>${coach.gmail}</td><td>${coach.password}</td>
            <td>${coach.coachType.type}</td>
            <td><button class="btn btn-danger" onclick="deleteCoach(${coach.id})">Delete</button></td>
            <td><button class="btn btn-warning" onclick="editCoach(${coach.id})">Edit</button></td></tr>`;
}

function displayCoachHeader() {
    return `<tr><th>CoachName</th>
            <th>Birthday</th>
            <th>Country</th>
            <th>Salary</th>
            <th>Bonus</th>
            <th>Avatar</th>
            <th>BackGround</th>
            <th>Gmail</th>
            <th>Password</th>
            <th>Coach Type</th>
            <th colspan="2">Action</th></tr>`
}

//hàm lấy list
function getCoach() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coaches`,
        success: function (data) {
            // hiển thị danh sách
            let content = displayCoachHeader();
            for (let i = 0; i < data.length; i++) {
                content += displayCoach(data[i]);
            }
            document.getElementById("coachList").innerHTML = content;

        }
    });
}

function editCoachPost() {
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
    let coachType = $('#coachType').val()
    let newCoach = {
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
        coachType: {
            id: coachType,
        }
    };
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(newCoach),
        url: `http://localhost:8080/api/coaches/${index}`,
        success: function () {
            getCoach()
        }
    });
    event.preventDefault();
}

//hàm xóa 1 product data theo id
function deleteCoach(id) {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/api/coaches/${id}`,
        success: function () {
            getCoach()
        }
    });
}

//hàm hiển thị thông tin edit product
function editCoach(id) {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/coaches/${id}`,
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
                editCoachPost()
            };
            getCoachType();
        }
    });
}

//hàm lấy list product theo page
function getCoachByPage(page) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coaches/page?page=${page}`,
        success: function (data) {
            let array = data.content
            let content = displayCoachHeader();
            for (let i = 0; i < array.length; i++) {
                content += displayCoach(array[i]);
            }
            document.getElementById("productList").innerHTML = content;
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
function displayPage(data) {
    return `<button class="btn btn-primary" id="backup" onclick="isPrevious(${data.pageable.pageNumber})">Previous</button>
    <span>${data.pageable.pageNumber + 1} | ${data.totalPages}</span>
    <button class="btn btn-primary" id="next" onclick="isNext(${data.pageable.pageNumber})">Next</button>`
}

//hàm lùi page
function isPrevious(pageNumber) {
    getCoachByPage(pageNumber - 1)
}

//hàm tiến page
function isNext(pageNumber) {
    getCoachByPage(pageNumber + 1)
}

//hàm tìm kiếm list product theo name gần đúng
function searchProduct() {
    let search = document.getElementById("search").value;
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coaches/search?search=${search}`,
        success: function (data) {
            let content = displayCoachHeader();
            for (let i = 0; i < data.length; i++) {
                content += displayCoach(data[i]);
            }
            document.getElementById('coachList').innerHTML = content;
            document.getElementById("searchForm").reset()
        }
    });
    event.preventDefault();
}
function uploadFile() {
    let form = document.forms[1];
    let formData = new FormData(form);
    console.log(form)
    console.log(formData)
    $.ajax({
        contentType : false,
        processData : false,
        type: "POST",
        data: formData,
        url: "http://localhost:8080/api/coaches/upload",
        success: function (data) {
            localStorage.setItem("a", data)
        }
    });
}
function uploadFileFull() {
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
    let coachType = $('#coachType').val()
    let newCoach = {
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
        coachType: {
            id: coachType,
        }
    };
    let formData = new FormData();
    formData.append("file", $('#file')[0].files[0])
    formData.append("coach", new Blob([JSON.stringify(newCoach)], {type : 'application/json'}))
    console.log(formData.get("file"))
    console.log(formData.get("coach"))
    $.ajax({
        contentType : false,
        processData : false,
        // mimeType: "multipart/form-data",
        type: "POST",
        data: formData,
        url: "http://localhost:8080/api/coaches/upload1",
        success: function (data) {
            console.log(data)
        }

    });
    event.preventDefault();
}
getCoach()
getCoachType()