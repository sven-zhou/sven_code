package com.auto.coder.structure.db;

import com.auto.coder.utils.ClassUtils;

/**
 * @author gaoqs
 * @date 2008-3-5 下午04:37:52
 * @function 数据库中的字段
 * @version 1.0
 */
public class Column {

	/**
	 * 列名
	 */
	private String name;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 字段类型
	 */
	private String jdbcType;

	/**
	 * 字段长度
	 */
	private String jdbcLength;

	/**
	 * java映射类型
	 */
	private String javaType;

	/**
	 * 默认值
	 */
	private String defaultValue;

	/**
	 * 是否可以空 "Y"为可以空
	 */
	private boolean nullAble;

	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJdbcLength() {
		return jdbcLength;
	}

	public void setJdbcLength(String jdbcLength) {
		this.jdbcLength = jdbcLength;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean getNullAble() {
		return nullAble;
	}

	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 21, 2011 12:36:19 PM</li>
	 * <li>comment:取得java类型，如String,Integer</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @return
	 */
	public String getShortJavaType() {
		return ClassUtils.getShortClassName(getJavaType());
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 21, 2011 12:36:47 PM</li>
	 * <li>comment:取得列小写名称</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @return
	 */
	public String getFieldName() {
		return ClassUtils.getFieldName(getName());
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 21, 2011 12:36:47 PM</li>
	 * <li>comment:取得列首字母大写名称</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @return
	 */
	public String getTemplateFieldName() {
		return ClassUtils.getClassName(getName());
	}

	/**
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            要设置的 remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
