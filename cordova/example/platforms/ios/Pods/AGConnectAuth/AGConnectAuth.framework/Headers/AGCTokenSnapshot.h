//
//  Copyright (c) Huawei Technologies Co., Ltd. 2020. All rights reserved
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSInteger, AGCTokenState) {
    AGCTokenStateSignedIn = 0,
    AGCTokenStateTokenUpdated,
    AGCTokenStateTokenInvalid,
    AGCTokenStateSignedOut
};

@interface AGCTokenSnapshot : NSObject

@property(nonatomic, readonly) AGCTokenState state;
@property(nonatomic, readonly, nullable, copy) NSString *token;

- (instancetype)init NS_UNAVAILABLE;
- (instancetype)initWithToken:(AGCTokenState)state;
- (instancetype)initWithState:(AGCTokenState)state token:(NSString * _Nullable)token;
@end

NS_ASSUME_NONNULL_END
