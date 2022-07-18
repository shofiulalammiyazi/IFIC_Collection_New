$(document).ready(function(){
    $(".cardAmountHide").hide();
    $(".intLimitLabel").hide();

    var customerId = $('.customer-id').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'GET',
        url: "/card/card-info-count",
        dataType: 'json',
        success: function (response) {
            $('#showCardInitNumber').text(response);
       
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    $("#currencyType").change(function() {
        var currencyType = $("#currencyType").val();

        if (currencyType == "Multicurrency") {
            $(".cardAmountHide").show();
            $(".intLimitLabel").show();

        } else if ( currencyType == "local") {
            $(".cardAmountHide").hide();
            $("#cardAmount").val(0.0);
            $(".intLimitLabel").hide();
            $("#initLimit").val(0.0);
        } else {
        }
    });




    $("#totalLimit").keyup(function () {
        var max=data.cardType.maxLimit;
        var min=data.cardType.minLimit;
        alert(max+" "+min+" "+this.value)

        if(this.value<min || this.value>max){
            alert("Total Limit must be between"+min+" and "+max);
        }
    })


    /*$("#existingLimitBDT").keyup(function () {
        var amount=$("#existingLimitBDT").val();
        if(amount.length>0)
        {
            var strAmnt=inWordsForCrd(amount);
            $("#amountInWordorCard").text(strAmnt);

        }else {
            $("#amountInWordorCard").text("");
        }
    })

    $("#existingLimitUSD").keyup(function () {
        var amount=$("#existingLimitUSD").val();
        if(amount.length>0)
        {
            var strAmnt=inWordsForCrd(amount);
            $("#amountInWordorCard").text(strAmnt);

        }else {
            $("#amountInWordorCard").text("");
        }
    })

    $("#totalExistingLimit").keyup(function () {
        var amount=$("#totalExistingLimit").val();
        if(amount.length>0)
        {
            var strAmnt=inWordsForCrd(amount);
            $("#amountInWordorCard").text(strAmnt);

        }else {
            $("#amountInWordorCard").text("");
        }
    })
*/
   // tot.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');

});


function generalInfoValidation() {
    var x = document.forms["generalInfoForm"]["branchId"].value;

    if(x==""){
        alert("Branch must be selected.");
        $("#branchId").focus();
        return false;
    }

    x = document.forms["generalInfoForm"]["appTypeId"].value;
    if(x==""){
        alert("Application Type must be selected.");
        $("#appTypeId").focus();
        return false;
    }

    x = document.forms["generalInfoForm"]["cardTypeId"].value;
    if(x==""){
        alert("Card Type must be selected.");
        $("#cardTypeId").focus();
        return false;
    }

    x = document.forms["generalInfoForm"]["cusTypeId"].value;
    if(x==""){
        alert("Customer Type must be selected.");
        $("#cusTypeId").focus();
        return false;
    }

    x = document.forms["generalInfoForm"]["currencyType"].value;
    if(x==""){
        alert("Currency Type must be selected.");
        $("#currencyType").focus();
        return false;
    }

    x = document.forms["generalInfoForm"]["natrPropId"].value;
    if(x==""){
        alert("Nature Of Proposal must be selected.");
        $("#natrPropId").focus();
        return false;
    }

    x = document.forms["generalInfoForm"]["totalLimit"].value;
    if(x==""){
        alert("Total Limit(BDT) must be inputed.");
        $("#totalLimit").focus();
        return false;
    }

   /* x = document.forms["generalInfoForm"]["totalLimit"].value;
    if(isNaN(x) || (!isNaN(x) && x<0)){
        alert("Total Limit(BDT) must be valid..");
        $("#totalLimit").focus();
        return false;
    }*/


}

$("#cardAmount").keyup(function(){
    var conversationrate= $("#conversationRate").val();
    var tk=$("#cardAmount").val().replace(/,/g , '');
    var usd=$("#initLimit").val().replace(/,/g , '');
   // var tot= (Number(tk) + (Number(usd)*Number(83.74))).toFixed(2);
    var tot= (Number(tk) + (Number(usd)*Number(conversationrate))).toFixed(2);
        tot = tot.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    $("#totalLimit").val(tot);
});

$("#initLimit").keyup(function(){
    var conversationrate= $("#conversationRate").val();
    var tk=$("#cardAmount").val().replace(/,/g , '');
    var usd=$("#initLimit").val().replace(/,/g , '');
   // var tot= (Number(tk) + (Number(usd)*Number(83.74))).toFixed(2);
    var tot= (Number(tk) + (Number(usd)*Number(conversationrate))).toFixed(2);
    tot = tot.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    $("#totalLimit").val(tot);
});
$("#conversationRate").keyup(function(){
    var conversationrate= $("#conversationRate").val();
    var tk=$("#cardAmount").val().replace(/,/g , '');
    var usd=$("#initLimit").val().replace(/,/g , '');
    // var tot= (Number(tk) + (Number(usd)*Number(83.74))).toFixed(2);
    var tot= (Number(tk) + (Number(usd)*Number(conversationrate))).toFixed(2);
    tot = tot.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    $("#totalLimit").val(tot);
});




$("#card-init-submit").click(function () {
    var data=$("#cardTypeIdSelected").find(":selected").data();
    var min= data.min_val;
    var max= data.max_val;
    var currencyType= $("#currencyType option:selected").val();
    min = parseFloat(min);
    max = parseFloat(max);
    if(currencyType=="local"){
        var val=$("#totalLimit").val();
        val = parseFloat(val);
        if(val>max || val<min){
            alert("Total Limit must be between"+min+" and "+max);
            $("#totalLimit").focus();
            return false
        }
    }
    if(currencyType == "Multicurrency"){
        var local=$("#cardAmount").val();
        var usd=$("#initLimit").val();
        var maxUSD=data.max_usd_val;
        var minUSD=data.min_usd_val;
        local=parseFloat(local);
        usd=parseFloat(usd);
        maxUSD = parseFloat(maxUSD);
        minUSD = parseFloat(minUSD);
        if(local>max || local<min){

            alert("Local Limit must be between"+min+" and "+max);
            $("#cardAmount").focus();
            return false
        }
        if(usd>maxUSD || usd<minUSD){
            alert("USD Limit must be between"+minUSD+" and "+maxUSD);
            $("#initLimit").focus();
            return false
        }
    }

})


$("#cardTypeIdSelected").change(function () {
    var data = $(this).find(":selected").data();
    var min= data.min_val;
    min = parseFloat(min);
    var max= data.max_val;
    max = parseFloat(max);
    var minUSD=data.min_usd_val;
    var maxUSD=data.max_usd_val;
    if(minUSD==null){
        $("#show_max_min").html(min + "-" + max)
    }
    else{
        minUSD=parseFloat(minUSD);
        maxUSD=parseFloat(maxUSD);
        $("#show_max_min").html("TK:"+min + "-" + max+"\n$:"+minUSD+"-"+maxUSD)
    }
})


$("#natrPropId").change(function () {
	var str=$("#natrPropId option:selected").text();
    var nopFlag=0;
    var str=$("#natrPropId option:selected").text();
    for(var i=0;i<str.length;i++){
        if(str.substr(i,11)=="Enhancement"||str.substr(i,9)=="Rearrange"){
            nopFlag=1;
            break;
        }
    }
    if(nopFlag==1)
        $(".existinglimitDiv").show();
    else
        $(".existinglimitDiv").hide();
})
//For Payment Status hide and show
var a = $('#genPaymnetStatus').val();
if(a == '"Regular"' || a == '"Irregular"' ){
   var  str = $('#genPaymnetStatus').val();
   if(str=='"Regular"'){
       var test =$("#genPaymnetStatus").val( str.replace('"Regular"', "Regular"));

   }
   else {
       $("#genPaymnetStatus").val( str.replace('"Irregular"', "Irregular"));
   }

}
else{
	$('.PaymentStatusint').hide();
}


if(document.getElementById('totalExistingLimit')) {

//number format with comma for totalLimit
    var totalLimit = document.getElementById('totalLimit');
    totalLimit.addEventListener('keyup', function() {
        var val = this.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }

        this.value = val;
    });


//number format with comma for totalLimit
    var cardamount = document.getElementById('cardAmount');
    cardamount.addEventListener('keyup', function() {
        var val = this.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }
        this.value = val;
    });


