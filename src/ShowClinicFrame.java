import db.Clinic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShowClinicFrame extends JFrame {

    public ShowClinicFrame(Clinic clinic) {
        this.setBounds(150,140,700,600);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(clinic.getName() + " - " + clinic.getDoctorName() + " - " + clinic.getSpecialty());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               Main frame = new Main();
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

        PatientForm panel1 = new PatientForm();
        ShowAllPatientPanel panel2 = new ShowAllPatientPanel();
        AppointmentPanel panel3 = new AppointmentPanel();
        ShowAllAppointmentPanel panel4 = new ShowAllAppointmentPanel();

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
            cardLayout.show(panelCenter , "b");
        });
        buttonAppointment.addActionListener(e->{
            cardLayout.show(panelCenter , "c");
        });

        buttonShowAllAppointment.addActionListener(e->{
            cardLayout.show(panelCenter , "d");
        });



    }



    class ShowAllPatientPanel extends JPanel{

        public ShowAllPatientPanel() {
            setBackground(Color.GREEN);

        }
    }

    class AppointmentPanel extends JPanel{

        public AppointmentPanel() {
            setBackground(Color.YELLOW);

        }
    }

    class ShowAllAppointmentPanel extends JPanel{

        public ShowAllAppointmentPanel() {
            setBackground(Color.PINK);

        }
    }



}
