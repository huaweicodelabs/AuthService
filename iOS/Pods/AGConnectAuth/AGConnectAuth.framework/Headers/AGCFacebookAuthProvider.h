//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * Facebook凭证提供者
 */
@interface AGCFacebookAuthProvider : NSObject
/**
 * 登录凭证构造器
 *
 * @param token 由Facebook授权后获得的accessToken
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token;
/**
 * 登录凭证构造器
 *
 * @param token 由Facebook授权后获得的accessToken
 * @param autoCreateUser 是否创建账户
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token autoCreateUser:(BOOL)autoCreateUser;

@end

NS_ASSUME_NONNULL_END
