function checkpass() {
    //console.log($("#password").val() +" "+$("#pass2").val());

        if (document.getElementById("password").value==document.getElementById("pass2").value){
            document.getElementById("message").style.color="green";
            document.getElementById("message").innerHTML="Matched";
            $('#userSave').removeAttr('disabled');
        }
        else {
            document.getElementById("message").style.color="red";
            document.getElementById("message").innerHTML="Not Matched";
            $('#userSave').attr('disabled','disabled');
        }

}