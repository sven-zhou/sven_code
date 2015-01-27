package com.auto.coder;

import java.util.ArrayList;
import java.util.List;

import com.auto.coder.utils.ClassUtils;

public class Test {
	
	 public static String replaceFirstChar(String tableName) {
	        return tableName.substring(0, 1).toLowerCase().concat(tableName.substring(1, tableName.length()));
	    }
	
	
public static void main(String[] args) {
	List<String> list = new ArrayList<String>();
	
//	
	list.add("CargoCode");
	list.add("CargoName");
	list.add("CargoEName");
	list.add("CargoNum");
	list.add("CargoUnitPrice");
	
	

	
	
	for (String string : list) {
		System.out.println("@XStreamAlias(\""+string+"\")");
		System.out.println("private String "+replaceFirstChar(ClassUtils.getClassName(string))+";");
	}
	
	for (String string : list) {
		System.out.println(replaceFirstChar(ClassUtils.getClassName(string)));
		
	}
}
}
