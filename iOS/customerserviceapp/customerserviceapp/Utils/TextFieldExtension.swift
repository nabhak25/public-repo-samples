//
//  TextFieldExtension.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 14/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit

extension UITextField {
    //MARK:- Set Image on the right of text fields
    
    func setupRightImage(imageName:String){
        let imageView = UIImageView(frame: CGRect(x: 20, y: 5, width: 30, height: 30))
        imageView.image = UIImage(named: imageName)
        imageView.image?.withTintColor(.lightGray)
        let imageContainerView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 60, height: 40))
        imageContainerView.addSubview(imageView)
        rightView = imageContainerView
        rightViewMode = .always
        self.tintColor = .lightGray
    }
    
    //MARK:- Set Image on left of text fields
    
    func setupLeftImage(imageName:String){
        let imageView = UIImageView(frame: CGRect(x: 10, y: 10, width: 20, height: 20))
        imageView.image = UIImage(named: imageName)
        let imageContainerView: UIView = UIView(frame: CGRect(x: 0, y: 0, width: 55, height: 40))
        imageContainerView.addSubview(imageView)
        leftView = imageContainerView
        leftViewMode = .always
        self.tintColor = .lightGray
    }
}