//number format with comma for usd proposedlimit
    var initLimit = document.getElementById('initLimit');
    initLimit.addEventListener('keyup', function() {
        var val = this.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }

        this.value = val;
    });

//number format with comma for usd proposedlimit
    var existingLimitBDT = document.getElementById('existingLimitBDT');
    existingLimitBDT.addEventListener('keyup', function() {
        var val = this.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }
        this.value = val;
    });


//number format with comma for usd proposedlimit
    var existingLimitUSD = document.getElementById('existingLimitUSD');
    existingLimitUSD.addEventListener('keyup', function() {
        var val = this.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }
        this.value = val;
    });


//number format with comma for usd proposedlimit
    var totalExistingLimit = document.getElementById('totalExistingLimit');
    totalExistingLimit.addEventListener('keyup', function() {
        var val = this.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }
        this.value = val;
    });
}

function amountFormat(id) {
    var val=0;
    var totalExistingLimit = document.getElementById(id);

    if(totalExistingLimit == null){

    }
    else {
        val = totalExistingLimit.value;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }
        totalExistingLimit.value = val;
    }


/*    if(totalExistingLimit!=null){
    var val = totalExistingLimit.value;
    val = val.replace(/[^0-9\.]/g,'');
    if(val != "") {
        valArr = val.split('.');
        valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
        val = valArr.join('.');
    }
    totalExistingLimit.value = val;}*/

}


$(document).ready(function(){
    amountFormat('local_proposed_limit_bdt');
    amountFormat('usd_proposed');
    amountFormat('total_bdt');
    amountFormat('ex_limit');
    amountFormat('ex_usd');
    amountFormat('total_lmit');

});


function amountFormatpreview(id) {
    var totalExistingLimit = document.getElementById(id);
    if(totalExistingLimit!=null){
        var val = totalExistingLimit.textContent;
        val = val.replace(/[^0-9\.]/g,'');
        if(val != "") {
            valArr = val.split('.');
            valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
            val = valArr.join('.');
        }
        document.getElementById(id).innerText  =val;

    }

}

$(document).ready(function(){
    amountFormatpreview('preview_total_limit');
    amountFormatpreview('preview_usd_proposed_limit');
    amountFormatpreview('preview_local_proposed_limit');
    amountFormatpreview('preview_total_ex_limit');
    amountFormatpreview('preview_ex_limit_bdt');
    amountFormatpreview('preview_ex_usd');

});


/*
//number format with comma for usd proposedlimit
var totalExistingLimit = document.getElementById('local_proposed_limit_bdt');
totalExistingLimit.addEventListener('load', function() {
    var val = this.value;
    val = val.replace(/[^0-9\.]/g,'');
    if(val != "") {
        valArr = val.split('.');
        valArr[0] = (parseInt(valArr[0],10)).toLocaleString();
        val = valArr.join('.');
    }
    this.value = val;
});*/


