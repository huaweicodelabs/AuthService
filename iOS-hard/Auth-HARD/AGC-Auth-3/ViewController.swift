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

        passwordTextField.frame = CGRect(x: 50, y: 280, width: 250, height: 50)
        passwordTextField.placeholder = "Please enter password"
        passwordTextField.layer.borderWidth = 0.5
        passwordTextField.layer.borderColor = UIColor.gray.cgColor
        self.view.addSubview(passwordTextField)

        let loginButton = UIButton(frame: CGRect(x: 50, y: 400, width: 200, height: 50))
        loginButton.setTitle("Login", for: .normal)
        loginButton.backgroundColor = UIColor.blue
        loginButton.addTarget(self, action: #selector(login), for: .touchUpInside)
        self.view.addSubview(loginButton)
        
        resultLabel.frame = CGRect(x: 50, y: 480, width: 250, height: 100)
        resultLabel.numberOfLines = 0
        resultLabel.textAlignment = .center
        self.view.addSubview(resultLabel)

        if self.hasLoggedin() {
            self.goLinkVc()
        }
    }

    @objc func login() {
        
        let countryCode = geocoTextField.text ?? ""
        let phoneNumber = phoneTextField.text ?? ""
        let password = passwordTextField.text ?? ""

        let credential = AGCPhoneAuthProvider.credential(withCountryCode: countryCode, phoneNumber: phoneNumber, password: password)
        AGCAuth.instance().signIn(credential: credential).onSuccess { (result) in

            self.goLinkVc()
            
        }.onFailure{ (error) in

            self.resultLabel.text = error.localizedDescription

        }
    }
    
    func hasLoggedin() -> Bool {
        
        let user = AGCAuth.instance().currentUser
        return user != nil
    }
    
    func goLinkVc() {
        
        let linkVc = LinkViewController()
        self.navigationController?.pushViewController(linkVc, animated: true)

    }


}

