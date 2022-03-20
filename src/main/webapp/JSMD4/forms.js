if (role_start === "ADMIN"){
    document.getElementById("forms-new-coach").hidden = false;
    document.getElementById("forms-new-player").hidden = false;
} else if (role_start === "COACH"){
    document.getElementById("forms-new-player").hidden = false;
}

function getTypeCoach() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/coach/type-coach`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="input-10" class="form-control form-control-rounded">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayTypeCoach(data[i]);
            }
            content += '</select>'
            document.getElementById('div-typeCoach').innerHTML = content;
        }
    });
}

getTypeCoach();

function displayTypeCoach(typeCoach) {
    return `<option id="${typeCoach.id}" value="${typeCoach.id}">${typeCoach.type}</option>`;
}

//hàm tạo mới product data
function addNewCoach() {
    let data = new FormData();
    let name = $('#input-1').val();
    let gmail = $('#input-2').val();
    let birthday = $('#input-3').val();
    let country = $('#input-4').val();
    let salary = $('#input-5').val();
    let bonus = $('#input-6').val();
    let introduction = $('#input-7').val();
    let coachType = $('#input-10').val();
    let password = $('#input-11').val();
    if(gmail === ''){
        document.getElementById("card-coach").innerHTML = "Gmail cannot be blank!";
        return false;
    }
    if (password === ''){
        document.getElementById("card-coach").innerHTML = "Password cannot be blank!";
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
    data.append("coach", new Blob([JSON.stringify(newCoach)], {type : 'application/json'}))
    if ($("#input-8")[0].files[0] !== undefined){
        data.append("avaFile-coach", $('#input-8')[0].files[0]);
    }
    if ($("#input-9")[0].files[0] !== undefined){
        data.append("backGroundFile-coach", $('#input-9')[0].files[0]);
    }
    console.log(data.get("avaFile-coach"))
    console.log(data.get("backGroundFile-coach"))
    console.log(data.get("coach"))
    if (confirm("Are you sure you want to create new coach ?")) {
        $.ajax({
            contentType : false,
            processData : false,
            type: "POST",
            data: data,
            url: "http://localhost:8080/api/coach/save-coach",
            success: function (data) {
                if (data.status === 204 || data.status === 400 || data.status === 404) {
                    document.getElementById("card-coach").innerHTML = "Pay attention to the information in the registration form (Gmail may be duplicated)!";
                } else {
                    document.getElementById("card-coach").innerHTML = "Sign Up Success !";
                    document.getElementById("form-new-coach").reset();
                }
            }
        });
    }
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function getPerformance() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/player/performance`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="input-24" class="form-control form-control-rounded">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayPerformance(data[i]);
            }
            content += '</select>'
            document.getElementById('div-performance').innerHTML = content;
        }
    });
}

getPerformance();

function displayPerformance(performance) {
    return `<option id="${performance.id}" value="${performance.id}">${performance.name}</option>`;
}

function getPosition() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/player/position`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="input-23" class="form-control form-control-rounded">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayPosition(data[i]);
            }
            content += '</select>'
            document.getElementById('div-position').innerHTML = content;
        }
    });
}

getPosition();

function displayPosition(position) {
    return `<option id="${position.id}" value="${position.id}">${position.name}</option>`;
}

function getStatus() {
    $.ajax({
        type: "GET",
        //tên API
        url: `http://localhost:8080/api/player/status`,
        //xử lý khi thành công
        success: function (data) {
            let content = '<select id="input-25" class="form-control form-control-rounded">\n'
            for (let i = 0; i < data.length; i++) {
                content += displayStatus(data[i]);
            }
            content += '</select>'
            document.getElementById('div-status').innerHTML = content;
        }
    });
}

getStatus();

function displayStatus(status) {
    return `<option id="${status.id}" value="${status.id}">${status.state}</option>`;
}

function addNewPlayer() {
    let data = new FormData();
    let name = $('#input-12').val();
    let gmail = $('#input-13').val();
    let birthday = $('#input-14').val();
    let country = $('#input-15').val();
    let height = $('#input-16').val();
    let weight = $('#input-17').val();
    let salary = $('#input-18').val();
    let bonus = $('#input-19').val();
    let introduction = $('#input-20').val();
    let position = $('#input-23').val();
    let performance = $('#input-24').val();
    let status = $('#input-25').val();
    let password = $('#input-26').val();
    if(gmail === ''){
        document.getElementById("card-player").innerHTML = "Gmail cannot be blank!";
        return false;
    }
    if (password === ''){
        document.getElementById("card-player").innerHTML = "Password cannot be blank!";
        return false;
    }
    let newPlayer = {
        name: name,
        gmail: gmail,
        birthday: birthday,
        country: country,
        height: height,
        weight: weight,
        salary: salary,
        bonus: bonus,
        introduction: introduction,
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
    data.append("player", new Blob([JSON.stringify(newPlayer)], {type : 'application/json'}))
    if ($("#input-21")[0].files[0] !== undefined){
        data.append("avaFile-player", $('#input-21')[0].files[0]);
    }
    if ($("#input-22")[0].files[0] !== undefined){
        data.append("backGroundFile-player", $('#input-22')[0].files[0]);
    }
    console.log(data.get("avaFile-player"))
    console.log(data.get("backGroundFile-player"))
    console.log(data.get("player"))
    if (confirm("Are you sure you want to create new player ?")) {
        $.ajax({
            contentType : false,
            processData : false,
            type: "POST",
            data: data,
            url: "http://localhost:8080/api/player/save-player",
            success: function (data) {
                if(data.status === 204 || data.status === 400 || data.status === 404){
                    document.getElementById("card-player").innerHTML = "Pay attention to the information in the registration form (Gmail may be duplicated)!";
                } else {
                    document.getElementById("card-coach").innerHTML = "Sign Up Success !";
                    document.getElementById("form-new-player").reset();
                }
            }
        });
    }
    event.preventDefault();
}