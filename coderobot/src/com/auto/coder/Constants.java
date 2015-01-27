package com.auto.coder;

import java.util.Properties;

import com.auto.coder.config.Env;

public interface Constants {
    
    static Properties prop = Env.prop;
    
    static final String DATABASE = prop.getProperty("jdbc.database");
    
    static final String DRIVER_CLASS = prop.getProperty("jdbc.driverClassName");
    
    static final String URL = prop.getProperty("jdbc.url");
    
    static final String USERNAME = prop.getProperty("jdbc.username");
    
    static final String PASSWORD = prop.getProperty("jdbc.password");
    
    static final String CODEPATH = prop.getProperty("codepath");
    
    static final String CODEPACKAGE = prop.getProperty("codepackage");
    
    static final String ENCODING_JAVA = prop.getProperty("encoding.java");
    
    static final String ENCODING_XML = prop.getProperty("encoding.xml");
    
    static final String ENCODING_PROPERTIES = prop.getProperty("encoding.properties");
    
    static final String CODE_SUPPORT = prop.getProperty("code.support");
    
    static final String JAVA_BACKUP = prop.getProperty("java.bakup");
    
    static final String CONFIG_SPRING_PATH = prop.getProperty("config.spring.path");
    
    static final String PROPERTIES_FILE = prop.getProperty("properties.file");
    
    static final String SQLMAP_CONFIG = "SqlMapConfig";
    
    static final String CLASS_BASE_BEAN = "BaseObject";
    
    static final String DAO = "DAO";
    
    static final String DAO_PACKAGE = "dao";
    
    static final String JAVA = ".java";
    
    static final String XML = ".xml";;
    
    static final String PROPERTIES = ".properties";
    
    static final String SEPARATOR = ";";
    
    static final String LINE = "\n";
    
    static final String CLASS_ANNOTATION = "/**\n";
    
    static final String BODY_ANNOTATION = " * ";
    
    static final String FOOT_ANNOTATION = "*/\n";
    
    static final String UNDERLINE = "_";
    
    static final String SPACE = " ";
    
    static final String TAB = "    ";
    
    static final String COMMA_SEPARATE = " , ";
    
    static final String CDATA_START = "<![CDATA[";
    
    static final String CDATA_STOP = "]]>";
    
    /** xml head */
    static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    
}
