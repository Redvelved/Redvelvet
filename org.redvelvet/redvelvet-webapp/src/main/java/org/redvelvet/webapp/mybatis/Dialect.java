package org.redvelvet.webapp.mybatis;

/**   
* @Title: Dialect.java 
* @Package org.redvelvet.webapp.mybatis 
* @Description: 数据库 方言类型
* @author yzh yzh yingzh@getui.com
* @date 2016年3月16日  
*/

public enum Dialect {
    mysql, oracle, sqlserver, db2;

    public static Dialect of(String dialect) {
        try {
            Dialect d = Dialect.valueOf(dialect);
            return d;
        } catch (IllegalArgumentException e) {
            String dialects = null;
            for (Dialect d : Dialect.values()) {
                if (dialects == null) {
                    dialects = d.toString();
                } else {
                    dialects += "," + d;
                }
            }
            throw new IllegalArgumentException("Mybatis分页插件dialect参数值错误，可选值为[" + dialects + "]");
        }
    }

    public static String[] dialects() {
        Dialect[] dialects = Dialect.values();
        String[] ds = new String[dialects.length];
        for (int i = 0; i < dialects.length; i++) {
            ds[i] = dialects[i].toString();
        }
        return ds;
    }

    public static String fromJdbcUrl(String jdbcUrl) {
        String[] dialects = dialects();
        for (String dialect : dialects) {
            if (jdbcUrl.indexOf(":" + dialect + ":") != -1) {
                return dialect;
            }
        }
        return null;
    }
}