function displayFormCreate() {
    document.getElementById("form").reset()
    document.getElementById("form").hidden = false;
    document.getElementById("form-button").onclick = function () {
        addNewCoach();
    }
    getCoachType();
}

//hàm tạo mới product data
function addNewCoach() {
    //lấy dữ liệu
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
        type: "POST",
        data: JSON.stringify(newCoach),
        //tên API
        url: "http://localhost:8080/api/coaches",
        //xử lý khi thành công
        success: function () {
            getCoach();
        }

    });
    event.preventDefault();
}