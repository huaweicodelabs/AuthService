var btnTest = document.getElementById("testCrash");
var btnEn = document.getElementById("crashCollectEnable");
var btnDis = document.getElementById("crashCollectDisable");
var btnRecord = document.getElementById("recordException");

document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    btnEn.addEventListener("click", crashCollectEnable);
    btnDis.addEventListener("click", crashCollectDisable);
    btnTest.addEventListener("click", testCrash);
    btnRecord.addEventListener("click", recordException);
}

function recordException() {
    createJaveScriptException1();
}

function createJaveScriptException1() {
    createJaveScriptException2();
}

function createJaveScriptException2() {
    createJaveScriptException3();
}

function createJaveScriptException3() {
    try {
         throw new Error("js crash");
    } catch (e) {
        AGCCrashPlugin.recordException(e.message, e.stack);
    }
}

function crashCollectEnable() {
    AGCCrashPlugin.enableCrashCollection(true);
}

function crashCollectDisable() {
    AGCCrashPlugin.enableCrashCollection(false);
}

function testCrash() {
    AGCCrashPlugin.setUserId("cordova user");

    AGCCrashPlugin.setCustomKey("key1", "value1");

    AGCCrashPlugin.setCustomKey("key2", 3);

    AGCCrashPlugin.setCustomKey("key3", true);

    AGCCrashPlugin.log("just test log");

    AGCCrashPlugin.logWithLevel(3, "just test logWithLevel");

    AGCCrashPlugin.testIt();

    function success() {
        //调用成功的处理
        //console.log('success');
    }

    function error() {
         //调用失败的处理
         //console.log('error');
    }
}
