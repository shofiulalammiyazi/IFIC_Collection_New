$(document).ready(function () {

    /* Fetch Account Details Informations On Tabe Change */
    // $('#tab-pd').click(function () {account-details-tbl
    //     getAccountDetails();
    // });
    /* Fetch Account Details Informations On Load */
    getAccountDetails();

    function getAccountDetails() {
        $('#account-details-tbody').html('');
        var dealerPin = $("#globalUserPinForSummary").val();
        // debugger;
        var unit = $("#globalUnitForSummary").val();
        var Url = '/retail/loan/dashboard/account-details?dealerPin=' + dealerPin;
        if (unit == 'Card') {
            Url = '/collection/card/dashboard/account-details?dealerPin=' + dealerPin;
        }
        $.ajax({
            type: 'GET',
            url: Url,
            success: function (response) {
                if (Array.isArray(response)) {
                    $.each(response, function (index, data) {
                        console.log(data)
                        var tr = '';
                        if (unit == 'Card') {
                            tr+= '<tr>'
                            + '<td class="text-right">' + (index + 1) + '</td>'
                            + '<td><a target="_blank" class="' + (data.numberOfContact > 0 ? 'text-success' : '') + ' text-bold" href="/profile/loanprofile/search?account=' + data.accountNo + '">' + data.accountNo + '</a></td>'
                            + '<td class="text-center">' + data.clientId + '</td>'
                            + '<td><a target="_blank" class="' + (data.numberOfContact > 0 ? 'text-success' : '') + ' text-bold">' +data.customerName + '</a></td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfContact) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfRightPartyContact) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfGuarantorOrThirdPartyContact) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.totalPtp) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.brokenPtp) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.curedPtp) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfVisit) + '</td>'
                            + '<td class="text-center">' + data.followUpDate + '</td>'
                            + '<td class="text-right">' + data.noOfDays + '</td>'
                            + '<td class="text-right">' + data.dpdBucket + '</td>'
                            + '<td class="text-right">' + data.clStatus + '</td>'
                            + '<td class="text-right">' + data.dpdBucket + '</td>'
                            // + '<td class="text-right">' + data.currentDpdBucket + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.outstanding) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.bdtOutstanding) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.bdtMinDue) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.bdtEol) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.usdLimit) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.usdOutstanding) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.usdMinDue) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.usdEol) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.overdueAmount) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.emiAmount) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.lastPayment) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.currentMonthPayment) + '</td>'
                            + '<td class="text-center">' + data.anniversaryDate + '</td>'
                            + '<td class="text-left">' + data.riskCategory + '</td>'
                            + '<td class="text-center">' + data.allocationDate + '</td>'
                            + '</tr>';
                        }

                        if(unit == 'Loan'){
                            tr+= '<tr>'
                            + '<td style="background: white" class="text-right">' + (index + 1) + '</td>'
                            + '<td style="background: white"><a target="_blank" class="' + (data.numberOfContact > 0 ? 'text-success' : '') + ' text-bold" href="/profile/loanprofile/search?account=' + data.accountNo + '">' + data.accountNo + '</a></td>'
                           /* + '<td>'+data.customerName+'</td>'*/
                            + '<td style="background: white">' +data.customerName + '</td>'
                                + '<td class="text-right">' + data.branchMnemonic + '</td>'
                                + '<td class="text-right">' + data.dealReference + '</td>'
                                + '<td class="text-right">' + data.productCode + '</td>'
                            + '<td class="text-left">' + data.productShortName + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfContact) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfRightPartyContact) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfGuarantorOrThirdPartyContact) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.totalPtp) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.brokenPtp) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.curedPtp) + '</td>'
                            + '<td class="text-right">' + formatBdIntegers(data.numberOfVisit) + '</td>'
                            + '<td class="text-center">' + data.followUpDate + '</td>'
                            + '<td class="text-right">' + data.dpdBucket + '</td>'
                            + '<td class="text-right">' + data.currentDpdBucket + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.outstanding) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.overdueAmount) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.emiAmount) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.lastPayment) + '</td>'
                            + '<td class="text-right">' + formatBdtInEnglish(data.currentMonthPayment) + '</td>'
                            + '<td class="text-left">' + data.branchName + '</td>'
                            + '<td class="text-left">' + data.riskCategory + '</td>'
                            + '<td class="text-center">' + data.allocationDate + '</td>'
                            + '</tr>';
                        }
                        $('#account-details-tbody').append(tr);
                    });

                    var designation = $("#globalUserDesignationForSummary").val();
                    var currentUserDesig = $("#globalUserDesignationForCurrentUser").val();
                    var table='';
                    if((designation == 'Dealer' || designation == 'dealer') &&
                        (currentUserDesig == 'Dealer' || currentUserDesig == 'dealer')){
                        table= $('#account-details-tbl').DataTable({
                            initComplete: function () {
                                count = 0;
                                this.api().columns().every( function () {
                                    var title = this.header();
                                    //replace spaces with dashes
                                    title = $(title).html().replace(/[\W]/g, '-');
                                    var column = this;
                                    var select = $('<select id="' + title + '" class="select2" ></select>')
                                        .appendTo( $(column.footer()).empty() )
                                        .on( 'change', function () {
                                            //Get the "text" property from each selected data
                                            //regex escape the value and store in array
                                            var data = $.map( $(this).select2('data'), function( value, key ) {
                                                return value.text ? '^' + $.fn.dataTable.util.escapeRegex(value.text) + '$' : null;
                                            });

                                            //if no data selected use ""
                                            if (data.length === 0) {
                                                data = [""];
                                            }

                                            //join array into string with regex or (|)
                                            var val = data.join('|');

                                            //search for the option(s) selected
                                            column.search( val ? val : '', true, false ).draw();
                                        } );

                                        column.data().unique().sort().each( function ( d, j ) {
                                            if(d.includes("href")){
                                                d = d.substring(d.indexOf(">")+1,d.length-4)
                                            }
                                        select.append( '<option value="'+d+'">'+d+'</option>' );
                                    } );

                                    //use column title as selector and placeholder
                                    $('#' + title).select2({
                                        multiple: true,
                                        closeOnSelect: false,
                                        placeholder: "Select a " + title
                                    });

                                    //initially clear select otherwise first option is selected
                                    $('.select2').val(null).trigger('change');
                                } );
                            },
                            'bInfo': true,
                            paging: true,
                            lengthChange: true,
                            searching: true,
                            ordering: true,
                            info: false,
                            autoWidth: true,
                            fixedColumns:   {
                                left: 3,
                            },
                            //dom: 'Bfrtip',
                            buttons: [
                                {
                                    extend:'excel',
                                    title:'Allocated Account List'
                                }
                            ]
                        });
                    }else{
                        table= $('#account-details-tbl').DataTable({
                            'bInfo': true,
                            paging: true,
                            lengthChange: true,
                            searching: true,
                            ordering: true,
                            info: false,
                            autoWidth: true,
                            dom: 'Bfrtip',
                            fixedColumns:   {
                                left: 3
                            },
                            buttons: [
                                {
                                    extend:'excel',
                                    title:'Allocated Account List'
                                }
                            ]
                        });
                    }
                  // var table= $('#account-details-tbl').DataTable({
                  //       'bInfo': true,
                  //       paging: true,
                  //       lengthChange: true,
                  //       searching: true,
                  //       ordering: true,
                  //       info: false,
                  //       autoWidth: true,
                  //       dom: 'Bfrtip',
                  //       buttons: [
                  //           {
                  //               extend:'excel',
                  //               title:'Allocated Account List'
                  //           }
                  //       ]
                  //   });

                    $('#account-details-tbl').parent('div').addClass('table-responsive');

                    // Table scarch Edit  by Abdullah
                    $('#account-details-tbl thead th').each(function(i) {
                        var title = $(this).text();
                        if(title == 'Account No' || title=='Customer Name' || title=='Branch Mnemonic' ||
                            title=='Product Code' || title=='Deal Reference' || title=='Product Short Name'
                            || title=='No. of Attempt' || title=='Right party'
                            || title=='Guarantor / Third party' || title=='Taken'
                            || title=='Broken' || title=='Kept' || title == 'DPD' || title == 'CL Status'
                            || title=='No. of Visit' || title=='Followup Date' || title == 'EOL'
                            || title=='Bucket' || title == 'Current Bucket' || title=='Outstanding'
                            || title=='Overdue' || title=='EMI' || title=='BDTOutstanding' || title=='USDOutstanding'
                            || title=='Last Payment' || title=='During the month' || title=='Branch Name'
                            || title=='Risk Category' || title=='Allocation date'
                            || title=='Contract ID' || title == 'Client ID' || title == 'Customer'
                            || title == 'Relatives/References/Others' || title == 'Age code'
                            || title == 'Total Limit' || title == 'BDTLimit' || title == 'BDTMin Due'
                            || title == 'BDTEOL %' || title == 'Anniversary Date' || title == 'USDLimit' || title == 'USDMin Due'
                            || title == 'USDEOL %'
                        ){
                            $(this).append('<br /><input type="text" style="width: 100%;" placeholder="Search ' + title + '" />');
                        }
// DataTable
                       // var table = $('#account-details-tbl').DataTable();
                        // Apply the search
                        table.columns().every(function() {
                            var that = this;

                            $('input', this.header()).on('keyup change', function() {
                                if (that.search() !== this.value) {
                                    that
                                        .search(this.value)
                                        .draw();
                                }
                            });
                        })
                    });

                    // End Table Scarch

                }
                else {
                    notifyError(response);
                }
            }
        })
    }

    // $('#example tfoot th').each(function() {
    //     var title = $(this).text();
    //     $(this).html('<input type="text" placeholder="Search ' + title + '" />');
    // });

