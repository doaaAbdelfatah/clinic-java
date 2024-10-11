import db.*;
import db.dao.AppointmentDAO;
import db.dao.PatientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowClinicFrame extends JFrame {

    private User user;
    public ShowClinicFrame(Clinic clinic ,User user) {
        this.user=user;
        this.setBounds(150,140,700,600);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(clinic.getName() + " - " + clinic.getDoctorName() + " - " + clinic.getSpecialty());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               Main frame = new Main(user);
               frame.pack();
               frame.setVisible(true);
            }
        });

        Font font = new Font("Tahoma" , Font.PLAIN ,16);
        JPanel panelNorth = new JPanel();
        getContentPane().add(panelNorth ,BorderLayout.NORTH);

        JButton buttonAddPatient = new JButton("Add New Patient");
        JButton buttonShowAllPatients = new JButton("Show All Patients");
        JButton buttonAppointment = new JButton("Add New Appointment");
        JButton buttonShowAllAppointment = new JButton("Show All Appointments");

        buttonAddPatient.setPreferredSize(new Dimension(200 ,100));
        buttonAddPatient.setFont(font);
        buttonAddPatient.setContentAreaFilled(false);

        buttonShowAllPatients.setPreferredSize(new Dimension(200 ,100));
        buttonShowAllPatients.setFont(font);
        buttonShowAllPatients.setContentAreaFilled(false);

        buttonAppointment.setPreferredSize(new Dimension(200 ,100));
        buttonAppointment.setFont(font);
        buttonAppointment.setContentAreaFilled(false);

        buttonShowAllAppointment.setPreferredSize(new Dimension(200 ,100));
        buttonShowAllAppointment.setFont(font);
        buttonShowAllAppointment.setContentAreaFilled(false);

        panelNorth.add(buttonAddPatient);
        panelNorth.add(buttonShowAllPatients);
        panelNorth.add(buttonAppointment);
        panelNorth.add(buttonShowAllAppointment);

        PatientForm panel1 = new PatientForm(clinic);
        ShowAllPatientPanel panel2 = new ShowAllPatientPanel(clinic);
        AppointmentsFrame panel3 = new AppointmentsFrame(clinic,user);
        ShowAllAppointmentPanel panel4 = new ShowAllAppointmentPanel(clinic);

        CardLayout cardLayout =new CardLayout();
        JPanel panelCenter = new JPanel(cardLayout);

        this.getContentPane().add(panelCenter);
        panelCenter.add(panel3 , "c");
        panelCenter.add(panel1 , "a");
        panelCenter.add(panel2 , "b");

        panelCenter.add(panel4 , "d");

        buttonAddPatient.addActionListener(e->{
            cardLayout.show(panelCenter , "a");
        });
        buttonShowAllPatients.addActionListener(e->{
            panel2.fillTable();
            cardLayout.show(panelCenter , "b");
        });
        buttonAppointment.addActionListener(e->{
            cardLayout.show(panelCenter , "c");
        });

        buttonShowAllAppointment.addActionListener(e->{
            panel4.fillTable();
            cardLayout.show(panelCenter , "d");
        });



    }




    class ShowAllPatientPanel extends JPanel {
        private DefaultTableModel dtm;
        private JTable table;
        private Clinic clinic;
        public ShowAllPatientPanel(Clinic clinic) {
            this.clinic =clinic;
            this.setLayout(new BorderLayout());
            String[] columns = {"id", "name", "action", "gender", "birth_date", "address", "phone", "mobile", "email", "disease", "medical_diagnosis", "created_at" ,"clinic" };
            dtm = new DefaultTableModel(null, columns);
            table = new JTable(dtm);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                   int row = table.getSelectedRow();
                   String pid =  table.getValueAt(row , 0).toString();
                   Patient patient;
                    try {
                        Connection cn = DBConfig.createConnection();
                        PatientDAO patientDAO =new PatientDAO(cn);
                        patient = patientDAO.get(Integer.parseInt(pid));
                        PatientDetailsForm patientDetailsForm = new PatientDetailsForm(patient);
                        patientDetailsForm.setVisible(true);


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });

            Font font = new Font("Arial", Font.PLAIN, 18);
            table.setFont(font);
            table.setForeground(new Color(100, 160, 100));
            table.getTableHeader().setFont(font);
            table.getTableHeader().setForeground(Color.darkGray);
            fillTable();
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane);
            JButton buttonDelete = new JButton("Delete");
            buttonDelete.setBackground(Color.red);
            this.add(buttonDelete, BorderLayout.SOUTH);
        }
        private void fillTable() {
            dtm.setRowCount(0);
            try {
                Connection cn = DBConfig.createConnection();
                PatientDAO patientDao = new PatientDAO(cn);
                ArrayList<Patient> list = patientDao.getByClinc(clinic.getId());
                for (Patient patient : list) {
                    String[] row = {
                            patient.getId() + "",
                            patient.getName(),
                            patient.getGender().name(),
                            patient.getBirthDate().toString(),
                            patient.getAddress(),
                            patient.getPhone(),
                            patient.getMobile(),
                            patient.getEmail(),
                            patient.getDisease(),
                            patient.getMedicalDiagnosis(),
                            patient.getCreatedAt().toString(),
                            patient.getClinic().getName()
                    };
                    dtm.addRow(row);
                    cn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    class ShowAllAppointmentPanel extends JPanel {
        private DefaultTableModel dtm;
        private JTable table;
        private Clinic clinic;

        public ShowAllAppointmentPanel(Clinic clinic) {
            setLayout(new BorderLayout());
            setBackground(Color.PINK);
            this.setLayout(new BorderLayout());
            this.clinic = clinic;
            setBackground(Color.YELLOW);
            String[] columns = {"id", " appointment_date", " clinic_id", "patient_id","patient Name", "medical_diagnosis",
                    "notes", "bill_amount", "user"};
            dtm = new DefaultTableModel(null, columns);
            table = new JTable(dtm);
            Font font = new Font("Arial", Font.PLAIN, 20);
            table.setFont(font);
            table.setForeground(Color.DARK_GRAY);
            table.getTableHeader().setFont(font);
            table.getTableHeader().setBackground(new Color(100, 227, 212));

            fillTable();
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane, BorderLayout.CENTER);

            JButton buttonDelete = new JButton("Delete");
            this.add(buttonDelete, BorderLayout.SOUTH);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    String pid =  table.getValueAt(row , 3).toString();
                    Patient patient;
                    try {
                        Connection cn = DBConfig.createConnection();
                        PatientDAO patientDAO =new PatientDAO(cn);
                        patient = patientDAO.get(Integer.parseInt(pid));
                        PatientDetailsForm patientDetailsForm = new PatientDetailsForm(patient);
                        patientDetailsForm.setVisible(true);


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
        }

        private void fillTable() {
            dtm.setRowCount(0);
            Connection a = null;
            try {
                a = DBConfig.createConnection();

                AppointmentDAO appointmentsDao = new AppointmentDAO(a);
                ArrayList<Appointment> arrayList = appointmentsDao.getByClinic(clinic.getId());
                ArrayList<Appointment> appointments = new ArrayList<>();
                for (Appointment appointment : arrayList) {
                    String[] row = {
                            appointment.getId() + "",
                            appointment.getAppointmentDate().toString(),
                            appointment.getClinic().getName(),
                            appointment.getPatient().getId() +"",
                            appointment.getPatient().getName(),
                            appointment.getMedicalDiagnosis(),
                            appointment.getNotes(),
                            appointment.getBillAmount() + "",
                            appointment.getUser().getName()
                    };
                    dtm.addRow(row);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
