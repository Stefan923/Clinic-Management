package com.sanitas.clinicapp.homepage;

import com.sanitas.clinicapp.fr.FrMVC;
import com.sanitas.clinicapp.hr.HrMVC;
import com.sanitas.clinicapp.mr.MrMVC;
import com.sanitas.clinicapp.profile.ProfileMVC;

public class HomePageModel {

    public void openMVC(int number, HomePageView view) {
        switch (number) {
            case 1:
                new HrMVC();
                break;
            case 2:
                new FrMVC(view);
                break;
            case 3:
                new MrMVC(view);
                break;
            case 4:
                new ProfileMVC(view);
                break;
            default:
                break;
        }
    }

}
