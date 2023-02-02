# query-executor

A query JDBC executor callable as a jar format.

## HOW TO USE

First it's necessary to create a json file with the following format:

```json
{
 "query": "Select MyData from table",
 "responseTimeout": 15,
 "resultFilePath": "/path/to/WhereResultWillBe",
 "jarPath": "path/to/dabaseDriver.jar",
 "connectionCredentials": {
     "driver": "driver.main.class",
     "connString": "jdbc:https://my-db-connstring",
     "user": "userName",
     "pass": "passwordWithBase64Encode",
   }
}
```
In order to run the query, use: 

```bash
java -jar <path/To/Json>
```

Once the query is finished there will be a file in the `resultFilePath` specifed path, that can either be a `.csv` if the query was successefuly executed or a `.txt` with the Exception if anything goes wrong.
