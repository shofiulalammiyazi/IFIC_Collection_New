// $(document).ready(function (){
//
//
//     let urlLink = window.location.href.split("search=")[1];
//     console.log(urlLink);
//     if(urlLink!="")
//         document.getElementById("tab-referenceInfo").scrollIntoView();
//
//
// });


function getCaseType(caseFileType, caseFileSubType) {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/case_suit",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data: {csFiledType: caseFileType, csFiledSubType: caseFileSubType},
    }).done(function (data) {

        $("#caseSuitType")
            .find('option')
            .remove()
            .end()
            .append('<option value>Select Case/Suit</option>');
        $.each(data, function (key, item) {
            $("#caseSuitType").append('<option value=' + item.id + '  caseTypeEnum="' + item.caseTypeEnum + '">' + item.name + ' </option>')


        });

    }).fail(function (data) {


    })
}

function getCaseAndModifySelect(previousLitigationCaseId, caseFileType, caseFileSubType) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/case_suit",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        data: {csFiledType: caseFileType, csFiledSubType: caseFileSubType},
    }).done(function (data) {

        let caseSuitTypeUpdate = $("#caseSuitTypeUpdate" + previousLitigationCaseId).val();
        $("#caseSuitTypeUpdate" + previousLitigationCaseId).find('option').remove();
        $("#caseSuitTypeUpdate" + previousLitigationCaseId)
            .find('option')
            .remove()
            .end()
            .append('<option value>Select Case/Suit</option>');
        $.each(data, function (key, item) {
            $("#caseSuitTypeUpdate" + previousLitigationCaseId).append('<option value=' + item.id + '  caseTypeEnum="' + item.caseTypeEnum + '">' + item.name + ' </option>');
        });
        $("#caseSuitTypeUpdate" + previousLitigationCaseId).val(caseSuitTypeUpdate);

    }).fail(function (data) {


    })
}


function getCourtsName(courts, selectedCourt) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/courts",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        //data: { csFiledType: caseFileType },
    }).done(function (data) {

        $("#" + courts)
            .find('option')
            .remove()
            .end();
        $("#" + courts).append('<option value>Select</option>')
        $.each(data, function (key, item) {
            $("#" + courts).append('<option value=' + item.id + '>' + item.name + '</option>')

        });
        $("#" + courts).val(selectedCourt);

    }).fail(function (data) {


    })
}

function getCaseStatus(caseStatus) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/case-status",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {

        $("#" + caseStatus)
            .find('option')
            .remove()
            .end();
        $("#" + caseStatus).append('<option value>Select</option>')
        $.each(data, function (key, item) {
            $("#" + caseStatus).append('<option value=' + item.id + ' >' + item.name + '</option>')


        });

    }).fail(function (data) {


    })
}


function getCaseFiledSubType(divId) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/caseFiledSubType",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {

        $("#" + divId)
            .find('option')
            .remove()
            .end()
            .append('<option value="">Select Case Filed Sub-Type</option>');
        $.each(data, function (key, item) {
            $("#" + divId).append('<option value=' + item.id + ' >' + item.name + '</option>')


        });

    }).fail(function (data) {


    })
}


function getCollateralSecurity(collateralSecurity) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/collateralSecurity",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {

        $("#" + collateralSecurity)
            .find('option')
            .remove()
            .end()
            .append('<option value>Select</option>');
        $.each(data, function (key, item) {
            $("#" + collateralSecurity).append('<option value=' + item.id + ' landprop="' + item.hasProperty + '">' + item.name + '</option>')


        });

    }).fail(function (data) {


    })
}


