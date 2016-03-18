package org.redvelvet.webapp.mybatis;

/**   
* @Title: QueryHandler.java 
* @Package org.redvelvet.webapp.database 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月16日  
*/

public abstract class QueryHandler<T> {

	private T queryParam;

	public T getQueryParam() {
		return queryParam;
	}

	abstract void setQueryParam(T queryParam);
}
