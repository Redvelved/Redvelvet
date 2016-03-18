package org.redvelvet.webapp.mybatis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: Page 
* @Description: 分页对象
* @author yzh yzh yingzh@getui.com
* @date 2016年3月16日 下午5:11:58 
* 
* @param <T>
 */
public class Page<T> {
	
	/**
     * 页码，从1开始
     */
    private int pageNum = 1;
    /**
     * 页面大小
     */
    private int pageSize = 15;
    /**
     * 总数
     */
    private int totalRecord;
    /**
     * 总页数
     */
    private int totalPage;
    
    //结果集
    private List<T> results;
    
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它分装成一个Map对象
 
 
    public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
    
    /**
     * 开始分页
     *
     * @param pageNum      页码
     * @param pageSize     每页显示数量
    **/
    public Page(int pageNum, int pageSize){
    	this.pageNum=pageNum;
    	this.pageSize=pageSize;
    }
 
    
    @Override
    public String toString() {
    	
    	final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", totalPage=").append(totalPage);
        sb.append(", totalRecord=").append(totalRecord);
        sb.append(", list=").append(results);
        sb.append('}');
        return sb.toString();
    }
 
}