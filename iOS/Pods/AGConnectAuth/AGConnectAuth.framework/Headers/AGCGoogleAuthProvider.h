//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * Google凭证提供者
 */
@interface AGCGoogleAuthProvider : NSObject
/**
 * 登录凭证构造器
 *
 * @param token 由Google授权后获得的idToken
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token;
/**
 * 登录凭证构造器
 *
 * @param token 由Google授权后获得的idToken
 * @param autoCreateUser 是否创建账户
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token autoCreateUser:(BOOL)autoCreateUser;

@end

NS_ASSUME_NONNULL_END
