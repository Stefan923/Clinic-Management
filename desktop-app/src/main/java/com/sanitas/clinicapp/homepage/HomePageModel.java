package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.hr.HrMVC;

public class HomePageModel {

    public void openMVC(int number) {
        switch (number) {
            case 1:
                new HrMVC();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

}
