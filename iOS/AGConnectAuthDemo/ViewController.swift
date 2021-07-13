//
//  ViewController.swift
//  AGConnectAuthDemo
//
//

import UIKit
import AGConnectAuth

class ViewController: UIViewController,UIScrollViewDelegate {
    
    @IBOutlet weak var loginSegmentedControl: UISegmentedControl!
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var phoneView: UIView!
    @IBOutlet weak var emailView: UIView!
    @IBOutlet weak var phoneText: UITextField!
    @IBOutlet weak var phoneVertifyText: UITextField!
    @IBOutlet weak var emailText: UITextField!
    @IBOutlet weak var emailVertifyText: UITextField!
    @IBOutlet weak var phonePassword: UITextField!
    @IBOutlet weak var emailPassword: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        scrollView.isScrollEnabled = false
        AGCInstance.startUp()
    }
    
    @IBAction func segmentChange(_ sender: UISegmentedControl) {
        let width = UIScreen.main.bounds.width
        let index = sender.selectedSegmentIndex
        self.scrollView.setContentOffset(CGPoint(x: Int(width)*index, y: 0), animated: true)
    }
    
    @IBAction func register(_ sender: Any) {
        if loginSegmentedControl.selectedSegmentIndex == 0 {
            AGCAuth.instance().createUser(withCountryCode: "86", phoneNumber: phoneText.text ?? "", password: phonePassword.text ?? "", verifyCode: phoneVertifyText.text ?? "").onSuccess { (result) in
                //手机注册成功
                print("手机注册成功")
            }.onFailure { (error) in
                //手机注册失败
                print("手机注册失败")
            }
        }else {
            AGCAuth.instance().createUser(withEmail: emailText.text ?? "", password: emailPassword.text ?? "", verifyCode: emailVertifyText.text ?? "").onSuccess { (result) in
                //邮箱注册成功
                print("邮箱注册成功")
            }.onFailure { (error) in
                //邮箱注册失败
                print("邮箱注册失败")
            }
        }
    }
    @IBAction func login(_ sender: Any) {
        if loginSegmentedControl.selectedSegmentIndex == 0 {
            let credential = AGCPhoneAuthProvider.credential(withCountryCode: "86", phoneNumber: phoneText.text ?? "", password: phonePassword.text ?? "")
            AGCAuth.instance().signIn(credential: credential).onSuccess { (result) in
                //登录成功
                print("登录成功")
            }.onFailure { (error) in
                //登录失败
                print("登录失败")
            }

        }else {
            let credential = AGCEmailAuthProvider.credential(withEmail: emailText.text ?? "", password: emailPassword.text ?? "")
            AGCAuth.instance().signIn(credential: credential).onSuccess { (result) in
                //登录成功
                print("登录成功")
            }.onFailure { (error) in
                //登录失败
                print("登录失败")
            }
        }
    }
    
    @IBAction func logout(_ sender: Any) {
        AGCAuth.instance().signOut()
    }
    
    @IBAction func phoneSendVertifyCode(_ sender: Any) {
        let setting = AGCVerifyCodeSettings.init(action: AGCVerifyCodeAction.registerLogin, locale: nil, sendInterval: 30)
        AGCPhoneAuthProvider.requestVerifyCode(withCountryCode: "86", phoneNumber: phoneText.text ?? "", settings: setting).onSuccess { (results) in
            //手机验证码发送成功
            print("手机验证码发送成功")
        }.onFailure { (error) in
            //手机验证码发送失败18551761037
            print("手机验证码发送失败")
        }
        
    }
    
    @IBAction func emailSendVertifyCode(_ sender: Any) {
        let setting = AGCVerifyCodeSettings.init(action: AGCVerifyCodeAction.registerLogin, locale: nil, sendInterval: 30)
        AGCEmailAuthProvider.requestVerifyCode(withEmail: emailText.text ?? "", settings: setting).onSuccess { (result) in
            //邮箱验证码发送成功
            print("邮箱验证码发送成功")
        }.onFailure { (error) in
            //邮箱验证码发送失败
            print("邮箱验证码发送成功")
        }
    }
    
}

