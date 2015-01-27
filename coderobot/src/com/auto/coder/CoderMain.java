package com.auto.coder;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.auto.coder.config.Env;
import com.auto.coder.data.Table;
import com.auto.coder.generator.Generator;
import com.auto.coder.generator.GeneratorFactory;
import com.auto.coder.utils.LoadExtendSql;
import com.auto.coder.utils.LoaderDataBase;

public class CoderMain {
    
    private static final Logger logger = Logger.getLogger(CoderMain.class);
    
    static Env env = new Env();
    
    static void initLog4j(String log4jProperties) {
        if (null != log4jProperties) {
            PropertyConfigurator.configure(log4jProperties);
        }
        
    }
    
    public static void main(String[] args) {
        try {
            PropertyConfigurator.configure("log4j.properties");
            env.init("config.properties");
            Connection conn = Env.getConnection();
            LoadExtendSql.init("sql.properties");
            List<Table> tables = LoaderDataBase.getTables(conn, Constants.DATABASE);
            Generator generator = GeneratorFactory.Generate(Constants.CODE_SUPPORT, tables);
            
            generator.generateJavaFile();
            generator.generateConfigFile();
            generator.generatePropertyFiles();
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}