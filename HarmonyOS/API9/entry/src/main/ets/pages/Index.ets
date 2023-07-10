import { Logger } from "@hw-agconnect/base-ohos"
import "@hw-agconnect/auth-ohos"
import {  VerifyCodeAction, VerifyCodeSettingBuilder, PhoneUserBuilder, PhoneAuthProvider
} from "@hw-agconnect/auth-ohos"

const TAG = "AGC_AUTH_DEMO";

let auth = globalThis.auth;

@Entry
@Component
struct Index {
  @State phoneNumber: string = ''
  @State countryCode: string = ''
  @State verifyCode: string = ''
  @State uid: string = ''

  build() {
    Row() {
      Column() {
        Text('Welcome to the Codelab' )
          .margin({ top: 20 })
          .fontSize(25)
          .fontColor(Color.Gray)
          .fontWeight(FontWeight.Bold)

        Row(){
          TextInput({ placeholder: 'country code' })
            .width('38%')
            .margin({ top: 20 })
            .onChange((value: string) => {
              this.countryCode = value;
            })

          TextInput({ placeholder: ' phone number' })
            .width('52%')
            .margin({ top: 20 })
            .onChange((value: string) => {
              this.phoneNumber = value;
            })
        }

        Row() {
          TextInput({ placeholder: 'verify code' })
            .width('40%')
            .margin({ top: 20 })
            .onChange((value: string) => {
              this.verifyCode = value;
            })

          Button("request verify code").width('50%').margin({ top: 20 })
            .onClick(() => {
              let verifyCodeSettings = new VerifyCodeSettingBuilder()
                .setAction(VerifyCodeAction.REGISTER_LOGIN)
                .setLang('zh_CN')
                .setSendInterval(60)
                .build();

              auth.requestPhoneVerifyCode(this.countryCode, this.phoneNumber, verifyCodeSettings)
                .then(verifyCodeResult => {
                  Logger.info(TAG, "requestPhoneVerifyCode success " + JSON.stringify(verifyCodeResult));
                }).catch(error => {
                Logger.error(TAG, "requestPhoneVerifyCode error " + JSON.stringify(error));
              })
            })
        }

        Button("signin phone").width('60%').margin({ top: 40 })
          .onClick(() => {
            let user = new PhoneUserBuilder()
              .setCountryCode(this.countryCode)
              .setPhoneNumber(this.phoneNumber)
              .setVerifyCode( this.verifyCode)
              .build();
            auth.createPhoneUser(user)
              .then(result => {
                Logger.info(TAG, "createPhoneUser success getUid:" + result.getUser().getUid());
              })

            let credential = PhoneAuthProvider.credentialWithVerifyCode(this.countryCode,this.phoneNumber, this.verifyCode);
            auth.signIn(credential).then(signInResult => {
              this.uid = "Uid:"+signInResult.getUser().getUid();
              Logger.info(TAG, "signInPhone success " + signInResult.getUser().getUid());
            });
          })

        Button("sign out").width('60%').margin({ top: 40 })
          .onClick(() => {
            auth.signOut().then(() => {
              this.uid = "sign out success";
              Logger.info(TAG, "sign out success");
            });
          })

        Text(this.uid)
          .margin({ top: 50 })
          .fontSize(25)
          .fontColor(Color.Gray)

      }
      .width('100%')
    }
    .height('100%')
  }
}