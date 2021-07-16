//
//  InfoTableViewCell.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 15/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit

class InfoTableViewCell: UITableViewCell {
    @IBOutlet weak var downloadButton: UIButton!
    @IBOutlet weak var viewLayout: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        downloadButton.rounded()
        viewLayout.rounded()
    }

    @IBAction func downloadClicked(_ sender: UIButton) {
        print("Downlaod...")
    }
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
