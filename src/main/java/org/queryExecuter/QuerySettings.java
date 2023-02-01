package org.queryExecuter;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class QuerySettings {
    private String query;
    private int responseTimeout;
    private String resultFile;
    private String databaseDriverJar;

    private JSONObject connectionCredentials;

    private void setQuery(String query) {
        this.query = query;
    }

    private void setResponseTimeout(int responseTimeout){this.responseTimeout = responseTimeout;}

    private void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    private void setDatabaseDriverJar(String databaseDriverJar) {
        this.databaseDriverJar = databaseDriverJar;
    }

    private void setConnectionCredentials(JSONObject connectionsCredential){
        this.connectionCredentials = connectionsCredential;
    }

    public void readSettingsQueryFile(Path querySettingPath) throws IOException {

        JSONObject settingContent = new JSONObject(new String(Files.readAllBytes(querySettingPath),
                StandardCharsets.UTF_8));

        this.setQuery(settingContent.getString("query"));
        this.setResponseTimeout(settingContent.getInt("responseTimeout"));
        this.setResultFile(settingContent.getString("resultFilePath"));
        this.setDatabaseDriverJar(settingContent.getString("jarPath"));
        this.setConnectionCredentials(settingContent.getJSONObject("connectionCredentials"));
    }

    public String getQuery() {
        return query;
    }

    public int getResponseTimeout(){return responseTimeout;}

    public String getResultFilePath() {
        return resultFile;
    }

    public Path getDatabaseDriverJarPath() {
        return Paths.get(databaseDriverJar);
    }

    public JSONObject getConnectionCredentials(){return this.connectionCredentials;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuerySettings that = (QuerySettings) o;
        return query.equals(that.query) && resultFile.equals(that.resultFile) && databaseDriverJar.equals(that.databaseDriverJar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, resultFile, databaseDriverJar);
    }
}
