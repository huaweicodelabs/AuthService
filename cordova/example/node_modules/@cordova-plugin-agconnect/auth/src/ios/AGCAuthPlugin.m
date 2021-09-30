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

#import "AGCAuthPlugin.h"
#import <Cordova/CDV.h>
#import <AGConnectAuth/AGConnectAuth.h>

@implementation AGCAuthPlugin

- (void)signIn:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        AGCAuthCredential *credential = [self getCredential:[command.arguments objectAtIndex:0]];
        if (credential) {
            [[[[AGCAuth getInstance] signIn:credential] addOnSuccessCallback:^(AGCSignInResult * _Nullable res) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"This provider is not support."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)signInAnonymously:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [[[[AGCAuth getInstance] signInAnonymously] addOnSuccessCallback:^(AGCSignInResult * _Nullable res) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (void)deleteUser:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [[AGCAuth getInstance] deleteUser];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)signOut:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        [[AGCAuth getInstance] signOut];
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)getCurrentUser:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        AGCUser *user = [[AGCAuth getInstance] currentUser];
        CDVPluginResult *pluginResult = nil;
        if (user) {
            NSDictionary *result = @{
                @"isAnonymous": @(user.isAnonymous),
                @"uid":user.uid ?: [NSNull null],
                @"email":user.email ?: [NSNull null],
                @"phone":user.phone ?: [NSNull null],
                @"displayName":user.displayName ?: [NSNull null],
                @"photoUrl":user.photoUrl ?: [NSNull null],
                @"providerId":@(user.providerId),
                @"providerInfo":user.providerInfo ?: [NSNull null],
                @"emailVerified":@(user.emailVerified),
                @"passwordSet":@(user.passwordSetted)
            };
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)createEmailUser:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSDictionary<NSString*, id> *params = [command.arguments objectAtIndex:0];
        NSString *email = params[@"email"] == [NSNull null] ? nil : params[@"email"];
        NSString *password = params[@"password"] == [NSNull null] ? nil : params[@"password"];
        NSString *verifyCode = params[@"verifyCode"] == [NSNull null] ? nil : params[@"verifyCode"];

        [[[[AGCAuth getInstance] createUserWithEmail:email password:password verifyCode:verifyCode] addOnSuccessCallback:^(AGCSignInResult * _Nullable res) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (void)createPhoneUser:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSDictionary<NSString*, id> *params = [command.arguments objectAtIndex:0];
        NSString *countryCode = params[@"countryCode"] == [NSNull null] ? nil : params[@"countryCode"];
        NSString *phoneNumber = params[@"phoneNumber"] == [NSNull null] ? nil : params[@"phoneNumber"];
        NSString *password = params[@"password"] == [NSNull null] ? nil : params[@"password"];
        NSString *verifyCode = params[@"verifyCode"] == [NSNull null] ? nil : params[@"verifyCode"];

        [[[[AGCAuth getInstance] createUserWithCountryCode:countryCode phoneNumber:phoneNumber password:password verifyCode:verifyCode] addOnSuccessCallback:^(AGCSignInResult * _Nullable res) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (void)resetPasswordWithEmail:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *email = [command.arguments objectAtIndex:0];
        NSString *password = [command.arguments objectAtIndex:1];
        NSString *verifyCode = [command.arguments objectAtIndex:2];
        [[[[AGCAuth getInstance] resetPasswordWithEmail:email newPassword:password verifyCode:verifyCode] addOnSuccessCallback:^(id  _Nullable res) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (void)resetPasswordWithPhone:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *countryCode = [command.arguments objectAtIndex:0];
        NSString *phoneNumber = [command.arguments objectAtIndex:1];
        NSString *password = [command.arguments objectAtIndex:2];
        NSString *verifyCode = [command.arguments objectAtIndex:3];
        [[[[AGCAuth getInstance] resetPasswordWithCountryCode:countryCode phoneNumber:phoneNumber newPassword:password verifyCode:verifyCode] addOnSuccessCallback:^(id  _Nullable res) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (void)link:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        AGCAuthCredential *credential = [self getCredential:[command.arguments objectAtIndex:0]];
        if ([AGCAuth.getInstance currentUser]) {
            if (credential) {
                [[[AGCAuth.getInstance.currentUser link:credential] addOnSuccessCallback:^(AGCSignInResult * _Nullable res) {
                    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
                }] addOnFailureCallback:^(NSError * _Nonnull error) {
                    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
                }];
            } else {
                CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"This provider is not support."];
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            }
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)unlink:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSInteger provider = [[command.arguments objectAtIndex:0] integerValue];
        if ([AGCAuth.getInstance currentUser]) {
            [[[AGCAuth.getInstance.currentUser unlink:provider] addOnSuccessCallback:^(AGCSignInResult * _Nullable res) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
               [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)updateProfile:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSDictionary<NSString*, id> *params = [command.arguments objectAtIndex:0];
        AGCProfileRequest *request = [AGCProfileRequest new];
        request.displayName = params[@"displayName"] == [NSNull null] ? nil : params[@"displayName"];
        request.photoUrl = params[@"photoUrl"] == [NSNull null] ? nil : params[@"photoUrl"];
        if ([AGCAuth.getInstance currentUser]) {
            [[[AGCAuth.getInstance.currentUser updateProfile:request] addOnSuccessCallback:^(id  _Nullable res) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)updateEmail:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *email = [command.arguments objectAtIndex:0];
        NSString *verifyCode = [command.arguments objectAtIndex:1];
        NSString *lang = [command.arguments objectAtIndex:2];
        NSLocale *locale = [self getLocale:lang];
        if ([AGCAuth.getInstance currentUser]) {
            [[[AGCAuth.getInstance.currentUser updateEmail:email verifyCode:verifyCode locale:locale] addOnSuccessCallback:^(id  _Nullable res) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)updatePhone:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *countryCode = [command.arguments objectAtIndex:0];
        NSString *phoneNumber = [command.arguments objectAtIndex:1];
        NSString *verifyCode = [command.arguments objectAtIndex:2];
        NSString *lang = [command.arguments objectAtIndex:3];
        NSLocale *locale = [self getLocale:lang];
        if ([AGCAuth.getInstance currentUser]) {
            [[[AGCAuth.getInstance.currentUser updatePhoneWithCountryCode:countryCode phoneNumber:phoneNumber verifyCode:verifyCode locale:locale] addOnSuccessCallback:^(id  _Nullable res) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)updatePassword:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *password = [command.arguments objectAtIndex:0 ];
        NSString *verifyCode = [command.arguments objectAtIndex:1];
        NSInteger provider= [[command.arguments objectAtIndex:2] integerValue];
        if ([AGCAuth.getInstance currentUser]) {
            [[[AGCAuth.getInstance.currentUser updatePassword:password verifyCode:verifyCode provider:provider] addOnSuccessCallback:^(id  _Nullable res) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)getUserExtra:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        if ([AGCAuth.getInstance currentUser]) {
            [[[AGCAuth.getInstance.currentUser getUserExtra] addOnSuccessCallback:^(AGCUserExtra * _Nullable res) {
                NSDictionary *userExtra = @{
                    @"createTime":res.createTime ?: [NSNull null],
                    @"lastSignInTime":res.lastSignInTime ?: [NSNull null]};
                CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:userExtra];
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

- (void)requestPhoneVerifyCode:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *countryCode = [command.arguments objectAtIndex:0];
        NSString *phoneNumber = [command.arguments objectAtIndex:1];
        NSDictionary<NSString*, id> *params = [command.arguments objectAtIndex:2];
        AGCVerifyCodeSettings *settings = [self getSettings:params];
        if (settings.action != AGCVerifyCodeActionRegisterLogin && settings.action != AGCVerifyCodeActionResetPassword) {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"VerifyCodeSettings action error"];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            return;
        }

        [[[AGCPhoneAuthProvider requestVerifyCodeWithCountryCode:countryCode phoneNumber:phoneNumber settings:settings] addOnSuccessCallback:^(AGCVerifyCodeResult * _Nullable res) {
            NSDictionary *verifyResult = @{
                @"shortestInterval":res.shortestInterval ?: [NSNull null],
                @"validityPeriod":res.validityPeriod ?: [NSNull null]};
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:verifyResult];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (void)requestEmailVerifyCode:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *email = [command.arguments objectAtIndex:0];
        NSDictionary<NSString*, id> *params = [command.arguments objectAtIndex:1];
        AGCVerifyCodeSettings *settings = [self getSettings:params];
        if (settings.action != AGCVerifyCodeActionRegisterLogin && settings.action != AGCVerifyCodeActionResetPassword) {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"VerifyCodeSettings action error"];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            return;
        }

        [[[AGCEmailAuthProvider requestVerifyCodeWithEmail:email settings:settings] addOnSuccessCallback:^(AGCVerifyCodeResult * _Nullable res) {
            NSDictionary *verifyResult = @{
                @"shortestInterval":res.shortestInterval ?: [NSNull null],
                @"validityPeriod":res.validityPeriod ?: [NSNull null]};
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:verifyResult];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }] addOnFailureCallback:^(NSError * _Nonnull error) {
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
        }];
    }];
}

- (NSLocale *)getLocale:(NSString *)lang {
    if (lang) {
        NSArray *array = [lang componentsSeparatedByString:@"_"];
        if (array && array.count == 2) {
            return [NSLocale localeWithLocaleIdentifier:lang];
        }
    }
    return nil;
}

- (AGCVerifyCodeSettings *)getSettings:(NSDictionary *)arguments {
    if (arguments) {
        NSInteger interval = (arguments[@"sendInterval"] && arguments[@"sendInterval"] != [NSNull null]) ? [arguments[@"sendInterval"] integerValue] : 0;
        NSInteger action = (arguments[@"action"] && arguments[@"action"] != [NSNull null]) ? [arguments[@"action"] integerValue] : AGCVerifyCodeActionRegisterLogin;
        NSString *lang = arguments[@"lang"] == [NSNull null] ? nil : arguments[@"lang"];
        NSLocale *locale = [self getLocale:lang];
        return [[AGCVerifyCodeSettings alloc] initWithAction:action locale:locale sendInterval:interval];
    }
    return nil;
}

- (AGCAuthCredential *)getCredential:(NSDictionary *)arguments {
    if (arguments[@"provider"] && arguments[@"provider"] != [NSNull null]) {
        AGCAuthProviderType type = [arguments[@"provider"] intValue];
        switch (type) {
            case AGCAuthProviderTypePhone:
            {
                NSString *countryCode = arguments[@"countryCode"] == [NSNull null] ? nil : arguments[@"countryCode"];
                NSString *phoneNumber = arguments[@"phoneNumber"] == [NSNull null] ? nil : arguments[@"phoneNumber"];
                NSString *password = arguments[@"password"] == [NSNull null] ? nil : arguments[@"password"];
                NSString *verifyCode = arguments[@"verifyCode"] == [NSNull null] ? nil : arguments[@"verifyCode"];
                return [AGCPhoneAuthProvider credentialWithCountryCode:countryCode phoneNumber:phoneNumber password:password verifyCode:verifyCode];
            }

            case AGCAuthProviderTypeEmail:
            {
                NSString *email = arguments[@"email"] == [NSNull null] ? nil : arguments[@"email"];
                NSString *password = arguments[@"password"] == [NSNull null] ? nil : arguments[@"password"];
                NSString *verifyCode = arguments[@"verifyCode"] == [NSNull null] ? nil : arguments[@"verifyCode"];
                return [AGCEmailAuthProvider credentialWithEmail:email password:password verifyCode:verifyCode];
            }

            case AGCAuthProviderTypeSelfBuild:
            {
                NSString *accessToken = arguments[@"accessToken"] == [NSNull null] ? nil : arguments[@"accessToken"];
                BOOL autoCreateUser = (arguments[@"autoCreateUser"] && arguments[@"autoCreateUser"] != [NSNull null]) ? [arguments[@"autoCreateUser"] boolValue] : true;
                return [AGCSelfBuildAuthProvider credentialWithToken:accessToken autoCreateUser:autoCreateUser];
            }

            default:
                return nil;
        }
    } else {
        return nil;
    }
}

void(^listener)(AGCTokenSnapshot * _Nonnull tokenSnapshot);

- (void)addTokenListener:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        if (!listener) {
            listener  = ^(AGCTokenSnapshot * _Nonnull tokenSnapshot) {
                NSDictionary *result = @{
                    @"state":@(tokenSnapshot.state),
                    @"token": tokenSnapshot.token ?: [NSNull null]};
                CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
                [pluginResult setKeepCallbackAsBool:YES];
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            };
            
           [AGCAuth.getInstance addTokenListener:listener];
        }
    }];
}

- (void)removeTokenListener:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        if (listener) {
            [[AGCAuth getInstance] removeTokenListener:listener];
            listener = NULL;
        }
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
    }];
}

- (void)getToken:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        if ([AGCAuth.getInstance currentUser]) {
            BOOL forceFresh = [[command.arguments objectAtIndex:0] boolValue];
            [[[AGCAuth.getInstance.currentUser getToken:forceFresh] addOnSuccessCallback:^(AGCTokenResult * _Nullable res) {
                NSDictionary *result = @{
                    @"token":res.token ?: [NSNull null],
                    @"expirePeriod":@(res.expirePeriod)};
                CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:result];
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
            }] addOnFailureCallback:^(NSError * _Nonnull error) {
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription] callbackId:command.callbackId];
            }];
        } else {
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"User Null."];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

@end
