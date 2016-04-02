package org.redvelvet.example.web.service;
import com.github.pagehelper.PageInfo;
import org.redvelvet.example.web.model.User;

/**
* @Title: UserService.java 
* @Package redvelvet.example.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月10日  
*/
public interface UserService {
	
	
	User select(int id);
	
	PageInfo<User> query(User queryParam, int pageIndex, int pageSize);
	
}
