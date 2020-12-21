package com.sanitas.clinicapp.hr.addemployee;

public class AddMVC {
    public AddMVC(){
        AddModel model=new AddModel();
        AddView view =new AddView();
        AddController controller= new AddController(model,view);

        view.setVisible(true);
    }
}
