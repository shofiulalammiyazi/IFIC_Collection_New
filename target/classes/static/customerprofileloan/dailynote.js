$(document).ready(function () {
    $('#dailyNoteSaveForm input[name="accountNo"]').val(accountNo);
})

async function dailyNoteSaveLoan() {

    // if (noteDetailsValidate()==false){
    //     return;
    // }
    var booleanMesg=false;
    var formValue = $("#dailyNoteSaveForm").serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
console.log(formValue+"----------------")
   await $.ajax({
        type:"post",
        url:"/customerloanprofile/dailynote/save",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data:formValue,
        success:function (data) {
            booleanMesg=true;
            // swal("Successfully saved !!");
            var dt = new Date();
            var month = dt.getMonth();
            if( month <= 9 ) {
                month = "0"+dt.getMonth();
            }
            //$('#modal-dailynote').modal('toggle');
            memoList.upDateMemoNoteList(data);
            // var formdatalist = $('#dailyNoteSaveForm');
            // var noteDetails = formdatalist.find('textarea[name="note"]').val();
            // var opt = '<tr>\n' +
            //     '\t\t\t\t\t\t\t\t\t\t\t\t<th>'+dt.getFullYear()+"-"+month+"-"+dt.getDate()+'</th>\n' +
            //     '\t\t\t\t\t\t\t\t\t\t\t\t<th style="text-align: right">PIN - First Name</th>\n' +
            //     '\t\t\t\t\t\t\t\t\t\t\t</tr>\n' +
            //     '\t\t\t\t\t\t\t\t\t\t\t<tr class="info">\n' +
            //     '\t\t\t\t\t\t\t\t\t\t\t\t<td colspan="2">'+noteDetails+'</td>\n' +
            //     '\t\t\t\t\t\t\t\t\t\t\t</tr>';
            // $("#memo-list").append(opt);
        }
    }).done(function () {
            // Clear Form
            $('#dailyNoteSaveForm').find(':input').val('');
        });

  return booleanMesg;
}

$('#modal-dailynote').on('show.bs.modal', function () {
    $(this).find('textarea').val('')
})

function noteDetailsValidate() {
    if($("#loan_note_details").val().length===0){
        alert("please fill up note details ")
        return false;
    }
}