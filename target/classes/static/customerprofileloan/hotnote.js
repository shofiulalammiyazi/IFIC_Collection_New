function loanHotNoteSave() {
    var formValue = $("#hotNoteSaveForm").serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

//     if($("#loan_vipStatus").val()==="Select" || $("#loan_vipStatus").val()=="" ||$("#loan_vipStatus").val()==="undefined"
//         ||$("#loan_hot_not_details").val()===""
// ){
//         alert("please select Vip status");
//         return false}
//    if ( $("#loan_hot_not_details").val()==="" ){
//         alert("Hot Note Details can't empty");
//         return false
//     }
//
//
//     if($("#loan_status_flag").val()==="Select" || $("#loan_status_flag").val()===""||$("#loan_status_flag").val()==="undefined"){
//         alert("please select  status");
//         return false
//     }



    if(($("#loan_vipStatus").val().length === 0 ||$("#loan_vipStatus").val()==="Select") && $("#loan_hot_not_details").val().length === 0 && ($("#loan_status_flag").val().length === 0 ||$("#loan_status_flag").val()==="Select" ))  {
        alert("Fill Up at least one field ");
        return false
    }
        $.ajax({
            type:"post",
            url:"/customerloanprofile/hotnote/save",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data:formValue,
            success:function (data) {
                var dt = new Date();
                var month = dt.getMonth();
                if( month <= 9 ) {
                    month = "0"+dt.getMonth();
                }


                $('#modal-hotnote').modal('toggle');
                // var formdatalist = $('#hotNoteSaveForm');
                $('#hotNoteSaveForm').trigger("reset");
                hotNote.upDateHoteNoteList(data);

            }
        })




}


function loanClearHotNoteForm() {
    $('#hotNoteSaveForm').trigger("reset");
}


$('#addHotNoteId').click(function () {

    $("#loan_vipStatus").val('');
    $("#editHotNoteId").val('');
    $("#loan_hot_not_details").val('');
    $("#loan_status_flag").val('');

})
