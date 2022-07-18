$(".tab-team-lead-product-wise-target-kpi-amount-wise,#tab-product_wise_summary").click(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: "GET",
        url: "/retail/loan/dashboard/team-lead-kpi-target-amount-wise",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
             console.log(data);
            $("#team_lead_kpi_target_amount_wise").html('');
            for (var i = 0; i < data.length; i++) {

                var tr = '';
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
                $("#team_lead_kpi_target_amount_wise").append(tr);



            }
        }
    })

})

$(".tab-team-lead-product-wise-target-kpi-account-wise").click(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        type: "GET",
        url: "/retail/loan/dashboard/team-lead-kpi-target-account-wise",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $(".team_lead_kpi_target_account_wise").html('');
            for (var i = 0; i < data.length; i++) {
                var tr = '';
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
                $("#team_lead_kpi_target_account_wise").append(tr);

            }
        }
    })

})