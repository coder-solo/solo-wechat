package com.solo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solo.entity.WeChat;
import com.solo.util.SignUtil;

@Controller
public class WechatController {

	private static final Logger logger = Logger.getLogger(WechatController.class);

	@RequestMapping(value = "/test")
	public String helloTest(Model model) {
		logger.info("controller:test!");
		model.addAttribute("message", "Hello Wechat!!!");
		return "test";
	}

	@RequestMapping(value = "/hello")
	public String helloWorld() {
		logger.info("controller:hello!");
		return "hello";
	}

	@RequestMapping(value = "/verifyWechat", method = RequestMethod.GET)
	public String get(HttpServletRequest request, HttpServletResponse response) {

		logger.info("verifyWechat start.request");

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		if (SignUtil.verifySignature(signature, timestamp, nonce)) {
			logger.info("verifyWechat end.");
			return echostr;
		} else {
			logger.info("verify error.");
			return null;
		}
	}

	@RequestMapping(value = "/verifyWechatEx", method = RequestMethod.GET)
	@ResponseBody
	public String verifyWechatByGet(WeChat wc) {

		logger.info("verifyWechat start.");

		logger.info("wc:" + wc);
		String signature = wc.getSignature();

		String timestamp = wc.getTimestamp();
		String nonce = wc.getNonce();
		String echostr = wc.getEchostr();

		if (SignUtil.verifySignature(signature, timestamp, nonce)) {
			logger.info("verifyWechat end.");
			return echostr;
		} else {
			logger.info("verify error.");
			return null;
		}
	}

	@RequestMapping(value = "/verifyWechat", method = RequestMethod.POST)
	@ResponseBody
	public String getWechatMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("verifyWechat start.");

		return "";
	}
}
