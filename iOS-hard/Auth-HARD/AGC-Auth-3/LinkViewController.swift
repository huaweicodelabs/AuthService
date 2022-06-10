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

class LinkViewController: UIViewController {

    let resultLabel = UILabel()

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.white
        // Do any additional setup after loading the view.
        let user = AGCAuth.instance().currentUser

        let userInfoLabel = UILabel(frame: CGRect(x: 50, y: 80, width: 200, height: 100))
        userInfoLabel.text = user?.uid
        userInfoLabel.textAlignment = .center
        self.view.addSubview(userInfoLabel)


        let linkButton = UIButton(frame: CGRect(x: 50, y: 300, width: 200, height: 50))
        linkButton.setTitle("Link Wechat", for: .normal)
        linkButton.backgroundColor = UIColor.blue
        linkButton.addTarget(self, action: #selector(link), for: .touchUpInside)
        self.view.addSubview(linkButton)

        resultLabel.frame = CGRect(x: 50, y: 400, width: 250, height: 100)
        resultLabel.numberOfLines = 0
        resultLabel.textAlignment = .center
        self.view.addSubview(resultLabel)

    }
    
    @objc func link() {
        

        AGCAuth.instance().currentUser?.link(.weiXin, controller: self).onSuccess { (result) in

            self.resultLabel.text = "link success"

        }.onFailure{ (error) in

            self.resultLabel.text = error.localizedDescription

        }
    }


    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
