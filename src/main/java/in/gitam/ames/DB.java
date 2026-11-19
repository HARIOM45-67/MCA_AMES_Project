package in.gitam.ames;
import java.sql.*;
public final class DB {
 private static final String URL = env("AMES_DB_URL", "jdbc:mysql://localhost:3306/mca_ames?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
 private static final String USER = env("AMES_DB_USER", "root");
 private static final String PASSWORD = env("AMES_DB_PASSWORD", "");
 private DB() {}
 private static String env(String name, String fallback) {
  String value = System.getenv(name);
  return value == null || value.isBlank() ? fallback : value;
 }
 public static Connection get() throws SQLException {
  try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch(ClassNotFoundException e) { throw new SQLException(e); }
  return DriverManager.getConnection(URL, USER, PASSWORD);
 }
}
