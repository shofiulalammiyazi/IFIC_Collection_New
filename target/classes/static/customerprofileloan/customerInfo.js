function customerInfoList(){
    $.getJSON('/loan/api/customer-other-info/view?customerId=' + customerId, function (json) {
        console.log('customer_borrower info --------------');
        console.log(json);
        var tr = [];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].status + '</td>');
            tr.push('<td>' +
                (json[i].status == 'PENDING' ? '': '<button onclick="editCustomerinfo(' + json[i].id + ')" class=\'referenceEdit btn btn-primary btn-xs\'>Edit</button> ') +
                '<button onclick="viewCustomerinfo(' + json[i].id + ')" class=\'btn btn-success btn-xs\'><i fa fa-eye></i>View</button>' +
                '</td>');

            // tr.push('<td><button onclick="viewReference(' + json[i].id + ')" class=\'btn btn-success btn-xs\'><i fa fa-eye></i>View</button></td>');
            tr.push('</tr>');
        }

        $('#customerInfo_tBody').append($(tr.join('')));
    })
}

customerInfoList(customerId);



function editCustomerinfo(id){
    $('#customer_info input[name="_csrf"]').val(csrfToken);
    var url = '/loan/api/customer-other-info/edit?id='+id;
    $.getJSON(url, function(data){
        var formdatalist = $('#customerInfoSaveForm');
        custBorrowerInfoChecker.entity = data;
        $('#modal-reference-info').modal('toggle');
    });
}

function viewCustomerinfo(id) {
    $('#customer_info input[name="_csrf"]').val(csrfToken);
    var url = '/loan/api/customer-other-info/edit?id='+id;
    $.getJSON(url, function(data){
        formData = data;
        $('#branchName').text(data.branchName);
        $('#refeName').text(data.accountName);
        $('#refeOccupation').text(data.loanAcNumber);
        $('#refeCompanyName').text(data.casaAcNumber);
        $('#refeDesignation').text(data.loanLimit);
        $('#refeHomeAddress').text(data.loanOS);
        $('#refeOfficeAddress').text(data.overDueAmount);
        $('#refePermanentAddress').text(data.emi);
        $('#refePhone').text(data.presentEmi);
        $('#reletionReference').text(data.rateOfInterest);
        $('#refNid').text(data.presentRateInterest);
        $('#refePhone').text(data.bucket);
        $('#reletionReference').text(data.crntScheduleDate);
        $('#reletionReference').text(data.paymentDate);
        $('#reletionReference').text(data.paymentAmount);
        $('#modal-reference-info-view').modal('toggle');
    });

}

