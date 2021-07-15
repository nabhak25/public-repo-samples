//
//  HomeViewController.swift
//  customerserviceapp
//
//  Created by Nabha Kamath on 15/07/21.
//  Copyright © 2021 Next e-Services. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController {
    @IBOutlet weak var shareInfoButton: UIButton!
    
    @IBOutlet weak var viewInfoButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupViews()
    }
    
    private func setupViews() {
        shareInfoButton.rounded()
        viewInfoButton.rounded()
    }
    
    
    @IBAction func shareInfoClicked(_ sender: UIButton) {
        print("share info")
    }
    
    
    @IBAction func viewInfoClicked(_ sender: UIButton) {
        print("view info")
        if let vc = UIStoryboard(name: "Details", bundle: nil).instantiateViewController(withIdentifier: "viewInfoScreen") as? ViewInfoTableViewController
        {
            present(vc, animated: true, completion: nil)
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
