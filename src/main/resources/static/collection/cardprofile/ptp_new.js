async function save_card_ptp_new() {
   var booleanmsg=false
    var contact = $('#ptp_contact_details_id').find(":selected").attr('data-name');
    if(contact === undefined) {
        alert('Choose  ptp contact Correctly !');
        return false;
    }
    $('#ptp_contact_details').val(contact);

    var location = $('#ptp_location_details_id').find(":selected").attr('data-name');
    if( location===undefined) {
        alert('Choose ptp location Correctly !');
        return false;
    }
    $('#ptp_location_details').val(location);

    var promisor = $('#ptp_promisor_details_id').find(":selected").attr('data-name');
    if( promisor === undefined ) {
        alert('Choose ptp promisor Correctly !');
        return false;
    }
    $('#ptp_promisor_details').val(promisor);


    var formValues = $('#card_ptpForm').serialize();
    var jsonFormData = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

  await  $.ajax({
        url: '/collection/card/ptp/save',
        type: 'POST',
        data: formValues,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            // alert(data.successMsg);
            // console.log("ptp data console view"+data.ptp);
            // $('#modal-ptp').modal('hide');
            $("#card_ptpForm")[0].reset();
              booleanmsg=true;
            ptpData();
        },
        error: function (err) {
            $("#specialAlert").replaceWith("<div class=\"modal-body\" id=\"specialAlert\">\n" +
                "\t\t\t\t\t</div>");
            var message = "<ul style='list-style-type:disc'><li>Error in saving Dairy Notes Information!</li></ul>";
            $("#specialAlert").addClass("errorDiv");
            $("#specialAlert").append(message);
            $("#exampleModalLong").modal("toggle");
            console.log(err);
        }

    });

return booleanmsg;
}


/*$("#tab-ptp").click(function (event) {
    ptpData();
});*/

