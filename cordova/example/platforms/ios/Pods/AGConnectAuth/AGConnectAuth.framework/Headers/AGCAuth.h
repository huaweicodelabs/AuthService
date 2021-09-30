//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <AGConnectCore/AGConnectCore.h>
#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"
#import "AGCSignInResult.h"
#import "AGCTokenSnapshot.h"
#import "AGCUser.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * 认证服务入口类
 */
@interface AGCAuth : NSObject

/**
 * 获取当前登录的用户信息，如果未登录则返回null
 */
@property(nonatomic, strong, nullable, readonly) AGCUser *currentUser;

/**
 * 获取AGCAuth的实例
 *
 * @return AGCAuth实例
 */
+ (instancetype)getInstance NS_SWIFT_NAME(instance());

/**
 * 登录接口，通过第三方认证来登录AGConnect平台
 *
 * @param credential 第三方OAuth2认证的凭证，需要通过对应的AuthProvider去创建。
 * @return 登录结果异步任务, 在任务成功后通过<code>getUser</code>获取登录的用户信息。
 */
- (HMFTask<AGCSignInResult *> *)signIn:(AGCAuthCredential *)credential
    NS_SWIFT_NAME(signIn(credential:));

/**
 * 匿名登录
 *
 * @return 登录结果异步任务, 在任务成功后通过<code>getUser</code>获取登录的用户信息。
 */
- (HMFTask<AGCSignInResult *> *)signInAnonymously;

/**
 * 在AGConnect服务器侧删除当前用户信息同时清除缓存信息
 */
- (void)deleteUser;

/**
 * 登出接口
 * 退出登录状态，删除缓存数据
 */
- (void)signOut;

/**
 * 添加token变更监听，可以同时添加多个监听对象，监听回调在UI线程调用
 *
 * @param listener 监听者
 */
- (void)addTokenListener:(void (^)(AGCTokenSnapshot *tokenSnapshot))listener;

/**
 * 移除token变更监听
 *
 * @param listener 监听者
 */
- (void)removeTokenListener:(void (^)(AGCTokenSnapshot *tokenSnapshot))listener;

/**
 * 邮箱创建账户
 *
 * @param email 邮箱
 * @param password 密码
 * @param verifyCode 验证码
 * @return 登录结果异步任务, 在任务成功后通过<code>getUser</code>获取登录的用户信息。
 */
- (HMFTask<AGCSignInResult *> *)createUserWithEmail:(NSString *)email
                                           password:(NSString *_Nullable)password
                                         verifyCode:(NSString *)verifyCode;

/**
 * 手机创建账户
 *
 * @param countryCode 国家码
 * @param phoneNumber 手机号
 * @param password 密码
 * @param verifyCode 验证码
 * @return 登录结果异步任务, 在任务成功后通过<code>getUser</code>获取登录的用户信息。
 */
- (HMFTask<AGCSignInResult *> *)createUserWithCountryCode:(NSString *)countryCode
                                              phoneNumber:(NSString *)phoneNumber
                                                 password:(NSString *_Nullable)password
                                               verifyCode:(NSString *)verifyCode;

/**
 * 利用邮箱重置密码
 *
 * @param email 邮箱
 * @param password 新密码
 * @param verifyCode 邮箱获取的验证码
 * @return 重置结果异步任务, 在任务成功后通过signIn重新登录。
 */
- (HMFTask *)resetPasswordWithEmail:(NSString *)email
                        newPassword:(NSString *)password
                         verifyCode:(NSString *)verifyCode;

/**
 * 利用手机重置密码
 *
 * @param countryCode 国家码
 * @param phoneNumber 手机号
 * @param password 新密码
 * @param verifyCode 验证码
 * @return 重置结果异步任务, 在任务成功后通过signIn重新登录。
 */
- (HMFTask *)resetPasswordWithCountryCode:(NSString *)countryCode
                              phoneNumber:(NSString *)phoneNumber
                              newPassword:(NSString *)password
                               verifyCode:(NSString *)verifyCode;

@end

NS_ASSUME_NONNULL_END
