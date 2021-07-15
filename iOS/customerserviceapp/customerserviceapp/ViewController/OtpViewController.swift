//
//  OtpViewController.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 15/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit
import OTPInputView

class OtpViewController: UIViewController {
    
    private var otpString: String = ""
    @IBOutlet weak var otpInputField: OTPInputView!
    @IBOutlet weak var verifyButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpViews()
        otpInputField.delegateOTP = self
    }
    
    @IBAction func onVerifyButtonClicked(_ sender: Any) {
        otpInputField.otpFetch()
    }
    
    private func setUpViews() {
        verifyButton.rounded()
    }
}

extension OtpViewController: OTPViewDelegate {
    
    func didFinishedEnterOTP(otpNumber: String) {
        print("OTP entered: \(otpNumber)")
    }
    
    func otpNotValid() {
        print("Error")
    }
}
