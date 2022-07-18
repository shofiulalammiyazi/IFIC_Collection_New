async function save_loan_ptp() {

    /*var contact = $('#ptp_contact_details_id').find(":selected").attr('data-name');
    if(contact === undefined) {
        alert('Choose  ptp contact Correctly !');
        return false;
    }
    $('#ptp_contact_details').val(contact);*/

    /*var location = $('#ptp_location_details_id').find(":selected").attr('data-name');
    if( location===undefined) {
        alert('Choose ptp location Correctly !');
        return false;
    }
    $('#ptp_location_details').val(location);*/

    /*var promisor = $('#ptp_promisor_details_id').find(":selected").attr('data-name');
    if( promisor === undefined ) {
        alert('Choose ptp promisor Correctly !');
        return false;
    }
    $('#ptp_promisor_details').val(promisor);*/

    // if (savePtpValidation()==false){
    //     return;
    // }
    var booleanMesg=false;
    var formValues = $('#loan_ptpForm').serialize();
    var jsonFormData = JSON.stringify(formValues);
    console.log("form value : "+jsonFormData)
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

   await $.ajax({
            url: '/collection/loan/ptp/save',
            type: 'POST',
            data: formValues,
            beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if(data){
                    booleanMesg=true;
                    // swal("Successfully saved !!");
                    $('#modal-ptp-data-create').modal('hide');
                    $("#loan_ptpForm")[0].reset();
                    ptpinfo.updateTheList();
                }else{
                    alert("Something wrong. Please try again !");
                }

            },
            error: function (err) {
                $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                    "\t\t\t\t\t</div>");
                var message = "<ul style='list-style-type:disc'><li>Error in saving PTP Information!</li></ul>";
                $("#specialAlert").addClass("errorDiv");
                $("#specialAlert").append(message);
                $("#exampleModalLong").modal("toggle");
                console.log(err);
            }

        });
   return booleanMesg;
}
function update_loan_ptp() {

    var formValues = $('#loan_ptpForm_update').serialize();
    var jsonFormData = JSON.stringify(formValues);
    console.log("form value : "+jsonFormData)
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/collection/loan/ptp/save',
        type: 'POST',
        data: formValues,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            if(data){
                swal("Successfully saved !!");
                $('#modal-ptp-data-create').modal('hide');
                $("#loan_ptpForm")[0].reset();
                ptpinfo.updateTheList();
            }else{
                alert("Something wrong. Please try again !");
            }

        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving PTP Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }
    });
}


function savePtpValidation() {
    if ($("#loan_amount").val().length === 0
        && $("#ptp_contact_details_id").val().length === 0
        && $("#ptp_location_details_id").val().length === 0
        && $("#ptp_promisor_details_id").val().length === 0
        && $("#loan_ptp_time").val().length === 0
        && $("#loan_ptp_dates").val().length === 0
        && $("#loan_ptp_remarks").val().length === 0) {
        alert("must be fill up at least one field");
        return false;
    }
}

