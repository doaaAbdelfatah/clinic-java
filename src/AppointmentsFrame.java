
import db.*;
import db.dao.AppointmentDAO;
import db.dao.PatientDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentsFrame extends JPanel {
    private JPanel panel1;
    private JLabel appointmentsDateLabel;
    private JTextField textFieldPatientId;
    private JTextField textFieldAppointmentDate;
    private JTextField textFieldClinicId;
    private JTextField textFieldMedicalDiagnosis;
    private JTextField textFieldNotes;
    private JTextField textFieldBillAmount;
    private JButton buttonClear;
    private JButton buttonSave;
    private JTextField textFieldUserId;
    private JPanel PanelNorth;
    private JPanel PanelWest;
    private JLabel appointmentMangaementsLabel;
    private JComboBox<Patient> comboBoxPatient;
    private JTextField textFieldId;

    private Clinic clinic;
private User user;
    public AppointmentsFrame(Clinic clinic , User user) {
        this.add(panel1);
        this.user=user;
        this.clinic = clinic;
        this.setBounds(150, 100, 700, 550);

        try {
            Connection connection = DBConfig.createConnection();
            PatientDAO patientDao = new PatientDAO(connection);
            ArrayList<Patient> allPatients= patientDao.getByClinc(clinic.getId());
            for (Patient p: allPatients){
                comboBoxPatient.addItem(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        comboBoxPatient.setModel(dm);
        buttonSave.addActionListener(e -> {
            try {
                save();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonClear.addActionListener(e -> {
            clearFields();
        });
    }

    private void save() throws SQLException {
        try {
            Connection connection = DBConfig.createConnection();
            AppointmentDAO appointmentsDao = new AppointmentDAO(connection);
            Appointment appointments = new Appointment();
            String appointmentDateString = textFieldAppointmentDate.getText();
            if (appointmentDateString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid appointment date.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            java.sql.Date appointmentDate = java.sql.Date.valueOf(appointmentDateString);
            appointments.setAppointmentDate(appointmentDate);
//             appointments.setClinicId(Integer.parseInt(textFieldClinicId.getText()));
//              appointments.setPatientId(Integer.parseInt(textFieldPatintId.getText()));
            appointments.setMedicalDiagnosis(textFieldMedicalDiagnosis.getText());
            appointments.setNotes(textFieldNotes.getText());
            appointments.setBillAmount(Double.parseDouble(textFieldBillAmount.getText()));
           appointments.setClinic(clinic);
//            PatientDao patientDao= new PatientDao(connection);
//            Patient patient= patientDao.get(Integer.parseInt(textFieldPatientId.getText()));
          Patient patient = (Patient) comboBoxPatient.getSelectedItem();
           appointments.setPatient(patient);
              appointments.setUser(user);

            appointmentsDao.add(appointments);

            JOptionPane.showMessageDialog(this, "Appointment added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        // textFieldId.setText("");
        textFieldAppointmentDate.setText("");
        textFieldClinicId.setText("");
        textFieldPatientId.setText("");
        textFieldMedicalDiagnosis.setText("");
        textFieldNotes.setText("");
        textFieldBillAmount.setText("");
        textFieldUserId.setText("");
    }
}




