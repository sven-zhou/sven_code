package com.auto.coder;

import java.util.ArrayList;
import java.util.List;

import com.auto.coder.utils.ClassUtils;

public class XsteamVoCode {
	
	 public static String replaceFirstChar(String tableName) {
	        return tableName.substring(0, 1).toLowerCase().concat(tableName.substring(1, tableName.length()));
	    }
	
/**
 * 	
* @Title: main 
* @Description: 据文档节点生成xstream 的vo
* @param @param args    设定文件 
* @return void    返回类型 
* @throws
 */
public static void main(String[] args) {
	List<String> list = new ArrayList<String>();
	
//	
	list.add("V_TAX_NO");
	list.add("V_TRADE_CODE");
	list.add("V_TRADE_NAME");
	list.add("V_TAXABLE_NO");
	list.add("N_STATUS");
	list.add("V_IEPC");
	list.add("D_EIDATE");
	list.add("V_PAY_NAME");
	list.add("V_PAY_ADDR");
	list.add("V_RECIVECARD_TYPE");
	list.add("V_RECEIVE_NUM");
	list.add("N_TAX_TOTAL");
	list.add("N_POST_SUM");
	list.add("N_TAX_MONEY");
	list.add("V_WARNING_FALG");
	list.add("N_TAXATION_USERID");
	list.add("D_TAXATION");
	list.add("D_LIST_G_DATE");
	list.add("N_GOOD_SUM");
	list.add("V_TAX_NOTE");
	list.add("V_NOTE");
	list.add("V_PLATFORM_CODE");
	list.add("V_PFREIGHT_NO");
	list.add("N_CANCEL_TYPE");
	
	for (String string : list) {
		System.out.println("@XStreamAlias(\""+string+"\")");
		System.out.println("private String "+replaceFirstChar(ClassUtils.getClassName(string))+";");
	}
	
//	for (String string : list) {
//		System.out.println(replaceFirstChar(ClassUtils.getClassName(string)));
//		
//	}
}
}
