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
    @IBOutlet weak var retryButton: UIButton!
    
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
    @IBAction func retryButtonClicked(_ sender: Any) {
        print("Retry called...")
    }
}

extension OtpViewController: OTPViewDelegate {
    
    func didFinishedEnterOTP(otpNumber: String) {
        print("OTP entered: \(otpNumber)")
        if let vc = UIStoryboard(name: "Details", bundle: nil).instantiateViewController(withIdentifier: "HomeScreen") as? HomeViewController
        {
            present(vc, animated: true, completion: nil)
        }
    }
    
    func otpNotValid() {
        print("Error")
        //        if let vc = UIStoryboard(name: "Details", bundle: nil).instantiateViewController(withIdentifier: "HomeScreen") as? HomeViewController
        //        {
        //            present(vc, animated: true, completion: nil)
        //        }
        
        //        if let vc = UIStoryboard(name: "Details", bundle: nil).instantiateViewController(identifier: "HomeScreen") as? HomeViewController {
        //            let navController = UINavigationController(rootViewController: vc) // Creating a navigation controller with vc at the root of the navigation stack.
        //            present(navController, animated:true, completion: nil)
        //        }
        
        let parentVC = presentingViewController
        dismiss(animated: true) {
            if let vc = UIStoryboard(name: "Details", bundle: nil).instantiateViewController(identifier: "HomeScreen") as? HomeViewController {
                let navController = UINavigationController(rootViewController: vc) // Creating a navigation controller with vc at the root of the navigation stack.
                navController.navigationBar.barTintColor = #colorLiteral(red: 0.3411764801, green: 0.6235294342, blue: 0.1686274558, alpha: 1)
                navController.navigationBar.tintColor = UIColor.white
                parentVC?.present(navController, animated:true, completion: nil)
            }
        }
    }
}
