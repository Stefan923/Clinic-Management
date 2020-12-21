package com.sanitas.clinicapp.hr.addemployee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddController {

    private AddModel model;
    private AddView view;

    AddController(AddModel model,AddView view){
        this.model=model;
        this.view=view;

        view.addBtnAddEmployeeListener(new ButtonListenerAdd());
    }
    class ButtonListenerAdd implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            List<String> txt=new ArrayList<String>();
            for(int i=0;i<12;i++)
            {
                txt.add(view.getTxt(i));
            }
            if(model.insertEmployee(txt.get(0), txt.get(1), txt.get(2), txt.get(3), txt.get(4), txt.get(5), Integer.parseInt(txt.get(6)), txt.get(7),
                    txt.get(8), Double.parseDouble(txt.get(9)), Integer.parseInt(txt.get(10))))
            {
                view.setTxtEmpty();
            }
            else
                view.sendError("Angajat existent!");
        }
    }
}
