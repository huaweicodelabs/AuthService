/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var exec = require('cordova/exec');

exports.testIt = function(success, error) {
    exec(success, error, 'AGCCrashPlugin', 'testIt', []);
};

exports.enableCrashCollection = function(enable, success, error) {
    if (typeof enable === 'boolean') {
        exec(success, error, 'AGCCrashPlugin', 'enableCrashCollection', [enable]);
    }
};

exports.setUserId = function(userId, success, error) {
    if (typeof userId === 'string') {
        exec(success, error, 'AGCCrashPlugin', 'setUserId', [userId]);
    }
};

exports.setCustomKey = function(key, value, success, error) {
    if (((typeof value === 'number') || (typeof value === 'string') || (typeof value === 'boolean'))
     && (typeof key === 'string')) {
        var locValue = value.toString();
        exec(success, error, 'AGCCrashPlugin', 'setCustomKey', [key, locValue]);
    }
};

exports.logWithLevel = function(level, message, success, error) {
    if ((typeof level === 'number') && (typeof message === 'string')) {
        exec(success, error, 'AGCCrashPlugin', 'logWithLevel', [level, message]);
    }
};

exports.log = function (message, success, error) {
    if (typeof message === 'string') {
        exec(success, error, 'AGCCrashPlugin', 'log', [message]);
    }
};

exports.recordException = function (summary, stack, success, error) {
    exec(success, error, 'AGCCrashPlugin', 'recordException', [summary, stack]);
};

