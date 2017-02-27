package com.solo.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.solo.message.resp.TextRespMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * WeChat message parse util class.
 * 
 * @author xiang.wang
 */
public class MessageUtil {

	/**
	 * Parse message of xml.
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		InputStream inputStream = request.getInputStream();

		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);

		Element root = document.getRootElement();

		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();

		for (Element el : elements) {
			map.put(el.getName(), el.getText());
		}

		inputStream.close();

		return map;
	}

	public static String textMessageToXml(TextRespMessage textMessage) {
		xstream.autodetectAnnotations(true);
		return xstream.toXML(textMessage);
	}

	private static XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {

				boolean cdata = false;

				@Override
				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
					if (clazz.equals(String.class)) {
						cdata = true;
					} else {
						cdata = false;
					}
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}
