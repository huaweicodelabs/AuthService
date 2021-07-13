//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface AGCTokenResult : NSObject

/**
 * 用户的access token
 */
@property(nonatomic, copy, readonly) NSString *token;

/**
 * token的有效期, 单位：秒
 */
@property(nonatomic, readonly) long expirePeriod;

/**
 * 请使用 initWithToken: expirePeriod: 方法初始化
 */
- (instancetype)init NS_UNAVAILABLE;

/**
 * 初始化方法
 * @param token 用户的access token
 * @param expirePeriod token的有效期
 * @return AGCUserExtra实例
 */
- (instancetype)initWithToken:(NSString *)token
                    expirePeriod:(long)expirePeriod NS_DESIGNATED_INITIALIZER;

@end

NS_ASSUME_NONNULL_END
