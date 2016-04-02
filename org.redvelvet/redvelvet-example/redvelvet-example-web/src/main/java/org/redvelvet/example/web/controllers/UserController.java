package org.redvelvet.example.web.controllers;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageInfo;
import org.redvelvet.example.model.User;
import org.redvelvet.example.service.UserService;

/**   
* @Title: UserController.java 
* @Package redvelvet.example.web.controllers 
* @Description: TODO(用一句话描述该文件做什么) 
* @author yzh yzh yingzh@getui.com
* @date 2016年3月8日  
*/
@Controller
@RequestMapping("/")
public class UserController {
	
	@Resource
    private UserService userService;
 
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
    
    @RequestMapping("/")
    public String index(){        
        return "index";
    }
    
    @ResponseBody
    @RequestMapping("/user/{id}")
    public User json(@PathVariable("id") int id){
        return userService.select(id);
    }
    
    @ResponseBody
    @RequestMapping("/user/query")
    public PageInfo<User> query(
    		@ModelAttribute User user,
    		@RequestParam(required = true, value = "index")  int index,
            @RequestParam(required = true, value = "count")  int count){
    	
        return userService.query(user, index, count);
    }
    
   /* @ResponseBody
    @RequestMapping("/user/query2")
    public PageInfo<User> query2(
    		@ModelAttribute User user,
    		@RequestParam(required = true, value = "index")  int index,
            @RequestParam(required = true, value = "count")  int count){
    	
         userService.query2(user, index, count);
         return null;
    }*/
}