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

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        let welcomeLabel = UILabel(frame: CGRect(x: 50, y: 80, width: 200, height: 100))
        welcomeLabel.text = "Welcome to the Codelab"
        welcomeLabel.textAlignment = .center
        self.view.addSubview(welcomeLabel)
        
        let anonymouslyLoginButton = UIButton(frame: CGRect(x: 50, y: 200, width: 200, height: 50))
        anonymouslyLoginButton.setTitle("Anonymously Login", for: .normal)
        anonymouslyLoginButton.backgroundColor = UIColor.blue
        anonymouslyLoginButton.addTarget(self, action: #selector(login), for: .touchUpInside)
        self.view.addSubview(anonymouslyLoginButton)
        
        resultLabel.frame = CGRect(x: 50, y: 300, width: 200, height: 150)
        resultLabel.numberOfLines = 0
        resultLabel.textAlignment = .center
        self.view.addSubview(resultLabel)

    }

    @objc func login() {
        
        AGCAuth.instance().signInAnonymously().onSuccess { (result) in
            
            self.resultLabel.text = result?.user.uid
            
        }.onFailure { (error) in
            
            self.resultLabel.text = error.localizedDescription
            
        }
        
    }
    
    

}

