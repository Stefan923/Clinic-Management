package com.sanitas.clinicapp.hr;

import java.util.List;

public class HrMVC {
    public static void main(String[] args) {
        HrModel model=new HrModel();
        List<Employee> list=model.getAllData("","","");
    }
}
