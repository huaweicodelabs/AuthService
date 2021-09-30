cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [
    {
      "id": "cordova-plugin-agc-crash.crash",
      "file": "plugins/cordova-plugin-agc-crash/www/AGCCrashPlugin.js",
      "pluginId": "cordova-plugin-agc-crash",
      "clobbers": [
        "AGCCrashPlugin"
      ]
    },
    {
      "id": "cordova-plugin-agc-auth.AGCAuthPlugin",
      "file": "plugins/cordova-plugin-agc-auth/www/AGCAuthPlugin.js",
      "pluginId": "cordova-plugin-agc-auth",
      "clobbers": [
        "AGCAuthPlugin"
      ]
    }
  ];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.4",
    "cordova-plugin-agc-crash": "1.1.0-beta",
    "cordova-plugin-agc-auth": "1.1.0-beta"
  };
});