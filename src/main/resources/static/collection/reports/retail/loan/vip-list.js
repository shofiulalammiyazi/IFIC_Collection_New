var isVipSearchLoading = false;

function vipListSearch() {

    if (isVipSearchLoading) {
        swal("Please wait...");
        return;
    }

    isVipSearchLoading = true;

    $('#isVipSearchLoading').show();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var vipStatus = $("#vipStatus").val();


    $.ajax({
        url: '/collection/report/retail/loan/vip-list/report',
        type: 'GET',
        data: {
            vipStatus: vipStatus
        },

        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            

            $('#vipListTbody').replaceWith("<tbody id='vipListTbody' >\n" +
                "\t\t\t\t\t\t\t</tbody>");
            for (var i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    var str = "<tr>" +
                        "<td>" + getStringValueOrDash(data[i].accountNumber) + "</td>" +
                        "<td>" + getStringValueOrDash(data[i].customerName) + "</td>" +
                        "<td>" + getStringValueOrDash(data[i].companyName) + "</td>" +
                        "<td>" + getStringValueOrDash(vipStatus) + "</td>" +
                        "<td>" + getStringValueOrDash(data[i].remarks) + "</td>" +
                        "</tr>";
                    $('#vipListTbody').append(str);
                }
            }
            isVipSearchLoading = false;
            $('#isVipSearchLoading').hide();
        },
        error: function (err) {
            isVipSearchLoading = false;
            $('#isVipSearchLoading').hide();
            swal("Something went wrong");
            
        }
    });
}

jQuery(function ($) {
    $("#exportVipListToExcelButton").click(function () {
        // parse the HTML table element having an id=exportTable
        var clientDbServer2DataSource = shield.DataSource.create({
            data: "#vipListTable",
            schema: {
                type: "table",
                fields: {
                    Account_Number: {type: String},
                    Customer_Name: {type: String},
                    Company_Name: {type: String},
                    Status: {type: String},
                    Remarks: {type: String}
                }
            }
        });

        // when parsing is done, export the data to Excel
        clientDbServer2DataSource.read().then(function (data) {

            new shield.exp.OOXMLWorkbook({
                author: "IFIC",
                worksheets: [
                    {
                        name: "VIP List",
                        rows: [
                            {
                                cells: [
                                    {
                                        style: {
                                            bold: true
                                        },
                                        type: String,
                                        value: "Account_Number"
                                    },
                                    {
                                        style: {
                                            bold: true
                                        },
                                        type: String,
                                        value: "Customer_Name"
                                    },
                                    {
                                        style: {
                                            bold: true
                                        },
                                        type: String,
                                        value: "Company_Name"
                                    },
                                    {
                                        style: {
                                            bold: true
                                        },
                                        type: String,
                                        value: "Status"
                                    },
                                    {
                                        style: {
                                            bold: true
                                        },
                                        type: String,
                                        value: "Remarks"
                                    },

                                ]
                            }
                        ].concat($.map(data, function (item) {

                            return {
                                cells: [
                                    {type: String, value: item.Account_Number},
                                    {type: String, value: item.Customer_Name},
                                    {type: String, value: item.Company_Name},
                                    {type: String, value: item.Status},
                                    {type: String, value: item.Remarks},
                                ]
                            };
                        }))
                    }
                ]
            }).saveAs({
                fileName: "Bank-VipList"
            });
        });
    });
});


jQuery(function ($) {
    $("#exportVipListToPdfButton").click(function () {
        // parse the HTML table element having an id=exportTable
        var clientDbServer2DataSource = shield.DataSource.create({
            data: "#vipListTable",
            schema: {
                type: "table",
                fields: {
                    Account_Number: {type: String},
                    Customer_Name: {type: String},
                    Company_Name: {type: String},
                    Status: {type: String},
                    Remarks: {type: String}
                }
            }
        });

        // when parsing is done, export the data to PDF
        clientDbServer2DataSource.read().then(function (data) {
            var pdf = new shield.exp.PDFDocument({
                author: "UCBL",
                fontSize: 8,
                created: new Date()
            });

            pdf.addPage("a4", "portrait");

            pdf.table(
                50,
                50,
                data,
                [
                    {field: "Account_Number", title: "Account Number", width: 80},
                    {field: "Customer_Name", title: "Customer Name", width: 100},
                    {field: "Company_Name", title: "Company Name", width: 100},
                    {field: "Status", title: "Status", width: 50},
                    {field: "Remarks", title: "Remarks", width: 150},

                ],
                {
                    margins: {
                        top: 50,
                        left: 50,
                        right: 50,
                        bottom: 50
                    }
                }
            );

            pdf.saveAs({
                fileName: "UCBL-VipList"
            });
        });
    });
});