function getCourseOfAction(caseTypeId, courseOfAction, courseOfActionContestedType, prevCourseOfActionId) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/courseOfAction",
        data: {"caseTypeId": caseTypeId},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {

        let contestedType = false;
        //$("#courseOfActionContestedTypeDiv").hide();
        //$("#courseOfActionContestedType").val("");

        $("#" + courseOfActionContestedType + "Div").hide();
        $("#" + courseOfActionContestedType).val();


        $("#" + courseOfAction)
            .find('option')
            .remove()
            .end()
            .append('<option value>Select</option>');

        $.each(data, function (key, item) {
            if (item.contestedType != null) {
                contestedType = true;
                return false;
            }


        });

        $.each(data, function (key, item) {

            if (contestedType) {
                $("#" + courseOfActionContestedType + "Div").show();
                $("#" + courseOfAction).append('<option value=' + item.id + ' contestedType="' + item.contestedType + '">' + item.name + '</option>');
                $("#" + courseOfAction).children('option').hide();
            }
            else {
                $("#" + courseOfAction).show();
                $("#" + courseOfActionContestedType + "Div").hide();
                $("#" + courseOfAction).append('<option value=' + item.id + ' >' + item.name + '</option>')
            }


        });
        if (prevCourseOfActionId != null || prevCourseOfActionId != undefined)
            $("#" + courseOfAction).val(prevCourseOfActionId);

    }).fail(function (data) {


    })
}


function getCourseOfActionSubProperty(courseOfActionId, actionType, previousLitigationCaseId) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/courseOfActionSubProperty",
        data: {"courseOfActionId": courseOfActionId},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {

        if (actionType == 'Create') {
            $("#courseOfActionSelectedSubDiv")
                .children()
                .remove()
                .end();
            if (data.hasPossession) {
                $("#courseOfActionSelectedSubDiv").append('<label style="color:red;margin-right: 5%">Possession in favour of Bank</label><label>Yes</label><input type="radio"  name="possessionInFavorBank" value="true"/>' +
                    '<label>No</label><input type="radio"  name="possessionInFavorBank" value="false"/>');


            }
            if (data.hasOthers)
                $("#courseOfActionhasOthersDiv").show();
            else if (data.hasRegistrationDate)
                $("#hasRegistrationDateDiv").show();
            else if (data.hasDeed)
                $("#courseOfActionhasDeedDiv").show();
            else if (data.hasMutation)
                $("#courseOfActionhasMutationDiv").show();
            else if (data.hasPropertyDisputed)
                $("#courseOfActionhasPropertyDisputedDiv").show();
        }
        else if (actionType == 'Update') {
            $("#courseOfActionSelectedSubUpdateDiv")
                .children()
                .remove()
                .end();
            if (data.hasPossession) {
                $("#courseOfActionSelectedSubUpdate" + previousLitigationCaseId + "Div").append('<label style="color:red;margin-right: 5%">Possession in favour of Bank</label><label>Yes</label><input type="radio"  name="possessionInFavorBank" value="true"/>' +
                    '<label>No</label><input type="radio"  name="possessionInFavorBank" value="false"/>');


            }
            if (data.hasOthers)
                $("#courseOfActionhasOthersUpdate" + previousLitigationCaseId + "Div").show();
            else if (data.hasRegistrationDate)
                $("#courseOfActionHasRegistrationDateUpdate" + previousLitigationCaseId + "Div").show();
            else if (data.hasDeed)
                $("#courseOfActionhasDeedUpdate" + previousLitigationCaseId + "Div").show();
            else if (data.hasMutation)
                $("#courseOfActionhasMutationUpdate" + previousLitigationCaseId + "Div").show();
            else if (data.hasPropertyDisputed)
                $("#courseOfActionhasPropertyDisputedUpdate" + previousLitigationCaseId + "Div").show();
        }


    }).fail(function (data) {


    })
}


