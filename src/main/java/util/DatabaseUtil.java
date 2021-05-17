package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseUtil {
    // bikinnya singleton
    private static HikariDataSource hikariDataSource;

    static {
        HikariConfig configuration = new HikariConfig();
        configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuration.setUsername("root");
        configuration.setPassword("");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/belajar_java_todolist");

        // pool
        configuration.setMaximumPoolSize(10);
        configuration.setMinimumIdle(5);
        configuration.setIdleTimeout(60_000); // 60000ms = 60 detik / 1 menit
        configuration.setMaxLifetime(3600000); // 3600000ms = 1 jam / 3600 detik
        hikariDataSource = new HikariDataSource(configuration);
    }
    // bikin sebuah method balikanya dalah hikari data source
    // yg dikirm ke repository adalah hikaridatasource nya, bukan lagi connection
    public static HikariDataSource getDataSource(){
        return hikariDataSource;
    }
}
