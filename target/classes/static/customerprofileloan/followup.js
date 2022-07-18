async function followUpSave() {

    // if (followUpValidate()==false){
    //     return;
    // }

    var booleanMesg = false;
    // var formValue = $("#followUpSaveForm").append();
    var formValues = $('#followUpSaveForm').serialize();
    var jsonFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    //console.log("token " + token + " Header = " + header);

    var followUpReason = [];
    $('.ads_Checkbox').each(function () {
        if ($(this).prop('checked') == true) {
            if (followUpReason.length > 0) {
                followUpReason += ", ";
            }
            followUpReason += $(this).val();
            console.log('follow up reason: '+ followUpReason);
        }
    });

    await $.ajax({
        type: "post",
        url: "/customerloanprofile/followup/save",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data: jsonFormData+"&followUpReason="+followUpReason,
        success: function (data) {
            booleanMesg=true;
            if (data){

                followUp.followUpList.push(data);
            }
        }
    }).done(function () {
        // Clear Form
        $('#followUpSaveForm').find(':input:not([type=hidden])').val('');
        $('#followUpSaveForm').find('input:checkbox').prop('checked', false);
    });

    return booleanMesg
}

var date = new Date();
$('.fu-date').datepicker({
    startDate: date,
    format: 'dd-mm-yyyy',
    autoclose: true,
    // container:'#modal-filefollowup',
    forceParse: false
});
$('.fu2-date').datepicker({
    endDate: date,
    format: 'dd-mm-yyyy',
    autoclose: true,
    // container:'#modal-filefollowup',
    forceParse: false
})


// function followUpValidate() {
//     if($("#loan_follow_up_dates").val().length===0 && $("#loan_follow_up_time").val().length===0 && $("input[type='checkbox'][class='checkFollowUpReason']:checked").length===0){
//         alert("must be fill up at least one field");
//         return false;
//     }
//
// }