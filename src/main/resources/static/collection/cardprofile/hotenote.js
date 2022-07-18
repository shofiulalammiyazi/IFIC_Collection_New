function hotNoteSave() {
    var formValue = $("#hotNoteSaveForm").serialize();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    console.log("form value : "+formValue)
    $.ajax({
        type:"post",
        url:"/collection/card/hotnotes/save",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data:formValue,
        success:function (data) {
            $('#modal-hotnotee').modal('toggle');
            var opt = '<tr>\n' +
                '<td class="info">'+data.followUp.vipStatus+'</td>\n' +
                '<td class="info">'+data.followUp.hotNote+'</td>\n' +
                '<td class="info">'+data.followUp.username+'-'+data.followUp.createdBy+'/'+data.followUp.createdDate+'</td>\n' +
                // '<td class="info">'+data.followUp.username+'</td>\n' +
                '</tr>';
            $("#hot-list").prepend(opt);

        }
    })

}