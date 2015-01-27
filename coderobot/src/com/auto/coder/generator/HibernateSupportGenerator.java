/**
 * hanman创建于9:52:03 AM
 * 
 * 修改记录: 1. 2.
 */
package com.auto.coder.generator;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.auto.coder.data.Column;
import com.auto.coder.data.Table;
import com.auto.coder.utils.ClassUtils;

/**
 * @author hanman
 *         <ul>
 *         <li>comment:</li>
 *         <ul>
 */
public class HibernateSupportGenerator extends AbstractGenerator {

	/** 所有表的集合 */
	private List<Table> tables;

	/** java文件的根目录 */
	private String javaFilePath;

	/** ibatis配置文件的根目录 */
	private String ibatisConfigFilePath;

	/** spring配置文件的根目录 */
	private String springConfigFilePath;

	/**
	 * 构建函数
	 * 
	 * @param tables
	 */
	HibernateSupportGenerator(List<Table> tables) {
		this.tables = tables;
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 10, 2011 5:25:47 PM</li>
	 * <li>comment:生成java文件</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @throws Exception
	 */
	public void generateJavaFile() throws Exception {
		try {
			// 取得java文件路径
			javaFilePath = CODEPATH.concat(File.separator).concat(CODEPACKAGE.replace(".", File.separator)).concat(File.separator);
			// 创建java文件目录
			File javaFileFolder = new File(javaFilePath);
			logger.debug("java file path:" + javaFilePath);
			if (!javaFileFolder.exists()) {
				FileUtils.forceMkdir(javaFileFolder);
			}
			// 生成Bean基类
			generateBaseBean();
			// 如果table不为空
			if (null != tables) {
				for (Iterator<Table> i = tables.iterator(); i.hasNext();) {
					Table table = i.next();
					generateBean(table);
					generateDAO(table);
					generateDAOSupport(table);
				}
			} else {
				logger.warn("There is no table.");
			}
		} catch (IOException e) {
			logger.error("Mkdir faild!");
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Generate file faild!");
			logger.error(e.getMessage(), e);
			System.exit(0);
		}
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 10, 2011 5:26:09 PM</li>
	 * <li>comment:生成配置文件</li>
	 * <p>
	 * </p>
	 * </ul>
	 */
	public void generateConfigFile() {
		try {
			// 取得配置文件路径
			ibatisConfigFilePath = CODEPATH.concat(File.separator).concat(CODE_SUPPORT).concat(File.separator);
			springConfigFilePath = CODEPATH.concat(File.separator).concat(CONFIG_SPRING_PATH).concat(File.separator);

			logger.debug("Ibatis config file path:" + ibatisConfigFilePath);
			logger.debug("Spring config file path:" + springConfigFilePath);

			// 创建配置文件目录
			File ibatisConfigFileFolder = new File(ibatisConfigFilePath);
			if (!ibatisConfigFileFolder.exists()) {
				FileUtils.forceMkdir(ibatisConfigFileFolder);
			}

			File springConfigFileFoler = new File(springConfigFilePath);
			if (!springConfigFileFoler.exists()) {
				FileUtils.forceMkdir(springConfigFileFoler);
			}

			// 生成sqlMapConfig.xml
			generateSqlMapConfig();

			// 生成spring 支持
			generateSpringIbatisConfig();
		} catch (IOException e) {
			logger.error("Mkdir faild!");
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Generate Config file faild!");
			logger.error(e.getMessage(), e);
			System.exit(0);
		}
	}

	public void generatePropertyFiles() throws Exception {
		File file = new File(CODEPATH.concat(File.separator).concat(PROPERTIES_FILE));
		bakFile(file);
		String content = coverVM2String("config.properties.vm");
		FileUtils.writeStringToFile(file, content, ENCODING_PROPERTIES);
	}

	private void generateBaseBean() throws Exception {
		String fileName = javaFilePath.concat(File.separator).concat(CLASS_BASE_BEAN).concat(JAVA);
		File file = new File(fileName);
		if (file.exists()) {
			bakFile(file);
		}
		FileUtils.touch(file);

		String content = coverVM2String("BaseObject.java.vm");
		// 写出文件
		FileUtils.writeStringToFile(file, content, ENCODING_JAVA);
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Aug 9, 2010 2:47:32 PM</li>
	 * <li>comment:根据表生成实体Bean</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @param table
	 * @throws IOException
	 */
	private void generateBean(Table table) throws Exception {
		String name = table.getName();
		String className = ClassUtils.getClassName(name);
		String beanPath = javaFilePath.concat(File.separator).concat("bean");
		// Bean目录
		File beanFolder = new File(beanPath);
		if (!beanFolder.exists()) {
			FileUtils.forceMkdir(beanFolder);
		}
		String fileName = beanPath.concat(File.separator).concat(className).concat(JAVA);
		File bean = new File(fileName);
		// 如果文件存在且需要备份,并删除原文件
		bakFile(bean);
		// 创建文件
		FileUtils.touch(bean);

		List<Column> cols = table.getColumns();

		String content = coverVM2String("Bean.java.vm", className, null, null, cols);

		FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Aug 9, 2010 2:48:09 PM</li>
	 * <li>comment:根据表生成DAO</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @param table
	 */
	private void generateDAO(Table table) throws Exception {
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("DAO");
		String beanPath = javaFilePath.concat(File.separator).concat("dao");

		// Bean目录
		File beanFolder = new File(beanPath);
		if (!beanFolder.exists()) {
			FileUtils.forceMkdir(beanFolder);
		}
		String fileName = beanPath.concat(File.separator).concat(className).concat(JAVA);
		File bean = new File(fileName);
		// 如果文件存在且需要备份,并删除原文件
		bakFile(bean);
		// 创建文件
		FileUtils.touch(bean);
		String content = coverVM2String("IbatisDao.java.vm", beanName);
		// 写出文件
		FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
	}

	private void generateDAOSupport(Table table) throws Exception {
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("IbatisDAOSupport");
		String beanPath = javaFilePath.concat(File.separator).concat(DAO_PACKAGE).concat(File.separator).concat(CODE_SUPPORT);
		// Bean目录
		File beanFolder = new File(beanPath);
		if (!beanFolder.exists()) {
			FileUtils.forceMkdir(beanFolder);
		}
		String fileName = beanPath.concat(File.separator).concat(className).concat(JAVA);
		File bean = new File(fileName);
		// 如果文件存在且需要备份,并删除原文件
		bakFile(bean);
		// 创建文件
		FileUtils.touch(bean);
		String content = coverVM2String("IbatisDaoSupport.java.vm", beanName);
		// 写出文件
		FileUtils.writeStringToFile(bean, content.toString(), ENCODING_JAVA);
	}

	private void generateSqlMapConfig() throws Exception {
		String sqlMapFilePath = ibatisConfigFilePath.concat(SQLMAP_CONFIG).concat(XML);

		File bean = new File(sqlMapFilePath);
		bakFile(bean);
		FileUtils.touch(bean);

		String content = coverVM2String("SqlMapConfig.xml.vm", null, tables, null, null);

		String sqlMapsFilePath = ibatisConfigFilePath.concat(DATABASE);

		File sqlMapFile = new File(sqlMapsFilePath);

		if (!sqlMapFile.exists()) {
			FileUtils.forceMkdir(sqlMapFile);
		}

		if (null != tables) {
			for (Iterator<Table> i = tables.iterator(); i.hasNext();) {
				Table table = i.next();
				String sqlMapName = "sqlmap-".concat(table.getJavaName()).concat(XML);
				generateSqlMap(table, sqlMapsFilePath.concat("/").concat(sqlMapName));
			}
		}
		FileUtils.writeStringToFile(bean, content, ENCODING_XML);
	}

	private void generateSqlMap(Table table, String sqlMapName) throws Exception {
		String name = table.getName();
		String simpleClassName = ClassUtils.getClassName(name);

		File sqlMapFile = new File(sqlMapName);
		bakFile(sqlMapFile);
		// 创建文件
		if (!sqlMapFile.exists()) {
			FileUtils.touch(sqlMapFile);
		}
		String content = coverVM2String("sqlmap.xml.vm", simpleClassName, null, null, table.getColumns());
		// 写入文件
		FileUtils.writeStringToFile(sqlMapFile, content, ENCODING_XML);
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 11, 2011 4:20:01 PM</li>
	 * <li>comment:生成spring支持ibatis的配置文件</li>
	 * <p>
	 * </p>
	 * </ul>
	 */
	private void generateSpringIbatisConfig() throws Exception {
		String filePath = springConfigFilePath.concat("beans-dao-ibatis.xml");

		File file = new File(filePath);
		bakFile(file);
		FileUtils.touch(file);

		String content = coverVM2String("beans-dao-ibatis.xml.vm", null, tables, null, null);
		// 写入文件
		FileUtils.writeStringToFile(file, content, ENCODING_XML);
	}

}
