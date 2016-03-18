package org.redvelvet.core.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.redvelvet.core.IConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: BaseConfig 
* @Description: 基础配置
* @author yzh yzh yingzh@getui.com
* @date 2016年3月8日 下午5:44:56 
*
 */
public class BaseConfig implements IConfig{
	
	private static Logger logger = LoggerFactory.getLogger(BaseConfig.class);
	
    private Properties properties;
    private String userdir;
    private static BaseConfig instance = new BaseConfig();

    public static BaseConfig getInstance() {
        return instance;
    }

    private BaseConfig() {
        userdir = System.getProperty("user.dir");
        load();
    }
  
    private void load() {
        FileInputStream fis = null;
        try {
            File configfile = getConfigFile();
            fis = new FileInputStream(configfile);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
        	logger.warn("BaseConfig load error : {}",e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
            if (properties==null) {
            	properties=new Properties();
            	//logger.warn("BaseConfig load error : properties is　null");
			}
        }
    }
    
    @Override
	public String getHomeDir() {
		return userdir;
	}
    
    @Override
    public String getConfigDir() {
        return getHomeDir() + File.separator + "config";
    }

    private File getConfigFile() {
        return new File(getConfigDir() + File.separator + "rd.properties");
    }
   

    public String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public Boolean getProperty(String name, Boolean defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return null;
    }

    public Integer getProperty(String name, Integer defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        if (value != null) {
            return Integer.parseInt(value);
        }
        return null;
    }

    public Long getProperty(String name, Long defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        if (value != null) {
            return Long.parseLong(value);
        }
        return null;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public float getProperty(String key, Float defaultValue) {
        String value = properties.getProperty(key, String.valueOf(defaultValue));
        if (value != null) {
            return Float.parseFloat(value);
        }

        return 0.0f;
    }

	

}