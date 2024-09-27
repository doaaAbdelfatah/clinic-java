import db.Clinic;
import db.DBConfig;
import db.dao.ClinicDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClinicFrame extends JFrame {
    private JPanel panel1;
    private JPanel panelWest;
    private JPanel panelNorth;
    private JTextField textFieldName;
    private JTextField textFieldSpecialty;
    private JTextField textFieldAddress;
    private JTextField textFieldPhone;
    private JTextField textFieldMobile;
    private JTextField textFieldEmail;
    private JButton buttonSave;
    private JTextField textFieldDoctorName;

    private DefaultTableModel dtm ;
    private JTable table;
    public ClinicFrame() {
        this.setBounds(150,140,700,600);
        this.getContentPane().add(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        String[] columns = {"id", "name", "Doctor", "specialty", "address", "phone", "mobile", "email"};
        dtm = new DefaultTableModel(null, columns);
        table = new JTable(dtm);
        Font font = new Font("Arial" ,Font.PLAIN ,18);
        table.setFont(font);
        table.setForeground(new Color(104,28,120)); //104, 28, 120
        table.getTableHeader().setFont(font);
        table.getTableHeader().setForeground(new Color(7, 101, 255)); //rgb(7, 101, 255)
        fillTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel1.add(scrollPane);
        buttonSave.addActionListener(e->{ save(); });
    }


    private void save(){
        // validation

        // save data

        try {
            Connection cn  =DBConfig.createConnection();
            ClinicDAO clinicDAO = new ClinicDAO(cn);

            Clinic clinic = new Clinic();
            clinic.setName(textFieldName.getText());
            clinic.setPhone(textFieldPhone.getText());
            clinic.setMobile(textFieldMobile.getText());
            clinic.setEmail(textFieldEmail.getText());
            clinic.setAddress(textFieldAddress.getText());
            clinic.setSpecialty(textFieldSpecialty.getText());
            clinic.setDoctorName(textFieldDoctorName.getText());

            clinicDAO.add(clinic);
            cn.close();

            fillTable();

            JOptionPane.showMessageDialog(this ,"Clinic Added Successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void fillTable(){
        dtm.setRowCount(0);
        try {
            Connection cn = DBConfig.createConnection();
            ClinicDAO clinicDAO = new ClinicDAO(cn);
            ArrayList<Clinic> clinicArrayList = clinicDAO.get();
            for (Clinic clinic : clinicArrayList){
                String [] row ={
                        clinic.getId()+"",
                        clinic.getName(),
                        clinic.getDoctorName(),
                        clinic.getSpecialty(),
                        clinic.getAddress(),
                        clinic.getPhone(),
                        clinic.getMobile(),
                        clinic.getEmail()
                };
                dtm.addRow(row);
            }
            cn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
