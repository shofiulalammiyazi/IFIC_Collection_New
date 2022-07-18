$(document).ready(function () {
    var dropDownUserId=$("#dropDownUserId").val();
    if(dropDownUserId != null){
        if(dropDownUserId.length>0)
        {
            var userId=$("#dropDownUserId").val();
            console.log("click : "+userId);
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                url: '/user-role/authentication/getRolesByid?userId='+userId,
                type: 'GET',
                beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    $(".roleList").prop('checked',false);
                    for(var i=0;i<data.length;i++)
                    {
                        $("#role"+data[i].roleId).prop('checked',true);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
    }

    $("#dropDownUserId").change(function () {
        var userId=$("#dropDownUserId").val();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/user-role/authentication/getRolesByid?userId='+userId,
            type: 'GET',
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                $(".roleList").prop('checked',false);

                for(var i=0;i<data.length;i++)
                {

                    $("#role"+data[i].roleId).prop('checked',true);
                }
            },
            error: function (err) {

                console.log(err);
            }
        });
    });
});