function getLitigationCaseHistoryById(fieldName, id, obj) {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $("#" + fieldName + "-Spinner-" + id).show();
    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/get-litigation-revision",
        data: {"id": id},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {

        console.log(data);
        $("#exampleModal" + id).modal();
        $(".modal-title").children().remove().end();
        $("#fieldNameBody" + id).children().remove().end();
        $(".modal-title").append('<label style="text-transform: uppercase">' + fieldName + ' update history</label>')

        let table = '<table class="table table-striped table-bordered table-condensed"><tr><th>SL#</th><th>' + fieldName + '</th><th>Update Time</th></tr>';

        $.each(data, function (key, item) {
            //var keys = Object.keys(item);
            //$.each(keys,function(key,keyItem){
            //if(keyItem==fieldName){
            //$("#fieldNameBody").append('<li><span>'+(++key)+') </span><label>Name: </label>'+item[fieldName]+'<label> | Update Date:</label><label>'+modifiedDate+'</label></li>');
            $("#" + fieldName + "-Spinner-" + id).hide();

            let modifiedDate = item.modifiedDate != null ? moment(item.modifiedDate).format("MMMM Do YYYY, h:mm:ss a") : item.createdDate.toString().split("T")[0];
            let fieldValue;
            if (obj == 'obj')
                fieldValue = item[fieldName] != null ? item[fieldName].name : "";
            else
                fieldValue = item[fieldName] != null ? item[fieldName] : "";

            if (key == 0) {
                table += '<tr><td>' + (++key) + '</td><td>' + fieldValue + '</td><td>' + modifiedDate + '</td></tr>'

            }
            else {
                if (item[fieldName] == null) {
                    let noChange = 'No Change';
                    table += '<tr><td>' + (++key) + '</td><td>' + noChange + '</td><td>' + modifiedDate + '</td></tr>'
                    //$("#fieldNameBody").append('<li><span>'+(++key)+') </span><label>Name: </label>'+noChange+'<label> | Update Date:</label><label>'+modifiedDate+'</label></li>');
                }
                else {
                    //$("#fieldNameBody").append('<li><span>'+(++key)+') </span><label>Name: </label>'+item[fieldName]+'<label> | Update Date:</label><label>'+modifiedDate+'</label></li>');
                    table += '<tr><td>' + (++key) + '</td><td>' + fieldValue + '</td><td>' + modifiedDate + '</td></tr>'
                }
            }
            //}
            //});

        });
        table += '</table>';
        $("#fieldNameBody" + id).append(table);


    }).fail(function (data) {


    });
}


function getLawyerChangeHistory(litigationCaseInfoId) {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/get-lawyer-change-history",
        data: {"id": litigationCaseInfoId},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },

    }).done(function (data) {
        console.log(data);
        if (data.length > 0) {
            $("#lawyersChangeDiv" + litigationCaseInfoId).show();
            $.each(data, function (key, item) {
                $("#lawyersChange" + litigationCaseInfoId).append('<option>' + item.name + '</option>');
            })
        }

    }).fail(function (data) {

    })
}


function getPlaintiffChangeHistory(litigationCaseInfoId) {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "GET",
        url: "/legal/setup/rest/get-plaintiff-change-history",
        data: {"id": litigationCaseInfoId},
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
    }).done(function (data) {
        console.log(data);
        if (data.length > 0) {
            $("#plaintiffChangeDiv" + litigationCaseInfoId).show();
            $.each(data, function (key, item) {
                $("#plaintiffChange" + litigationCaseInfoId).append('<option>' + item + '</option>');
            })
        }
    }).fail(function (data) {

    });


}

function getAllHistoryById(id) {

}


function saveLitigation(formData) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    $.ajax({
        type: "POST",
        url: "/legal/setup/rest/save-litigation",
        data: JSON.stringify(formData),
        contentType: "application/json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        //data: { csFiledType: caseFileType },
    }).done(function (data) {
        let title = "Successfully Saved";
        let success = data === 'succeed';
        if (!success) {
            title = "Something went wrong";
        }
        swal({"title": title},
            function () {
                if (success) {
                    window.location.hash = '#tab-litigation';
                    window.location.reload(true);
                }
            }
        );

        //window.location.hash = '#tab-litigation';
        //window.location.reload(true);
        //console.log(window.location.href);


    }).fail(function (data) {
        swal({"title": "Something went wrong"},()=>{});

    })
}