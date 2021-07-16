//
//  ShareInfoViewController.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 15/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit
import ValidationTextField

class ShareInfoViewController: UIViewController {
    @IBOutlet weak var nameTextField: ValidationTextField!
    
    @IBOutlet weak var mobileNumberTextField: ValidationTextField!
    
    @IBOutlet weak var addressTextField: ValidationTextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setupImageForTextFields()
    }
    
    private func setupImageForTextFields() {
        nameTextField.setupRightImage(imageName: "ic_mobile")
        mobileNumberTextField.setupRightImage(imageName: "ic_mobile")
        addressTextField.setupRightImage(imageName: "ic_mobile")
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
