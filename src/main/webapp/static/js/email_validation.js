import {dataHandler} from "./dataHandler.js";

function init() {
    sendDataIfAllValidated();

}

function validateDataAndSendData () {
    'use strict'

    window.addEventListener('load', function () {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        let forms = document.getElementsByClassName('needs-validation')

        // Loop over them and prevent submission
        Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
    }, false)
}

function sendDataIfAllValidated () {
    let submitButton = document.querySelector('.submit-button');
    submitButton.addEventListener("click", (event) => {
        let forms = document.getElementsByClassName('needs-validation');
        let counter = 0;
        for (const form of forms) {
            if (form.checkValidity() === false) {
                event.preventDefault()
                event.stopPropagation()
            } else {
                counter++;
            }
            form.classList.add('was-validated')
        }
        if (counter === forms.length) {
            console.log("heeeeeeeeeeeeeeeeeeeeeeeeey") ;
        }
    })
}

function sendData() {
    let forms = document.getElementsByClassName('needs-validation');
    let counter = 0;
    for (const form of forms) {
    }
}


init();


// function ValidateEmail(inputText) {
//     let mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
//     if(inputText.value.match(mailformat)) {
//         alert("Valid email address!");
//         document.emailinput.focus();
//         return true;
//     } else {
//         alert("You have entered an invalid email address!");
//         document.emailinput.focus();
//         return false;
//     }
// }
//
//
// function init() {
//     let emailinput = document.getElementsByName("#emailinput").value;
//     let verfied_email = emailinput.addEventListener("focusout", ValidateEmail(emailinput))
//     if (!verfied_email) {
//         return ValidateEmail(email)
//     }
//     return verfied_email;
// }
//
//
//
// *************
// function emailIsValid (email) {
//     return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
// }