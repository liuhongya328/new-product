package springmvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet{

	/**
	 * һ����򵥵�servlet
	 */
	private static final long serialVersionUID = 7683475760018217521L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data="�����ҵĵ�һ��servlet!";
		System.out.println(data);
	}
}
