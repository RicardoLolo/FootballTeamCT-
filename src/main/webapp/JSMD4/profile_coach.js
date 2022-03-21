if (currentUser.roles[0].authority === "ADMIN" || currentUser.roles[0].authority === "COACH"){
    document.getElementById("edit-coach").hidden = false;
}

function getTypeCoach() {
    $.ajax({
        type: "GET", //tên API
        url: `http://localhost:8080/api/coach/type-coach`, //xử lý khi thành công
        success: function (data) {
            let content = '<select id="coachType-coach-edit" class="form-control">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayTypeCoach(data[i]);
            }
            content += '</select>'
            document.getElementById('div-typeCoach-edit').innerHTML = content;
        }
    });
}

getTypeCoach();

function displayTypeCoach(typeCoach) {
    return `<option id="${typeCoach.id}" value="${typeCoach.id}">${typeCoach.type}</option>`;
}


function displayEditCoach(coach) {
    $('#name-coach-edit').val(coach.name);
    $('#gmail-coach-edit').val(coach.gmail);
    $('#birthday-coach-edit').val(coach.birthday);
    $('#country-coach-edit').val(coach.country);
    $('#salary-coach-edit').val(coach.salary);
    $('#bonus-coach-edit').val(coach.bonus);
    $('#introduction-coach-edit').val(coach.introduction);
    $('#coachType-coach-edit').val(coach.coachType.id);
    $('#password-coach-edit').val(coach.password);
}

function displayBGCoach(coach) {
    return `<img class="img-fluid" src="${coach.avatarBackGround}" alt="Card image cap" width="800px" height="500px">`;
}

function displayAvaCoach(coach) {
    return `<img src="${coach.avatarURL}" alt="profile-image" class="profile" width="110px" height="110px">
            <h5 class="card-title">${coach.name}</h5>
            <p class="card-text">${coach.introduction}</p>`;

}

function displayValueCoach(coach) {
    return `<tr>
                <th>
                  <strong>Email</strong>
                </th>
                <td>
                  <h6>${coach.gmail}</h6>
                </td>
            </tr>
            <tr>
                <th>
                  <strong>Date of Birth</strong>
                </th>
                <td>
                  <h6>${coach.birthday}</h6>
                </td>
            </tr>
            <tr>
                <th>
                  <strong>Country</strong>
                </th>
                <td>
                  <h6>${coach.country}</h6>
                </td>
            </tr>
            <tr>
                <th>
                   <strong>Salary</strong>
                   <small class="badge float-right badge-light">bonus</small>
                </th>
                <td>
                    <strong>$ ${coach.salary}</strong>
                    <span class="badge float-right badge-success">$ ${coach.bonus}</span>
                </td>
            </tr>
            <tr>
                <th>
                    <strong>Coach Type</strong>
                </th>
                <td>
                    <strong>${coach.coachType.type}</strong>
                </td>
            </tr>`;
}

function findCoachById() {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/api/coach/find-coach-by-id/${localStorage.getItem("id_coach")}`,
        success: function (data) {
            document.getElementById("displayBGCoach").innerHTML = displayBGCoach(data);
            document.getElementById("displayAvaCoach").innerHTML = displayAvaCoach(data);
            document.getElementById("displayValueCoach").innerHTML = displayValueCoach(data);
            console.log(localStorage.getItem("id_coach"));
            displayEditCoach(data);
        }
    });
}

findCoachById();

function editCoach() {
    let data = new FormData();
    let name = $('#name-coach-edit').val();
    let gmail = $('#gmail-coach-edit').val();
    let birthday = $('#birthday-coach-edit').val();
    let country = $('#country-coach-edit').val();
    let salary = $('#salary-coach-edit').val();
    let bonus = $('#bonus-coach-edit').val();
    let introduction = $('#introduction-coach-edit').val();
    let coachType = $('#coachType-coach-edit').val();
    let password = $('#password-coach-edit').val();
    if (gmail === '') {
        document.getElementById("card-edit-coach").innerHTML = "Gmail cannot be blank!";
        return false;
    }
    if (password === '') {
        document.getElementById("card-edit-coach").innerHTML = "Password cannot be blank!";
        return false;
    }
    let newCoach = {
        name: name,
        gmail: gmail,
        birthday: birthday,
        country: country,
        salary: salary,
        bonus: bonus,
        introduction: introduction,
        password: password,
        coachType: {
            id: coachType,
        }
    };
    data.append("coach", new Blob([JSON.stringify(newCoach)], {type: 'application/json'}))
    if ($("#avaFile-coach-edit")[0].files[0] !== undefined) {
        data.append("avaFile_coach", $('#avaFile-coach-edit')[0].files[0]);
    }
    if ($("#BGFile-coach-edit")[0].files[0] !== undefined) {
        data.append("backGroundFile_coach", $('#BGFile-coach-edit')[0].files[0]);
    }
    if (confirm("Are you sure you want to edit event ?")) {
        $.ajax({
            contentType: false,
            processData: false,
            type: "PUT",
            data: data,
            url: `http://localhost:8080/api/coach/edit-coach/${localStorage.getItem("id_coach")}`,
            success: function () {
                if (data.status === 204 || data.status === 400 || data.status === 404) {
                    document.getElementById("card-edit-coach").innerHTML = "Pay attention to the information in the registration form (Gmail may be duplicated)!";
                } else {
                    document.getElementById("card-edit-coach").innerHTML = "Update Success !";
                    window.location.href = "profile_coach.html"
                    findCoachById();
                }
            }
        });
    }
    event.preventDefault();
}