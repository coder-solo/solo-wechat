package com.solo.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.solo.message.resp.TextRespMessage;
import com.solo.service.AutoReplyService;
import com.solo.util.Constants;
import com.solo.util.MessageUtil;

@Service
public class AutoReplyServiceImpl implements AutoReplyService {

	private static final Logger logger = Logger.getLogger(AutoReplyServiceImpl.class);

	@Override
	public String processRequest(HttpServletRequest request) {

		logger.info("processRequest start.");

		String respXmlMessage = null;
		try {
			Map<String, String> map = MessageUtil.parseXml(request);

			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String msgType = map.get("MsgType");

			TextRespMessage textRespMessage = new TextRespMessage();
			textRespMessage.setToUserName(fromUserName);
			textRespMessage.setFromUserName(toUserName);
			textRespMessage.setCreateTime(new Date().getTime());
			textRespMessage.setMsgType(Constants.RESP_MESSAGE_TYPE);

			// text type
			if (Constants.REQ_MESSAGE_TYPE.equals(msgType)) {

				String content = map.get("Content");
				if (content.contains("1")) {
					textRespMessage.setContent("收到的是1");
					respXmlMessage = MessageUtil.textMessageToXml(textRespMessage);
				} else if (content.contains("2")) {
					textRespMessage.setContent("收到的是2");
					respXmlMessage = MessageUtil.textMessageToXml(textRespMessage);
				} else {
					textRespMessage.setContent("你说的是什么，我不懂！");
					respXmlMessage = MessageUtil.textMessageToXml(textRespMessage);
				}
			} else {
				textRespMessage.setContent("收到的是我不能理解的类型！");
				respXmlMessage = MessageUtil.textMessageToXml(textRespMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
			respXmlMessage = "success"; // 官网推荐设置
		}

		logger.info("processRequest end.");
		return respXmlMessage;
	}

}
