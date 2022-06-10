/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import UIKit
import AGConnectAuth

class ViewController: UIViewController {

    let resultLabel = UILabel()
    let geocoTextField = UITextField()
    let phoneTextField = UITextField()
    let verifyTextField = UITextField()
    let passwordTextField = UITextField()

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        let welcomeLabel = UILabel(frame: CGRect(x: 50, y: 80, width: 200, height: 100))
        welcomeLabel.text = "Welcome to the Codelab"
        welcomeLabel.textAlignment = .center
        self.view.addSubview(welcomeLabel)
        
        geocoTextField.frame = CGRect(x: 50, y: 200, width: 50, height: 50)
        geocoTextField.placeholder = "Geoco"
        geocoTextField.layer.borderWidth = 0.5
        geocoTextField.layer.borderColor = UIColor.gray.cgColor
        self.view.addSubview(geocoTextField)

        phoneTextField.frame = CGRect(x: 110, y: 200, width: 190, height: 50)
        phoneTextField.placeholder = "Please enter phone number"
        phoneTextField.layer.borderWidth = 0.5
        phoneTextField.layer.borderColor = UIColor.gray.cgColor
        self.view.addSubview(phoneTextField)

        verifyTextField.frame = CGRect(x: 50, y: 265, width: 125, height: 50)
        verifyTextField.placeholder = "Verify Code"
        verifyTextField.layer.borderWidth = 0.5
        verifyTextField.layer.borderColor = UIColor.gray.cgColor
        self.view.addSubview(verifyTextField)

        let sendButton = UIButton(frame: CGRect(x: 175, y: 265, width: 125, height: 50))
        sendButton.setTitle("VERIFY CODE", for: .normal)
        sendButton.backgroundColor = UIColor.blue
        sendButton.addTarget(self, action: #selector(send), for: .touchUpInside)
        self.view.addSubview(sendButton)

        passwordTextField.frame = CGRect(x: 50, y: 330, width: 250, height: 50)
        passwordTextField.placeholder = "Please enter password"
        passwordTextField.layer.borderWidth = 0.5
        passwordTextField.layer.borderColor = UIColor.gray.cgColor
        self.view.addSubview(passwordTextField)

        let registerButton = UIButton(frame: CGRect(x: 50, y: 400, width: 200, height: 50))
        registerButton.setTitle("Register", for: .normal)
        registerButton.backgroundColor = UIColor.blue
        registerButton.addTarget(self, action: #selector(register), for: .touchUpInside)
        self.view.addSubview(registerButton)
        
        resultLabel.frame = CGRect(x: 50, y: 480, width: 250, height: 100)
        resultLabel.numberOfLines = 0
        resultLabel.textAlignment = .center
        self.view.addSubview(resultLabel)

    }

    @objc func send() {
        
        let countryCode = geocoTextField.text ?? ""
        let phoneNumber = phoneTextField.text ?? ""
        
        let setting = AGCVerifyCodeSettings.init(action:AGCVerifyCodeAction.registerLogin, locale:nil, sendInterval:30)
        AGCPhoneAuthProvider.requestVerifyCode(withCountryCode:countryCode, phoneNumber:phoneNumber, settings:setting).onSuccess{ (result) in

            self.resultLabel.text = "send VerifyCode success"
            
        }.onFailure{ (error) in

            self.resultLabel.text = error.localizedDescription

        }
        
    }

    @objc func register() {
        
        let countryCode = geocoTextField.text ?? ""
        let phoneNumber = phoneTextField.text ?? ""
        let verifyCode = verifyTextField.text ?? ""
        let password = passwordTextField.text ?? ""

        AGCAuth.instance().createUser(withCountryCode:countryCode, phoneNumber:phoneNumber, password: password, verifyCode:verifyCode).onSuccess{ (result) in

            self.resultLabel.text = result?.user.uid

        }.onFailure{ (error) in

            self.resultLabel.text = error.localizedDescription

        }
    }

}

