//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * provider类型枚举
 */
typedef NS_ENUM(NSInteger, AGCAuthProviderType) {
    /**
     * 匿名登录
     */
    AGCAuthProviderTypeAnonymous = 0,

    /**
     * HMS登录
     */
    AGCAuthProviderTypeHMS = 1,

    /**
     * Facebook登录
     */
    AGCAuthProviderTypeFacebook = 2,

    /**
     * Twitter登录
     */
    AGCAuthProviderTypeTwitter = 3,

    /**
     * WeiXin登录
     */
    AGCAuthProviderTypeWeiXin = 4,

    /**
     * Huawei游戏登录
     */
    AGCAuthProviderTypeHWGame = 5,

    /**
     * QQ登录
     */
    AGCAuthProviderTypeQQ = 6,

    /**
     * 微博登录
     */
    AGCAuthProviderTypeWeiBo = 7,

    /**
     * Google账号登录
     */
    AGCAuthProviderTypeGoogle = 8,

    /**
     * Google游戏登录
     */
    AGCAuthProviderTypeGoogleGame = 9,

    /**
     * 自建账号登录
     */
    AGCAuthProviderTypeSelfBuild = 10,

    /**
     * 托管手机账号
     */
    AGCAuthProviderTypePhone = 11,

    /**
     * 托管email账号
     */
    AGCAuthProviderTypeEmail = 12,

    /**
     * Apple账号
     */
    AGCAuthProviderTypeApple = 13,
};

/**
 * auth服务credential
 *
 */
@interface AGCAuthCredential : NSObject

/**
 * 获取provider信息
 */
@property(nonatomic, readonly) AGCAuthProviderType provider;

@end

NS_ASSUME_NONNULL_END
