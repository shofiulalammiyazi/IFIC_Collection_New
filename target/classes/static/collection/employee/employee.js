
$(document).ready(function () {

    if($('#unitVal').length > 0)
    {
        var heystackUnit = $("#unitVal").val();

        console.log(heystackUnit);
        if(heystackUnit.length>0)
        {
            if(strstr(heystackUnit,"Loan"))
            {
                $("#unitLoan").prop("checked",true);
                console.log("1");
            }
            if(strstr(heystackUnit,"Card"))
            {
                $("#unitCard").prop("checked",true);
                console.log("2");
            }
            if(strstr(heystackUnit,"MIS"))
            {
                $("#unitMIS").prop("checked",true);
            }
            if(strstr(heystackUnit,"SAM"))
            {
                $("#unitSAM").prop("checked",true);
            }
        }
    }


    if($('#alVal').length > 0)
    {
        var heystack = $("#alVal").val();
        if(heystack.length>0)
        {
            if(strstr(heystack,"Performance"))
            {
                $("#alPerformance").prop("checked",true);
            }
            if(strstr(heystack,"Disciplinary"))
            {
                $("#alDisciplinary").prop("checked",true);
            }
            if(strstr(heystack,"Others"))
            {
                $("#alOthers").prop("checked",true);
            }
        }
    }

})