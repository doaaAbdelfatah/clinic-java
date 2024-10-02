import db.Clinic;
import db.DBConfig;
import db.dao.ClinicDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends JFrame {
    public Main(){
        setTitle("clinics program");
        setLocation(350,250);
//        setBounds(350,250,500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menuManage = new JMenu("Manage");
        jMenuBar.add(menuManage);
        setJMenuBar(jMenuBar);
        JMenuItem menuItemClinic = new JMenuItem("Manage Clinics");
        menuManage.add(menuItemClinic);
        menuItemClinic.addActionListener(e->{
            ClinicFrame clinicFrame = new ClinicFrame();
            clinicFrame.setVisible(true);
            this.setVisible(false); // Hide Main
        });

        JPanel panelFlow = new JPanel();
        panelFlow.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        getContentPane().add(panelFlow);

        try {
            Connection cn = DBConfig.createConnection();
            ClinicDAO clinicDAO = new ClinicDAO(cn);
            ArrayList<Clinic> clinicArrayList = clinicDAO.get();
            for (Clinic clinic : clinicArrayList){
                JButton button = new JButton("<html><center>" + clinic.getName() +"<br><br>" + clinic.getDoctorName() +"</center></html>");
                button.setPreferredSize(new Dimension(200,200));
                button.setContentAreaFilled(false);
                button.setFont(new Font("Arial" ,Font.BOLD ,16));
                panelFlow.add(button);
                button.addActionListener(e->{
                    new ShowClinicFrame(clinic).setVisible(true);
                    this.setVisible(false);
                });
            }
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.pack();
        main.setVisible(true);
    }
}
