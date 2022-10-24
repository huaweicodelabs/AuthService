import agconnect from '@hw-agconnect/api-ohos';
import "@hw-agconnect/core-ohos";
import "@hw-agconnect/auth-ohos";
import { EmailAuthProvider, VerifyCodeAction, EmailUserBuilder, VerifyCodeSettingBuilder, PhoneUserBuilder,
  PhoneAuthProvider, AGConnectAuth, AGConnectAuthCredentialProvider
} from "@hw-agconnect/auth-ohos"
import { Logger } from "@hw-agconnect/base-ohos"

const TAG = "AUTH_HARMONY_DEMO"
let auth: AGConnectAuth = globalThis.auth;

@Entry
@Component
struct Index {
  @State result: string = ''
  @State phoneNumber: string = ''
  @State verifyCode: string = ''

  build() {
    Row() {
      Column() {
        Text(this.result).width('90%').margin({ top: 20 }).onClick((event) => {
          this.result = '';
        })

        TextInput({ placeholder: 'input your phone number' })
          .width('90%')
          .margin({ top: 20 })
          .onChange((value: string) => {
            this.phoneNumber = value;
          })

        TextInput({ placeholder: 'input your verifyCode' })
          .width('90%')
          .margin({ top: 20 })
          .onChange((value: string) => {
            this.verifyCode = value;
          })

        Button("requestPhoneVerifyCode").width('50%').margin({ top: 20 })
          .onClick(() => {
            let verifyCodeSettings = new VerifyCodeSettingBuilder()
              .setAction(VerifyCodeAction.REGISTER_LOGIN)
              .setLang('zh_CN')
              .setSendInterval(60)
              .build();
            auth.requestPhoneVerifyCode("86", this.phoneNumber, verifyCodeSettings).then(verifyCodeResult => {
              Logger.info(TAG, "requestPhoneVerifyCode success " + JSON.stringify(verifyCodeResult));
              this.result = "requestPhoneVerifyCode success " + JSON.stringify(verifyCodeResult);
            }).catch(error => {
              Logger.error(TAG, "requestPhoneVerifyCode error " + JSON.stringify(error));
              this.result = "requestPhoneVerifyCode error " + JSON.stringify(error);
            })
          })

        Button("signInPhone").width('50%').margin({ top: 20 })
          .onClick(() => {
            auth.signOut();
            let credential = PhoneAuthProvider.credentialWithVerifyCode("86",this.phoneNumber, this.verifyCode);
            auth.signIn(credential).then(signInResult => {
              Logger.info(TAG, "signInPhone success " + signInResult.getUser().getUid());
              this.result = "signInPhone success " + signInResult.getUser().getUid();
            }).catch(error => {
              Logger.error(TAG, "signInPhone error " + JSON.stringify(error));
              this.result = "signInPhone error " + JSON.stringify(error);
            })
          })
      }
      .width('100%')
    }
    .height('100%')
  }
}