package com.auto.coder.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.auto.coder.Constants;
import com.auto.coder.data.Column;
import com.auto.coder.data.ExtendSql;
import com.auto.coder.data.Table;

public abstract class AbstractGenerator implements Generator, Constants {

	/** log4j实例 */
	protected final Logger logger = Logger.getLogger(getClass());

	protected final String vmPath = getClass().getResource("/vm/" + CODE_SUPPORT).getPath();

	/**
	 * 
	 * <ul>
	 * <li>author zhouxr </li>
	 * 
	 * @param vm
	 *            必需的值
	 * @param beanName
	 *            不是必需的根据实际情况可以为null
	 * @param tables
	 *            不是必需的根据实际情况可以为null
	 * @param cols
	 *            不是必需的根据实际情况可以为null
	 * @param cols
	 *            不是必需的根据实际情况可以为null
	 * @return
	 * @throws Exception
	 */
	protected String coverVM2String(String vm, String beanName, List<Table> tables, List<Column> pkCols, List<Column> cols,List<ExtendSql> sqlList) throws Exception {

		VelocityEngine ve = new VelocityEngine();
		Properties properties = new Properties();
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, vmPath); // 此处的fileDir可以直接用绝对路径来
		// 指定,如"D:/template",但记住只要指定到文件夹就行了
		ve.init(properties); // 初始化
		// 取得velocity的模版
		Template t = ve.getTemplate(vm,"utf-8");
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		// 把数据填入上下文
		context.put("codepackage", CODEPACKAGE);
		context.put("database", DATABASE);
		context.put("driverClassName", DRIVER_CLASS);
		context.put("url", URL);
		context.put("username", USERNAME);
		context.put("password", PASSWORD);
		
	
		if (null != beanName) {
			context.put("beanName", beanName);
			context.put("entityName", beanName + "Entity");
		}
		if (null != tables) {
			context.put("tables", tables);
		}
		if (null != cols) {
			context.put("cols", cols);
		}
		if(null!= pkCols){
			context.put("pkCols", pkCols);
		}
		if(null!= sqlList){
			context.put("sqlList", sqlList);
		}

		StringWriter writer = new StringWriter();
		// 转换输出
		t.merge(context, writer);
		logger.debug("Rs:\n" + writer.toString());
		return writer.toString();
	}
	
	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 21, 2011 10:16:17 AM</li>
	 * <li>comment:</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @param vm
	 *            必需的值
	 * @param beanName
	 *            不是必需的根据实际情况可以为null
	 * @param tables
	 *            不是必需的根据实际情况可以为null
	 * @param cols
	 *            不是必需的根据实际情况可以为null
	 * @param cols
	 *            不是必需的根据实际情况可以为null
	 * @return
	 * @throws Exception
	 */
	protected String coverVM2String(String vm, String beanName, List<Table> tables, List<Column> pkCols, List<Column> cols) throws Exception {

		VelocityEngine ve = new VelocityEngine();
		Properties properties = new Properties();
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, vmPath); // 此处的fileDir可以直接用绝对路径来
		// 指定,如"D:/template",但记住只要指定到文件夹就行了
		ve.init(properties); // 初始化
		// 取得velocity的模版
		Template t = ve.getTemplate(vm,"utf-8");
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		// 把数据填入上下文
		context.put("codepackage", CODEPACKAGE);
		context.put("database", DATABASE);
		context.put("driverClassName", DRIVER_CLASS);
		context.put("url", URL);
		context.put("username", USERNAME);
		context.put("password", PASSWORD);

		if (null != beanName) {
			context.put("beanName", beanName);
			context.put("entityName", beanName + "Entity");
		}
		if (null != tables) {
			context.put("tables", tables);
		}
		if (null != cols) {
			context.put("cols", cols);
		}
		if(null!= pkCols){
			context.put("pkCols", pkCols);
		}

		StringWriter writer = new StringWriter();
		// 转换输出
		t.merge(context, writer);
		logger.debug("Rs:\n" + writer.toString());
		return writer.toString();
	}

	protected String coverVM2String(String vm) throws Exception {
		return coverVM2String(vm, null, null, null, null,null);
	}

	protected String coverVM2String(String vm, String beanName) throws Exception {
		return coverVM2String(vm, beanName, null, null, null,null);
	}

	protected void bakFile(File file) {
		if (file.exists() && "yes".equalsIgnoreCase(JAVA_BACKUP)) {
			try {
				FileUtils.copyFile(file, new File(file.getPath().concat(".bak")));
				FileUtils.forceDelete(file);
			} catch (IOException e) {
				logger.warn("Bakup file faild.path=" + file.getName());
			}
		}
	}
}
