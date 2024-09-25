package db.dao;

import java.sql.Connection;

public class PatientDocumentDAO {
    private Connection connection;

    public PatientDocumentDAO(Connection connection) {
        this.connection = connection;
    }
}
