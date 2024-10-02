import db.Clinic;
import db.DBConfig;
import db.dao.ClinicDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private JButton buttonDelete;
    private JButton buttonHome;

    private DefaultTableModel dtm ;
    private JTable table;
    public ClinicFrame() {
        this.setBounds(150,140,700,600);
        this.getContentPane().add(panel1);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        buttonDelete.addActionListener(e->{ delete(); });
        buttonHome.addActionListener(e->{
            showMain();
            this.setVisible(false); // hide clinicFrame
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showMain();
            }
        });
    }
    private void showMain(){
        Main m = new Main();
        m.pack();
        m.setVisible(true);
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

    private void delete(){
        // validation

        // save data

        try {
            int rowNum =table.getSelectedRow();
            int id = Integer.parseInt(((String) dtm.getValueAt(rowNum , 0)));
            Connection cn  =DBConfig.createConnection();
            ClinicDAO clinicDAO = new ClinicDAO(cn);
            clinicDAO.delete(id); // delete from DB
            cn.close();
            dtm.removeRow(rowNum); // delete from table
            JOptionPane.showMessageDialog(this ,"Clinic Deleted Successfully");

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
