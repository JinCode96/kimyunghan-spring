package hello.jdbc.connection;

/**
 * abstract 로 객체 생성을 막음
 * 상수라 객체생성되면 안됨
 */
public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
