//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * 验证码类型
 */
typedef NS_ENUM(NSInteger, AGCVerifyCodeAction) {
    /**
     * 验证码类型：注册/登录
     */
    AGCVerifyCodeActionRegisterLogin = 1001,
    /**
     * 验证码类型：重置密码
     */
    AGCVerifyCodeActionResetPassword = 1002
};

/**
 * 验证码结果
 */
@interface AGCVerifyCodeResult : NSObject

/**
 * 两次发送验证码的最小间隔，单位：秒
 */
@property(nonatomic, readonly) NSString *shortestInterval;

/**
 * 验证码有效期，单位：秒
 */
@property(nonatomic, readonly) NSString *validityPeriod;

/**
 * 请使用 initWithShortestInterval: validityPeriod: 方法初始化
 */
- (instancetype)init NS_UNAVAILABLE;

/**
 * 初始化方法
 * @param interval 验证码发送间隔
 * @param period 验证码有效期
 * @return AGCVerifyCodeResult实例
 */
- (instancetype)initWithShortestInterval:(NSString *)interval
                          validityPeriod:(NSString *)period NS_DESIGNATED_INITIALIZER;

@end

/**
 * 验证码设置
 */
@interface AGCVerifyCodeSettings : NSObject

/**
 * 获取验证码类型
 */
@property(nonatomic, readonly) AGCVerifyCodeAction action;

/**
 * 获取语言
 */
@property(nonatomic, readonly) NSString *lang;

/**
 * 获取发送间隔
 */
@property(nonatomic, readonly) NSInteger sendInterval;

/**
 * 请使用 initWithAction: locale:sendInterval: 方法初始化
 */
- (instancetype)init NS_UNAVAILABLE;

/**
 * 初始化方法
 * @param action 验证码类型
 * @param locale 发送验证码所选语言，locale需包含语言码和国家码，nil表示使用系统偏好语言
 * @param interval 验证码
 * @return AGCEmailUser实例
 */
- (instancetype)initWithAction:(AGCVerifyCodeAction)action
                        locale:(nullable NSLocale *)locale
                  sendInterval:(NSInteger)interval NS_DESIGNATED_INITIALIZER;

@end

NS_ASSUME_NONNULL_END