function ptpData() {
    var id = $("#cardptpcustomerId").val();

    console.log(" card ptp Id"+id);

    $.ajax({
        url: "/collection/card/ptp/list?id="+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {

            console.log(data);
            $('#ptptBody').replaceWith("<tbody id=\"ptptBody\" >\n" + "\t\t\t\t\t\t\t</tbody>");

            for(var i=0;i<data.ptp.length;i++){
                if (data.ptp[i]!=null){

                    let date = new Date(data.ptp[i].card_ptp_date);

                    let date1 = new Date();
                    date1.setHours(0);
                    date1.setMinutes(0);
                    date1.setSeconds(0);

                    var str1 = '';
                    var str2 = '';
                    let b = date >= date1;
                    if(b){
                        str1 = "<a style=\"margin-left:2px\" class=\"btn btn-xs btn-info\" onclick=\"card_ptp_editRow(this,"+data.ptp[i].card_contact_details_id+","+data.ptp[i].card_contact_location_id+","+data.ptp[i].card_promisor_details_id+")\"> <i class=\"fa fa-edit\"></i></a>"
                        str2 = "<a class=\"btn btn-xs btn-danger\" onclick=\"card_ptp_deleteRow(this)\"> <i class=\"fa fa-trash\"></i></a>";
                    }


                    var str="<tr>"+
                        "<td style=\"display:none;\">" + data.ptp[i].id + "</td>" +
                        "<td>" + data.ptp[i].card_amount + "</td>" +
                        "<td>" + data.ptp[i].card_ptp_date + "</td>" +
                        "<td>" + data.ptp[i].card_ptp_time + "</td>" +
                        "<td>" + data.ptp[i].card_contact_details + "</td>" +
                        "<td>" + data.ptp[i].card_contact_location + "</td>" +
                        "<td>"  + data.ptp[i].card_promisor_details + "</td>" +
                        "<td style=\"display:none;\">" + data.ptp[i].card_ptp_remarks + "</td>" +
                        "<td>"+ str2 + str1 + "<a style=\"margin-left:2px\" class=\"btn btn-xs btn-info\" onclick=\"card_ptp_viewRow(this)\"> <i class=\"fa fa-eye\"></i></a>"+"</td>" +
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


function card_ptp_deleteRow(node){
    var result = confirm("Want to delete?");
    if (result) {
        var id=node.parentNode.parentNode.cells[0].innerHTML;
        console.log(id);
        $.ajax({
            url: "/collection/card/ptp/remove?id="+id,
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

//Edit Data

function card_ptp_editRow(node,contact_id,location_id,promisor_id){

    var row=node.parentNode.parentNode;
    globalRow=row;
    ptpdetails(1,contact_id,row.cells[4].innerHTML,location_id,row.cells[5].innerHTML,promisor_id,row.cells[6].innerHTML);

    console.log(row);

    $("#card_amount").val(row.cells[1].innerHTML);
    $("#card_ptp_date").val(row.cells[2].innerHTML);
    $("#card_ptp_time").val(row.cells[3].innerHTML);
    $("#card_ptp_remarks").val(row.cells[7].innerHTML);
    $("#id").val(row.cells[0].innerHTML);


    /*
    $("#loan_amount").val(row.cells[3].innerHTML);
    $("#loan_ptp_date").val(row.cells[4].innerHTML);
    $("#loan_ptp_time").val(row.cells[5].innerHTML);
    $("#loan_contact_details_id").val(row.cells[6].innerHTML);
    $("#loan_contact_location").val(row.cells[7].innerHTML);
    $("#loan_promisor_details").val(row.cells[8].innerHTML);
    $("#loan_remarks").val(row.cells[9].innerHTML);
    */

    $('#modal-ptp').modal('show');
}

function card_ptp_viewRow(node) {
    var listid=node.parentNode.parentNode.cells[0].innerHTML;

    console.log(listid);
    $.ajax({
        url: "/collection/card/ptp/listById?cardId="+listid,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {


            $("#card_ptp_amount_view").text(data.cardPtpList.card_amount);
            $("#card_ptp_contact_view").text(data.cardPtpList.card_contact_details);
            $("#card_contact_location_view").text(data.cardPtpList.card_contact_details);
            $("#card_ptp_promisor_view").text(data.cardPtpList.card_promisor_details);
            $("#card_ptp_date_view").text(data.cardPtpList.card_ptp_date);
            $("#card_ptp_time_view").text(data.cardPtpList.card_ptp_time);
            $("#card_ptp_remark_view").text(data.cardPtpList.card_ptp_remarks);


        },
        error: function (data) {
            //alert("Error!");
        }
    });

    $("#card_ptp_save_btn").hide()
    $('#modal-ptp-view').modal('show');

}

//  list ptp details using ajax request
function ptpdetails(edit=0,contact_id=0,contact_name="",location_id=0,location_name="",promisor_id=0,promisor_name=""){
    if(edit==0) {
        $("#card_amount").val("");
        // $("#loan_ptp_date").val("");
        // $("#card_ptp_time").val("");
        $("#card_ptp_remarks").val("");
        console.log("I am hrere");
    }

    $("#id").val("");

    $.ajax({
        url: "/collection/ptp_contact_details/listall",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(data.menuList);
            $('#ptp_contact_details_id').empty();

            if (edit == 0) {
                $("#ptp_contact_details_id").append('<option value="">Choose</option>');
            }

            if(edit==1){
                $('#ptp_contact_details_id').append('<option value ="'+contact_id+'" data-name="'+contact_name+'" selected>'+contact_name+'</option>');
            }

            $.each(data.contact, function(index, value){
                $('#ptp_contact_details_id').append('<option value ="'+ value.id +'" data-name="'+value.contactName.toString()+'">'+value.contactName+'</option>');
            });
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
                $('#ptp_location_details_id').append('<option value ="'+location_id+'" data-name="'+location_name+'" selected>'+location_name+'</option>');
            }

            $.each(data.location, function(index, value){
                $('#ptp_location_details_id').append('<option value ="'+ value.id +'" data-name="'+value.contactLocation.toString()+'">'+value.contactLocation+'</option>');
            });
        },
        error: function (data) {
            //alert("Failure!");
        }
    });

    $.ajax({
        url: "/collection/ptp_promisor/listall",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
            // console.log(data.menuList);
            $('#ptp_promisor_details_id').empty();
            if (edit == 0) {
                $("#ptp_promisor_details_id").append('<option value="">Choose</option>');
            }

            if(edit==1){
                $('#ptp_promisor_details_id').append('<option value ="'+promisor_id+'" data-name="'+promisor_name+'" selected>'+promisor_name+'</option>');
            }
            $.each(data.promisor, function(index, value){
                $('#ptp_promisor_details_id').append('<option value ="'+ value.id +'" data-name="'+value.promisorDetail.toString()+'">'+value.promisorDetail+'</option>');
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
    $("#card_ptpForm :input").prop("disabled", false);
    $("#card_ptp_save_btn").show()
    // location.reload(true);
}

