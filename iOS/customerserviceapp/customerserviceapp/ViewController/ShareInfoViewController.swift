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
    
    @IBOutlet weak var nameTextField: UITextField!
    
    @IBOutlet weak var mobileNumberTextField: UITextField!
    
    @IBOutlet weak var addressTextField: UITextField!
    
    @IBOutlet weak var natureTextField: UITextField!
    
    @IBOutlet weak var placeTextField: UITextField!
    
    @IBOutlet weak var districtTextField: UITextField!
    
    @IBOutlet weak var stationTextField: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setupImageForTextFields()
        self.navigationController?.navigationBar.barStyle = .default
        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        self.navigationItem.title = "Share Information"
    }
    
    private func setupImageForTextFields() {
        nameTextField.setupRightImage(imageName: "ic_account")
        mobileNumberTextField.setupRightImage(imageName: "ic_mobile")
        addressTextField.setupRightImage(imageName: "ic_place")
        natureTextField.setupRightImage(imageName: "ic_arrow_down")
        districtTextField.setupRightImage(imageName: "ic_arrow_down")
        stationTextField.setupRightImage(imageName: "ic_arrow_down")
        placeTextField.setupRightImage(imageName: "ic_place")
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
