//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"

NS_ASSUME_NONNULL_BEGIN

/// Apple 账号凭证提供者
@interface AGCAppleIDAuthProvider : NSObject

/// 登录凭证构造器
/// @param identityToken 由Apple登录授权后返回的JWT
/// @param nonce 授权请求中的随机字符串
/// @return 用于登录的凭证
+ (AGCAuthCredential *)credentialWithIdentityToken:(NSData *)identityToken nonce:(NSString *)nonce;

/// 登录凭证构造器
/// @param identityToken 由Apple登录授权后返回的JWT
/// @param nonce 授权请求中的随机字符串
/// @param autoCreateUser 是否创建账户
/// @return 用于登录的凭证
+ (AGCAuthCredential *)credentialWithIdentityToken:(NSData *)identityToken
                                             nonce:(NSString *)nonce
                                    autoCreateUser:(BOOL)autoCreateUser;

@end

NS_ASSUME_NONNULL_END
