package db.dao;

import db.Clinic;
import db.Patient;

import java.sql.*;
import java.util.ArrayList;

public class PatientDAO {
    private Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    public  void add(Patient  patient) throws SQLException {
        String sql ="insert into patients (name, gender, birth_date, address, phone, mobile, email, disease, medical_diagnosis ,clinic_id) values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st = connection.prepareStatement(sql);
//        st.setInt(1,clinic.getId());
        st.setString(1,patient.getName());
        st.setString(2, String.valueOf(patient.getGender()));
        st.setDate(3,  new java.sql.Date(patient.getBirthDate().getTime()));
        st.setString(4,patient.getAddress());
        st.setString(5,patient.getPhone());
        st.setString(6,patient.getMobile());
        st.setString(7,patient.getEmail());
        st.setString(8,patient.getDisease());
        st.setString(9,patient.getMedicalDiagnosis());
        st.setInt(10,patient.getClinic().getId());
        st.executeUpdate();
        st.close();
    }
    public Patient get(int pid) throws SQLException {
        String sql ="SELECT id, name, gender, birth_date, address, phone, mobile, email, disease, medical_diagnosis, created_at, clinic_id FROM patients where id=?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1,pid);
        ResultSet resultSet = st.executeQuery();
        if (resultSet.next()){
            Patient p = new Patient();
            p.setId(resultSet.getInt("id"));
            p.setName(resultSet.getString("name"));
            if (resultSet.getString("gender").equals("male")){
                p.setGender(Patient.GENDER.male);
            }else{
                p.setGender(Patient.GENDER.female);
            }
            p.setAddress(resultSet.getString("address"));
            p.setPhone(resultSet.getString("phone"));
            p.setMobile(resultSet.getString("mobile"));
            p.setEmail(resultSet.getString("email"));
            p.setDisease(resultSet.getString("disease"));
            p.setMedicalDiagnosis(resultSet.getString("medical_diagnosis"));
            p.setBirthDate(resultSet.getDate("birth_date"));
            p.setCreatedAt(resultSet.getDate("created_at"));

            ClinicDAO clinicDAO = new ClinicDAO(connection);
            Clinic clinic = clinicDAO.get(resultSet.getInt("clinic_id"));
            p.setClinic(clinic);
           return  p;
        }
       return null;
    }

    public ArrayList<Patient> getByClinc(int clinicID) throws SQLException {
        String sql ="SELECT id, name, gender, birth_date, address, phone, mobile, email, disease, medical_diagnosis, created_at, clinic_id FROM patients where clinic_id=?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1,clinicID);
        ArrayList<Patient> list = new ArrayList<>();
        ResultSet resultSet = st.executeQuery();
        while (resultSet.next()){
            Patient p = new Patient();
            p.setId(resultSet.getInt("id"));
            p.setName(resultSet.getString("name"));
            if (resultSet.getString("gender").equals("male")){
                p.setGender(Patient.GENDER.male);
            }else{
                p.setGender(Patient.GENDER.female);
            }
            p.setAddress(resultSet.getString("address"));
            p.setPhone(resultSet.getString("phone"));
            p.setMobile(resultSet.getString("mobile"));
            p.setEmail(resultSet.getString("email"));
            p.setDisease(resultSet.getString("disease"));
            p.setMedicalDiagnosis(resultSet.getString("medical_diagnosis"));
            p.setBirthDate(resultSet.getDate("birth_date"));
            p.setCreatedAt(resultSet.getDate("created_at"));

            ClinicDAO clinicDAO = new ClinicDAO(connection);
            Clinic clinic = clinicDAO.get(resultSet.getInt("clinic_id"));
            p.setClinic(clinic);
            list.add(p);
        }
        return  list;
    }

}
