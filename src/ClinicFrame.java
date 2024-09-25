import javax.swing.*;

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
    private JTextField textField1;

    public ClinicFrame() {
        this.setBounds(150,140,700,600);
        this.getContentPane().add(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
