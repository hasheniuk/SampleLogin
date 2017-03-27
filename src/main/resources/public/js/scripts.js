"use strict";

$(function () {

    $(document).on("keyup", ".not-valid input[name='email']", function () {
        validateEmail($(this));
    });

    $(document).on("keyup", ".not-valid input[name='password']", function () {
        validatePassword($(this));
    });

    $(document).on("keyup", ".not-valid input[name='confirmPassword']", function () {
        validateConfirmation($("input[name='password']:eq(1)"), $(this));
    });
});

function toggleSections() {
    toggleControls(false);
    $('#signin-section, #signup-section').fadeToggle("slow", "swing", function() {
        toggleControls(true);
    });
}

function toggleControls(state) {
    var fields = $("input");
    fields.val("");
    $("button").add(fields).prop("disabled", !state);
    $("section:visible input").first().focus();
    $("a").toggleClass("disabled", !state);
    $(".error").hide();
    fields.parent().removeClass("not-valid");
}

function validateSigninForm(thisForm) {
    var validEmail = validateEmail($("input[name='email']", $(thisForm)));
    var validPassword = validatePassword($("input[name='password']", $(thisForm)));
    return validEmail && validPassword;
}

function validateSignupForm(thisForm) {
    var validEmail = validateEmail($("input[name='email']", $(thisForm)));
    var passwordField = $("input[name='password']", $(thisForm));
    var validPassword = validatePassword(passwordField);
    var confirmPasswordField = $("input[name='confirmPassword']", $(thisForm));
    var validConfirmPassword = validateConfirmation(passwordField, confirmPasswordField);
    return validEmail && validPassword && validConfirmPassword;
}

function validateEmail(emailField) {
    var email = emailField.val();
    var re = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var errorMessage = "";
    if (!email || email === "") {
        errorMessage = "Email required";
    } else if (!re.test(email)) {
        errorMessage = "Incorrect email";
    }
    var valid = errorMessage.length === 0;
    toggleError(emailField, errorMessage, valid);
    return valid;
}

function validatePassword(passwordField) {
    var password = passwordField.val();
    var re = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
    var errorMessage = "";
    if (!password || password === "") {
        errorMessage = "Password required";
    } else if (!re.test(password)) {
        errorMessage = "Password must contains minimum 8 characters " +
            "at least 1 letter, 1 number and 1 special character";
    }
    var valid = errorMessage.length === 0;
    toggleError(passwordField, errorMessage, valid);
    return valid;
}

function validateConfirmation(passwordField, confirmPasswordField) {
    var password = passwordField.val();
    var confirmPassword = confirmPasswordField.val();
    var errorMessage = "";
    if (password !== confirmPassword) {
        errorMessage = "Password not confirmed";
    }
    var valid = errorMessage.length === 0;
    toggleError(confirmPasswordField, errorMessage, valid);
    return valid;
}

function toggleError(field, message, valid) {
    var error = field.parent().next(".error");
    error.text(message);
    valid ? error.hide() : error.show();
    field.parent().toggleClass("not-valid", !valid);
}

function connectTwitter() {
    $("<form action='/connect/twitter' method='post'></form>").submit();
}

function connectLinkedIn() {
    $("<form action='/connect/linkedin' method='post'></form>").submit();
}

function connectGoogle() {
    $(  "<form action='/connect/google' method='post'>" +
            "<input name='scope' value= 'email https://www.googleapis.com/auth/plus.login'>" +
        "</form>").submit();
}