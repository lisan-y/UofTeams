package log_out.ui;

import log_out.interface_adapters.LogOutController;
import log_out.interface_adapters.LogOutUserInputData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogOutView extends JPanel implements ActionListener {

    private final LogOutController controller;

    public JButton logOutButton = new JButton("Log Out");

    public LogOutView(LogOutController controller){
        this.controller = controller;

        logOutButton.addActionListener(this);
        this.add(logOutButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(logOutButton)) {
            String logOut = "Log Out";
            LogOutUserInputData data = new LogOutUserInputData(logOut);
            controller.logOutInitializer(data);
        }
    }
}
