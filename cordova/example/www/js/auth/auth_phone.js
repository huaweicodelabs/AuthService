document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    document.getElementById('signIn').addEventListener('click', signIn);
    document.getElementById('createPhoneUser').addEventListener('click', createPhoneUser);
    document.getElementById('requestPhoneVerifyCode').addEventListener('click', requestPhoneVerifyCode);
    document.getElementById('signOut').addEventListener('click',signOut);
}

function Menu() {
    window.location.href='auth_base.html';
}

function signIn() {
    console.log('func signIn.');

    var countryCodeStr = prompt("countryCode");
    if (countryCodeStr == null) {
        alert("countryCode can not be null");
        return;
    }
    var phoneNumberStr = prompt("phoneNumber");
    if (phoneNumberStr == null) {
        alert("phoneNumber can not be null");
        return;
    }
    var passwordStr = prompt("password");
    if (passwordStr == null) {
        alert("password can not be null");
        return;
    }

    var phoneCredential = {
        provider: 11,
        countryCode: countryCodeStr,
        phoneNumber: phoneNumberStr,
        password: passwordStr
    }
    AGCAuthPlugin.signIn(phoneCredential).then(success).catch(error);

    function success() {
        console.log('success');
    }

    function error(result) {
        console.log('error:' + result);
    }
}

function createPhoneUser() {
    console.log('func createPhoneUser.');

    var countryCodeStr = prompt("countryCode");
    if (countryCodeStr == null) {
        alert("countryCode can not be null");
        return;
    }
    var phoneNumberStr = prompt("phoneNumber");
    if (phoneNumberStr == null) {
        alert("phoneNumber can not be null");
        return;
    }
    var passwordStr = prompt("password");

    var verifyCodeStr = prompt("verifyCode");
    if (verifyCodeStr == null) {
        alert("verifyCode can not be null");
        return;
    }

    var phoneUser = {
        countryCode: countryCodeStr,
        phoneNumber: phoneNumberStr,
        password: passwordStr,
        verifyCode: verifyCodeStr
    }

    AGCAuthPlugin.createPhoneUser(phoneUser).then(success).catch(error);

    function success() {
        console.log('success');
    }

    function error(result) {
        console.log('error:' + result);
    }
}


function requestPhoneVerifyCode() {
    console.log('func requestPhoneVerifyCode.');

    var countryCodeStr = prompt("countryCode");
    if (countryCodeStr == null) {
        alert("countryCode can not be null");
        return;
    }
    var phoneNumberStr = prompt("phoneNumber");
    if (phoneNumberStr == null) {
        alert("phoneNumber can not be null");
        return;
    }

//    var con = confirm("Is Login/Register");
//    var actionNum;
//    if (con == true) {
//        actionNum = 1001;
//    } else {
//        actionNum = 1002;
//    }

    var settings = {
        action: 1001,
        sendInterval: 100
    }

    AGCAuthPlugin.requestPhoneVerifyCode(countryCodeStr, phoneNumberStr, settings).then(success).catch(error);

    function success(result) {
        console.log('shortestInterval:' + result.shortestInterval);
        console.log('validityPeriod:' + result.validityPeriod);
    }

    function error(result) {
        console.log('error:' + result);
    }
}

function signOut() {
    
    console.log('func signOut.');

    AGCAuthPlugin.signOut().then(success).catch(error);
    function success() {
        console.log('success');
    }

    function error(result) {
        console.log('error:' + result);
    }
}
