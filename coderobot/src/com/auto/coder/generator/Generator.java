package com.auto.coder.generator;

/**
 * 
 * @author zhouxr
 * <ul>
 * <li>comment:代码生成器</li>
 * <ul>
 */
public interface Generator {
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Mar 21, 2011 1:55:29 PM</li>
     * <li>comment:生成java文件</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @throws Exception
     */
    public void generateJavaFile() throws Exception;
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Mar 21, 2011 1:55:42 PM</li>
     * <li>comment:生成xml配置文件</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @throws Exception
     */
    public void generateConfigFile() throws Exception;
    
    /**
     * 
     * <ul>
     * <li>author hanman</li>
     * <li>createDate: Mar 21, 2011 1:56:07 PM</li>
     * <li>comment:生成properties配置文件</li>
     * <p>
     * </p>
     * </ul>
     * 
     * @throws Exception
     */
    public void generatePropertyFiles() throws Exception;
}
