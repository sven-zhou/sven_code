package com.auto.coder.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
* @ClassName: Env 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhouxr
*
 */
public class Env {
    
    private static final Logger logger = Logger.getLogger(Env.class);
    
    public static Connection conn = null;
    
    public static Properties prop = null;
    
    public void init(String path) throws Exception {
        prop = new Properties();
       // InputStream inStream = new FileInputStream(Loader.getResource(path).toURI().toString());
        InputStream   inStream   =   Constants.class.getClassLoader().getResourceAsStream(path); 
        prop.load(inStream);
    }
    
    public static Connection getConnection() throws Exception {
        if (null == prop) {
            logger.warn("config.properties was not found!");
            return conn;
        }
        logger.info("driver=" + Constants.DRIVER_CLASS);
        logger.info("url=" + Constants.URL);
        Class.forName(Constants.DRIVER_CLASS);
        Properties props = new Properties();
        props.put("remarksReporting","true");//获取 字段表注释（oracle 默认为false）
        props.put("user",Constants.USERNAME);
        props.put("password",Constants.PASSWORD);

     //   conn = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD,props);
        conn = DriverManager.getConnection(Constants.URL, props);
        
        return conn;
    }
}   
