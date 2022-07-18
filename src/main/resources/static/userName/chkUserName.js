$(document).ready(function () {

    $("#uniqueUserNameMsg").hide();

    $("#uniqueUserName").focusout(function () {
        //console.log("focusout");

        var userName=$("#uniqueUserName").val();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        if(userName.length > 2)
        {
            $("#uniqueUserNameMsg").slideUp(600);

            $.ajax({
                url: '/user/checkUsername?userName='+userName,
                type: 'GET',
                beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    //console.log("DATA : "+data);

                    if(data)
                    {
                        $("#uniqueUserNameMsg").text("Username already exists.Please choose another");

                        $("#uniqueUserNameMsg").slideDown(600);
                        $("#userSave").attr("disabled", true);
                    }else {
                        $("#uniqueUserNameMsg").slideUp(600);
                        $("#userSave").attr("disabled", false);
                    }
                },
                error: function (err) {

                    console.log(err);
                }
            });
        }else {
            $("#uniqueUserNameMsg").text("Username must be more than two characters");

            $("#uniqueUserNameMsg").slideDown(600);
            $("#userSave").attr("disabled", true);
        }
    });
});