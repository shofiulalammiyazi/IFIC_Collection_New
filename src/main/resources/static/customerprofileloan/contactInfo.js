async function contactSave() {

    // if (contactValidate()==false){
    //     return;
    // }
    var bool = false;
    var form = $("#considerAttemptForm");
    var formValue = $("#considerAttemptForm").serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    // if(form.find("#consider_attempt_id").val() == "Call Received") {
    //     if (form.find("#considet_contact_id").val() == "") {
    //         alert("Please select Consideration as Contact.");
    //         return false
    //     }
    //
    //     if (form.find("#contact_category_id").val() == "") {
    //         alert("Please select Contact Category.");
    //         return false
    //     }
    //
    // }

    await $.ajax({
        type:"post",
        url:"/customerLoanProfile/ContactInfo/save",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data:formValue,
        success:function (data) {
            // swal("Successfully saved !!");
            // successMesg()
            bool = true;
            var dt = new Date();
            var month = dt.getMonth();
            if( month <= 9 ) {
                month = "0"+dt.getMonth();
            }
            contactInfo.upDateContactList(data);
            contactInfoone.upDateContactList(data);
            $('#considerAttemptForm').trigger("reset");

        }
    })
    return bool;
}

$('#modal-contactInfo').on('show.bs.modal', function () {
    $(this).find('textarea').val('');
});