//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Auth模块错误码
 */
typedef NS_ENUM(NSUInteger, AGCAuthErrorCode) {
    /**
     * AccessToken为空
     * 建议重新登录
     */
    AGCAuthErrorCodeNullToken = 1,

    /**
     * 未登录时获取AccessToken信息
     */
    AGCAuthErrorCodeNotSignIn = 2,

    /**
     * 用户已经关联此Provider
     */
    AGCAuthErrorCodeUserLinked = 3,

    /**
     * 用户未关联此Provider
     */
    AGCAuthErrorCodeUserUnlinked = 4,

    /**
     * 已经登录过一个用户，在未登出情况登录账户
     */
    AGCAuthErrorCodeAlreadySignInUser = 5,

    /**
     * 邮件验证码为空
     */
    AGCAuthErrorCodeEmailVerificationIsEmpty = 6,

    /**
     * 电话验证码为空
     */
    AGCAuthErrorCodePhoneVerificationIsEmpty = 7,

    /**
     * 输入的邮件地址不合法
     */
    AGCAuthErrorCodeInvalidEmail = 0x0c260107,

    /**
     * 输入的手机号码不合法
     */
    AGCAuthErrorCodeInvalidPhone = 0x0c260108,

    /**
     * 获取用户id失败
     */
    AGCAuthErrorCodeGetUidError = 0x0c260300,

    /**
     * 用户id和产品id不匹配
     */
    AGCAuthErrorCodeUidProductIDNotMatch = 0x0c260301,

    /**
     * 获取用户信息失败
     */
    AGCAuthErrorCodeGetUserInfoError = 0x0c260302,

    /**
     * 当前Auth微服务部署了4个局点，每个局点支持的认证方式不同
     */
    AGCAuthErrorCodeAuthMethodNotSupport = 0x0c260304,

    /**
     * 产品没有开通认证服务
     */
    AGCAuthErrorCodeProductStatusError = 0x0c260310,

    /**
     * 密码验证码次数超过限制
     */
    AGCAuthErrorCodePasswordVerificationCodeOverLimit = 0x0c260353,

    /**
     * Client Token 不可用
     */
    AGCAuthErrorCodeInvalidToken = 0x0c260400,

    /**
     * Access Token 不可用
     */
    AGCAuthErrorCodeInvalidAccessToken = 0x0c260401,

    /**
     * Refresh Token 不可用
     */
    AGCAuthErrorCodeInvalidRefreshToken = 0x0c260402,

    /**
     * Token和Product Id不匹配
     * 建议检查agconnect-services.json是否与平台上申请的信息一致
     */
    AGCAuthErrorCodeTokenAndProductIdNotMatch = 0x0c260403,

    /**
     * 不支持的认证方式
     * 建议检查平台是否开启对应认证方式，例如检查是否开启支持Facebook登录
     */
    AGCAuthErrorCodeAuthMethodIsDisabled = 0x0c260404,

    /**
     * 获取第三方用户信息失败
     */
    AGCAuthErrorCodeFailToGetThirdUserInfo = 0x0c260405,

    /**
     * 获取第三方Union id失败
     */
    AGCAuthErrorCodeFailToGetThirdUserUnionId = 0x0c260406,

    /**
     * AccessToken数量超过了限定数量
     */
    AGCAuthErrorCodeAccessTokenOverLimit = 0x0c260407,

    /**
     * link用户失败
     */
    AGCAuthErrorCodeFailToUserLink = 0x0c260408,

    /**
     * unlink用户失败
     */
    AGCAuthErrorCodeFailToUserUnlink = 0x0c260409,

    /**
     * 匿名用户登录超过限制
     */
    AGCAuthErrorCodeAnonymousSigninOverLimit = 0x0c260423,

    /**
     * appid 不可用
     */
    AGCAuthErrorCodeInvalidAppID = 0x0c260424,

    /**
     * app secret 不可用
     */
    AGCAuthErrorCodeInvalidAppSecret = 0x0c260425,

    /**
     * 获取qq 第三方用户信息失败
     */
    AGCAuthErrorCodeGetQQUserInfoError = 0x0c260427,

    /**
     * 获取QQInfo 返回为空
     */
    AGCAuthErrorCodeQQInfoResponseIsNull = 0x0c260428,

    /**
     * 获取QQ uid 返回为空
     */
    AGCAuthErrorCodeGetQQUidError = 0x0c260429,

    /**
     * 密码和验证码错误
     */
    AGCAuthErrorCodePasswordVerifyCodeError = 0x0c260430,

    /**
     * GOOGLE返回信息与appid不匹配
     */
    AGCAuthErrorCodeGoogleResponseNotEqualAppID = 0x0c260431,

    /**
     * 用户被CP停用
     */
    AGCAuthErrorCodeSignInUserStatusError = 0x0c260434,

    /**
     * 用户密码错误
     */
    AGCAuthErrorCodeSignInUserPasswordError = 0x0c260435,

    /**
     * 提供者已经被其他用户绑定
     */
    AGCAuthErrorCodeProviderUserHaveBeenLinked = 0x0c260436,

    /**
     * 账号中该提供者类型已经被绑定过
     */
    AGCAuthErrorCodeProviderHaveLinkedOneUser = 0x0c260437,

    /**
     * 获取提供者用户失败
     */
    AGCAuthErrorCodeFailGetProviderUser = 0x0c260438,

    /**
     * 不能对单一的提供者做unlink操作
     */
    AGCAuthErrorCodeCannotUnlinkOneProviderUser = 0x0c260439,

    /**
     * 在发送间隔内发送验证码
     */
    AGCAuthErrorCodeVerifyCodeIntervalLimit = 0x0c260440,

    /**
     * 验证码为空
     */
    AGCAuthErrorCodeVerifyCodeEmpty = 0x0c260441,

    /**
     * 验证码发送语言为空
     */
    AGCAuthErrorCodeVerifyCodeLanguageEmpty = 0x0c260442,

    /**
     * 验证码接收器为空
     */
    AGCAuthErrorCodeVerifyCodeReceiverEmpty = 0x0c260443,

    /**
     * 验证码类型为空
     */
    AGCAuthErrorCodeVerifyCodeActionError = 0x0c260444,

    /**
     * 验证码发送次数超过限制
     * 建议一个小时后在尝试
     */
    AGCAuthErrorCodeVerifyCodeTimeLimit = 0x0c260445,

    /**
     * 用户名密码一致
     */
    AGCAuthErrorCodeAccountPasswordSame = 0x0c260450,

    /**
     * 密码强度太低
     */
    AGCAuthErrorCodePasswordStrengthLow = 0x0c260451,

    /**
     * 更新密码失败
     */
    AGCAuthErrorCodeUpdatePasswordError = 0x0c260452,

    /**
     * 密码与老密码相同
     */
    AGCAuthErrorCodePasswordSameAsBefore = 0x0c260453,

    /**
     * 密码为空
     */
    AGCAuthErrorCodePasswordIsEmpty = 0x0c260454,

    /**
     * 密码太长
     */
    AGCAuthErrorCodePasswordTooLong = 0x0c260457,

    /**
     * 敏感操作的最近登录时间超时
     */
    AGCAuthErrorCodeSensitiveOperationTimeout = 0x0c260461,

    /**
     * 账号已经被注册
     */
    AGCAuthErrorCodeAccountHaveBeenRegistered = 0x0c260462,

    /**
     * 更新账号失败
     */
    AGCAuthErrorCodeUpdateAccountError = 0x0c260464,

    /**
     * 用户没有注册
     */
    AGCAuthErrorCodeUserNotRegistered = 0x0c260467,

    /**
     * 验证码错误
     */
    AGCAuthErrorCodeVerifyCodeError = 0x0c260491,
    /**
     * 用户已经被注册
     */
    AGCAuthErrorCodeUserHaveBeenRegistered = 0x0c260492,

    /**
     * 注册账号为空
     */
    AGCAuthErrorCodeRegisterAccountIsEmpty = 0x0c260494,

    /**
     * 验证码格式错误
     */
    AGCAuthErrorCodeVerifyCodeFormatError = 0x0c260496,

    /**
     * 验证码和密码都为空
     */
    AGCAuthErrorCodeVerifyCodeAndPasswordBothNull = 0x0c260497,

    /**
     * 发送邮件失败
     */
    AGCAuthErrorCodeSendEmailFail = 0x0c260500,

    /**
     * 发送短信失败
     */
    AGCAuthErrorCodeSendMessageFail = 0x0c260501,

    /**
     * 配置密码/验证码最大尝试次数/冻结时常错误
     */
    AGCAuthErrorCodeConfigLockTimeError = 0x0c260515
};

/**
 * Auth服务错误码
 *
 */
@interface AGCAuthError : NSError

@end

NS_ASSUME_NONNULL_END
