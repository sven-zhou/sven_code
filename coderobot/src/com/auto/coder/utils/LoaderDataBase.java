package com.auto.coder.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.auto.coder.config.Constants;
import com.auto.coder.convert.ConvertType;
import com.auto.coder.convert.ConvertTypeMsSQL;
import com.auto.coder.convert.ConvertTypeMySQL;
import com.auto.coder.convert.ConvertTypeOracle;
import com.auto.coder.structure.conf.ExtendSql;
import com.auto.coder.structure.conf.MultiTableExendSql;
import com.auto.coder.structure.db.Column;
import com.auto.coder.structure.db.Table;

/**
* @ClassName: LoaderDataBase 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhouxr
* @date 2014年10月14日 下午1:54:56 
*
 */
public class LoaderDataBase {

	private static final Logger logger = Logger.getLogger(LoaderDataBase.class);

	private static String database;

	/**
	* @Title: getTables 
	* @Description: 组装表数据
	* @param @param conn
	* @param @param database
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<Table>    返回类型 
	* @throws
	 */
	public static List<Table> getTables(Connection conn, String database) throws Exception {
		LoaderDataBase.database = database;
		ArrayList<Table> tables = new ArrayList<Table>();
		
		try {
			
			//加载表结构
			DatabaseMetaData dbMetaData = conn.getMetaData();
			// 可为:"TABLE", "VIEW", "SYSTEM TABLE",
			// "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"
			String[] types = { "TABLE" };
			ResultSet tabs = dbMetaData.getTables(null, dbMetaData.getUserName(),"%", types);
			while (tabs.next()) {
				String tableName = (String) tabs.getObject("TABLE_NAME");
				//可以根据表明进行筛选所需要的表
				if (tableName.startsWith(Constants.TABLE_START_NAME)) {
					logger.debug("tableName = " + tableName);
					boolean flag = true;
					for (Table table : tables) {
						if (table.getName().equals(tableName)) {
							flag = false;
						}
					}
					if (flag){
						Table table = getTable(dbMetaData, tableName);
						
						//将单表自定义功能加入table数据
						table.setSqlList(LoadExtendSql.extendSqls);
						table.setType("ACTUAL");
						tables.add(table);
					}
				}
			}
			
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return tables;
	}

	private static Table getTable(DatabaseMetaData dbMetaData, String tableName) {
		Table table = new Table();
		// 从resultset中取值时一定要按查询的顺序读取。否则会跑异常（流以关闭）
		try {
			String remark;
			table.setName(tableName);
			table.setAlias(tableName.toUpperCase());
			ResultSet colRet = dbMetaData.getColumns(null, "%", tableName, "%");
			ResultSet pkSet = dbMetaData.getPrimaryKeys(null, null, tableName);
			while (colRet.next()) {
				String columnName = colRet.getString("COLUMN_NAME");
				String columnType = colRet.getString("TYPE_NAME");
				
				if("VARCHAR2".equals(columnType)||"LONGTEXT".equals(columnType)||"ENUM".equals(columnType))
					columnType = "VARCHAR";
				else if("NUMBER".equals(columnType)){
					columnType = "NUMERIC";
				}else if("DATE".equals(columnType) || columnType.startsWith("TIMESTAMP")||"DATETIME".equals(columnType)){
					columnType = "TIMESTAMP";
				}

				int nullable = colRet.getInt("NULLABLE");
				String dfs = (String) colRet.getObject("COLUMN_DEF");
				String defaultValue = (null == dfs) ? null : dfs;
				int datasize = colRet.getInt("COLUMN_SIZE");
				// int digits = colRet.getInt("DECIMAL_DIGITS");

				remark = (String) colRet.getObject("REMARKS");
				Column col = new Column();
				col.setAlias(columnName.toUpperCase());
				col.setDefaultValue(defaultValue);
				col.setJavaType(getConvertType().getType(columnType));
				col.setJdbcLength(String.valueOf(datasize));
				col.setName(columnName);
				col.setJdbcType("INT".equals(columnType)?"INTEGER":columnType);
				col.setNullAble(nullable == 0 ? false : true);
				col.setRemark(remark);
				logger.info("columnName=" + columnName + " columnType=" + columnType + " javaType= " + col.getJavaType());
				List<Column> list = table.getColumns();

				boolean flag = true;
				for (Column column : list) {
					if (column.getName().equals(col.getName())) {
						flag = false;
					}
				}
				if (flag)
					table.getColumns().add(col);
			}
			// 添加主键
			while (pkSet.next()) {
				String columnName = pkSet.getString("COLUMN_NAME");
				Column col = new Column();
				col.setAlias(columnName.toUpperCase());
				col.setName(columnName);
				
				List<Column> list = table.getPkColumns();

				boolean flag = true;
				for (Column column : list) {
					if (column.getName().equals(col.getName())) {
						flag = false;
					}
				}
				if (flag)
					table.getPkColumns().add(col);
			}
			pkSet.close();
			colRet.close();

		} catch (SQLException e) {
			e.printStackTrace();
			// logger.error(e.getMessage(), e);
		}
		return table;
	}

	private static ConvertType getConvertType() {
		// 默认Mysql数据库
		if (null == database || database.equalsIgnoreCase("mysql")) {
			return new ConvertTypeMySQL();
		} else if (database.equals("oracle")) {
			return new ConvertTypeOracle();
		} else {
			return new ConvertTypeMsSQL();
		}
	}
}