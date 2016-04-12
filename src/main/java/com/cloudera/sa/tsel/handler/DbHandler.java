package com.cloudera.sa.tsel.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DbHandler<T> {

    final Logger logger = LoggerFactory.getLogger(getClass());
    private Connection connection;

    public DbHandler(Connection connection) {
        this.connection = connection;
    }

    public interface InsertOperation<T> {
        void doInsert(T[] data) throws SQLException;
    }

    public interface ResultSetTransformer<T> {
        T[] transform(ResultSet rs) throws SQLException;
    }

    public void insert(InsertOperation<T> insertOperation, T[] data) {
        try {
            insertOperation.doInsert(data);
            connection.commit();
        } catch(SQLException ex) {
            logger.error(ex.getMessage());
            if (connection != null) {
                try {
                    logger.error("Transaction is being rolled back");
                    connection.rollback();
                } catch(SQLException excep) {
                    logger.error(excep.getMessage());
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch(SQLException excep) {
                    logger.error(excep.getMessage());
                }
            } 
        }
    }

    public ResultSet query(PreparedStatement stmt) throws SQLException {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch(SQLException ex) {
            logger.error(ex.getMessage());
        }
        return rs;
    }

    public T[] query(PreparedStatement stmt, ResultSetTransformer<T> transformer) throws SQLException {
        ResultSet rs = query(stmt);
        return transformer.transform(rs);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
