//
//  LoginViewController.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 14/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    
    @IBOutlet weak var loginHolderView: UIView!
    @IBOutlet weak var getOtpButton: UIButton!
    @IBOutlet weak var mobileNumberTextField: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupViews()
    }
    
    private func setupViews() {
        loginHolderView.rounded()
        getOtpButton.rounded()
        mobileNumberTextField.setupRightImage(imageName: "ic_mobile")
    }
    
    @IBAction func onGetOtpClicked(_ sender: Any) {
        
        mobileNumberTextField.endEditing(true)
        mobileNumberTextField.text = ""
        if let vc = UIStoryboard(name: "Login", bundle: nil).instantiateViewController(withIdentifier: "OtpScreen") as? OtpViewController
        {
            present(vc, animated: true, completion: nil)
        }
    }
}

