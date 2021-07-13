//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * 自建账号凭证提供者
 */
@interface AGCSelfBuildAuthProvider : NSObject
/**
 * 登录凭证构造器
 *
 * @param token 由自建账号服务器授权后获得的JWT token
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token;
/**
 * 登录凭证构造器
 *
 * @param token 由自建账号服务器授权后获得的JWT token
 * @param autoCreateUser 是否创建账户
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token autoCreateUser:(BOOL)autoCreateUser;

@end

NS_ASSUME_NONNULL_END