// // DataTable
//     var table = $('#example').DataTable();
//
// // Apply the search
//     table.columns().every(function() {
//         var that = this;
//
//         $('input', this.footer()).on('keyup change', function() {
//             if (that.search() !== this.value) {
//                 that
//                     .search(this.value)
//                     .draw();
//             }
//         });
//     })


    $("#tab_secured_card").click(function () {

        $(".secured_tr").html('');

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/card/secured_card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }

                    for (var i = 0; i < data.length; i++) {
                        var row = "<tr class='secured_tr'>\n" +
                            "                        <td class=\"text-right\">" + (i + 1) + "</td>\n" +
                            "                        <td class=\"text-center\">" + data[i].cardAccountBasicInfo.cardNo + "</td>" +
                            "                        <td  class=\"text-center\">" + data[i].cardAccountBasicInfo.customerEntity.customerId + "</td>\n" +
                            "                        <td class=\"text-left\">" + data[i].cardAccountBasicInfo.customerEntity.customerName + "</td>\n" +
                            "                        <td></td>\n" +
                            "                        <td class=\"text-right\">" + formatBdtInEnglish(data[i].outstandingAmount) + "</td>\n" +
                            "                        <td class=\"text-right\">" + formatBdtInEnglish(data[i].minDuePayment) + "</td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>\n" +
                            "                        <td></td>";

                        $("#secured_card_tbody").append(row);
                    }

                }
            })
        }
        //else if(unit == 'Loan')
        // {
        //     $.ajax({
        //         type:"GET",
        //         url:"/collection/dashboard/team/esau/loan",
        //         beforeSend: function (xhr) {
        //             xhr.setRequestHeader(header, token);
        //         },
        //         data:{
        //             userPin:userPin,
        //             unit:unit,
        //             designation:designation,
        //             userId:userId
        //         },
        //         success:function (json) {
        //
        //             console.log(data);
        //
        //             var row="<tr class='esau_tr'>\n" +
        //                 "                            <td>"+data.performanceAvg+"</td>\n" +
        //                 "                            <td>"+data.ratingName+"</td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                            <td></td>\n" +
        //                 "                        </tr>";
        //
        //             $("#esa_loan_tbody").append(row);
        //         }
        //     })
        // }
    })


    $("#tab-esau").click(function () {

        $(".esau_tr").html('');

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/esau/card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        notifyError();
                    }

                    var row = "<tr class='esau_tr'>\n" +
                        "                            <td class='text-right'>" + data.performanceAvg + "</td>\n" +
                        "                            <td> class='text-left'" + data.ratingName + "</td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                            <td></td>\n" +
                        "                        </tr>";

                    $("#esa_card_tbody").append(row);
                }
            })
        } else if (unit == 'Loan') {
            return;
        }


    })

    $("#tab-payment-detail").click(function () {

        $(".payment_status_row").html('');

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();
        if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/retail/loan/dashboard/payment-summary",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {
                    if (typeof data === 'string') {
                        notifyError();
                        return;
                    }
                    dealerPaymentDetails.categories = data;
                    console.log(data);
                }
            })
        } else if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/payment-summary-card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {
                    // console.log("card data for test : "+JSON.stringify(data));
                    console.log(data)
                    // if (typeof data === 'string') {
                    //     // swal("Something went wrong");
                    //     // window.location.reload();
                    //     notifyError();
                    //     return;
                    // }

                    var total = 0;
                    var arr = new Array();

                    console.log(data);
                    //dealerPaymentDetails.narration = [];
                    dealerPaymentDetails.categories = data;

                    // for (var i = 0; i < data.length; i++) {
                    //
                    //     if (data[i].narration == 'total') {
                    //         cardPaymentDetails.setTotalDetails(data[i].cardAccountPaymentDetails);
                    //     }
                    //     else if (data[i].narration == 'save') {
                    //         cardPaymentDetails.setSaveDetails(data[i].cardAccountPaymentDetails);
                    //     }
                    //     else if (data[i].narration == 'notSave') {
                    //         cardPaymentDetails.setNotSaveDetails(data[i].cardAccountPaymentDetails);
                    //     }
                    //     else if (data[i].narration == 'unpaidList') {
                    //         cardPaymentDetails.setUnpaidDetails(data[i].cardAccountPaymentDetails);
                    //     }
                    //     else {
                    //         cardPaymentDetails.addNewNarration(data[i]);
                    //     }
                    //
                    //
                    // }
                }
            })
        }

    })

    $("#btn_daily_acivity").click(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();
        var selectedDate = $("#selectedDate").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();


        $.ajax({
            type: "GET",
            url: "/collection/dashboard/team/daily_acivity",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: {
                userPin: userPin,
                unit: unit,
                designation: designation,
                userId: userId,
                selectedDate: selectedDate,
                startTime: startTime,
                endTime: endTime

            },
            success: function (data) {

                if (typeof data === 'string') {
                    swal('Please provide valid date and time or try reloding the page');
                    return;
                }

                $(".daily_activity_status_row").html('');
                for (var i = 0; i < data.length; i++) {
                    var tr = '<tr class="daily_activity_status_row">\n' +
                        '<td class="text-left">' + data[i].dealerPin + '</td>\n' +
                        '<td class="text-left">' + data[i].dealerName + '</td>\n' +
                        '<td class="text-right">' + data[i].dailyNotes + '</td>\n' +
                        '<td class="text-right">' + data[i].dairyNote + '</td>\n' +
                        '<td class="text-right">' + data[i].ptp + '</td>\n' +
                        '<td class="text-right">' + data[i].hotNote + '</td>\n' +
                        '<td class="text-right">' + data[i].visitLedger + '</td>\n' +
                        '</tr>';
                    $("#daily_activity_list").append(tr);
                }
            }
        })
    })

    $("#tab_resource_details").click(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();


        $.ajax({
            type: "GET",
            url: "/collection/dashboard/team/resource_details/loan",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: {
                userPin: userPin,
                unit: unit,
                designation: designation,
                userId: userId
            },
            success: function (data) {
                if (typeof data === 'string') {
                    swal("Something went wrong");
                    window.location.reload();
                    return;
                }
                for (var i = 0; i < data.length; i++) {
                    var tr = '<tr>\n' +
                        '<td class="text-left">' + data[i].statusName + '</td>\n' +
                        '<td class="text-right">' + data[i].noOfEmp + '</td>\n' +
                        '</tr>';
                    $("#resource_details").append(tr);
                }
            }
        })

    })

    $("#tab_agency_detail").click(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/agency_detail/loan",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }


                    var total = 0;
                    var arr = new Array();

                    console.log(data);

                    $(".agency_details_row").html('');
                    for (var i = 0; i < data.length; i++) {
                        var tr = '<tr class="agency_details_row">' +
                            '<td class="text-left">' + data[i].product + '</td>' +
                            '<td class="text-right">' + data[i].dpd + '</td>' +
                            '<td class="text-left">' + data[i].agencyName + '</td>' +
                            '<td class="text-right">' + data[i].totalAc + '</td>' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].totalOutstanding) + '</td>' +
                            '<td class="text-right">' + data[i].noOfPtp + '</td>' +
                            '<td class="text-right">' + data[i].noOfFollowUp + '</td>' +
                            '</tr>';
                        $("#agency_details").append(tr);
                    }

                }
            })

        } else if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/agency_detail/card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }


                    var total = 0;
                    var arr = new Array();

                    console.log(data);

                    $(".agency_details_row").html('');
                    for (var i = 0; i < data.length; i++) {
                        var tr = '<tr class="agency_details_row">' +
                            '<td class="text-left">' + data[i].product + '</td>' +
                            '<td class="text-right">' + data[i].dpd + '</td>' +
                            '<td class="text-left">' + data[i].agencyName + '</td>' +
                            '<td class="text-right">' + data[i].totalAc + '</td>' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].totalOutstanding) + '</td>' +
                            '<td class="text-right">' + data[i].noOfPtp + '</td>' +
                            '<td class="text-right">' + data[i].noOfFollowUp + '</td>' +
                            '</tr>';
                        $("#agency_details").append(tr);
                    }

                }
            });
        }
    })

    $("#tab-follow-up").click(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/retail/loan/dashboard/date-wise-follow-up",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }
                    data = data.loanFollowup;

                    $("#team_datewise_followUp_tbody").html("");
                    for (var i = 0; i < data.length; i++) {
                        var tr = '';

                        var reason = '';
                        let reasonList = data[i].followUpReason;
                        for (var k = 0; k < data[i].followUpReason.length; k++) {
                            if (reasonList[k].toLowerCase() === 'call' || reasonList[k].toLowerCase() === 'phone')
                                reason += '<span class="btn btn-xs bg-light-blue" title="Call" style="margin: 0px 2px"><i class="fa fa-phone"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'email')
                                reason += '<span class="btn btn-xs bg-orange"  title="Email" style="margin: 0px 2px"><i class="fa fa-envelope"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'sms')
                                reason += '<span class="btn btn-xs bg-olive"  title="SMS" style="margin: 0px 2px"><i class="fa fa-comments"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'visit')
                                reason += '<span class="btn btn-xs bg-purple"  title="Visit" style="margin: 0px 2px"><i class="fa fa-user"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'letter')
                                reason += '<span class="btn btn-xs bg-green"  title="Letter" style="margin: 0px 2px"><i class="fa fa-envelope"></i></span>';
                        }

                        tr = tr + '<tr >\n' +
                            '<td  class="text-center text-nowrap">' + formatDate_DD_MMM_YYYY(data[i].createdDate) + '</td>\n' +
                            '<td class="text-center"><a href="/profile/loanprofile/search?account=' + data[i].accNo + '" target="_blank">' + data[i].accNo + '</a></td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].outstanding) + '</td>\n' +
                            '<td class="text-left">' + reason + '</td>\n' +
                            '<td  class="text-center text-nowrap">' + formatDate_DD_MMM_YYYY(data[i].followUpDate) + '</td>\n' +
                            '</tr>'

                        console.log(tr);
                        $("#team_datewise_followUp_tbody").append(tr);
                        // $("#team_accountwise_followUp_tbody").append(trr);

                        setTimeout(function () {
                            $('#date-wise-followup-table').DataTable().destroy();
                            $('#date-wise-followup-table').DataTable();
                        }, 100);
                    }
                }
            })

        }
        else if (unit == 'Card') {

            $.ajax({
                type: "GET",
                url: "/collection/card/dashboard/date-wise-follow-up",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }

                    data = data.cardFollowup;

                    $("#team_datewise_followUp_tbody").html("");
                    for (var i = 0; i < data.length; i++) {
                        var tr = '';

                        var reason = '';
                        let reasonList = data[i].followUpReason;
                        for (var k = 0; k < data[i].followUpReason.length; k++) {
                            if (reasonList[k].toLowerCase() === 'call' || reasonList[k].toLowerCase() === 'phone')
                                reason += '<span class="btn btn-xs bg-light-blue" title="Call" style="margin: 0px 2px"><i class="fa fa-phone"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'email')
                                reason += '<span class="btn btn-xs bg-orange"  title="Email" style="margin: 0px 2px"><i class="fa fa-envelope"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'sms')
                                reason += '<span class="btn btn-xs bg-olive"  title="SMS" style="margin: 0px 2px"><i class="fa fa-comments"></i></span>';
                            else if (reasonList[k].toLowerCase() === 'visit')
                                reason += '<span class="btn btn-xs bg-purple"  title="Visit" style="margin: 0px 2px"><i class="fas fa-people-arrows"></i></span>';
                        }

                        tr = tr + '<tr >\n' +
                            '<td  class="text-center text-nowrap">' + formatDate_DD_MMM_YYYY(data[i].createdDate) + '</td>\n' +
                            '<td class="text-center"><a href="/profile/loanprofile/search?account=' + data[i].accNo + '" target="_blank">' + data[i].accNo + '</a></td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].outstanding) + '</td>\n' +
                            '<td class="text-left">' + reason + '</td>\n' +
                            '<td  class="text-center text-nowrap">' + formatDate_DD_MMM_YYYY(data[i].followUpDate) + '</td>\n' +
                            '</tr>'

                        $("#team_datewise_followUp_tbody").append(tr);
                        // $("#team_accountwise_followUp_tbody").append(tr);

                        setTimeout(function () {
                            $('#date-wise-followup-table').DataTable().destroy();
                            $('#date-wise-followup-table').DataTable();
                        }, 100)
                    }
                }
            })

        }

    })

    $("#team_summary_details_account").click(function () {
        $('.dass_account_custom').remove();
        console.log("Team");

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/summary_detail_account/loan",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }


                    var total = 0;
                    var arr = new Array();
                    var arrPer = new Array();

                    console.log(data);
                    for (var i = 0; i < data.length; i++) {

                        var tr = '';
                        var trPer = '';

                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="">\n' +
                                '<td class="text-left">' + data[i].product + '</td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '</tr>';
                            arr.push(data[i].product);
                        }

                        if (arrPer.indexOf(data[i].product) < 0) {
                            trPer += '<tr class="">\n' +
                                '<td class="text-left">' + data[i].product + '</td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '</tr>';
                            arrPer.push(data[i].product);
                        }

                        tr += '<tr class="">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-center"></td>\n' +

                            //regular
                            '<td class="text-right">' + data[i].tarNoOfAcRegular + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].regularAcNo * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallRegular * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].regularPerFormance * 100) / 100 + '</td>\n' +
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +


                            //back
                            '<td class="text-right">' + data[i].tarOfBackAc + '</td>\n' +
                            '<td class="text-right">' + data[i].noOfBackAc + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallBack * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].backPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerformmance * 100) / 100 + '</td>\n' +


                            //save
                            '<td class="text-right">' + Math.round(data[i].tarOfSaveAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].noOfSaveAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallSave * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].savePerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerformmance * 100) / 100 + '</td>\n' +


                            //flow
                            '<td class="text-right">' + Math.round(data[i].tarOfFlowAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].noOfFlowAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallFlow * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].flowPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerformmance * 100) / 100 + '</td>\n' +


                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.touchedNumber * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.unTouchedNumber * 100) / 100 + '</td>\n' +

                            //par
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.totalParRel * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.totalParRelRem * 100) / 100 + '</td>\n' +

                            //npl
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.totalNplRel * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.totalNplRem * 100) / 100 + '</td>\n' +
                            '</tr>';

                        $("#outstanding_wise_total_account").append(tr);

                        trPer += '<tr class="">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-center"></td>\n' +

                            //regular
                            '<td class="text-center"></td>\n' +
                            '<td class="text-center"></td>\n' +
                            '<td class="text-center"></td>\n' +

                            '<td class="text-center"></td>\n' +
                            '<td class="text-center"></td>\n' +
                            '<td class="text-center"></td>\n' +


                            //back
                            '<td class="text-right">' + Math.round(data[i].tarOfBackAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].noOfBackAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallBack * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].backPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerformmance * 100) / 100 + '</td>\n' +


                            //save
                            '<td class="text-right">' + Math.round(data[i].tarOfSaveAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].noOfSaveAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallSave * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].savePerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerformmance * 100) / 100 + '</td>\n' +


                            //flow
                            '<td class="text-right">' + Math.round(data[i].tarOfFlowAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].noOfFlowAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallFlow * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].flowPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerformmance * 100) / 100 + '</td>\n' +


                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.touchedPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.unTouchedPerc * 100) / 100 + '</td>\n' +

                            //par
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.parRelPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.parRelRemPerc * 100) / 100 + '</td>\n' +

                            //npl
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.nplRelPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].accountWiseSummaryModel.nplRemPerc * 100) / 100 + '</td>\n' +
                            '</tr>';
                        $("#outstanding_wise_total_account_per").append(trPer);
                    }

                }
            })

        } else if (unit == 'Card') {

            $.ajax({
                type: "GET",
                url: "/collection/dashboard/distribution/accountWise/card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {


                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }


                    var total = 0;
                    var arr = new Array();

                    var ageCodeMap = new Map();


                    for (var j = 0; j < data.length; j++) {
                        if (!ageCodeMap.has(data[j].productAndDpd)) {
                            ageCodeMap.set(data[j].productAndDpd, data[j]);
                        } else {
                            var newVar = ageCodeMap.get(data[j].productAndDpd);

                            newVar.numberPerDpd += data[j].numberPerDpd;
                            newVar.touchedNumber += data[j].touchedNumber;
                            newVar.unTouchedNumber += data[j].unTouchedNumber;
                            newVar.totalPending += data[j].totalPending;
                            newVar.totalParRel += data[j].totalParRel;
                            newVar.totalParRelRem += data[j].totalParRelRem;
                            newVar.totalParQ += data[j].totalParQ;
                            newVar.totalNplRel += data[j].totalNplRel;
                            newVar.totalNplRem += data[j].totalNplRem;
                            newVar.totalNplQ += data[j].totalNplQ;
                        }
                    }


                    for (const [key, value] of ageCodeMap.entries()) {
                        var data = value;
                        var tr = '';
                        tr += '<tr class="dass_account_custom">\n' +
                            '                        <td class="text-left">' + data.productAndDpd + '</td>\n' +
                            '                        <td class="text-right">' + data.numberPerDpd + '</td>\n' +
                            '                        <td class="text-right">' + data.touchedNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.unTouchedNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.totalPending + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParRel + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParRelRem + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParQ + '</td>\n' +
                            // '                        <td class="text-right">-</td>\n' +
                            '                        <td class="text-right">' + data.totalNplRel + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplRem + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplQ + '</td>\n' +
                            // '                        <td class="text-center">-</td>\n' +
                            '                    </tr>';

                        $('#dass_account_data').append(tr);
                        tr = '';
                        tr += '<tr class="dass_account_custom">\n' +
                            '                        <td>-</td>\n' +
                            '                        <td class="text-left">' + data.productAndDpd + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.totalPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.touchedPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.unTouchedPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.pendingPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.parRelPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.parRelRemPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.parQPerc * 100) / 100 + '</td>\n' +
                            // '                        <td class="text-right"></td>\n' +
                            '                        <td class="text-right">' + Math.round(data.nplRelPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.nplRemPerc * 100) / 100 + '</td>\n' +
                            '                        <td class="text-right">' + Math.round(data.nplQPerc * 100) / 100 + '</td>\n' +
                            // '                        <td class="text-center"></td>\n' +
                            '                    </tr>';

                        $('#dass_account_per_data').append(tr);

                    }


                }
            }).done(function () {

            });

        }
    })

    $("#team_summary_details").click(function () {
        $('.dass_amount_custom').remove();
        console.log("TEAM super");

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/summary_detail/loan",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }


                    var total = 0;
                    var arr = new Array();
                    var arrPer = new Array();

                    console.log(data);
                    $(".outstanding_wise_total_amount_per_row").html('');
                    $(".outstanding_wise_total_amount_row").html('');

                    for (var i = 0; i < data.length; i++) {
                        var tr = '';
                        var trper = '';


                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="outstanding_wise_total_amount_row">\n' +
                                '<td class="text-left">' + data[i].product + '</td>\n' +
                                '<td></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +

                                '</tr>';
                            arr.push(data[i].product);
                        }

                        if (arrPer.indexOf(data[i].product) < 0) {
                            trper += '<tr class="outstanding_wise_total_amount_per_row">\n' +
                                '<td class="text-left">' + data[i].product + '</td>\n' +
                                '<td></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +

                                '</tr>';
                            arrPer.push(data[i].product);
                        }

                        tr += '<tr class="outstanding_wise_total_amount_row">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-left">' + data[i].dpdBucket + '</td>\n' +

                            //regular
                            '<td class="text-right">' + Math.round(data[i].tarAmntRegular * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntRegular * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallRegular * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].regularPerFormance * 100) / 100 + '</td>\n' +
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +


                            //back
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfBackAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfBackAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallBack * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].backPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerformmance * 100) / 100 + '</td>\n' +


                            //save
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfSaveAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfSaveAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallSave * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].savePerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerformmance * 100) / 100 + '</td>\n' +


                            //follow
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfFlowAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfFlowAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallFlow * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].flowPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerformmance * 100) / 100 + '</td>\n' +


                            //touched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.touchedNumber * 100) / 100 + '</td>\n' +


                            //Untouched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.unTouchedNumber * 100) / 100 + '</td>\n' +


                            //PAR Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalParRel * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalParRelRem * 100) / 100 + '</td>\n' +


                            //NPL Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalNplRel * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalNplRem * 100) / 100 + '</td>\n' +

                            '</tr>';

                        $("#outstanding_wise_total_amount").append(tr);

                        trper += '<tr class="outstanding_wise_total_amount_per_row">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-left">' + data[i].dpdBucket + '</td>\n' +

                            //regular
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntRegularPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right"></td>\n' +

                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +


                            //back
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfBackAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfBackAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallBack * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].backPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerformmance * 100) / 100 + '</td>\n' +


                            //save
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfSaveAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfSaveAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallSave * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].savePerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerformmance * 100) / 100 + '</td>\n' +


                            //follow
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfFlowAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfFlowAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallFlow * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].flowPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerformmance * 100) / 100 + '</td>\n' +


                            //touched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.touchedPerc * 100) / 100 + '</td>\n' +


                            //Untouched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.unTouchedPerc * 100) / 100 + '</td>\n' +


                            //PAR Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.parRelPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.parRelRemPerc * 100) / 100 + '</td>\n' +


                            //NPL Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.nplRelPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.nplRemPerc * 100) / 100 + '</td>\n' +

                            '</tr>';

                        $("#outstanding_wise_total_amount_percentage").append(trper);
                    }

                }
            })

        } else if (unit == 'Card') {

            $.ajax({
                type: "GET",
                url: "/collection/dashboard/distribution/amountWise/card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (data) {

                    if (typeof data === 'string') {
                        swal("Something went wrong");
                        window.location.reload();
                        return;
                    }


                    var total = 0;
                    var arr = new Array();
                    var arrPer = new Array();

                    console.log(data);
                    $(".outstanding_wise_total_amount_per_row").html('');
                    $(".outstanding_wise_total_amount_row").html('');

                    for (var i = 0; i < data.length; i++) {
                        var tr = '';
                        var trper = '';

                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="outstanding_wise_total_amount_row">\n' +
                                '<td class="text-left">' + data[i].product + '</td>\n' +
                                '<td></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +

                                '</tr>';
                            arr.push(data[i].product);
                        }

                        if (arrPer.indexOf(data[i].product) < 0) {
                            trper += '<tr class="outstanding_wise_total_amount_per_row">\n' +
                                '<td class="text-left">' + data[i].product + '</td>\n' +
                                '<td></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +
                                '<td class="text-center"></td>\n' +

                                '</tr>';
                            arrPer.push(data[i].product);
                        }

                        tr += '<tr class="outstanding_wise_total_amount_row">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-left">' + data[i].dpdBucket + '</td>\n' +

                            //regular
                            '<td class="text-right">' + Math.round(data[i].tarAmntRegular * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntRegular * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallRegular * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].regularPerFormance * 100) / 100 + '</td>\n' +
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +


                            //back
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfBackAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfBackAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallBack * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].backPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerformmance * 100) / 100 + '</td>\n' +


                            //save
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfSaveAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfSaveAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallSave * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].savePerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerformmance * 100) / 100 + '</td>\n' +


                            //follow
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfFlowAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfFlowAc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallFlow * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].flowPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerformmance * 100) / 100 + '</td>\n' +


                            //touched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.touchedNumber * 100) / 100 + '</td>\n' +


                            //Untouched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.unTouchedNumber * 100) / 100 + '</td>\n' +


                            //PAR Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalParRel * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalParRelRem * 100) / 100 + '</td>\n' +


                            //NPL Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalNplRel * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.totalNplRem * 100) / 100 + '</td>\n' +

                            '</tr>';

                        $("#outstanding_wise_total_amount").append(tr);

                        trper += '<tr class="outstanding_wise_total_amount_per_row">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-left">' + data[i].dpdBucket + '</td>\n' +

                            //regular
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntRegularPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right"></td>\n' +

                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +
                            '<td class="text-right"></td>\n' +


                            //back
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfBackAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfBackAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallBack * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].backPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightBackPerformmance * 100) / 100 + '</td>\n' +


                            //save
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfSaveAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfSaveAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallSave * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].savePerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightSavePerformmance * 100) / 100 + '</td>\n' +


                            //follow
                            '<td class="text-right">' + Math.round(data[i].tarAmntOfFlowAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amntOfFlowAcPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].shortFallFlow * 100) / 100 + '</td>\n' +

                            '<td class="text-right">' + Math.round(data[i].flowPerformance * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].weightFlowPerformmance * 100) / 100 + '</td>\n' +


                            //touched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.touchedPerc * 100) / 100 + '</td>\n' +


                            //Untouched
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.unTouchedPerc * 100) / 100 + '</td>\n' +


                            //PAR Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.parRelPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.parRelRemPerc * 100) / 100 + '</td>\n' +


                            //NPL Saved Amount
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.nplRelPerc * 100) / 100 + '</td>\n' +
                            '<td class="text-right">' + Math.round(data[i].amountWiseSummaryModel.nplRemPerc * 100) / 100 + '</td>\n' +

                            '</tr>';

                        $("#outstanding_wise_total_amount_percentage").append(trper);
                    }

                }
            }).done(function () {

            });

        }
    })

    // $("#tab_rfd_detail").click(function () {
    //     //     var token = $("meta[name='_csrf']").attr("content");
    //     //     var header = $("meta[name='_csrf_header']").attr("content");
    //     //     var unit = $("#globalUnitForSummary").val().toLowerCase();
    //     //
    //     //     $.ajax({
    //     //         type: "GET",
    //     //         url: "/collection/dashboard/team/rfd_detail?unit=" + unit,
    //     //         beforeSend: function (xhr) {
    //     //             xhr.setRequestHeader(header, token);
    //     //         },
    //     //         success: function (data) {
    //     //
    //     //             if (typeof data === 'string') {
    //     //                 swal("Something went wrong");
    //     //                 window.location.reload();
    //     //                 return;
    //     //             }
    //     //
    //     //             rfdMenuApp.updateRfdData(data);
    //     //         }
    //     //     })
    //     //
    //     // })





    $("#tab_rfd_detail").click(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var unit = $("#globalUnitForSummary").val().toLowerCase();
        var userPin= $("#globalUserPinForSummary").val();
       console.log(" unitrw "+unit +'  '+userPin);
        $.ajax({
            type: "GET",
            url: "/collection/dashboard/team/rfd_detail?pin="+userPin+"&unit=" + unit,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (json) {
                rfdMenuApp.updateRfdData(json);
            }
        })

    })


});
