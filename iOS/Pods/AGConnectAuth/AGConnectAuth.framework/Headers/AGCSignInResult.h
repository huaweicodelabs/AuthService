//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>
@class AGCUser;

NS_ASSUME_NONNULL_BEGIN
/**
 * 登录返回结果
 *
 */
@interface AGCSignInResult : NSObject

/**
 * 返回当前登录的用户信息
 */
@property(nonatomic, readonly) AGCUser *user;

- (instancetype)init NS_UNAVAILABLE;
- (instancetype)initWithUser:(AGCUser *)user NS_DESIGNATED_INITIALIZER;

@end

NS_ASSUME_NONNULL_END
