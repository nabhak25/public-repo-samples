//
//  Incident.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 17/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

struct Incident: Codable {
    
    var srNo: Int
    var incidentCd: Int
    var incidentType: String
    var langCd: Int
    

    enum CodingKeys: String, CodingKey {
        case srNo = "sr_no"
        case incidentCd = "INCIDENT_CD"
        case incidentType = "INCIDENT_TYPE"
        case langCd = "LANG_CD"
    }
}
