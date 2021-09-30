//
//  AGCGWBaseResponse.h
//  AGConnectCredential
//
//  Created by x00318692 on 2020/7/22.
//  Copyright © 2020 huawei. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AGCModelProtocol.h"

NS_ASSUME_NONNULL_BEGIN

@interface AGCGWBaseResponse : NSObject<NSCoding,AGCModelProtocol>

@property(nonatomic) NSInteger retCode;
@property(nonatomic) NSString *retMsg;

- (BOOL)isSuccess;
- (NSError *)failError;

@end

NS_ASSUME_NONNULL_END
