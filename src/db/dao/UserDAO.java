package db.dao;
import db.User;

import  java.sql.Connection;
// dao Data Access Object
public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user){

    }



}
