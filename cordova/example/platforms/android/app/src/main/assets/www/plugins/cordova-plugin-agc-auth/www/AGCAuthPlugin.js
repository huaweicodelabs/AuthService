cordova.define("cordova-plugin-agc-auth.AGCAuthPlugin", function(require, exports, module) {
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

class AGCAuthPlugin {
    asyncExecute(action, param) {
        return new Promise((resolve, reject) => {
            exec(resolve, reject, 'AGCAuthPlugin', action, (param === undefined || param === null) ? [] : param);
        });
    }

    addTokenListener(callback) {
        exec(callback, null, 'AGCAuthPlugin', 'addTokenListener', []);
    }

    removeTokenListener() {
        exec(null, null, 'AGCAuthPlugin', 'removeTokenListener', []);
    }

    signIn(credential) {
        return this.asyncExecute('signIn', [credential]);
    }

    signInAnonymously() {
        return this.asyncExecute('signInAnonymously', null);
    }

    deleteUser() {
        return this.asyncExecute('deleteUser', null);
    }

    signOut() {
        return this.asyncExecute('signOut', null);
    }

    getCurrentUser() {
        return this.asyncExecute('getCurrentUser', null);
    }

    createEmailUser(emailUser) {
        return this.asyncExecute('createEmailUser', [emailUser]);
    }

    createPhoneUser(phoneUser) {
        return this.asyncExecute('createPhoneUser', [phoneUser]);
    }

    resetPasswordWithEmail(email, newPassword, verifyCode) {
        return this.asyncExecute('resetPasswordWithEmail', [email, newPassword, verifyCode]);
    }

    resetPasswordWithPhone(countryCode, phoneNumber, newPassword, verifyCode) {
        return this.asyncExecute('resetPasswordWithPhone', [countryCode, phoneNumber, newPassword, verifyCode]);
    }

    requestPhoneVerifyCode(countryCode, phoneNumber, settings) {
        return this.asyncExecute('requestPhoneVerifyCode', [countryCode, phoneNumber, settings]);
    }

    requestEmailVerifyCode(email, settings) {
        return this.asyncExecute('requestEmailVerifyCode', [email, settings]);
    }

    link(credential) {
        return this.asyncExecute('link', [credential]);
    }

    unlink(provider) {
        return this.asyncExecute('unlink', [provider]);
    }

    updateProfile(userProfile) {
        return this.asyncExecute('updateProfile', [userProfile]);
    }

    updateEmail(newEmail, newVerifyCode, locale) {
        if (typeof locale === 'string') {
            return this.asyncExecute('updateEmail', [newEmail, newVerifyCode, locale]);

        } else {
            return this.asyncExecute('updateEmail', [newEmail, newVerifyCode]);
        }
    }

    updatePhone(countryCode, phoneNumber, newVerifyCode, locale) {
        if (typeof locale === 'string') {
            return this.asyncExecute('updatePhone', [countryCode, phoneNumber, newVerifyCode, locale]);
        } else {
            return this.asyncExecute('updatePhone', [countryCode, phoneNumber, newVerifyCode]);
        }
    }

    updatePassword(newPassword, verifyCode, provider) {
        return this.asyncExecute('updatePassword', [newPassword, verifyCode, provider]);
    }

    getUserExtra() {
        return this.asyncExecute('getUserExtra', null);
    }

    getToken(forceRefresh) {
        return this.asyncExecute('getToken', [forceRefresh]);
    }
}

module.exports = new AGCAuthPlugin();


});
