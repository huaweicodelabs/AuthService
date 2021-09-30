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

#import "AGCCrashPlugin.h"
#import <Cordova/CDV.h>
#import <AGConnectCrash/AGConnectCrash.h>

@implementation AGCCrashPlugin

- (void)testIt:(CDVInvokedUrlCommand*)command {
    NSLog(@"func testIt");
    dispatch_async(dispatch_get_main_queue(),^{
         [[AGCCrash sharedInstance] testIt];
    });
}

- (void)enableCrashCollection:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        BOOL enable = [[command.arguments objectAtIndex:0] boolValue];
        NSLog(@"func enableCrashCollection, enable=%d", enable);
        [[AGCCrash sharedInstance] enableCrashCollection:enable];

        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)setUserId:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *userId = [command.arguments objectAtIndex:0];
        NSLog(@"func setUserId, userId=%@", userId);
        [[AGCCrash sharedInstance] setUserId:userId];

        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)setCustomKey:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *key = [command.arguments objectAtIndex:0];
        NSString *value = [command.arguments objectAtIndex:1];
        NSLog(@"func setCustomKey key=%@, value=%@", key, value);
        [[AGCCrash sharedInstance] setCustomValue:value forKey:key];

        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)logWithLevel:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        int level = [[command.arguments objectAtIndex:0] intValue];
        NSString *message = [command.arguments objectAtIndex:1];
        NSLog(@"func logWithLevel, level=%d, message=%@", level,message);
        [[AGCCrash sharedInstance] logWithLevel:level message:message];

        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)log:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *message = [command.arguments objectAtIndex:0];
        NSLog(@"func log, message=%@", message);
        [[AGCCrash sharedInstance] log:message];

        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)recordException:(CDVInvokedUrlCommand*)command {
        NSString *summary = [command.arguments objectAtIndex:0];
        NSString *stack = [command.arguments objectAtIndex:1];

        AGCExceptionModel *agcExceptionModule = [[AGCExceptionModel alloc] initWithName:summary reason:summary stackTrace:stack];
        [agcExceptionModule setValue:@"Cordova" forKey:@"type"];
        [[AGCCrash sharedInstance] recordExceptionModel:agcExceptionModule];

        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
