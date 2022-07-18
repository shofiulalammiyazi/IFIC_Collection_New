var isPeoductWiseClReportLoading = false;

function productWiseClReport() {

    if (isPeoductWiseClReportLoading) {
        swal("Please wait - loading...");
        return;
    }

    isPeoductWiseClReportLoading = true;

    $('#isPeoductWiseClReportLoading').show();

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    let month = $("#productWiseClReportMonth").val() + '-01';

    $.ajax({
        url: '/collection/report/retail/loan/product-wise-cl/report',
        type: 'GET',
        data: {
            month: month
        },

        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);
            //
            // $('#productWiseClReportTbody').replaceWith("<tbody id='productWiseClReportTbody' >\n" +
            //     "\t\t\t\t\t\t\t</tbody>");
            // for (let i = 0; i < data.length; i++) {
            //     if (data[i] != null) {
            //         let str = "<tr>" +
            //             "<td>" + getStringValueOrDash(data[i].accountNo) + "</td>" +
            //             "<td>" + getStringValueOrDash(data[i].customerName) + "</td>" +
            //             "<td>" + getStringValueOrDash(data[i].companyName) + "</td>" +
            //             "<td>" + getStringValueOrDash(month) + "</td>" +
            //             "<td>" + getStringValueOrDash(data[i].remarks) + "</td>" +
            //             "</tr>";
            //         $('#productWiseClReportTbody').append(str);
            //     }
            // }
            isPeoductWiseClReportLoading = false;
            $('#isPeoductWiseClReportLoading').hide();
        },
        error: function (err) {
            isPeoductWiseClReportLoading = false;
            $('#isPeoductWiseClReportLoading').hide();
            swal("Something went wrong");
            console.log(err);
        }
    });
}

jQuery(function ($) {
    $("#exportProductWiseClReportToExcelButton").click(function () {
        // parse the HTML table element having an id=exportTable
        let clientDbServer2DataSource = shield.DataSource.create({
            data: "#productWiseClReportTable",
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
            console.log(data);
            new shield.exp.OOXMLWorkbook({
                author: "UCBL",
                worksheets: [
                    {
                        name: "UCBL VIP List",
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
                            console.log(item);
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
                fileName: "UCBL-productWiseClReport"
            });
        });
    });
});


jQuery(function ($) {
    $("#exportProductWiseClReportToPdfButton").click(function () {
        // parse the HTML table element having an id=exportTable
        let clientDbServer2DataSource = shield.DataSource.create({
            data: "#productWiseClReportTable",
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
            let pdf = new shield.exp.PDFDocument({
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
                fileName: "UCBL-productWiseClReport"
            });
        });
    });
});