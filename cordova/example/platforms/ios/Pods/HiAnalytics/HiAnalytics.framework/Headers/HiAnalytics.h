//
//  HiAnalytics.h
//  HiAnalytics
//
//  Created by cbg_bigdata on 2019/12/27.
//  Copyright © 2019 cbg_bigdata. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HAEventType.h"
#import "HAParamType.h"
#import "HAReportPolicy.h"

@interface HiAnalytics : NSObject

/**
 * Initialize configuration.
 */
+ (void)config;

/**
 * Configure report policies.
 */
+ (void)setReportPolicies:(nullable NSArray<HAReportPolicy *> *)policies;

/**
 * Report custom events.
 *
 * @param eventId Event ID, a string that contains a maximum of 256 characters excluding spaces and invisible characters. The value cannot be empty or set to the ID of an automatically collected event.
 * @params params Information carried in the event. The key value cannot contain spaces or invisible characters.
 */
+ (void)onEvent:(nonnull NSString *)eventId setParams:(nonnull NSDictionary<NSString *, id> *)params;

/**
 * User attribute values remain unchanged throughout the app's lifecycle and session. A maximum of 25 user attribute names are supported. If an attribute name is duplicate with an existing one, the attribute names needs to be changed.
 *
 * @param name User attribute name, a string that contains a maximum of 256 characters excluding spaces and invisible characters. The value cannot be empty.
 * @param value Attribute value, a string that contains a maximum of 256 characters. 
 */
+ (void)setUserProfile:(nonnull NSString *)name setValue:(nullable NSString *)value;

/**
 * Enable AB Testing. Predefined or custom user attributes are supported.
 *
 * @param preDefined Indicates whether to obtain predefined user attributes.
 * @return Predefined or custom user attributes.
 */
+ (nullable NSDictionary<NSString *, id> *)userProfiles:(BOOL)preDefined;

/**
 * Enable event collection. No data will be collected when this function is disabled.
 *
 * @param enabled Indicates whether to enable event collection. YES: enabled (default); NO: disabled.
 */
+ (void)setAnalyticsEnabled:(BOOL)enabled;

/**
 * Obtain the app instance ID from AppGallery Connect.
 *
 * @return AAID
 */
+ (nonnull NSString *)AAID;

/**
 * Set a user ID.
 * When the setUserId API is called, if the old userId is not empty and is different from the new userId, a new session is generated. If you do not want to use setUserId to identify a user (for example, when a user signs out), set userId to NULL.
 * id: App ID that identifies a subscriber. Analytics Kit uses this ID to associate user data. The use of userId must comply with related privacy regulations. Please declare how it is used in the privacy statement of your app.
 *
 * @param userId User ID, a string that contains a maximum of 256 characters. The value cannot be empty.
 */
+ (void)setUserId:(nullable NSString *)userId;

/**
 * Set the session timeout interval. The app is running in the foreground. When the interval between two adjacent events exceeds the specified timeout interval, a new session is generated. The default value is 30 minutes.
 *
 * @param milliseconds Session timeout interval, in milliseconds.
 */
+ (void)setSessionDuration:(NSTimeInterval)milliseconds;

/**
 * Delete all collected data in the local cache, including the cached data that fails to be sent.
 */
+ (void)clearCachedData;

@end
