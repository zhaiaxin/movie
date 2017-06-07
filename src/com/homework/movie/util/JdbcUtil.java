package com.homework.movie.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static java.lang.Class.forName;

/**
 * 连接数据库工具类
 *
 * @author zhaiaxin
 * @time: 2017/6/6 19:12.
 */
public class JdbcUtil {

    public static String db_url;
    public static String username;
    public static String password;
    public static String driver;

    static {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try{
            inputStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
            properties.load(inputStream);
            db_url = (String) properties.get("jdbc.url");
            username = (String) properties.get("jdbc.username");
            password = (String) properties.get("jdbc.password");
            driver = (String) properties.get("jdbc.driver");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
