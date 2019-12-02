package springboot.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import springboot.util.ProjectUtil.WebXml;

public class XMLConfigUtil extends DefaultHandler {
	// 查找项目中所有的servlet
	public Map<String, Object> servlets = new HashMap<String, Object>();

	public Map<String, Object> servletMapping = new HashMap<String, Object>();

	// 实例对象
	public Map<String, Object> servletInstances = new HashMap<String, Object>();

	private String tag;// 存储操作的标签
	private boolean isMapping = false;

	private String currentServlet;

	private String currentServletMapping;

	public WebXml load(String path) throws SAXException, IOException, ParserConfigurationException {
		// SAX解析
		// 1、获取SAX解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2、从解析工厂中获取解析器
		SAXParser parse = factory.newSAXParser();
		XMLConfigUtil handler = new XMLConfigUtil();
		// 5、解析
		parse.parse(path, this);
		// 6、获取数据
		ProjectUtil.WebXml webXMl = new ProjectUtil().new WebXml();
		webXMl.servlets = this.servlets;
		webXMl.servletMapping = this.servletMapping;
		return webXMl;

	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (null != qName) {
			tag = qName;// 存储标签名
			if (tag.equals("servlet")) {
				isMapping = false;
			} else if (tag.equals("servlet-mapping")) {
				isMapping = true;
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch, start, length).trim();
		if (null != contents) {// 处理空的问题
			if (isMapping) {// 操作servlet-mapping
				if (tag.equals("servlet-name")) {
					currentServletMapping = contents;
				} else if (tag.equals("url-pattern")) {
					String urlPattern = contents;
					servletMapping.put(urlPattern, currentServlet);
				}
			} else {// 操作servlet
				if (tag.equals("servlet-name")) {
					currentServlet = contents;
					currentServletMapping = contents;
				} else if (tag.equals("servlet-class")) {
					String servletClass = contents;
					servlets.put(currentServlet, servletClass);
				}
			}
		}
	}

}
