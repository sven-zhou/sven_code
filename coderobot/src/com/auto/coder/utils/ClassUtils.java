package com.auto.coder.utils;

import com.auto.coder.Constants;

public class ClassUtils {
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Aug 9, 2010 12:10:56 PM</li>
     * <li>comment:转化类名</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @param tableName
     * @return
     */
    public static String getClassName(String tableName) {
        String className = null;
        // 先转换首字母大写
        className = replaceFirstChar(tableName);
        // 如果有下划线，把下划线后的字母转为大写
        className = replaceUnderLine(className);
        return className;
    }
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Aug 9, 2010 12:07:21 PM</li>
     * <li>comment: 取得类的短名，如:java.lang.String返回String</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @param className
     * @return
     */
    public static String getShortClassName(String className) {
        return className.substring(className.lastIndexOf(".") + 1, className.length());
    }
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Aug 9, 2010 12:14:45 PM</li>
     * <li>comment:取得属性值以驼峰形式</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @param fieldName
     * @return
     */
    public static String getFieldName(String fieldName) {
        return replaceUnderLine(fieldName.toLowerCase());
    }
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Aug 9, 2010 11:05:14 AM</li>
     * <li>comment:转换首字母大写</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @param tableName
     * @return
     */
    public static String replaceFirstChar(String tableName) {
        return tableName.substring(0, 1).toUpperCase().concat(tableName.toLowerCase().substring(1, tableName.length()));
    }
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Aug 9, 2010 11:05:34 AM</li>
     * <li>comment:把下划线后的字母转为大写，并去掉下划线</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @param className
     * @return
     */
    public static String replaceUnderLine(String className) {
        if (className.contains(Constants.UNDERLINE)) {
            className = className.substring(0, className.indexOf(Constants.UNDERLINE)).concat(
                    replaceFirstChar(className
                            .substring(className.indexOf(Constants.UNDERLINE) + 1, className.length())));
        } else {
            return className;
        }
        className = replaceUnderLine(className);
        return className;
    }
    
    public static void main(String[] args) {
        System.out.println(ClassUtils.getClassName("tAble"));
        System.out.println(ClassUtils.getClassName("table_table"));
        System.out.println(ClassUtils.getClassName("table_table_table"));
        
        
    }
}
