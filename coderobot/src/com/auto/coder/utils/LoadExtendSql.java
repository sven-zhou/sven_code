package com.auto.coder.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.auto.coder.Constants;
import com.auto.coder.data.ExtendSql;
import com.auto.coder.data.ParamAndParamTypeMap;

public class LoadExtendSql {

	public static Properties prop = null;

	public static void init(String path) throws Exception {

		prop = new Properties();
		// InputStream inStream = new
		// FileInputStream(Loader.getResource(path).toURI().toString());
		InputStream inStream = Constants.class.getClassLoader().getResourceAsStream(path);
		prop.load(inStream);
	}

	public static List<ExtendSql> installData() throws Exception {

		Workbook workbook = Workbook.getWorkbook(LoadExtendSql.class.getClassLoader().getResourceAsStream("sql.xls"));

		List<ExtendSql> extendSqls = new ArrayList<ExtendSql>();

		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		for (int i = 0; i < rows; i++) {
			Cell[] cells = sheet.getRow(i);

			if (cells.length==0) {
				continue;
			}
			
			ExtendSql extendSql = new ExtendSql();
			extendSql.setKey(cells[0].getContents());
			extendSql.setTableName(cells[1].getContents());
			extendSql.setAction(cells[2].getContents());
			extendSql.setParameterType(cells[3].getContents());
			List<ParamAndParamTypeMap> list = new ArrayList<ParamAndParamTypeMap>();
			String[] paramType = cells[4].getContents().split(",");
			String[] param = cells[5].getContents().split(",");
			for (int j = 0; j < param.length; j++) {
				ParamAndParamTypeMap map = new ParamAndParamTypeMap();
				map.setParam(param[j]);
				map.setParamType(paramType[j]);
				list.add(map);
			}
			extendSql.setJavaParamTypeAndParam(list);
			extendSql.setSql(cells[6].getContents());
			extendSqls.add(extendSql);
		}

		/*
		 * Set<Object> keySet = prop.keySet(); for (Object object : keySet) {
		 * String key = (String) object; String value = (String) prop.get(key);
		 * ExtendSql extendSql = new ExtendSql(); extendSql.setKey(key);
		 * extendSql.setTableName(value.split("~")[0]);
		 * extendSql.setAction(value.split("~")[1]);
		 * extendSql.setParameterType(value.split("~")[2]);
		 * extendSql.setJavaParamType(value.split("~")[3]);
		 * extendSql.setJavaParam(value.split("~")[4]);
		 * extendSql.setSql(value.split("~")[5]); extendSqls.add(extendSql); }
		 */
		return extendSqls;

	}

	public static void main(String[] args) throws Exception {

		LoadExtendSql.installData();
	}

}
