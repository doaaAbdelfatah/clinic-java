
import db.DBConfig;
import db.Patient;
import db.dao.PatientDAO;
import db.dao.PatientDocumentsDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PatientDocuments extends JPanel {
    private JPanel panel1;
    private JPanel PanelNorth;
    private JTextField textFieldName;
    private JTextField textFieldFile;
    private JTextField textFieldResult;
    private JTextField textFieldComments;
    private JComboBox comboBox1;
    private JPanel PanelWest;
    private JButton buttonSave;
    private JButton buttonClear;
    private JButton ButtonDelete;
    private JLabel DocumentManage;
    private JTextField textFieldCreatesAt;
    private JTextField textFieldPatientId;
    private JLabel LabelName;
    private JLabel LabelFile;
    private JLabel LabelResult;
    private DefaultTableModel dtm;
    private JTable table;

    private Patient patient;
    public PatientDocuments(Patient patient) {
        this.patient =patient;
//        this.setBounds(150, 100, 700, 500);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.add(panel1);

        //id, name, file, type, result, comments, created_at, patient_id

        String[] columns = {"id", "name", "file", "type", "result", "comments", " created_at", " patient_id"};
        dtm = new DefaultTableModel(null, columns);
        table = new JTable(dtm);
        Font font = new Font("Arial", Font.PLAIN, 20);
        table.setFont(font);
        table.setForeground(Color.DARK_GRAY);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setBackground(new Color(100, 227, 212));
        fillTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel1.add(scrollPane);
        buttonSave.addActionListener(e -> {
            save();
        });
    }
        private void save() {
            try {

                Connection connection = DBConfig.createConnection();
               PatientDocumentsDao patientDocumentsDao = new PatientDocumentsDao(connection);

                db.PatientDocuments patientDocuments = new db.PatientDocuments();
                patientDocuments.setName(textFieldName.getText());
                patientDocuments.setFile(textFieldFile.getText());
                patientDocuments.setResult(textFieldResult.getText());
                patientDocuments.setComments(textFieldComments.getText());
                patientDocuments.setCreatedAt(new Date());
                patientDocuments.setType(comboBox1.getSelectedItem().toString());

                patientDocuments.setPatient(this.patient);
                patientDocumentsDao.add(patientDocuments);
                fillTable();

                JOptionPane.showMessageDialog(this, "Patient Document added successfully");
            } catch (SQLException e) {
e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Patient Document added  Error");
            }
        }
    private void  fillTable() {
                dtm.setRowCount(0);
                try {
                    Connection pd = DBConfig.createConnection();
                    PatientDocumentsDao patientDocumentsDao = new PatientDocumentsDao(pd);
                    ArrayList<db.PatientDocuments> documentsList = patientDocumentsDao.getByPatientID(this.patient.getId());

                    for (db.PatientDocuments document : documentsList) {
                        String[] row = {
                              //  document.getId();
                                document.getName(),
                                document.getFile(),
                              //  document.getType(),
                                document.getResult(),
                                document.getComments(),
                                document.getCreatedAt().toString(),

                        };
                        dtm.addRow(row);
                    }
                    pd.close();
                } catch (SQLException e) {

                    JOptionPane.showMessageDialog(this, "");
                }
                buttonClear.addActionListener(e -> {

                    textFieldName.setText("");
                    textFieldFile.setText("");
                   textFieldResult.setText("");
                    textFieldComments.setText("");
                   textFieldCreatesAt.setText("");
                   textFieldPatientId.setText("");
                });
                table.getModel().addTableModelListener(e -> {
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    if (row >= 0 && row < table.getRowCount() && col >= 0 && col < table.getColumnCount()) {
                        String value = (String) table.getValueAt(row, col);
                        String patientDocuments = (String) table.getValueAt(row, 0);
                        try {
                            Connection pd = DBConfig.createConnection();
                           PatientDocumentsDao patientDocumentsDao = new PatientDocumentsDao(pd);
                           pd.close();
                        } catch (SQLException exception) {
                            throw new RuntimeException();
                        }
                    }
                });
        }

    public Component getPanel() {
        return null;
    }
}

