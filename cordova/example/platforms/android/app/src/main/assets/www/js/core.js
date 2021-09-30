document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    document.getElementById('setApiKey').addEventListener('click', setApiKey);
    document.getElementById('setClientId').addEventListener('click', setClientId);
    document.getElementById('setClientSecret').addEventListener('click', setClientSecret);
}

function setApiKey() {
    console.log('func setApiKey.');

    var apiKey = "CgB6e3x9TNfncELMysIkj/F+UoyO8LoqZRMll5+KTswvOTFSjdpeBKbKflFibKitGkIjkVjopZjD05favxPbELNp";
    AGCCorePlugin.setApiKey(apiKey).then(success).catch(error);

    function success() {
        console.log('success');
    }

    function error(result) {
        console.log('error:' + result);
    }
}

function setClientId() {
    console.log('func setClientId.');

    var clientId = "432298528715310272";
    AGCCorePlugin.setClientId(clientId).then(success).catch(error);

    function success() {
        console.log('success');
    }

    function error(result) {
        console.log('error:' + result);
    }
}

function setClientSecret() {
    console.log('func setClientSecret.');

    var clientSecret = "AA3B66CECC2966ED4657BABB51102C14A15AD7B351822EFA616E798E96796F36";
    AGCCorePlugin.setClientSecret(clientSecret).then(success).catch(error);

    function success() {
        console.log('success');
    }

    function error(result) {
        console.log('error:' + result);
    }
}
