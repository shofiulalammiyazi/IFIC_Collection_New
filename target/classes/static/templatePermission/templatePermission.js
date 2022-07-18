$(document).ready(function () {

    $("#selectAll").click(function () {

        if($("#selectAll").prop('checked'))
        {
            $(".pageList").prop('checked',true);
        }else {
            $(".pageList").prop('checked',false);
        }
    })
});
