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

#import "AppDelegate+AGCCrashPlugin.h"
#import <AGConnectCore/AGConnectCore.h>
#import <HiAnalytics/HiAnalytics.h>
#import <objc/runtime.h>

@implementation AppDelegate (AGCCrashPlugin)

+ (void)load {
    Method original = class_getInstanceMethod(self, @selector(application:didFinishLaunchingWithOptions:));
    Method swizzled = class_getInstanceMethod(self, @selector(application:agcCrashDidFinishLaunchingWithOptions:));
    method_exchangeImplementations(original, swizzled);
}

- (BOOL)application:(UIApplication*)application agcCrashDidFinishLaunchingWithOptions:(NSDictionary*)launchOptions {
    [self application:application agcCrashDidFinishLaunchingWithOptions:launchOptions];
    [AGCInstance startUp];
    [HiAnalytics config];
    return YES;
}

@end
