package springboot.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;

/**
 * 
 * 加载项目信息
 * 
 * */
public class ProjectUtil {
	
	public static Map<String,WebXml> load() throws Exception{
		final Map<String,WebXml> projetInfo = new HashMap<String,WebXml>();
		 //j2ee定义的是tomcat下的webapp目录，本地我取自己打包的war包目录
		 String webapps = "E:\\learning\\SpringMVC\\SpringMVC\\webapps";
		 //读取tomcat发布的所有的项目
		 File[] projects = new File(webapps).listFiles(projectName -> projectName.isDirectory());
		 
		 for(File project : projects) {
			 //根据每个项目的web.xml读取servlet信息
			 WebXml webXml = new XMLConfigUtil().load(project.getPath()+"\\WEB-INF\\web.xml");
			 webXml.projectPath = project.getPath();
			 //类加载，加载class文件-----war包或者jar包中解压的class文件
			 webXml.loadServlet();
			 projetInfo.put(project.getName(), webXml);
		 }
		
		 
		 return projetInfo;
	}
	
	
	public class WebXml{
		public String projectPath = null;
		
		//查找项目中所有的servlet
		public Map<String,Object> servlets = new HashMap<String,Object>();
		
		public Map<String,Object> servletMapping = new HashMap<String,Object>();
		
		//实例对象
		public Map<String,Object> servletInstances = new HashMap<String,Object>();
		
		public void loadServlet() throws Exception {
			//jvm加载class文件
			URL url = new URL("file:"+projectPath+"\\WEB-INF\\classes\\");
			URLClassLoader classLoader = new URLClassLoader(new URL[] {url});
			
			//加载servlet,创建servlet对象
			for(Entry<String,Object> entry : servlets.entrySet()) {
				//servlet的名称
				String servletName = entry.getKey();
				//servlet类的完整路径
				String servletClassName = entry.getValue().toString();
				
				//加载
				Class<?> clazz = classLoader.loadClass(servletClassName);
				//反射创建对象,这就是为什么j2ee规范 servlet要继承HttpServlet，
				Servlet servlet = (Servlet) clazz.newInstance();
				this.servletInstances.put(servletName, servlet);
			}
		}
	}
}
