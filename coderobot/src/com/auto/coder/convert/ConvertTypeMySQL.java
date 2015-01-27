package com.auto.coder.convert;

import com.auto.coder.ConvertType;

/**
* @ClassName: Env 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhouxr
*
 */
public class ConvertTypeMySQL implements ConvertType {
	/** 转换成 java.lang.String 的mysql类型 */
	private static final String[] string = { "CHAR", "VARCHAR", "TINYTEXT", "TEXT", "MEDIUMTEXT", "LONGTEXT","ENUM" };
	/** 转换成 java.lang.Integer 的mysql类型 */
	private static final String[] inttype = { "TINYINT", "SMALLINT", "INT", "INT UNSIGNED" };
	/** 转换成 java.math.BigInteger 的mysql类型 */
	private static final String[] bigint = { "BIGINT" };
	/** 转换成 java.lang.Float 的mysql类型 */
	private static final String[] floattype = { "FLOAT" };
	/** 转换成 java.lang.Double 的mysql类型 */
	private static final String[] doubletype = { "DOUBLE" };
	/** 转换成 java.math.BigDecimal 的mysql类型 */
	private static final String[] decimal = { "DECIMAL" };
	/** 转换成 java.util.Date */
	private static final String[] date = { "DATE", "YEAR", "DATETIME", "TIMESTAMP" };
	/** 转换成 java.sql.Time */
	private static final String[] time = { "TIME" };

	/**
	 * 根据取得mysql类型确定java类型
	 * 
	 * @param mysqlType
	 *            从mysql取出在类型
	 * @return
	 */
	public String getType(String mysqlType) {
		String str = "java.lang.String";
		if (equalsType(mysqlType, string)) {
			return str;
		} else if (equalsType(mysqlType, inttype)) {
			return "java.lang.Integer";
		} else if (equalsType(mysqlType, bigint)) {
			return "java.math.BigInteger";
		} else if (equalsType(mysqlType, floattype)) {
			return "java.lang.Float";
		} else if (equalsType(mysqlType, doubletype)) {
			return "java.lang.Double";
		} else if (equalsType(mysqlType, decimal)) {
			return "java.math.BigDecimal";
		} else if (equalsType(mysqlType, date)) {
			return "java.sql.Date";
		} else if (equalsType(mysqlType, time)) {
			return "java.sql.Time";
		}
		return str;
	}

	/**
	 * 比较给定的类型是否是在符合的类型中
	 * 
	 * @param mysqlType
	 *            查出的SQL类型
	 * @param str
	 * @return
	 */
	private boolean equalsType(String mysqlType, String[] str) {
		boolean isEquals = false;
		if (null == mysqlType) {
			return isEquals;
		}
		for (int i = 0; i < str.length; i++) {
			if (mysqlType.equals(str[i])) {
				isEquals = true;
				break;
			}
		}
		return isEquals;
	}
}
