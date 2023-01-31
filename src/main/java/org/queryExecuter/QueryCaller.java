package org.queryExecuter;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;

public class QueryCaller {

    protected ConnectionCredential credential;
    protected QuerySettings settings;

    public QueryCaller(ConnectionCredential credential, QuerySettings settings){

        this.credential = credential;
        this.settings = settings;
    }

    private void loadDatabaseDriver() throws SQLException {

            try {
                URLClassLoader classLoader = (URLClassLoader) QueryExecutor.class.getClassLoader();

                Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                addURL.invoke(classLoader, settings.getDatabaseDriverJarPath().toUri().toURL());
            }catch(Exception e){
                throw new SQLException(e);
            }
    }

    public java.sql.Connection openConnection() throws SQLException {

        loadDatabaseDriver();
         return DriverManager.getConnection(credential.getConnectionString(), credential.getUsername(),
                credential.getPassword());

    }

    public ResultSet executeSQL(@NotNull Connection databaseConnection) throws SQLException {

        Statement st = databaseConnection.createStatement();
        st.setQueryTimeout(settings.getResponseTimeout());
        ResultSet retrievedData = st.executeQuery(this.settings.getQuery());

        return retrievedData;
    }

}
