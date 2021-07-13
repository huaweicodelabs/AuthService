//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//
#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * 用户其他信息
 *
 */
@interface AGCUserExtra : NSObject

/**
 * 创建用户时间
 */
@property(nonatomic, copy, readonly) NSString *createTime;

/**
 * 最近一次登录时间
 */
@property(nonatomic, copy, readonly) NSString *lastSignInTime;

/**
 * 请使用 initWithCreateTime: lastSignInTime: 方法初始化
 */
- (instancetype)init NS_UNAVAILABLE;

/**
 * 初始化方法
 * @param createTime 创建时间
 * @param signInTime 最近一次登录时间
 * @return AGCUserExtra实例
 */
- (instancetype)initWithCreateTime:(NSString *)createTime
                    lastSignInTime:(NSString *)signInTime NS_DESIGNATED_INITIALIZER;

@end

NS_ASSUME_NONNULL_END
