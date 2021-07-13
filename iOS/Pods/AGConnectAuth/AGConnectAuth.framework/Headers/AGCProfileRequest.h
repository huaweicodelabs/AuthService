//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * 更新用户信息请求类
 *
 */
@interface AGCProfileRequest : NSObject

/**
 * 用户展示名称
 */
@property(nonatomic) NSString *displayName;

/**
 * 用户头像地址
 */
@property(nonatomic) NSString *photoUrl;

@end

NS_ASSUME_NONNULL_END