function ptpData() {
    var id = $("#loanptpcustomerId").val();
    var date = new Date();
    var startDate = new Date(date.getFullYear(), date.getMonth(), 1);
    var endDate = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    $.ajax({
        url: "/collection/loan/ptp//ptp-list?customerId="+id+"startDate="+startDate+"endDate="+endDate,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {

            console.log("ptp list -> ; "+JSON.stringify(data));
            $('#ptptBody').replaceWith("<tbody id=\"ptptBody\" >\n" + "\t\t\t\t\t\t\t</tbody>");

            for(var i=0;i<data.ptp.length;i++){
                if (data.ptp[i]!=null){

                    let date = new Date(data.ptp[i].loan_ptp_date);

                    let date1 = new Date();
                    date1.setHours(0);
                    date1.setMinutes(0);
                    date1.setSeconds(0);

                    var str1 = '';
                    var str2 = '';
                    let b = date >= date1;
                    if(b){
                        str1 = "<a style=\"margin-left:2px\" class=\"btn btn-xs btn-info\" onclick=\"loan_ptp_editRow(this,"+data.ptp[i].loan_contact_details_id+","+data.ptp[i].loan_contact_location_id+","+data.ptp[i].loan_promisor_details_id+","+data.ptp[i].loan_ptp_date+")\"> <i class=\"fa fa-edit\"></i></a>"
                        str2 = "<a class=\"btn btn-xs btn-danger\" onclick=\"loan_ptp_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a>";
                    }


                    var str="<tr>"+
                        "<td style=\"display:none;\">" + data.ptp[i].id + "</td>" +
                        "<td>" + data.ptp[i].loan_amount + "</td>" +
                        "<td>" + data.ptp[i].loan_ptp_date + "</td>" +
                        "<td>" + data.ptp[i].loan_ptp_time + "</td>" +
                        "<td>" + data.ptp[i].loan_contact_details + "</td>" +
                        "<td>" + data.ptp[i].loan_contact_location + "</td>" +
                        "<td>"  + data.ptp[i].loan_promisor_details + "</td>" +
                        "<td> " + data.ptp[i].loan_ptp_status + "</td>" +
                        "<td>"+ str2 + str1 + "<a style=\"margin-left:2px\" class=\"btn btn-xs btn-info\" onclick=\"loan_ptp_viewRow(this)\"> <i class=\"fa fa-eye\"></i></a>"+"</td>" +
                        "</tr>";


                    $('#ptptBody').append(str);
                }
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function loan_ptp_deleteRow(node){
    var result = confirm("Want to delete?");
    if (result) {
        var id=node.parentNode.parentNode.cells[0].innerHTML;
        console.log(id);
        $.ajax({
            url: "/collection/loan/ptp/remove?id="+id,
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                alert(data.successMsg);
            },
            error: function (data) {
                //alert("Error!");
            }
        });

        $(node).closest("tr").remove();
    }

}

function loan_ptp_editRow(node,contact_id,location_id,promisor_id){
    $("#ptp_contact_details_id_single").html('');
    $("#ptp_location_details_id_single").html('');
    $("#ptp_promisor_details_id_single").html('');

    var row=node.parentNode.parentNode;
    globalRow=row;
    ptpdetails(1,contact_id,row.cells[3].innerHTML,location_id,row.cells[4].innerHTML,promisor_id,row.cells[5].innerHTML);

    $("#loan_amount_single").val(parseFloat(row.cells[2].innerHTML.replace(/,/g, '')));

    document.getElementById("card_ptp_time_single").value = row.cells[4].innerHTML.trim();
    $("#loan_ptp_remarks_single").val(row.cells[5].innerHTML.trim());
    $("#id_single").val(row.cells[0].innerHTML);
    $('#modal-ptp-data-create').modal('show');

    var date = new Date(row.cells[3].innerHTML);
    var year = date.getFullYear();
    var month = date.getMonth()+1; //if(month<10){month='0'+month}
    var day = date.getDate(); //if(day<10){day='0'+day}
    var monthName = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'];
    date = month +'-'+day+'-'+year;
    var date2 = day +' '+monthName[month]+' '+year;

    $("#loan_ptp_date_update").val(date);
    $("#loan_ptp_date_single").val(date2);
    //ptp.date=date;
}

function loan_ptp_viewRow(node) {
    var listid=node.parentNode.parentNode.cells[0].innerHTML;
    var creator = node.parentNode.parentNode.cells[1].innerHTML;
    $.ajax({
        url: "/collection/loan/ptp/listById?loanPtpId="+listid,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log("data : "+JSON.stringify(data));
            $("#loan_ptp_amount_view").text(data.loanptpList.loan_amount);
            $("#loan_ptp_contact_view").text(data.loanptpList.loan_contact_details);
            $("#loan_contact_location_view").text(data.loanptpList.loan_contact_details);
            $("#loan_ptp_promisor_view").text(data.loanptpList.loan_promisor_details);
            $("#loan_ptp_date_view").text(data.loanptpList.loan_ptp_date);
            $("#loan_ptp_time_view").text(data.loanptpList.loan_ptp_time);
            // $("#loan_ptp_remark_view").text(data.loanptpList.loan_ptp_remarks);
            $("#loan_ptp_creator_view").text(creator);

        },
        error: function (data) {
            //alert("Error!");
        }
    });
    $('#modal-ptp-view').modal('show');

}

function ptpdetails(edit=0,contact_id=0,contact_name="",location_id=0,location_name="",promisor_id=0,promisor_name=""){
    console.log("ptp details function : "+edit)
    if(edit==0) {
        $("#loan_amount").val("");
        // $("#loan_ptp_date").val("");
        // $("#card_ptp_time").val("");
        // $("#loan_ptp_remarks").val("");
    }

    $("#id").val("");

    $.ajax({
        url: "/collection/ptp_contact_details/listall",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
             console.log("all ptp list : "+JSON.stringify(data));
            $('#ptp_contact_details_id').empty();

            if (edit == 0) {
                $("#ptp_contact_details_id").append('<option value=""></option>');
            }

            $.each(data.contact, function(index, value){
                $('#ptp_contact_details_id').append('<option value ="'+ value.id +'" data-name="'+value.contactName.toString()+'">'+value.contactName+'</option>');
                $('#ptp_contact_details_id_single').append('<option value ="'+ value.id +'" data-name="'+value.contactName.toString()+'">'+value.contactName+'</option>');
            });

            if(edit==1){

                setTimeout(function () {
                    contactDetailsForUpdate();
                    $('select#ptp_contact_details_id_single option[value="' + contact_id + '"]').prop('selected', true);
                }, 100);
            }
        },
        error: function (data) {
            //alert("Failure!");
        }
    });

    $.ajax({
        url: "/collection/ptpContact_location/listall",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(data.menuList);
            $('#ptp_location_details_id').empty();

            if (edit == 0) {
                $("#ptp_location_details_id").append('<option value="">Choose</option>');
            }

            if(edit==1){
                // $('#ptp_location_details_id').append('<option value ="'+location_id+'" data-name="'+location_name+'" selected>'+location_name+'</option>');

                console.log("location id : "+location_id)
                setTimeout(function () {
                    contactLocationForUpdate();
                    $('select#ptp_location_details_id_single option[value="' + location_id + '"]').prop('selected', true);
                }, 100);
            }

            $.each(data.location, function(index, value){
                $('#ptp_location_details_id').append('<option value ="'+ value.id +'" data-name="'+value.contactLocation.toString()+'">'+value.contactLocation+'</option>');
                $('#ptp_location_details_id_single').append('<option value ="'+ value.id +'" data-name="'+value.contactLocation.toString()+'">'+value.contactLocation+'</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });


    // $.ajax({
    //     url: "/collection/ptpContact_location/listall",
    //     type: "GET",
    //     contentType: "application/json; charset=utf-8",
    //     dataType: "json",
    //     success: function (data) {
    //         // console.log(data.menuList);
    //         $('#ptp_location_details_id').empty();
    //
    //         if (edit == 0) {
    //             $("#ptp_location_details_id").append('<option value="">Choose</option>');
    //         }
    //
    //         if(edit==1){
    //             // $('#ptp_location_details_id').append('<option value ="'+location_id+'" data-name="'+location_name+'" selected>'+location_name+'</option>');
    //
    //             console.log("location id : "+location_id)
    //             setTimeout(function () {
    //                 contactLocationForUpdate();
    //                 $('select#ptp_location_details_id_single option[value="' + location_id + '"]').prop('selected', true);
    //             }, 100);
    //         }
    //
    //         $.each(data.location, function(index, value){
    //             $('#ptp_location_details_id').append('<option value ="'+ value.id +'" data-name="'+value.contactLocation.toString()+'">'+value.contactLocation+'</option>');
    //             $('#ptp_location_details_id_single').append('<option value ="'+ value.id +'" data-name="'+value.contactLocation.toString()+'">'+value.contactLocation+'</option>');
    //         });
    //     },
    //     error: function (data) {
    //         //alert("Failure!");
    //     }
    // });

    $.ajax({
        url: "/collection/ptp_promisor/listall",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(data.menuList);
            $('#ptp_promisor_details_id').empty();
            if (edit == 0) {
                $("#ptp_promisor_details_id").append('<option value=""></option>');
            }

            if(edit==1){
                // $('#ptp_promisor_details_id').append('<option value ="'+promisor_id+'" data-name="'+promisor_name+'" selected>'+promisor_name+'</option>');

                setTimeout(function () {
                    promisorDetailsForUpdate();
                    $('select#ptp_promisor_details_id_single option[value="' + promisor_id + '"]').prop('selected', true);
                }, 100);
            }
            $.each(data.promisor, function(index, value){
                $('#ptp_promisor_details_id').append('<option value ="'+ value.id +'" data-name="'+value.promisorDetail.toString()+'">'+value.promisorDetail+'</option>');
                $('#ptp_promisor_details_id_single').append('<option value ="'+ value.id +'" data-name="'+value.promisorDetail.toString()+'">'+value.promisorDetail+'</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });
}

function dairyNoteValidation() {

    if($("#card_menu").val()==""){
        alert("menu be filled out!");
        $("#card_menu").focus();
        return false;
    }
    if($("#card_submenu_one").val()==""){
        alert("sub menu one be filled out!");
        $("#card_submenu_one").focus();
        return false;
    }

    if($("#card_submenu_two").val()==""){
        alert("sub menu two  must be filled out!");
        $("#card_submenu_two").focus();
        return false;
    }



    if($("#card_submenu_three").val()==""){
        alert("sub menu three must be filled out!");
        $("#card_submenu_three").focus();
        return false;
    }
    if($("#card_time").val()==""){
        alert(" time  must be filled out!");
        $("#card_time").focus();
        return false;
    }
    if($("#card_date").val()==""){
        alert("date must be filled out!");
        $("#card_date").focus();
        return false;
    }
    if($("#card_short_note").val()==""){
        alert("short note must be filled out!");
        $("#card_short_note").focus();
        return false;
    }

}

function clearformdisable() {
    $("#loan_ptpForm :input").prop("disabled", false);
    $("#loan_ptp_save_btn").show()
    // location.reload(true);
}


$(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var maxDate = year + '-' + month + '-' + day;
    // alert(maxDate);
    $('#loan_ptp_date').attr('min', maxDate);
});


$(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var maxDate = year + '-' + month + '-' + day;
    // alert(maxDate);
    $('#loan_ptp_date').attr('min', maxDate);
});