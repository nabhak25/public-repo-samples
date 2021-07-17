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
    
    @IBOutlet weak var suspectThingTextField: UITextField!
    
    @IBOutlet weak var problemDescriptionTextField: UITextField!
    let districtPickerView = UIPickerView()
    
    let incidentPickerView = UIPickerView()
    
    private let jsonReader = JsonReader()
    private let imagePicker = UIImagePickerController()
    private var imageUtils = ImageUtils()
    
    var districtModel = [District]()
    var incidentModel = [Incident]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupImageForTextFields()
        setupNavigationBar()
        readJsonFile()
        setUpPickerView()
    }
    
    private func setUpPickerView() {
        districtPickerView.dataSource = self
        districtPickerView.delegate = self
        districtPickerView.tag = 1
        
        incidentPickerView.dataSource = self
        incidentPickerView.delegate = self
        incidentPickerView.tag = 2
        
        districtTextField.delegate = self
        districtTextField.inputView = districtPickerView
        
        natureTextField.delegate = self
        natureTextField.inputView = incidentPickerView
        
        nameTextField.delegate = self
        mobileNumberTextField.delegate = self
        addressTextField.delegate = self
        placeTextField.delegate = self
        stationTextField.delegate = self
        suspectThingTextField.delegate = self
        problemDescriptionTextField.delegate = self
        
        // ToolBar
        let toolBar = UIToolbar()
        toolBar.barStyle = .default
        toolBar.isTranslucent = true
        toolBar.tintColor = UIColor(red: 92/255, green: 216/255, blue: 255/255, alpha: 1)
        toolBar.sizeToFit()
        
        // Adding Button ToolBar
        let doneButton = UIBarButtonItem(title: "Done", style: .plain, target: self, action: #selector(ShareInfoViewController.doneClick))
        let spaceButton = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let cancelButton = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(ShareInfoViewController.cancelClick))
        toolBar.setItems([cancelButton, spaceButton, doneButton], animated: false)
        toolBar.isUserInteractionEnabled = true
        districtTextField.inputAccessoryView = toolBar
        natureTextField.inputAccessoryView = toolBar
    }
    
    @objc private func doneClick() {
        districtTextField.resignFirstResponder()
        natureTextField.resignFirstResponder()
    }
    
    @objc private func cancelClick() {
        districtTextField.resignFirstResponder()
        natureTextField.resignFirstResponder()
    }
    
    private func readJsonFile() {
        let districtJsonData = jsonReader.readLocalFile(forName: "district")
        let incidentJsonData = jsonReader.readLocalFile(forName: "incident")
        districtModel = jsonReader.parseDistricts(json: districtJsonData!)
        incidentModel = jsonReader.parseIncidents(json: incidentJsonData!)
        print("data")
        
    }
    
    private func setupNavigationBar() {
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
    
    
    @IBAction func onSubmitClicked(_ sender: UIButton) {
        print("Submit")
    }
    
    @IBAction func uploadPhotoAction(_ sender: UIButton) {
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            imagePicker.sourceType = .camera
            self.present(imagePicker, animated: true, completion: nil)
        } else {
            print("Camera not available")
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

extension ShareInfoViewController: UIPickerViewDelegate, UIPickerViewDataSource, UITextFieldDelegate {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1 {
            return districtModel.count
        }
        return incidentModel.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1 {
            return districtModel[row].district
        }
        return incidentModel[row].incidentType
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1 {
            let districtAtRow = districtModel[row]
            districtTextField.text = districtAtRow.district
        } else {
            let incidentAtRow = incidentModel[row]
            natureTextField.text = incidentAtRow.incidentType
        }
        
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        return textField.tag != 1 && textField.tag != 2
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
}

extension ShareInfoViewController: UIImagePickerControllerDelegate {
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        
        guard let userPickedImage = info[.originalImage] as? UIImage else {
            fatalError("Expected an image")
        }
        
        let base64String = imageUtils.convertImageToBase64String(userPickedImage)
        print("Image base 64: \(base64String)")
        
    }
}
