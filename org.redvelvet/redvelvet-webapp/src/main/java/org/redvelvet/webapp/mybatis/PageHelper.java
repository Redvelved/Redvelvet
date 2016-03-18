/*package org.redvelvet.webapp.mybatis;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

*//**
 * 
 * @ClassName: PageHelper
 * @Description:分页拦截器
 * @author yzh yzh yingzh@getui.com
 * @date 2016年3月16日 下午5:03:19
 *
 *//*

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class PageHelper implements Interceptor {

	*//**
	 * ThreadLocal 存储 分页信息
	 *//*
	private static final ThreadLocal<Page<?>> LOCAL_PAGE = new ThreadLocal<Page<?>>();

	private String databaseType = "mysql";// 数据库类型，不同的数据库有不同的分页方法

	// 属性参数信息
	private Properties properties;

	*//**
	 * 
	 * @Title: intercept @Description: 拦截方法 @param @param
	 * invocation @param @return @param @throws Throwable @throws
	 *//*
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Object parameter = invocation.getArgs()[1];
		
		try {
			//无分页信息
			if (LOCAL_PAGE.get() == null) {   
				return invocation.proceed(); // 执行
			} else {
				Page<?> page = getPage(parameter);
				return handlePaging(invocation, parameter, page);
			}

		} finally {
			clearLocalPage();
		}
	}

	@SuppressWarnings("rawtypes")
	protected List handlePaging(Invocation invocation, Object parameter, Page page) throws Exception {
		
		Object[] args=invocation.getArgs();
		
		MappedStatement mappedStatement = (MappedStatement)args[0] ;
		
		MappedStatement query_statement = newPageMappedStatement(mappedStatement,parameter, page);

		args[0]=query_statement;
		
		List data =(List) invocation.proceed();
		//List data = (List) exeQuery(invocation, query_statement);
		// 设置到page对象
		page.setResults(data);
		//page.setCount(getTotalSize(invocation, configuration, mappedStatement, boundSql, parameter));
		return data;
	}
	
	*//**
     * 根据提供的语句执行查询操作
     * <p>
     *
     * @param invocation
     * @param query_statement
     * @return
     * @throws Exception 
    *//*
    protected Object exeQuery(Invocation invocation, MappedStatement query_statement) throws Exception {
        Object[] args = invocation.getArgs();
        return invocation.getMethod().invoke(invocation.getTarget(),
                new Object[] { query_statement, args[1], args[2], args[3] });
    }

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	*//**
	 * 设置注册拦截器时设定的属性
	 *//*
	@Override
	public void setProperties(Properties p) {

		// 数据库方言
		// String dialect = p.getProperty("dialect");

		
		 * if (dialect == null || dialect.length() == 0) { this.properties = p;
		 * } else { autoDialect = false; sqlUtil = new SqlUtil(dialect);
		 * sqlUtil.setProperties(`p); }
		 

		// this.databaseType = properties.getProperty("databaseType");

		System.out.println("setProperties");
	}

	*//**
	 * 
	 * @Title: startPage @Description: 静态 设置 分页信息 @param @param
	 * pageNum @param @param pageSize @return void 返回类型 @throws
	 *//*
	public static void startPage(int pageNum, int pageSize) {
		Page<?> page = new Page<>(pageNum, pageSize);
		LOCAL_PAGE.set(page);
	}

	*//**
	 * 移除本地变量
	 *//*
	public static void clearLocalPage() {
		LOCAL_PAGE.remove();
	}

	*//**
	 * 获取分页参数(1.从local获取,2.从 RowBounds获取)
	 *
	 * @param params
	 *            RowBounds参数
	 * @return 返回Page对象
	 *//*
	public Page<?> getPage(Object params) {
		Page<?> page = LOCAL_PAGE.get();
		if (page == null) {
			if (params instanceof RowBounds) {
				RowBounds rowBounds = (RowBounds) params;
				page = new Page(rowBounds.getOffset(), rowBounds.getLimit());
			} else {
				// page = getPageFromObject(params);
			}
			LOCAL_PAGE.set(page);
		}
		return page;
	}

	*//**
	 * 
	 * @Title: getPageSql @Description: 获取分页SQL @param @param page @param @param
	 * sql @param @return @return String 返回类型 @throws
	 *//*
	private String getPageSql(Page<?> page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return getMysqlPageSql(page, sqlBuffer);
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return getOraclePageSql(page, sqlBuffer);
		}
		return sqlBuffer.toString();
	}
	
	*//**
     * 新建count查询和分页查询的MappedStatement
     *
     * @param ms
     * @return
     *//*
    public  MappedStatement newPageMappedStatement(MappedStatement ms,Object parameter,Page<?> page) {
       
    	List<ParameterMapping> parameterMappings= ms.getBoundSql(parameter).getParameterMappings();
    	parameterMappings.clear();
		// 查询结果集 
		StaticSqlSource sqlsource = new StaticSqlSource(
				ms.getConfiguration(), 
				getPageSql(page,ms.getBoundSql(parameter).getSql()),
				parameterMappings
				);

		
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), "id_page", sqlsource,
				SqlCommandType.SELECT);
		
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.statementType(ms.getStatementType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

	*//**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 *//*
	private String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (page.getPageNum() - 1) * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	}

	*//**
	 * 获取Oracle数据库的分页查询语句
	 * 
	 * @param page
	 *            分页对象
	 * @param sqlBuffer
	 *            包含原sql语句的StringBuffer对象
	 * @return Oracle数据库的分页查询语句
	 *//*
	private String getOraclePageSql(Page<?> page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (page.getPageNum() - 1) * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
				.append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		// 上面的Sql语句拼接之后大概是这个样子：
		// select * from (select u.*, rownum r from (select * from t_user) u
		// where rownum < 31) where r >= 16
		return sqlBuffer.toString();
	}

	*//**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @param page
	 *            Mapper映射语句对应的参数对象
	 * @param mappedStatement
	 *            Mapper映射语句
	 * @param connection
	 *            当前的数据库连接
	 *//*
	private void setTotalRecord(Page<?> page, MappedStatement mappedStatement, Connection connection) {
		// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
		// delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
		BoundSql boundSql = mappedStatement.getBoundSql(page);
		// 获取到我们自己写在Mapper映射语句中对应的Sql语句
		String sql = boundSql.getSql();
		// 通过查询Sql语句获取到对应的计算总记录数的sql语句
		String countSql = this.getCountSql(sql);
		// 通过BoundSql获取对应的参数映射
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		// 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
		// 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
		// 通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			// 之后就是执行获取总记录数的Sql语句和获取结果了。
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				// 给当前的参数page对象设置总记录数
				page.setTotalRecord(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	*//**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 *//*
	private String getCountSql(String sql) {

		return "select count(1) from (" + sql + ")";
	}

	*//**
	 * 利用反射进行操作的一个工具类
	 * 
	 *//*
	private static class ReflectUtil {
		*//**
		 * 利用反射获取指定对象的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标属性的值
		 *//*
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					result = field.get(obj);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		*//**
		 * 利用反射获取指定对象里面的指定属性
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @return 目标字段
		 *//*
		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException e) {
					// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
				}
			}
			return field;
		}

		*//**
		 * 利用反射设置指定对象的指定属性为指定的值
		 * 
		 * @param obj
		 *            目标对象
		 * @param fieldName
		 *            目标属性
		 * @param fieldValue
		 *            目标值
		 *//*
		public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
			Field field = ReflectUtil.getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}*/