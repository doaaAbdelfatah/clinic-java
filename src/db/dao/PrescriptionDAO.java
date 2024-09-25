package db.dao;

import java.sql.Connection;

public class PrescriptionDAO {
    private Connection connection;

    public PrescriptionDAO(Connection connection) {
        this.connection = connection;
    }
}
