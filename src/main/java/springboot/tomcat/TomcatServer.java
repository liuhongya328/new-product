package springboot.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import springboot.util.ProjectUtil;

/**
 * @author liuhongya328
 *
 */
public class TomcatServer {
	
	static int threads = 10;
	private static ExecutorService pool = Executors.newCachedThreadPool();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//加载项目,获取tomcat需要发布的所有项目及项目下的所有servlet信息
		final Map<String, ProjectUtil.WebXml> projectIno = ProjectUtil.load();

		ServerSocket serverSocket = new ServerSocket(8080);
		System.out.println("Tomcat 启动成功");
		while(!serverSocket.isClosed()) {
			//阻塞获取新连接
			Socket socket = serverSocket.accept();

			pool.submit(()->{
				//接收数据
				InputStream inputStream = socket.getInputStream();;
				//请求响应结果
				OutputStream outputStream = socket.getOutputStream();
				try {
					System.out.println("收到请求：-------");
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
					String msg = null;
					StringBuilder requestInfo = new StringBuilder();
					while((msg = reader.readLine())!=null) {
						if(msg.length()==0) {
							break;
						}
						requestInfo.append(msg).append("\r\n");
					}
					System.out.println(requestInfo.toString()+"--------");
					
					//请求需要访问的servlet,这个servlet相当于业务代码的controller，是需要放入tomcat中启动的项目，用原生的servlet便于从底层代码理解
					//1.获取请求方式 及请求路径 GET /SpringMVC/servlet/littleServlet HTTP/1.1
					String firstLine = requestInfo.toString().split("\r\n")[0];
					String projectName = firstLine.split(" ")[1].split("/")[1];
					String servletPath = firstLine.split(" ")[1].replace("/"+projectName, "");

					//找到servlet对应的名称--j2ee规范
					String servletName = projectIno.get(projectName).servletMapping.get(servletPath).toString();
					//找到servlet对应的实例
					Servlet servlet =  (Servlet) projectIno.get(projectName).servletInstances.get(servletName);
					
					//将socket的inputsteam 转成 request对象 ,如果想servlet接收和传递消息,需要实现自己的HttpServletRequest/HttpServletResponse即可。
					//这里的创建仅作展示，实际没有作用，并不会传递socket的信息。
					HttpServletRequest servletReuqest = createRequest();
					
					HttpServletResponse servletResponse = createResponse();
					//servlet的生命周期
					servlet.service(servletReuqest, servletResponse);
					
					
					outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
					outputStream.write("Content-Length: 12\r\n\r\n".getBytes());
					outputStream.write("Hello World!".getBytes());
					outputStream.flush();
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				socket.close();
				return null;
			});
		}
		serverSocket.close();
	}
	
	
	private static HttpServletRequest createRequest() {
		return new HttpServletRequest() {
			@Override
			public Object getAttribute(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getCharacterEncoding() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getContentLength() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getContentLengthLong() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ServletInputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getParameter(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String[] getParameterValues(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getProtocol() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public BufferedReader getReader() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getRemoteAddr() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getRemoteHost() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setAttribute(String name, Object o) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeAttribute(String name) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public RequestDispatcher getRequestDispatcher(String path) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getRealPath(String path) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getRemotePort() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getLocalName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getLocalAddr() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getLocalPort() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public AsyncContext startAsync() throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
					throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isAsyncStarted() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isAsyncSupported() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public AsyncContext getAsyncContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public DispatcherType getDispatcherType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getAuthType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Cookie[] getCookies() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getDateHeader(String name) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getHeader(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<String> getHeaders(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Enumeration<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getIntHeader(String name) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getMethod() {
				//示例
				return "GET";
			}

			@Override
			public String getPathInfo() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getPathTranslated() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getContextPath() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getQueryString() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getRemoteUser() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isUserInRole(String role) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getRequestedSessionId() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getRequestURI() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public StringBuffer getRequestURL() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getServletPath() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HttpSession getSession(boolean create) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HttpSession getSession() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String changeSessionId() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isRequestedSessionIdValid() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromCookie() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromURL() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRequestedSessionIdFromUrl() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void login(String username, String password) throws ServletException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logout() throws ServletException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Collection<Part> getParts() throws IOException, ServletException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Part getPart(String name) throws IOException, ServletException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass)
					throws IOException, ServletException {
				// TODO Auto-generated method stub
				return null;
			}
			};
	}
	
	private static HttpServletResponse createResponse() {
		return new HttpServletResponse() {

			@Override
			public String getCharacterEncoding() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return null;
			}

			@Override
			public PrintWriter getWriter() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setCharacterEncoding(String charset) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setContentLength(int len) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setContentLengthLong(long length) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setContentType(String type) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setBufferSize(int size) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getBufferSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void flushBuffer() throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void resetBuffer() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean isCommitted() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setLocale(Locale loc) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void addCookie(Cookie cookie) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean containsHeader(String name) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String encodeURL(String url) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String encodeRedirectURL(String url) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String encodeUrl(String url) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String encodeRedirectUrl(String url) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void sendError(int sc, String msg) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendError(int sc) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void sendRedirect(String location) throws IOException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setDateHeader(String name, long date) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addDateHeader(String name, long date) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setHeader(String name, String value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addHeader(String name, String value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setIntHeader(String name, int value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addIntHeader(String name, int value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setStatus(int sc) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setStatus(int sc, String sm) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getStatus() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getHeader(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection<String> getHeaders(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
