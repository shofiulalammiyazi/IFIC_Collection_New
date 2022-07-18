var myInput = document.getElementById("password");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var repeatitiveCharacter = document.getElementById("repeatitiveCharacter");
var length = document.getElementById("length");
var special = document.getElementById("special");


if (myInput) {
    myInput.onfocus = function () {
        document.getElementById("policy_message").style.display = "block";
        document.getElementById("message").innerHTML = "";
        document.getElementById("check_box").style.display = "none";

    }

    myInput.onkeyup = function () {
        let isValidPassword = true;

        // Validate lowercase letters
        var lowerCaseLetters = /[a-z]/g;
        if (myInput.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid");
            letter.classList.add("valid");
        } else {
            letter.classList.remove("valid");
            letter.classList.add("invalid");
            isValidPassword = false;
        }

        // Validate capital letters
        var upperCaseLetters = /[A-Z]/g;
        if (myInput.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid");
            capital.classList.add("valid");
        } else {
            capital.classList.remove("valid");
            capital.classList.add("invalid");
            isValidPassword = false;
        }

        // Validate numbers
        var numbers = /[0-9]/g;
        if (myInput.value.match(numbers)) {
            number.classList.remove("invalid");
            number.classList.add("valid");
        } else {
            number.classList.remove("valid");
            number.classList.add("invalid");
            isValidPassword = false;
        }

        // Validate Repetitive Characters
        var repetitiveChars = /(\w)\1+/;
        if (!myInput.value.match(repetitiveChars)) {
            repeatitiveCharacter.classList.remove("invalid");
            repeatitiveCharacter.classList.add("valid");
        } else {
            repeatitiveCharacter.classList.remove("valid");
            repeatitiveCharacter.classList.add("invalid");
            isValidPassword = false;
        }

        if (loginPolicy.specialCharacterRequired) {
            var specilal = /[!@#$%^&*(),.?":{}|<>]/g;
            if (myInput.value.match(specilal)) {
                special.classList.remove("invalid");
                special.classList.add("valid");
            } else {
                special.classList.remove("valid");
                special.classList.add("invalid");
                isValidPassword = false;
            }
        }

        // Validate length
        if (myInput.value.length >= loginPolicy.paswLength) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
            isValidPassword = false;
        }

        if (!isValidPassword) {
            $('#pass2').attr('disabled', 'disabled');
            return;
        }

        $('#pass2').removeAttr('disabled');
        document.getElementById("policy_message").style.display = "none";
        document.getElementById("check_box").style.display = "block";

    }

}

function myFunction() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }

    var y = document.getElementById("pass2");
    if (y.type === "password") {
        y.type = "text";
    } else {
        y.type = "password";
    }

    setTimeout(function () {
        x.type = "password";
        y.type = "password";
    }, 1000);
}


function checkpass() {

    if (document.getElementById("password").value == document.getElementById("pass2").value) {
        document.getElementById("message").style.color = "green";
        document.getElementById("message").innerHTML = "Password Matched";
        $('#userSave').removeAttr('disabled');
    }
    else {
        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerHTML = "Password doesn't Matched";
        $('#userSave').attr('disabled', 'disabled');
    }
}