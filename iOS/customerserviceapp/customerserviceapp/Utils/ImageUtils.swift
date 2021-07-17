//
//  ImageUtils.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 17/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit

struct ImageUtils {
    
    mutating func convertImageToBase64String(_ image:UIImage?) -> String {
        guard let encodedValue = image?.jpegData(compressionQuality: 0.0)?.base64EncodedString(options: .lineLength76Characters) else {
            fatalError("Error converting to base 64")
        }
        return encodedValue
    }
}
