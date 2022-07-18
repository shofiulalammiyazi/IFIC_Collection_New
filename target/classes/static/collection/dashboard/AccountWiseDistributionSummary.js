$(document).ready(function () {
    /* Managerial Target vs Ach */

    $("#tab-manager_target_by_amt").click(function () {

        $('.man_target_achievement_amount_wise').remove();
        $('.man_target_achievement_amount_wise_percentage').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/targetByManager/amountWise/card",
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
                    }

                    var total = 0;
                    var arr = new Array();

                    console.log(data);

                    for (var i = 0; i < data.length; i++) {

                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }

                        // NUMBER ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="man_target_achievement_amount_wise">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        var flowAch__ = data[i].flowAch;
                        flowAch__ = parseFloat(flowAch__);
                        var flowPerformace__ = 0;
                        if (flowTarget > 0 && flowAch__ > 0) {
                            flowPerformace__ = (((flowTarget - flowAch__) / flowTarget) + 1);
                        }


                        tr += '<tr class="man_target_achievement_amount_wise">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].amntPerAgeCode) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(flowPerformace__) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#man_target_achievement_amount_wise').append(tr);
                    }


                    var total = 0;
                    var arr = new Array();

                    for (var i = 0; i < data.length; i++) {


                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }


                        // PERCENTAGE ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="man_target_achievement_amount_wise_percentage">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        tr += '<tr class="man_target_achievement_amount_wise_percentage">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].flowAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].flowTargetPerc - ((data[i].flowAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].flowAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].saveAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].saveTargetPerc - ((data[i].saveAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].saveAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].backAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].backTargetPerc - ((data[i].backAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].backAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].regAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].regTargetPerc - ((data[i].regAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].regAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].parAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].parTargetPerc - ((data[i].parAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].parAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].nplAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].nplTargetPerc - ((data[i].nplAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].nplAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].rawAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].rawTargetPerc - ((data[i].rawAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].rawAmntPerDay)) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(((data[i].minAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].minTargetPerc - ((data[i].minAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].minAmntPerDay)) + '</td>\n' +

                            '                        </tr>';

                        $('#man_target_achievement_amount_wise_percentage').append(tr);
                    }
                }
            }).done(function () {

            });
        }
        else if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/targetByManager/amountWise/loan",
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
                    }

                    var total = 0;
                    var arr = new Array();
                    var arrPerc = new Array();

                    console.log(data);
                    $(".man_target_achievement_loan_row").html('');
                    for (var i = 0; i < data.length; i++) {

                        var tr = '';
                        var trPerc = '';

                        //for total on loan amount wise
                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="man_target_achievement_loan_row">\n' +
                                '<td>' + data[i].product + '</td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '</tr>';
                            arr.push(data[i].product);
                        }

                        tr += '<tr class="man_target_achievement_loan_row">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].dpdBucket) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntEachDpd) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfFlowAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfFlowAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallFlow) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfSaveAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfSaveAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallSave) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfBackAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfBackAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallBack) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOverDue) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOverDue) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallOverDue) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overDuePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverdDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverDuePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntRegular) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntRegular) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallRegular) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerFormance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerformmance) + '</td>\n' +
                            '</tr>';
                        $("#man_target_achievement_amount_wise").append(tr);


                        //for percentage on loan amount wise
                        if (arrPerc.indexOf(data[i].product) < 0) {
                            trPerc += '<tr class="man_target_achievement_loan_row">\n' +
                                '<td>' + data[i].product + '</td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '</tr>';
                            arrPerc.push(data[i].product);
                        }

                        trPerc += '<tr class="man_target_achievement_loan_row">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntEachDpd) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfFlowAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfFlowAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOfFlowAcPerc - data[i].amntOfFlowAcPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfSaveAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfSaveAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOfSaveAcPerc - data[i].amntOfSaveAcPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfBackAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfBackAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOfBackAcPerc - data[i].amntOfBackAcPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOverDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOverDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOverDuePerc - data[i].amntOverDuePerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overDuePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverdDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverDuePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntRegularPerc - data[i].amntRegularPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerFormance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerformmance) + '</td>\n' +

                            '</tr>';
                        $("#man_target_achievement_amount_wise_percentage").append(trPerc);
                    }
                }
            }).done(function () {

            });
        }

    })


    $("#tab-product_wise_summary_by_acc_man").click(function () {

        $('.paa_account_custom').remove();
        $('.man_target_achievement_account_wise').remove();
        $('.man_target_achievement_account_wise_percentage').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/targetByManager/accountWise/card",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (json) {
                    var data = JSON.stringify(json);
                    data = JSON.parse(data);

                    var total = 0;
                    var arr = new Array();

                    console.log(data);

                    for (var i = 0; i < data.length; i++) {

                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }

                        // NUMBER ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="man_target_achievement_account_wise">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        var flowAch__ = data[i].flowAch;
                        flowAch__ = parseFloat(flowAch__);
                        var flowPerformace__ = 0;
                        if (flowTarget > 0 && flowAch__ > 0) {
                            flowPerformace__ = (((flowTarget - flowAch__) / flowTarget) + 1);
                        }


                        tr += '<tr class="man_target_achievement_account_wise">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].amntPerAgeCode) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(flowPerformace__) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#man_target_achievement_account_wise').append(tr);
                    }


                    var total = 0;
                    var arr = new Array();

                    for (var i = 0; i < data.length; i++) {


                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }


                        // PERCENTAGE ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="man_target_achievement_account_wise_percentage">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        tr += '<tr class="man_target_achievement_account_wise_percentage">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].ageCode) + '</td>\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].flowAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].flowTargetPerc - ((data[i].flowAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].saveAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].saveTargetPerc - ((data[i].saveAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].backAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].backTargetPerc - ((data[i].backAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].regAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].regTargetPerc - ((data[i].regAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].parAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].parTargetPerc - ((data[i].parAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].nplAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].nplTargetPerc - ((data[i].nplAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].rawAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].rawTargetPerc - ((data[i].rawAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].minAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].minTargetPerc - ((data[i].minAch / data[i].amntPerAgeCode)))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#man_target_achievement_account_wise_percentage').append(tr);
                    }
                }
            }).done(function () {

            });
        } else if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/team/targetByManager/accountWise/loan",
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
                    }

                    var total = 0;
                    var arr = new Array();
                    var arrPerc = new Array();

                    console.log(data);
                    $(".man_target_achievement_loan_row").html('');
                    for (var i = 0; i < data.length; i++) {

                        var tr = '';
                        var trPerc = '';
                        //for total on loan amount wise
                        if (arr.indexOf(data[i].product) < 0) {
                            tr += '<tr class="man_target_achievement_account_wise">\n' +
                                '<td>' + data[i].product + '</td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '</tr>';
                            arr.push(data[i].product);
                        }

                        tr += '<tr class="man_target_achievement_account_wise">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].dpdBucket) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntEachDpd) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfFlowAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfFlowAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallFlow) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfSaveAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfSaveAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallSave) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfBackAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfBackAc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallBack) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOverDue) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOverDue) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallOverDue) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overDuePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverdDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverDuePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntRegular) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntRegular) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].shortFallRegular) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerFormance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerformmance) + '</td>\n' +
                            '</tr>';
                        $("#man_target_achievement_account_wise").append(tr);


                        //for percentage on loan amount wise
                        if (arrPerc.indexOf(data[i].product) < 0) {
                            trPerc += '<tr class="man_target_achievement_account_wise">\n' +
                                '<td>' + data[i].product + '</td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '<td class="text-right"></td>\n' +
                                '</tr>';
                            arrPerc.push(data[i].product);
                        }

                        trPerc += '<tr class="man_target_achievement_account_wise_percentage">\n' +
                            '<td>-</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntEachDpd) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfFlowAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfFlowAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOfFlowAcPerc - data[i].amntOfFlowAcPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightFlowPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfSaveAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfSaveAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOfSaveAcPerc - data[i].amntOfSaveAcPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightSavePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOfBackAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOfBackAcPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOfBackAcPerc - data[i].amntOfBackAcPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightBackPerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntOverDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntOverDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntOverDuePerc - data[i].amntOverDuePerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overDuePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverdDuePerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightOverDuePerformmance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].tarAmntRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].amntRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].tarAmntRegularPerc - data[i].amntRegularPerc)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerFormance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerc) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].weightRegularPerformmance) + '</td>\n' +

                            '</tr>';
                        $("#man_target_achievement_account_wise_percentage").append(trPerc);
                    }
                }
            }).done(function () {

            });
        }

    });
    /* Managerial Target vs Ach END */


    $("#tab-product-wise-summary").click(function () {

        $('.kpi_target_achievement_amount_wise').remove();
        $('.kpi_target_achievement_amount_wise_percentage').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/distribution/kpi_vs_achiev/card",
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
                    }

                    var total = 0;
                    var arr = new Array();

                    console.log(data);

                    for (var i = 0; i < data.length; i++) {

                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }

                        // NUMBER ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) != null) {
                            tr += '<tr class="kpi_target_achievement_amount_wise">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        var flowAch__ = data[i].flowAch;
                        flowAch__ = parseFloat(flowAch__);
                        var flowPerformace__ = 0;
                        if (flowTarget > 0 && flowAch__ > 0) {
                            flowPerformace__ = (((flowTarget - flowAch__) / flowTarget) + 1);
                        }


                        tr += '<tr class="kpi_target_achievement_amount_wise">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].amntPerAgeCode) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(flowPerformace__) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#kpi_target_achievement_amount_wise').append(tr);
                    }


                    var total = 0;
                    var arr = new Array();

                    for (var i = 0; i < data.length; i++) {


                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }


                        // PERCENTAGE ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) != null) {
                            tr += '<tr class="kpi_target_achievement_amount_wise_percentage">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right">' + data[i].amntPerPT + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        tr += '<tr class="kpi_target_achievement_amount_wise_percentage">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].flowAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPerc - ((data[i].flowAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].saveAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPerc - ((data[i].saveAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].backAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTargetPerc - ((data[i].backAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].regAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTargetPerc - ((data[i].regAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].parAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTargetPerc - ((data[i].parAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].nplAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTargetPerc - ((data[i].nplAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].rawAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTargetPerc - ((data[i].rawAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].minAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTargetPerc - ((data[i].minAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#kpi_target_achievement_amount_wise_percentage').append(tr);
                    }
                }
            }).done(function () {

            });
        } else if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/retail/loan/dashboard/kpi-target-vs-achievement-amount-wise",
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
                    $(".kpi_target_achievement_loan_row").html('');
                    for (var i = 0; i < data.length; i++) {

                        var tr = '';
                        var trPerc = '';

                        tr += '<tr class="kpi_target_achievement_loan_row">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].total) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformance) + '</td>\n' +
                            '</tr>';
                        $("#kpi_target_achievement_amount_wise").append(tr);


                        //for percentage on loan amount wise

                        trPerc += '<tr class="kpi_target_achievement_loan_row">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(100) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].flowShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].saveShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].backShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].overdueShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].regularShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformancePercentage) + '</td>\n' +

                            '</tr>';
                        $("#kpi_target_achievement_amount_wise_percentage").append(trPerc);
                    }
                }
            }).done(function () {

            });
        }

    })

    $("#tab-product_wise_summary_by_amount").click(function () {

        $('.kpi_target_achievement_amount_wise').remove();
        $('.kpi_target_achievement_amount_wise_percentage').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/distribution/kpi_vs_achiev/card",
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
                    }

                    var total = 0;
                    var arr = new Array();

                    console.log(data);

                    for (var i = 0; i < data.length; i++) {

                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }

                        // NUMBER ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) != null) {
                            tr += '<tr class="kpi_target_achievement_amount_wise">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        var flowAch__ = data[i].flowAch;
                        flowAch__ = parseFloat(flowAch__);
                        var flowPerformace__ = 0;
                        if (flowTarget > 0 && flowAch__ > 0) {
                            flowPerformace__ = (((flowTarget - flowAch__) / flowTarget) + 1);
                        }


                        tr += '<tr class="kpi_target_achievement_amount_wise">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].amntPerAgeCode) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(flowPerformace__) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTarget) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAch) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minShortFall) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#kpi_target_achievement_amount_wise').append(tr);
                    }


                    var total = 0;
                    var arr = new Array();

                    for (var i = 0; i < data.length; i++) {


                        var flowTarget = data[i].flowTarget;
                        flowTarget = parseFloat(flowTarget);

                        var saveTarget = data[i].saveTarget;
                        saveTarget = parseFloat(saveTarget);

                        var backTarget = data[i].backTarget;
                        backTarget = parseFloat(backTarget);

                        var regTarget = data[i].regTarget;
                        regTarget = parseFloat(regTarget);

                        var parTarget = data[i].parTarget;
                        parTarget = parseFloat(parTarget);

                        var nplTarget = data[i].nplTarget;
                        nplTarget = parseFloat(nplTarget);

                        var rawTarget = data[i].rawTarget;
                        rawTarget = parseFloat(rawTarget);

                        if (flowTarget <= 0 && saveTarget <= 0 && backTarget <= 0 && regTarget <= 0 && parTarget <= 0 && nplTarget <= 0 && rawTarget <= 0) {
                            continue;
                        }


                        // PERCENTAGE ONLY
                        var tr = "";
                        if (arr.indexOf(data[i].product) != null) {
                            tr += '<tr class="kpi_target_achievement_amount_wise_percentage">\n' +
                                '                            <td class="text-right">' + data[i].cardsCategory + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right">' + data[i].amntPerPT + '</td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                            <td class="text-right"></td>\n' +
                                '                        </tr>';
                            arr.push(data[i].product);

                        }


                        tr += '<tr class="kpi_target_achievement_amount_wise_percentage">\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + data[i].ageCode + '</td>\n' +
                            '                            <td class="text-right"></td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].flowAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPerc - ((data[i].flowAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].flowAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].saveAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPerc - ((data[i].saveAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].saveAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].backAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backTargetPerc - ((data[i].backAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].backAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].regAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regTargetPerc - ((data[i].regAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].regAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].parAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parTargetPerc - ((data[i].parAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].parAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].nplAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplTargetPerc - ((data[i].nplAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].nplAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].rawAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawTargetPerc - ((data[i].rawAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].rawAmntPerDay) + '</td>\n' +

                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTargetPerc) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish((data[i].minAch / data[i].amntPerAgeCode)) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minTargetPerc - ((data[i].minAch / data[i].amntPerAgeCode))) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeight) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minWeightedPerformance) + '</td>\n' +
                            '                            <td class="text-right">' + formatBdtInEnglish(data[i].minAmntPerDay) + '</td>\n' +
                            '                        </tr>';

                        $('#kpi_target_achievement_amount_wise_percentage').append(tr);
                    }
                }
            }).done(function () {

            });
        } else if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/retail/loan/dashboard/kpi-target-vs-achievement-amount-wise",
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
                    if (!Array.isArray(data)) {
                        notifyError();
                        return;
                    }
                    $(".kpi_target_achievement_loan_row").html('');
                    for (var i = 0; i < data.length; i++) {

                        var tr = '';
                        var trPerc = '';

                        tr += '<tr class="kpi_target_achievement_loan_row">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].total) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformance) + '</td>\n' +
                            '</tr>';
                        $("#kpi_target_achievement_amount_wise").append(tr);


                        //for percentage on loan amount wise

                        trPerc += '<tr class="kpi_target_achievement_loan_row">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(100) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].flowShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].saveShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].backShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].overdueShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].regularShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformancePercentage) + '</td>\n' +

                            '</tr>';
                        $("#kpi_target_achievement_amount_wise_percentage").append(trPerc);
                    }
                }
            }).done(function () {

            });
        }

    })

    $("#tab-product_wise_summary_by_acc").click(function () {

        $('.paa_account_custom').remove();
        $('.kpi_target_achievement_account_wise').remove();
        $('.kpi_target_achievement_account_wise_percentage').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/distribution/kpi_vs_achiev/card/account",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                data: {
                    userPin: userPin,
                    unit: unit,
                    designation: designation,
                    userId: userId
                },
                success: function (json) {
                    for (var i = 0; i < data.length; i++) {

                        // NUMBER ONLY
                        var tr = "";

                        tr += '<tr class="kpi_target_achievement_account_wise">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].total) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformance) + '</td>\n' +
                            '</tr>';
                        $('#kpi_target_achievement_account_wise').append(tr);
                    }

                    for (var i = 0; i < data.length; i++) {

                        // PERCENTAGE ONLY
                        var tr = "";

                        tr += '<tr class="kpi_target_achievement_account_wise_percentage">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(100) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].flowShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].saveShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].backShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].overdueShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].regularShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformancePercentage) + '</td>\n' +

                            '</tr>';

                        $('#kpi_target_achievement_account_wise_percentage').append(tr);
                    }
                }
            }).done(function () {

            });
        } else if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/retail/loan/dashboard/kpi-target-vs-achievement-account-wise",
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
                    if (!Array.isArray(data)) {
                        notifyError();
                        return;
                    }

                    $(".kpi_target_achievement_loan_row").html('');
                    for (var i = 0; i < data.length; i++) {

                        var tr = '';
                        var trPerc = '';

                        //for total on loan account wise
                        tr += '<tr class="kpi_target_achievement_account_wise">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].total) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformance) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTarget) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievement) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularShortfall) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformance) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeight) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformance) + '</td>\n' +
                            '</tr>';
                        $("#kpi_target_achievement_account_wise").append(tr);


                        //for percentage on loan amount wise
                        trPerc += '<tr class="kpi_target_achievement_account_wise_percentage">\n' +
                            '<td style="white-space: nowrap">' + data[i].productGroup+ '</td>\n' +
                            '<td class="text-right">' + data[i].dpdBucket+ '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(100) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].flowShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].flowWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].saveShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].savePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].saveWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].backTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].backShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].backWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].overdueShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overduePerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].overdueWeightedPerformancePercentage) + '</td>\n' +

                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularTargetPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularAchievementPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish((data[i].regularShortfallPercentage)) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularPerformancePercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightPercentage) + '</td>\n' +
                            '<td class="text-right">' + formatBdtInEnglish(data[i].regularWeightedPerformancePercentage) + '</td>\n' +

                            '</tr>';
                        $("#kpi_target_achievement_account_wise_percentage").append(trPerc);
                    }
                }
            }).done(function () {

            });
        }

    });

    $("#loanAccWiseDistSummaryTab").click(function () {

        $('.dass_account_custom').remove();
        $('.dass_account_per_custom').remove();
        $('.dass_amount_per_custom').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {
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
                        notifyError();
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

                    //Ignoring redundant data
                    let previousDealer = '';
                    let previousProduct = '';

                    for (const [key, value] of ageCodeMap.entries()) {
                        var data = value;
                        var tr = '';
                        tr += '<tr class="dass_account_custom">\n' +
                            '                        <td class="text-right">' + data.productAndDpd + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.touchedNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.unTouchedNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.totalPending + '</td>\n' +
                            '                        <td class="text-right"></td>\n' +
                            '                        <td class="text-right"></td>\n' +
                            '                        <td class="text-right"></td>\n' +
                            '                        <td class="text-right">' + data.totalParRel + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParRelRem + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParQ + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParQueueNextDay + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplRel + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplRem + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplQ + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplQueueNextDay + '</td>\n' +
                            '                    </tr>';

                        $('#dass_account_data').append(tr);
                        // tr = '';
                        // tr += '<tr class="dass_account_custom">\n' +
                        //     '                        <td class="text-right">'+data.productAndDpd+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.totalPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.touchedPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.unTouchedPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.pendingPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.parRelPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.parRelRemPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.parQPerc)+'</td>\n' +
                        //     '                        <td class="text-right"></td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.nplRelPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.nplRemPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.nplQPerc)+'</td>\n' +
                        //     '                        <td class="text-right"></td>\n' +
                        //     '                    </tr>';
                        //
                        // $('#dass_account_per_data').append(tr);

                    }
                    // for(var i=0; i<data.length; i++) {
                    //
                    //
                    //     var tr = '';
                    //     if(arr.indexOf(data[i].pg) < 0) {
                    //
                    //         total += data[i].totalNumber;
                    //         tr += '<tr class="dass_account_custom">\n' +
                    //             '                        <td>'+data[i].pg+'</td>\n' +
                    //             '                        <td class="text-right">-</td>\n' +
                    //             '                        <td class="text-right">'+data[i].totalNumber+'</td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                    </tr>';
                    //         arr.push(data[i].pg);
                    //
                    //         // continue;
                    //     }
                    //
                    //
                    //
                    //
                    //     tr += '<tr class="dass_account_custom">\n' +
                    //         '                        <td>-</td>\n' +
                    //         '                        <td class="text-right">'+data[i].productAndDpd+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].numberPerDpd+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].touchedNumber+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].unTouchedNumber+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalPending+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalParRel+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalParRelRem+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalParQ+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalNplRel+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalNplRem+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalNplQ+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                    </tr>';
                    //
                    //     $('#dass_account_data').append(tr);
                    // }
                    //
                    // // PERCENTAGE
                    // var arr = new Array();
                    // for(var i=0; i<data.length; i++) {
                    //
                    //     var tr = '';
                    //     if(arr.indexOf(data[i].pg) < 0) {
                    //         var per = (data[i].totalNumber/total);
                    //
                    //         tr += '<tr class="dass_amount_per_custom">\n' +
                    //             '                        <td>'+data[i].pg+'</td>\n' +
                    //             '                        <td class="text-right" colspan="2">'+formatBdtInEnglish(per)+'</td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                    </tr>';
                    //         arr.push(data[i].pg);
                    //
                    //         // continue;
                    //     }
                    //
                    //
                    //
                    //
                    //     tr += '<tr class="dass_account_custom">\n' +
                    //         '                        <td>-</td>\n' +
                    //         '                        <td class="text-right">'+data[i].productAndDpd+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].totalPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].touchedPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].unTouchedPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].pendingPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].parRelPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].parRelRemPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].parQPerc)+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].nplRelPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].nplRemPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].nplQPerc)+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                    </tr>';
                    //
                    //     $('#dass_account_per_data').append(tr);
                    // }
                }
            }).done(function () {

            });
        }
        else if (unit == 'Loan') {
            $.ajax({
                type: "GET",
                url: "/collection/dashboard/distribution/accountWise",
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


                    var total = 0;
                    var arr = new Array();
                    var ageCodeMap = new Map();

                    var parReleaseTotal = 0;
                    var parRemainingTotal = 0;
                    var parQueueTotal = 0;
                    var parQueueNDTotal = 0;
                    var nplReleaseTotal = 0;
                    var nplRemainingTotal = 0;
                    var nplQueueTotal = 0;
                    var nplQueueNDTotal = 0;

                    for (var i = 0; i<data.length; i++){
                        parReleaseTotal = parReleaseTotal + data[i].totalParRel;
                        parRemainingTotal = parRemainingTotal + data[i].totalParRelRem;
                        parQueueTotal = parQueueTotal + data[i].totalParQ;
                        nplReleaseTotal = nplReleaseTotal + data[i].totalNplRel;
                        nplRemainingTotal = nplRemainingTotal + data[i].totalNplRem;
                        nplQueueTotal = nplQueueTotal + data[i].totalNplQ;

                    }

                    $('#parReleaseTotal').text(parReleaseTotal);
                    $('#parRemainingTotal').text(parRemainingTotal);
                    $('#parQueueTotal').text(parQueueTotal);
                    $('#nplReleaseTotal').text(nplReleaseTotal);
                    $('#nplRemainingTotal').text(nplRemainingTotal);
                    $('#nplQueueTotal').text(nplQueueTotal);


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

                    //Ignoring redundant data
                    let previousDealer = '';
                    let previousProduct = '';

                    for (const [key, value] of ageCodeMap.entries()) {
                        var data = value;
                        var tr = '';
                        tr += '<tr class="dass_account_custom">\n' +
                            '                        <td class="text-left">' + getNewDataIgnoringPreviousData(data.pg, previousProduct) + '</td>\n' +
                            '                        <td class="text-right">' + data.productAndDpd + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.touchedNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.unTouchedNumber + '</td>\n' +
                            '                        <td class="text-right">' + data.totalPending + '</td>\n' +
                            '                        <td class="text-right">'+ data.ntPtp +'</td>\n' +
                            '                        <td class="text-right">'+ data.keptPtp +'</td>\n' +
                            '                        <td class="text-right">'+ data.brokenPtp +'</td>\n' +
                            '                        <td class="text-right">' + data.totalParRel + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParRelRem + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParQ + '</td>\n' +
                            '                        <td class="text-right">' + data.totalParQueueNextDay + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplRel + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplRem + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplQ + '</td>\n' +
                            '                        <td class="text-right">' + data.totalNplQueueNextDay + '</td>\n' +
                            '                    </tr>';

                        $('#dass_account_data').append(tr);


                    }
                    // for(var i=0; i<data.length; i++) {
                    //
                    //
                    //     var tr = '';
                    //     if(arr.indexOf(data[i].pg) < 0) {
                    //
                    //         total += data[i].totalNumber;
                    //         tr += '<tr class="dass_account_custom">\n' +
                    //             '                        <td>'+data[i].pg+'</td>\n' +
                    //             '                        <td class="text-right">-</td>\n' +
                    //             '                        <td class="text-right">'+data[i].totalNumber+'</td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                    </tr>';
                    //         arr.push(data[i].pg);
                    //
                    //         // continue;
                    //     }
                    //
                    //
                    //
                    //
                    //     tr += '<tr class="dass_account_custom">\n' +
                    //         '                        <td>-</td>\n' +
                    //         '                        <td class="text-right">'+data[i].productAndDpd+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].numberPerDpd+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].touchedNumber+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].unTouchedNumber+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalPending+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalParRel+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalParRelRem+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalParQ+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalNplRel+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalNplRem+'</td>\n' +
                    //         '                        <td class="text-right">'+data[i].totalNplQ+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                    </tr>';
                    //
                    //     $('#dass_account_data').append(tr);
                    // }
                    //
                    // // PERCENTAGE
                    // var arr = new Array();
                    // for(var i=0; i<data.length; i++) {
                    //
                    //     var tr = '';
                    //     if(arr.indexOf(data[i].pg) < 0) {
                    //         var per = (data[i].totalNumber/total);
                    //
                    //         tr += '<tr class="dass_amount_per_custom">\n' +
                    //             '                        <td>'+data[i].pg+'</td>\n' +
                    //             '                        <td class="text-right" colspan="2">'+formatBdtInEnglish(per)+'</td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                        <td class="text-right"></td>\n' +
                    //             '                    </tr>';
                    //         arr.push(data[i].pg);
                    //
                    //         // continue;
                    //     }
                    //
                    //
                    //
                    //
                    //     tr += '<tr class="dass_account_custom">\n' +
                    //         '                        <td>-</td>\n' +
                    //         '                        <td class="text-right">'+data[i].productAndDpd+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].totalPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].touchedPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].unTouchedPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].pendingPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].parRelPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].parRelRemPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].parQPerc)+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].nplRelPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].nplRemPerc)+'</td>\n' +
                    //         '                        <td class="text-right">'+formatBdtInEnglish(data[i].nplQPerc)+'</td>\n' +
                    //         '                        <td class="text-right"></td>\n' +
                    //         '                    </tr>';
                    //
                    //     $('#dass_account_per_data').append(tr);
                    // }
                }
            }).done(function () {

            });
        }
    });

    $("#tab-dass").click(function () {


        $('.dass_amount_custom').remove();
        $('.dass_amount_per_custom').remove();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var userPin = $("#globalUserPinForSummary").val();
        var unit = $("#globalUnitForSummary").val();
        var designation = $("#globalUserDesignationForSummary").val();
        var userId = $("#globalUserIdForSummary").val();

        if (unit == 'Card') {

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
                        notifyError();
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

                    //Ignoring redundant data
                    let previousDealer = '';
                    let previousProduct = '';

                    for (const [key, value] of ageCodeMap.entries()) {
                        var data = value;
                        var tr = '';

                        tr += '<tr class="dass_amount_custom">\n' +
                            '    <td class="text-right">' + data.productAndDpd + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.numberPerDpd) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.touchedNumber) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.unTouchedNumber) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalPending) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalParRel) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalParRelRem) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalParQ) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalParQueueNextDay) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalNplRel) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalNplRem) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalNplQ) + '</td>\n' +
                            '    <td class="text-right">' + formatBdtInEnglish(data.totalNplQueueNextDay) + '</td>\n' +
                            '</tr>';

                        $('#dass_amount_data').append(tr);
                        tr = '';
                        // tr += '<tr class="dass_amount_custom">\n' +
                        //     '                        <td class="text-right">'+data.productAndDpd+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.totalPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.touchedPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.unTouchedPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.pendingPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.parRelPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.parRelRemPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.parQPerc)+'</td>\n' +
                        //     '                        <td class="text-right"></td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.nplRelPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.nplRemPerc)+'</td>\n' +
                        //     '                        <td class="text-right">'+formatBdtInEnglish(data.nplQPerc)+'</td>\n' +
                        //     '                        <td class="text-right"></td>\n' +
                        //     '                    </tr>';
                        //
                        // $('#dass_amount_per_data').append(tr);
                    }
                }
            }).done(function () {

            });


        } else if (unit == 'Loan') {

            // $.ajax({
            //     type: "GET",
            //     url: "/collection/dashboard/distribution/amountWise",
            //     beforeSend: function (xhr) {
            //         xhr.setRequestHeader(header, token);
            //     },
            //     data: {
            //         userPin: userPin,
            //         unit: unit,
            //         designation: designation,
            //         userId: userId
            //     },
            //     success: function (data) {
            //
            //         if (typeof data === 'string') {
            //             notifyError();
            //             return;
            //         }
            //
            //         var total = 0;
            //         var arr = new Array();
            //         var ageCodeMap = new Map();
            //
            //
            //         for (var j = 0; j < data.length; j++) {
            //             if (!ageCodeMap.has(data[j].productAndDpd)) {
            //                 ageCodeMap.set(data[j].productAndDpd, data[j]);
            //             } else {
            //                 var newVar = ageCodeMap.get(data[j].productAndDpd);
            //
            //                 newVar.numberPerDpd += data[j].numberPerDpd;
            //                 newVar.touchedNumber += data[j].touchedNumber;
            //                 newVar.unTouchedNumber += data[j].unTouchedNumber;
            //                 newVar.totalPending += data[j].totalPending;
            //                 newVar.totalParRel += data[j].totalParRel;
            //                 newVar.totalParRelRem += data[j].totalParRelRem;
            //                 newVar.totalParQ += data[j].totalParQ;
            //                 newVar.totalNplRel += data[j].totalNplRel;
            //                 newVar.totalNplRem += data[j].totalNplRem;
            //                 newVar.totalNplQ += data[j].totalNplQ;
            //             }
            //         }
            //
            //         //Ignoring redundant data
            //         let previousDealer = '';
            //         let previousProduct = '';
            //
            //         for (const [key, value] of ageCodeMap.entries()) {
            //             var data = value;
            //             var tr = '';
            //
            //             tr += '<tr class="dass_amount_custom">\n' +
            //                 '    <td class="text-right">' + getNewDataIgnoringPreviousData(data.dealerPin, previousDealer) + '</td>\n' +
            //                 '    <td class="text-left">' + getNewDataIgnoringPreviousData(data.pg, previousProduct) + '</td>\n' +
            //                 '    <td class="text-right">' + data.productAndDpd + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.numberPerDpd) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.touchedNumber) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.unTouchedNumber) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalPending) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalParRel) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalParRelRem) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalParQ) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalParQueueNextDay) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalNplRel) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalNplRem) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalNplQ) + '</td>\n' +
            //                 '    <td class="text-right">' + formatBdtInEnglish(data.totalNplQueueNextDay) + '</td>\n' +
            //                 '</tr>';
            //
            //             $('#dass_amount_data').append(tr);
            //             tr = '';
            //         }
            //     }
            // }).done(function () {
            //
            // });
        }

    });

})

