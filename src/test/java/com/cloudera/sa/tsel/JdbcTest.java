package com.cloudera.sa.tsel;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class JdbcTest {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private Connection conn;

    @Before
    public void setup() {
        try {
            // More information here
            // http://www.h2database.com/html/features.html#database_url
            // db name here is "test_mem"
            conn = DriverManager.getConnection("jdbc:h2:mem:test_mem");
        } catch(SQLException sqle) {
            logger.error(sqle.getMessage());
        }
    }

    @Test
    public void smokeTest() throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt.executeUpdate("SET MODE MySQL");
        stmt.executeUpdate("CREATE TABLE Sells " +
                "(bar VARCHAR2(40), beer VARCHAR2(40), price REAL)" );
        stmt.executeUpdate("INSERT INTO Sells " +
                "VALUES ('Bar Of Foo', 'BudLite', 2.00)" );
        ResultSet rs = stmt.executeQuery("SELECT * FROM Sells");
        String bar, beer;
        float price;
        while (rs.next()) {
            bar = rs.getString("bar");
            beer = rs.getString("beer");
            price = rs.getFloat("price");
            logger.info(bar + " sells " + beer + " for " + price + " Dollars.");
        }
    }

    public String getFileContent(String resourceUrl) {
        try {
            URL url = getClass().getResource(resourceUrl);
            Path resPath = Paths.get(url.toURI());
            return new String(Files.readAllBytes(resPath), "UTF8");
        } catch(URISyntaxException e) {
            throw new RuntimeException(e);
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    // See available mode here
    // http://www.h2database.com/html/features.html#compatibility
    public void setDbCompatMode(String mode, Statement stmt) throws SQLException {
        stmt.executeUpdate(String.format("SET MODE %s", mode));
        logger.info("Set DB Compat Mode to: " + mode);
    }

    public void setDbCompatMode(String mode) throws SQLException {
        Statement stmt = getConnection().createStatement();
        setDbCompatMode(mode, stmt);
        stmt.close();
    }

    // Use this to bootstrap the tables
    // when the script is outside the resource folder
    public void initScript(String sqlScript, Statement stmt) throws SQLException {
        String absPath = new File(sqlScript).getAbsolutePath();
        stmt.executeUpdate(String.format("RUNSCRIPT FROM '%s'", absPath));
    }

    public void initScript(String sqlScript) throws SQLException {
        Statement stmt = getConnection().createStatement();
        initScript(sqlScript, stmt);
        stmt.close();
    }

    public Connection getConnection() {
        return this.conn;
    }
}
