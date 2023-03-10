package org.queryExecuter;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutor {

    public static ResultSet retrievedData;
    private static final char csvSeparator = ',';

    private static QuerySettings querySetting;

    private static Connection databaseConnection;

    public static void main(String[] queryFilePath) throws Throwable{

        Path querySettingFile = Paths.get(queryFilePath[0]);

        querySetting = getQueryFileNameFromPython(querySettingFile);

        ConnectionCredential connectionCredential = new ConnectionCredential(querySetting.getConnectionCredentials());
        connectionCredential.loadCredential();

        try {
            QueryCaller databaseCaller = new QueryCaller(connectionCredential, querySetting);
            databaseConnection = databaseCaller.openConnection();
            retrievedData = databaseCaller.executeSQL(databaseConnection);
            saveQueryResult(retrievedData, querySetting.getResultFilePath(), csvSeparator);
        }catch (SQLException e ){
            saveExceptionMessage(e.getMessage());
            throw new SQLException(e);
        } finally{
            databaseConnection.close();
        }
    }

    private static QuerySettings getQueryFileNameFromPython(Path inputFile) throws IOException {

        QuerySettings currentSettings = new QuerySettings();
        currentSettings.readSettingsQueryFile(inputFile);
        return currentSettings;
    }

    public static void saveQueryResult(ResultSet queryResult, String dataFile, char csvSeparator ) {

        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(dataFile+".csv"), csvSeparator);
            csvWriter.writeAll(queryResult, true);
            csvWriter.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveExceptionMessage(String errorMessage) throws IOException {

        FileWriter errorMessageFile = new FileWriter(querySetting.getResultFilePath()+".txt");
        errorMessageFile.write(errorMessage);
        errorMessageFile.close();
    }


}
