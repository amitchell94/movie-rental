package data;

public class ConnectionCreator {
    public static String createConnectionUrl() {
        String host = "localhost";
        String dbName = "blockbuster";
        String user = "andy94";
        String password = "andy94";
        return "jdbc:mysql://" + host + "/" + dbName + "?" + "user=" + user + "&password=" + password + "&useSSL=false&allowPublicKeyRetrieval=true";
    }
}
