import db.Clinic;
import db.DBConfig;
import db.Patient;
import db.dao.ClinicDAO;
import db.dao.PatientDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class PatientForm extends JPanel {
    private JTextField textFieldName;
    private JRadioButton femaleRadioButton;
    private JRadioButton maleRadioButton;
    private JTextField textFieldBirthDate;
    private JTextField textFieldAddress;
    private JTextField textFieldPhone;
    private JPanel panel1;
    private JTextField textFieldMobile;
    private JTextField textFieldEmail;
    private JTextArea textAreaDisease;
    private JTextArea textAreaMedicalDiagnosis;
    private JButton buttonSave;
    private JButton buttonNew;
    private Clinic clinic;


    public PatientForm(Clinic clinic) {
        this.clinic=clinic;
        this.add(panel1);
        buttonSave.addActionListener(e->{
            save();
        });
    }

    private void save(){
        // validation

        // save data

        try {
            Connection cn  = DBConfig.createConnection();
            PatientDAO patientDAO = new PatientDAO(cn);

            Patient patient = new Patient();
            patient.setName(textFieldName.getText());
            patient.setBirthDate(new Date()); // textFieldBirthDate.getText()
            patient.setPhone(textFieldPhone.getText());
            patient.setMobile(textFieldMobile.getText());
            patient.setEmail(textFieldEmail.getText());
            patient.setAddress(textFieldAddress.getText());
            patient.setDisease(textAreaDisease.getText());
            patient.setMedicalDiagnosis(textAreaMedicalDiagnosis.getText());
            patient.setClinic(clinic);

            if(maleRadioButton.isSelected()) {
                patient.setGender(Patient.GENDER.male);
            }else{
                patient.setGender(Patient.GENDER.female);
            }
            patientDAO.add(patient);
            cn.close();

            JOptionPane.showMessageDialog(this ,"Patient Added Successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
