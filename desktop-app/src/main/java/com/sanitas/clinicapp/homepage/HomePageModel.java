package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.hr.HrMVC;
import com.sanitas.clinicapp.mr.MrMVC;

public class HomePageModel {

    public void openMVC(int number, HomePageView view) {
        switch (number) {
            case 1:
                new HrMVC();
                break;
            case 2:
                break;
            case 3:
                new MrMVC(view);
                break;
            case 4:
                break;
            default:
                break;
        }
    }

}
