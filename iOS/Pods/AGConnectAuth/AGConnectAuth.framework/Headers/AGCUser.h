//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>
#import <HMFoundation/HMFoundation.h>
#import "AGCAuthCredential.h"
#import "AGCProfileRequest.h"
#import "AGCSignInResult.h"
#import "AGCUserExtra.h"
#import "AGCTokenResult.h"
@class AGCToken;

NS_ASSUME_NONNULL_BEGIN

/// 用户信息
@interface AGCUser : NSObject

/// 是否是匿名登录用户
@property(nonatomic, readonly) BOOL isAnonymous;

/// 用户id，此id由AGConnect生成
@property(nonatomic, readonly) NSString *uid;

/// 用户Email
@property(nonatomic, readonly, nullable) NSString *email;

/// 用户手机号
@property(nonatomic, readonly, nullable) NSString *phone;

/// 用户名称
@property(nonatomic, readonly, nullable) NSString *displayName;

/// 用户头像
@property(nonatomic, readonly, nullable) NSString *photoUrl;

/// 当前用户的提供者
@property(nonatomic, readonly) AGCAuthProviderType providerId;

/// 全部第三方平台的用户信息
@property(nonatomic, readonly, nullable) NSArray<NSDictionary<NSString *, id> *> *providerInfo;

/// 邮箱验证标记
@property(nonatomic, readonly) NSInteger emailVerified;

/// 密码设置标记
@property(nonatomic, readonly) NSInteger passwordSetted;

/**
 * 当前用户关联新的登录方式
 *
 * @param credential 新的第三方登录凭证
 * @return 登录结果异步任务, 在任务成功后通过<code>getUser</code>获取登录的用户信息。
 */
- (HMFTask<AGCSignInResult *> *)link:(AGCAuthCredential *)credential;

/**
 * 当前用户解除关联的登录方式
 *
 * @param provider 要解除的登录方式，对应的值参考AGConnectAuthCredential
 * @return 登录结果异步任务, 在任务成功后通过<code>getUser</code>获取登录的用户信息。
 */
- (HMFTask<AGCSignInResult *> *)unlink:(AGCAuthProviderType)provider;

/**
 * 更新当前用户的个人信息
 *
 * @param userProfile 个人信息
 * @return 更新结果异步任务
 */
- (HMFTask *)updateProfile:(AGCProfileRequest *)userProfile;

/**
 * 更新当前用户邮箱
 *
 * @param newEmail 新邮箱地址
 * @param newVerifyCode 验证码
 * @return 更新结果异步任务
 */
- (HMFTask *)updateEmail:(NSString *)newEmail verifyCode:(NSString *)newVerifyCode;

/**
 * 更新当前用户邮箱
 *
 * @param newEmail 新邮箱地址
 * @param newVerifyCode 验证码
 * @param locale 发送给原有邮箱通知邮件的语言的locale对象，为nil表示当前系统语言.
 * @return 更新结果异步任务
 */
- (HMFTask *)updateEmail:(NSString *)newEmail
              verifyCode:(NSString *)newVerifyCode
                  locale:(NSLocale *_Nullable)locale;

/**
 * 更新当前用户手机号
 *
 * @param countryCode 国际区号
 * @param phoneNumber 新手机号
 * @param newVerifyCode 验证码
 * @return 更新结果异步任务
 */
- (HMFTask *)updatePhoneWithCountryCode:(NSString *)countryCode
                            phoneNumber:(NSString *)phoneNumber
                             verifyCode:(NSString *)newVerifyCode;

/**
 * 更新当前用户手机号
 *
 * @param countryCode 国际区号
 * @param phoneNumber 新手机号
 * @param newVerifyCode 验证码
 * @param locale 发送给原有手机号通知短信的语言的locale对象，为nil表示当前系统语言.
 * @return 更新结果异步任务
 */
- (HMFTask *)updatePhoneWithCountryCode:(NSString *)countryCode
                            phoneNumber:(NSString *)phoneNumber
                             verifyCode:(NSString *)newVerifyCode
                                 locale:(NSLocale *_Nullable)locale;

/**
 * 更新当前用户的密码
 *
 * @param newPassword 新密码
 * @param verifyCode 验证码
 * @param provider provider类型
 * @return 更新结果异步任务
 */
- (HMFTask *)updatePassword:(NSString *)newPassword
                 verifyCode:(NSString *)verifyCode
                   provider:(NSInteger)provider;

/**
 * 获取当前用户的Extra信息
 *
 * @return 获取结果异步任务
 */
- (HMFTask<AGCUserExtra *> *)getUserExtra;

/**
 * 获取当前用户的Access Token
 *
 * @param isForceRefresh 是否强制刷新
 * @return 获取结果异步任务
 */
- (HMFTask<AGCTokenResult *> *)getToken:(BOOL)isForceRefresh;

@end

NS_ASSUME_NONNULL_END
