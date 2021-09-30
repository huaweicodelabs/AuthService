//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * 微博凭证提供者
 */
@interface AGCWeiboAuthProvider : NSObject
/**
 * 登录凭证构造器
 *
 * @param token 由微博 SDK授权后获得的token
 * @param uid 由微博 SDK授权后获得的uid
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token uid:(NSString *)uid;
/**
 * 登录凭证构造器
 *
 * @param token 由微博 SDK授权后获得的token
 * @param uid 由微博 SDK授权后获得的uid
 * @param autoCreateUser 是否创建账户
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token
                                       uid:(NSString *)uid
                            autoCreateUser:(BOOL)autoCreateUser;

@end

NS_ASSUME_NONNULL_END
