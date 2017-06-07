package com.homework.movie.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 建立连接池
 *
 * @author zhaiaxin
 * @time: 2017/6/6 19:42.
 */
public class ConnPool {

    public static String db_url;
    public static String username;
    public static String password;
    public static String driver;

    public static DruidDataSource dataSource;

    static {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
            properties.load(inputStream);
            db_url = (String) properties.get("jdbc.url");
            username = (String) properties.get("jdbc.username");
            password = (String) properties.get("jdbc.password");
            driver = (String) properties.get("jdbc.driver");
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(db_url);
        dataSource.setInitialSize(10);//初始化10个连接
        dataSource.setMinIdle(2);//闲置连接2个
        dataSource.setMaxActive(20);//连接数的上限为20个

    }

    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(db_url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void release(ResultSet rs, PreparedStatement ps, Connection connection){
        if(rs != null){
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null){
            try{
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void release(Object object){
        try{
            if(object instanceof ResultSet){
                ((ResultSet)object).close();
            }else if(object instanceof Statement){
                ((Statement)object).close();
            }else if(object instanceof Connection){
                ((Connection)object).close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
