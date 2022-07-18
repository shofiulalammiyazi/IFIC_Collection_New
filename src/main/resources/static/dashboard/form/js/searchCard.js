function addSearchCard() {

    var formValues = $('#cardSearchForm').serializeJSON();
    var dt = JSON.stringify(formValues);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: '/card/search/findcard',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: dt,
        beforeSend: function (xhr) {xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            var myTable = document.getElementById("cardSearchTable");
            var row = myTable.rows.length;
            for (var i=row-1; i>0; i--) {
                myTable.deleteRow(i);
            }
            //for (var i in data.list) {
            for(var i=0; i<data.list.length;i++){
                var ii =  i + 1;
                var str = "<tr>" +
                    "<td>" + ii + "</td>" +
                    "<td>" + data.list[i].caseNumber + "</td>" +
                    "<td>" + data.list[i].fullName + "</td>" +
                    "<td>" + data.list[i].cusType + "</td>" +
                    "<td>" + data.list[i].contact+ "</td>" +
                    "<td>" + data.list[i].tinNo + "</td>" +
                    "<td>" + data.list[i].nid + "</td>" +
                    "<td>Edit |  Delete</td>" +
                    "</tr>";
                $('#cardSearch_tBody').append(str);
            }

        },
        error: function (err) {
            //alert(err);
        }
    });
}

$('#cardSearchButton').click(function(event) {
    event.preventDefault();
    addSearchCard();
});