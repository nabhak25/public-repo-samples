//
//  JsonReader.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 17/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import Foundation

struct JsonReader {
    
    func readLocalFile(forName name: String) -> Data? {
        do {
            if let bundlePath = Bundle.main.path(forResource: name,
                                                 ofType: "json"),
                let jsonData = try String(contentsOfFile: bundlePath).data(using: .utf8) {
                return jsonData
            }
        } catch {
            print(error)
        }
        
        return nil
    }
    
    func parseDistricts(json: Data) -> [District] {
        let decoder = JSONDecoder()
        var result = [District]()

        if let jsonPetitions = try? decoder.decode([District].self, from: json) {
            result = jsonPetitions
        }
        
        return result
    }
    
    func parseIncidents(json: Data) -> [Incident] {
        let decoder = JSONDecoder()
        var result = [Incident]()

        if let jsonPetitions = try? decoder.decode([Incident].self, from: json) {
            result = jsonPetitions
        }
        
        return result
    }
}
