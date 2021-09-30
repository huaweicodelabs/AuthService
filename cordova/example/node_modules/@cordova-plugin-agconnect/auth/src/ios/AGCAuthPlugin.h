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

#import <Cordova/CDV.h>

@interface AGCAuthPlugin : CDVPlugin
- (void)signIn:(CDVInvokedUrlCommand*)command;
- (void)signInAnonymously:(CDVInvokedUrlCommand*)command;
- (void)deleteUser:(CDVInvokedUrlCommand*)command;
- (void)signOut:(CDVInvokedUrlCommand*)command;
- (void)getCurrentUser:(CDVInvokedUrlCommand*)command;
- (void)createEmailUser:(CDVInvokedUrlCommand*)command;
- (void)createPhoneUser:(CDVInvokedUrlCommand*)command;
- (void)resetPasswordWithEmail:(CDVInvokedUrlCommand*)command;
- (void)resetPasswordWithPhone:(CDVInvokedUrlCommand*)command;
- (void)link:(CDVInvokedUrlCommand*)command;
- (void)unlink:(CDVInvokedUrlCommand*)command;
- (void)updateProfile:(CDVInvokedUrlCommand*)command;
- (void)updateEmail:(CDVInvokedUrlCommand*)command;
- (void)updatePhone:(CDVInvokedUrlCommand*)command;
- (void)updatePassword:(CDVInvokedUrlCommand*)command;
- (void)getUserExtra:(CDVInvokedUrlCommand*)command;
- (void)requestPhoneVerifyCode:(CDVInvokedUrlCommand*)command;
- (void)requestEmailVerifyCode:(CDVInvokedUrlCommand*)command;
- (void)addTokenListener:(CDVInvokedUrlCommand*)command;
- (void)removeTokenListener:(CDVInvokedUrlCommand*)command;
- (void)getToken:(CDVInvokedUrlCommand*)command;
@end
