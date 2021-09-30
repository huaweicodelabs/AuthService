//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>
#import "AGCAuthCredential.h"

NS_ASSUME_NONNULL_BEGIN
/**
 * 微信凭证提供者
 */
@interface AGCWeiXinAuthProvider : NSObject
/**
 * 登录凭证构造器
 *
 * @param token 由WeiXin SDK授权后获得的token
 * @param openId 由WeiXin SDK授权后获得的openId
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token openId:(NSString *)openId;
/**
 * 登录凭证构造器
 *
 * @param token 由WeiXin SDK授权后获得的token
 * @param openId 由WeiXin SDK授权后获得的openId
 * @param autoCreateUser 是否创建账户
 * @return 用于登录的凭证
 */
+ (AGCAuthCredential *)credentialWithToken:(NSString *)token
                                    openId:(NSString *)openId
                            autoCreateUser:(BOOL)autoCreateUser;

@end

NS_ASSUME_NONNULL_END
