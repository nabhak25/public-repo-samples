//
//  District.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 17/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

struct District: Codable {
    var districtId: Int
    var districtCd: Int
    var langCd: Int
    var stateCd: Int
    var rangeCd: Int
    var district: String
    var recordCreatedOn: String
    
    enum CodingKeys: String, CodingKey {
        case districtId = "DISTRICT_ID"
        case districtCd = "DISTRICT_CD"
        case langCd = "LANG_CD"
        case stateCd = "STATE_CD"
        case rangeCd = "RANGE_CD"
        case district = "DISTRICT"
        case recordCreatedOn = "RECORD_CREATED_ON"
    }
}
