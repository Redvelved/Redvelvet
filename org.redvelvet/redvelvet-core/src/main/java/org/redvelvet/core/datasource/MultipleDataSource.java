/*package org.redvelvet.core.datasource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

*//**   
* @Title: MultipleDataSource.java 
* @Package mybatis 
* @Description: 多数据源选择器
* @author yzh yzh yingzh@getui.com
* @date 2016年3月15日  
*//*

public class MultipleDataSource extends AbstractRoutingDataSource {
	
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }
    
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
*/