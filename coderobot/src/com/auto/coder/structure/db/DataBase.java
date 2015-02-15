package com.auto.coder.structure.db;

import java.util.ArrayList;
/**
* @ClassName: Env 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhouxr
*
 */
public class DataBase {

	/**
	 * 数据库中的表
	 */
	public static ArrayList<Table> tables = new ArrayList<Table>();

	/**
	 * 数据库中的视图
	 */
	public static ArrayList<View> views = new ArrayList<View>();

}