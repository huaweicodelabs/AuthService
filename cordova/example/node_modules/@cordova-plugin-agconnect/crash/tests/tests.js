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

exports.defineAutoTests = function () {
    describe('AGCCrashPlugin (cordova)', function () {
        it('AGCCrashPlugin should exist', function () {
            expect(AGCCrashPlugin).toBeDefined();
        });

        it('AGCCrashPlugin.testIt should exist', function () {
            expect(AGCCrashPlugin.testIt).toBeDefined();
        });

        it('AGCCrashPlugin.enableCrashCollection should exist', function () {
            expect(AGCCrashPlugin.enableCrashCollection).toBeDefined();
        });

        it('AGCCrashPlugin.setUserId should exist', function () {
            expect(AGCCrashPlugin.setUserId).toBeDefined();
        });

        it('AGCCrashPlugin.setCustomKey should exist', function () {
            expect(AGCCrashPlugin.setCustomKey).toBeDefined();
        });

        it('AGCCrashPlugin.logWithLevel should exist', function () {
            expect(AGCCrashPlugin.logWithLevel).toBeDefined();
        });

        it('AGCCrashPlugin.log should exist', function () {
            expect(AGCCrashPlugin.log).toBeDefined();
        });
    });
};

exports.defineManualTests = function () {

};
