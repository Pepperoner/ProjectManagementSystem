package dao.pooledJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PooledJDBCUserDAO {

    private DataSource dataSource;

    public PooledJDBCUserDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
