var btnCrash = document.getElementById("crash");
var btnConfig = document.getElementById("config");
var btnAuth = document.getElementById("auth");
var btnCore = document.getElementById("core");

document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    btnCrash.addEventListener("click", startCrash);
    btnConfig.addEventListener("click", startConfig);
    btnAuth.addEventListener("click", startAuth);
    btnCore.addEventListener("click", startCore);
}

function startCrash() {
    window.location.href='crash.html';
}

function startConfig() {
    window.location.href='remoteconfig.html';
}

function startAuth() {
    window.location.href='auth_activity/auth_base.html';
}

function startCore() {
    window.location.href='core.html';
}

