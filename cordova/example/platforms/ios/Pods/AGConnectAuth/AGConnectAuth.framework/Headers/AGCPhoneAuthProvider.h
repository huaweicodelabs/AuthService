//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>
#import <HMFoundation/HMFoundation.h>
#import "AGCVerifyCodeSettings.h"
@class AGCAuthCredential;

NS_ASSUME_NONNULL_BEGIN
/**
 * 手机凭证提供者
 */
@interface AGCPhoneAuthProvider : NSObject

/**
 * 通过手机号和密码获取凭证
 * 如果创建用户时设置了密码，可以采用手机号和密码登录
 *
 * @param countryCode 国际区号
 * @param phoneNumber 手机号
 * @param password 密码
 * @return 手机凭证
 */
+ (AGCAuthCredential *)credentialWithCountryCode:(NSString *)countryCode
                                     phoneNumber:(NSString *)phoneNumber
                                        password:(NSString *)password;

/**
 * 通过邮箱和验证码获取凭证
 * 如果创建账户的时候没有设置过密码，则只能通过此接口进行登录，此时的password字段不赋值
 *
 * @param countryCode 国际区号
 * @param phoneNumber 手机号
 * @param password 密码
 * @param verifyCode 验证码 Link用户的时候必填
 * @return 手机凭证
 */
+ (AGCAuthCredential *)credentialWithCountryCode:(NSString *)countryCode
                                     phoneNumber:(NSString *)phoneNumber
                                        password:(nullable NSString *)password
                                      verifyCode:(NSString *)verifyCode;

/**
 * 手机申请验证码
 *
 * @param countryCode 国际区号
 * @param phoneNumber 手机号码
 * @param settings 验证码参数
 * @return 申请验证码异步任务
 */
+ (HMFTask<AGCVerifyCodeResult *> *)requestVerifyCodeWithCountryCode:(NSString *)countryCode
                                                         phoneNumber:(NSString *)phoneNumber
                                                            settings:
                                                                (AGCVerifyCodeSettings *)settings;

@end

NS_ASSUME_NONNULL_END
