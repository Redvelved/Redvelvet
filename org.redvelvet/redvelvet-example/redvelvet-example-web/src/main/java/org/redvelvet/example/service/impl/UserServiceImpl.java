package org.redvelvet.example.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.redvelvet.example.dao.UserMapper;
import org.redvelvet.webapp.mybatis.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/*import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;*/

import org.redvelvet.example.model.User;
import org.redvelvet.example.model.UserExample;
import org.redvelvet.example.service.UserService;

/**   
* @Title: UserServiceImpl.java 
* @Package redvelvet.example.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月10日  
*/
@Repository
public class UserServiceImpl implements UserService {

	@Resource
    private UserMapper userMapper;
	
	@Override
	public User select(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<User> query(User queryParam,int pageIndex, int pageSize) {
		
		PageHelper.startPage(pageIndex, pageSize);
		
		// 查询条件 开始
		
		UserExample e =new UserExample();
		e.or().andRoleIdEqualTo(queryParam.getRoleId());
		
		//  查询条件 结束
	
		List<User> result=userMapper.selectByExample(e);
		return new PageInfo<User>(result);
	}
}
