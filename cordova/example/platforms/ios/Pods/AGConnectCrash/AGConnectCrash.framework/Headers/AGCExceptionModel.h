//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
/**
 * 自定义异常类
 */
@interface AGCExceptionModel : NSObject

/**
 * 异常名称
 */
@property (nonatomic, readonly) NSString *name;

/**
 * 异常原因
 */
@property (nonatomic, readonly) NSString *reason;

/**
 * 异常堆栈
 */
@property (nonatomic, readonly) NSString *stackTrace;

/**
 * 初始化自定义异常
 */
- (instancetype)initWithName:(NSString *)name reason:(NSString *)reason stackTrace:(NSString *)stacktrace;

@end

NS_ASSUME_NONNULL_END
