package org.queryExecuter;

import org.json.JSONObject;
import java.util.Base64;
import java.util.Objects;


public class ConnectionCredential {

    private final JSONObject connectionContent;
    private String userName;
    private String password;
    private String connectionString;
    private String driverMainClass;

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    private void setDriverMainClass(String driverMainClass) {
        this.driverMainClass = driverMainClass;
    }

    public ConnectionCredential(JSONObject connectionCredential){
        this.connectionContent = connectionCredential;
    }

    public void loadCredential(){

        this.setUserName(this.connectionContent.getString("user"));
        this.setPassword(this.connectionContent.getString("pass"));
        this.setConnectionString(this.connectionContent.getString("connString"));
        this.setDriverMainClass(this.connectionContent.getString("driver"));
    }

    public String getDriverMainClass(){
        return this.driverMainClass;
    }

    public String getConnectionString(){
        return this.connectionString;
    }

    public String getUsername(){
        return this.userName;
    }

    public String getPassword(){
        byte[] decodedPassword = Base64.getDecoder().decode(this.password);
        return new String(decodedPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionCredential that = (ConnectionCredential) o;
        return Objects.equals(connectionContent, that.connectionContent) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(connectionString, that.connectionString) && Objects.equals(driverMainClass, that.driverMainClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionContent, userName, password, connectionString, driverMainClass);
    }
}
