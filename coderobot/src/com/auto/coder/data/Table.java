/**
 * @auther gaoqs
 * @date 下午04:37:30
 * @function
 * @version 1.0
 */
package com.auto.coder.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.auto.coder.utils.ClassUtils;

/**
 * @author gaoqs
 * @date 2008-3-5 下午04:37:30
 * @function 数据库中的表
 * @version 1.0
 */
public class Table {

	/**
	 * 表中的字段
	 */
	private ArrayList<Column> columns = new ArrayList<Column>();

	/**
	 * 字段类型与java类型的映身关系,未找到,则映射为java.lang.String
	 */
	private HashMap<String, String> columnMap = new HashMap<String, String>();

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 表格名称
	 */
	private String name;

	/**
	 * 表主键
	 */
	private ArrayList<Column> pkColumns = new ArrayList<Column>();;

	/**
	 * 表外键
	 */
	private HashMap<String, String> fkMap;

	private List<ExtendSql> sqlList;

	public List<ExtendSql> getSqlList() {

		return sqlList;
	}

	public void setSqlList(List<ExtendSql> sqlList) {

		this.sqlList = sqlList;
	}

	public ArrayList<Column> getColumns() {

		return columns;
	}

	public void setColumns(ArrayList<Column> columns) {

		this.columns = columns;
	}

	public HashMap<String, String> getColumnMap() {

		return columnMap;
	}

	public void setColumnMap(HashMap<String, String> columnMap) {

		this.columnMap = columnMap;
	}

	public String getAlias() {

		return alias;
	}

	public void setAlias(String alias) {

		this.alias = alias;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public ArrayList<Column> getPkColumns() {

		return pkColumns;
	}

	public void setPkColumns(ArrayList<Column> pkColumns) {

		this.pkColumns = pkColumns;
	}

	public HashMap<String, String> getFkMap() {

		return fkMap;
	}

	public void setFkMap(HashMap<String, String> fkMap) {

		this.fkMap = fkMap;
	}

	public String getJavaName() {

		return ClassUtils.getClassName(getName());
	}

}
