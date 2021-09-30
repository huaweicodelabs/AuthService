//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <HMFoundation/HMFoundation.h>
#import "AGCBackend.h"
#import "AGCGWBaseRequest.h"

NS_ASSUME_NONNULL_BEGIN

/// AGC 网关请求
@interface AGCGWBackend : AGCBackend

/// 单例对象
+ (instancetype)sharedInstance;

/// 发送网络请求
/// @param request 继承自AGCGWBaseRequest的请求类
- (HMFTask *)send:(AGCGWBaseRequest *)request;

/// 发送网络请求，会在请求中添加设备认证字段
/// @param request 继承自AGCGWBaseRequest的请求类
- (HMFTask *)sendWithToken:(AGCGWBaseRequest *)request;

/// 发送网络请求，会在请求中添加用户认证和设备认证字段
/// @param request 继承自AGCGWBaseRequest的请求类
- (HMFTask *)sendWithAccessToken:(AGCGWBaseRequest *)request;

@end

NS_ASSUME_NONNULL_END
