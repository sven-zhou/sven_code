package com.auto.coder.generator;

import java.util.List;

import com.auto.coder.structure.db.Table;

public class GeneratorFactory {
    
    public static Generator Generate(String codeSupport, List<Table> tables) {
        // 默认为ibatis
        if (null == codeSupport) {
            codeSupport = "ibatis";
        }
        if (codeSupport.equals("ibatis")) {
            return new IbatisSupportGenerator(tables);
        } else if (codeSupport.equals("hibernate")) {
            return new HibernateSupportGenerator(tables);
        } else {
            throw new RuntimeException(codeSupport + " is not support!");
        }
    }
}
