package redvelvet.example.service;
import com.github.pagehelper.PageInfo;
import redvelvet.example.model.User;
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
