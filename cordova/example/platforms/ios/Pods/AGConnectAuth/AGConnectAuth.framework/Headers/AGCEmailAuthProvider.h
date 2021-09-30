//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>
#import <HMFoundation/HMFoundation.h>
#import "AGCAuthCredential.h"
#import "AGCVerifyCodeSettings.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * 邮箱凭证提供者
 */
@interface AGCEmailAuthProvider : NSObject

/**
 * 通过邮箱和密码获取凭证
 * 如果创建用户时设置了密码，可以采用邮箱和密码登录
 *
 * @param email 邮箱
 * @param password 密码
 * @return Email凭证
 */
+ (AGCAuthCredential *)credentialWithEmail:(NSString *)email password:(NSString *)password;

/**
 * 通过邮箱和验证码获取凭证
 * 如果创建账户的时候没有设置过密码，则只能通过此接口进行登录，此时的password字段不赋值
 *
 * @param email 邮箱
 * @param password 密码
 * @param verifyCode 验证码 Link用户的时候必填
 * @return Email凭证
 */
+ (AGCAuthCredential *)credentialWithEmail:(NSString *)email
                                  password:(nullable NSString *)password
                                verifyCode:(NSString *)verifyCode;

/**
 * 邮箱申请验证码
 *
 * @param email 邮箱地址
 * @param settings 验证码参数
 * @return 申请验证码异步任务
 */
+ (HMFTask<AGCVerifyCodeResult *> *)requestVerifyCodeWithEmail:(NSString *)email
                                                      settings:(AGCVerifyCodeSettings *)settings;

@end

NS_ASSUME_NONNULL_END
