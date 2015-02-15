package com.auto.coder.structure.conf;

import java.util.List;

/**
 * 
 * @ClassName: ExtendSql
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhouxr
 * @date 2014年10月14日 上午11:30:06
 * 
 */
public class ExtendSql {

	/**
	 * sql对应的key
	 */
	private String key;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * sql的操作类型
	 */
	private String action;

	/**
	 * 参数类型
	 */
	private String parameterType;

	/**
	 * java代码里的参数类型
	 */
	private List<ParamAndParamTypeMap> javaParamTypeAndParam;

	/**
	 * sql
	 */
	private String sql;

	public List<ParamAndParamTypeMap> getJavaParamTypeAndParam() {

		return javaParamTypeAndParam;
	}

	public void setJavaParamTypeAndParam(List<ParamAndParamTypeMap> javaParamTypeAndParam) {

		this.javaParamTypeAndParam = javaParamTypeAndParam;
	}

	public String getParameterType() {

		return parameterType;
	}

	public void setParameterType(String parameterType) {

		this.parameterType = parameterType;
	}

	public String getAction() {

		return action;
	}

	public void setAction(String action) {

		this.action = action;
	}

	public String getTableName() {

		return tableName;
	}

	public void setTableName(String tableName) {

		this.tableName = tableName;
	}

	public String getKey() {

		return key;
	}

	public void setKey(String key) {

		this.key = key;
	}

	public String getSql() {

		return sql;
	}

	public void setSql(String sql) {

		this.sql = sql;
	}

}
