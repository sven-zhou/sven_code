package com.auto.coder.utils;

import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.auto.coder.config.Constants;
import com.auto.coder.structure.conf.ExtendSql;
import com.auto.coder.structure.conf.MultiTableExendSql;
import com.auto.coder.structure.conf.ParamAndParamTypeMap;
import com.auto.coder.structure.db.Column;
import com.auto.coder.structure.db.Table;

/**
 * 
 * @ClassName: LoadExtendSql
 * @Description: 获取excel的sql数据
 * @author zhouxr
 * @date 2015年2月3日 下午4:28:47
 * 
 */
public class LoadExtendSql {

	public static List<ExtendSql> extendSqls = new ArrayList<ExtendSql>();

	public static List<MultiTableExendSql> multiTableExtendSql = new ArrayList<MultiTableExendSql>();
	
	/**
	 * @Title: installData
	 * @Description: 由excel内数据组装成自定义服务的数据
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return List<ExtendSql> 返回类型
	 * @throws
	 */
	public static void installData() throws Exception {

		Workbook workbook = Workbook.getWorkbook(LoadExtendSql.class.getClassLoader().getResourceAsStream(Constants.SQL_XLS));

		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		for (int i = 0; i < rows; i++) {
			Cell[] cells = sheet.getRow(i);

			if (cells.length == 0 || "method".equals(cells[2].getContents())) {
				continue;
			}

			MultiTableExendSql extendSql = new MultiTableExendSql();
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

			if (cells[1].getContents().contains(",")) {

				String resultParam = cells[7].getContents();
				extendSql.setResultParam(resultParam);
				multiTableExtendSql.add(extendSql);
			} else {
				extendSqls.add(extendSql);

			}
		}

	}

	
	public static List<Table> getmultiColumn(List<Table> tables){
		List<Table> dummyTable = new ArrayList<Table>();
		for (MultiTableExendSql tableExend : multiTableExtendSql) {
			
			//构造虚拟表
			Table mutitVMTable = new Table();
			ArrayList<Column> VMTableColumn = new ArrayList<Column>();
			
			//解析出表名,返回参数名
			String[] vmTables = tableExend.getTableName().split(",");
			String[] resultParams = tableExend.getResultParam().split(",");
			
			//根据表名获取返回值的列
			for (String vmTable : vmTables) {
				for (Table actTable : tables) {
					if (vmTable.equalsIgnoreCase(actTable.getName())) {
						ArrayList<Column> columns = actTable.getColumns();
						
						//根据匹配的表找出他的列
						for (String vmColumn : resultParams) {
							for (Column actColumn : columns) {
								if(actColumn.getName().equalsIgnoreCase(vmColumn)){
									VMTableColumn.add(actColumn);
								}
							}
							
						}
					}
				}
			}
			mutitVMTable.setName(tableExend.getKey());
			mutitVMTable.setName(tableExend.getKey().toUpperCase());
			mutitVMTable.setColumns(VMTableColumn);
			dummyTable.add(mutitVMTable);
		}
		return dummyTable;
	}
	
	public int matchSameHeard(String frist,String secend ){
		char[] charArray = frist.toCharArray();
		int point = 0;
		for (int i = 0; i < charArray.length; i++) {
			if(secend.charAt(i)==charArray[i])
				point = i;
			
		}
		return point;
	}
	
	public static void main(String[] args) throws Exception {
		LoadExtendSql loadExtendSql = new LoadExtendSql();
		int matchSameHeard = loadExtendSql.matchSameHeard("tbl_ab", "tbl_smae");
		System.out.println(matchSameHeard);
	}

}
