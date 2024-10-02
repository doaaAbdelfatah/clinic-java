import javax.swing.*;

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

    public PatientForm() {

        this.add(panel1);

    }
}
