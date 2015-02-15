/**
 * hanman创建于9:52:03 AM
 * 
 * 修改记录: 1. 2.
 */
package com.auto.coder.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.auto.coder.structure.conf.ExtendSql;
import com.auto.coder.structure.conf.MultiTableExendSql;
import com.auto.coder.structure.db.Column;
import com.auto.coder.structure.db.Table;
import com.auto.coder.utils.ClassUtils;
import com.auto.coder.utils.LoadExtendSql;

/**
 * @author zhouxr
 *         <ul>
 *         <li>comment:</li>
 *         <ul>
 */
public class IbatisSupportGenerator extends AbstractGenerator {

	/** 所有表的集合 */
	private List<Table> tables;

	/** java文件的根目录 */
	private String javaFilePath;

	/** ibatis配置文件的根目录 */
	private String ibatisConfigFilePath;

	/** spring配置文件的根目录 */
	private String springConfigFilePath;

	IbatisSupportGenerator(List<Table> tables) {
		this.tables = tables;
	}

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
			// generateBaseBean();
			// 如果table不为空
			if (null != tables) {
				//单表的增删改查
				for (Iterator<Table> i = tables.iterator(); i.hasNext();) {
					Table table = i.next();
					generateBean(table);
//					if(table.getType().equals("ACTUAL")){
						generateDAO(table);
						generateServiceInterface(table);
						generateTest(table);
//					}
//					generateDAOSupport(table);
//					generateDto(table);
//					generateFacade(table);
//					generateFacadeImpl(table);
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
	 * <li>author zhouxr</li>
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

			// 生成sqlMapConfig.xml
			generateSqlMapConfig();

			// 生成spring 支持
			//generateSpringIbatisConfig();
			generateSpringContextConfig();
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
		
		String className = ClassUtils.getClassName(name) + "Entity";
		String beanPath = javaFilePath.concat(File.separator).concat("entity");
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
	 * <li>author zhouxr</li>
	 * <li>createDate: Aug 9, 2010 2:47:32 PM</li>
	 * <li>comment:根据表生成实体Bean</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @param table
	 * @throws IOException
	 */
	private void generateDto(Table table) throws Exception {
		String name = table.getName();
		
		String className = ClassUtils.getClassName(name) + "Dto";
		String beanPath = javaFilePath.concat(File.separator).concat("dto");
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

		String content = coverVM2String("dto.java.vm", className, null, null, cols);

		FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
	}

	private void generateMultiBean(MultiTableExendSql extendSql) throws Exception{
		
		String name = extendSql.getTableName();
		
		String className = ClassUtils.getClassName(name) + "Entity";
		String beanPath = javaFilePath.concat(File.separator).concat("entity");
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

		String content = coverVM2String("multitable/MultiBean.java.vm", extendSql);

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
		String className = beanName.concat("Dao");
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
		
		List<Table> list = new ArrayList<Table>();
		list.add(table);
		String content = coverVM2String("IbatisDao.java.vm", beanName, list, table.getPkColumns(), null,table.getSqlList());
		// 写出文件
		FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
	}
	
	/**
	 * 
	 * <ul>
	 * <li>author zhouxr</li>
	 * <li>createDate: Aug 9, 2010 2:48:09 PM</li>
	 * <li>comment:根据表生成DAO</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @param table
	 */
	private void generateFacade(Table table) throws Exception {
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("Facade");
		String beanPath = javaFilePath.concat(File.separator).concat("facade");

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
		String content = coverVM2String("facade.java.vm", beanName, null, table.getPkColumns(), null);
		// 写出文件
		FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
	}
	
	/**
	 * 
	 * <ul>
	 * <li>author zhouxr</li>
	 * <li>createDate: Aug 9, 2010 2:48:09 PM</li>
	 * <li>comment:根据表生成DAO</li>
	 * <p>
	 * </p>
	 * </ul>
	 * 
	 * @param table
	 */
	private void generateFacadeImpl(Table table) throws Exception {
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("FacadeImpl");
		String beanPath = javaFilePath.concat(File.separator).concat("facadeImpl");

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
		String content = coverVM2String("facadeImpl.java.vm", beanName, null, table.getPkColumns(), null);
		// 写出文件
		FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
	}


	
	@SuppressWarnings("unused")
	private void generateDAOSupport(Table table) throws Exception {
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("Impl");
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

	private void generateServiceInterface(Table table) {
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("Service");
		String beanPath = javaFilePath.concat(File.separator).concat("service");

		File beanFolder = new File(beanPath);
		try {
			if (!beanFolder.exists()) {
				FileUtils.forceMkdir(beanFolder);
			}
			String fileName = beanPath.concat(File.separator).concat(className).concat(JAVA);
			File bean = new File(fileName);
			// 如果文件存在且需要备份,并删除原文件
			bakFile(bean);
			// 创建文件
			FileUtils.touch(bean);
			
			List<Table> list = new ArrayList<Table>();
			list.add(table);
			String content = coverVM2String("Service.java.vm", beanName, list, table.getPkColumns(), null,table.getSqlList());
			// 写出文件
			FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateSqlMapConfig() throws Exception {
		//String sqlMapFilePath = ibatisConfigFilePath.concat(SQLMAP_CONFIG).concat(XML);
		String sqlMapFilePath = CODEPATH.concat(File.separator).concat(SQLMAP_CONFIG).concat(XML);
		File bean = new File(sqlMapFilePath);
		bakFile(bean);
		FileUtils.touch(bean);

		String content = coverVM2String("SqlMapConfig.xml.vm", null, tables, null, null);

		String sqlMapsFilePath = CODEPATH.concat(File.separator).concat("sqlMappers");

		File sqlMapFile = new File(sqlMapsFilePath);

		if (!sqlMapFile.exists()) {
			FileUtils.forceMkdir(sqlMapFile);
		}

		if (null != tables) {
			for (Iterator<Table> i = tables.iterator(); i.hasNext();) {
				Table table = i.next();
				if(table.getType().equals("ACTUAL")){
					
					//生成实际表的sqlmap
					String sqlMapName = "sqlmap-".concat(table.getJavaName()).concat(XML);
					generateSqlMap(table, sqlMapsFilePath.concat("/").concat(sqlMapName));
				}else{
					//生成虚拟表的sqlmap
					
				}
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
		List<Table> list = new ArrayList<Table>();
		list.add(table);
		String content = coverVM2String("sqlmap.xml.vm", simpleClassName, list, table.getPkColumns(), table.getColumns(),table.getSqlList());
		// 写入文件
		FileUtils.writeStringToFile(sqlMapFile, content, ENCODING_XML);
	}

	/**
	 * 
	 * <ul>
	 * <li>author hanman</li>
	 * <li>createDate: Mar 11, 2011 4:20:01 PM</li>
	 * <li>comment:生成spring支持ibatis的配置文件</li>
	 * </ul>
	 */
	@SuppressWarnings("unused")
	private void generateSpringIbatisConfig() throws Exception {
		String filePath = springConfigFilePath.concat("beans-dao-ibatis.xml");

		File file = new File(filePath);
		bakFile(file);
		FileUtils.touch(file);

		String content = coverVM2String("beans-dao-ibatis.xml.vm", null, tables, null, null);
		// 写入文件
		FileUtils.writeStringToFile(file, content, ENCODING_XML);
	}

	/**
	 * 生产spring配置文件
	 * @throws Exception
	 */
	private void generateSpringContextConfig() throws Exception
	{
		String filePath = CODEPATH.concat(File.separator).concat("application_context_test.xml");

		File file = new File(filePath);
		bakFile(file);
		FileUtils.touch(file);
		
		String content = coverVM2String("application_context_test.xml.vm", null, tables, null, null);
		// 写入文件
		FileUtils.writeStringToFile(file, content, ENCODING_XML);
	}
	
	
	public void generateTest(Table table){
		String name = table.getName();
		String beanName = ClassUtils.getClassName(name);
		String className = beanName.concat("ServiceTest");
		String beanPath = javaFilePath.concat(File.separator).concat("test");

		File beanFolder = new File(beanPath);
		try {
			if (!beanFolder.exists()) {
				FileUtils.forceMkdir(beanFolder);
			}
			String fileName = beanPath.concat(File.separator).concat(className).concat(JAVA);
			File bean = new File(fileName);
			// 如果文件存在且需要备份,并删除原文件
			bakFile(bean);
			// 创建文件
			FileUtils.touch(bean);
			String content = coverVM2String("Test.java.vm", beanName);
			// 写出文件
			FileUtils.writeStringToFile(bean, content, ENCODING_JAVA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
