//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>
#import "AGCExceptionModel.h"

NS_ASSUME_NONNULL_BEGIN

/**
 * 用户自定义日志的级别
 */

typedef NS_ENUM(NSUInteger, AGCCrashLogLevel) {

    /**
     * debug级别
     */
    AGCCrashLogLevelDebug = 2,

    /**
     * info级别
     */
    AGCCrashLogLevelInfo,

    /**
     * warning级别
     */
    AGCCrashLogLevelWarning,

    /**
     * error级别
     */
    AGCCrashLogLevelError
};

/**
 * 崩溃收集服务入口类
 */
@interface AGCCrash : NSObject

/**
 * 获取AGCCrash实例
 * @return AGCCrash实例
 */
+ (instancetype)sharedInstance;

/**
 * 创造一个crash异常，用于开发者调试
 */
- (void)testIt;

/**
 * 设置是否收集和上报应用的崩溃信息，默认为YES
 * @param enable  为YES表示收集并上报崩溃信息，NO表示不收集且不上报崩溃信息。
 */
- (void)enableCrashCollection:(BOOL)enable NS_SWIFT_NAME(enableCrashCollection(enable:));

/**
 * 设置用户id
 * @param userId  用户id。
 */
- (void)setUserId:(NSString *)userId;

/**
 * 设置用户自定义状态
 * @param value  自定义状态的value值
 * @param key  自定义状态的key值
 */
- (void)setCustomValue:(id)value forKey:(NSString *)key NS_SWIFT_NAME(setCustomValue(value:key:));

/**
 * 设置用户自定义日志
 * @param level  自定义日志的级别
 * @param message  自定义状态的内容
 */
- (void)logWithLevel:(AGCCrashLogLevel)level
             message:(NSString *)message NS_SWIFT_NAME(log(level:message:));

/**
 * 设置用户自定义日志，默认级别为AGCCrashLogLevelInfo
 * @param message  自定义状态的内容
 */
- (void)log:(NSString *)message NS_SWIFT_NAME(log(message:));

/**
 * 记录iOS的非致命异常
 * 该接口会收集线程堆栈，频繁调用可能会影响性能
 * @param error  iOS的异常，使用error的domain和code作为标志区分不同异常。
 */
- (void)recordError:(NSError *)error;

/**
 * 记录自定义异常，可以设置自定义的异常内容
 * @param exception  自定义的异常。
 */
- (void)recordExceptionModel:(AGCExceptionModel *)exception;

@end

NS_ASSUME_NONNULL_END
