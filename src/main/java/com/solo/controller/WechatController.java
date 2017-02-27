package com.solo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.solo.service.AutoReplyService;
import com.solo.util.SignUtil;

@Controller
public class WechatController {

	private final static Logger logger = Logger.getLogger(WechatController.class);

	@Autowired
	private AutoReplyService autoReplyService;

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
	public void get(HttpServletRequest request, HttpServletResponse response) {

		logger.info("verifyWechat start.");

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (SignUtil.verifySignature(signature, timestamp, nonce)) {
				out.print(echostr);
				logger.info("verifyWechat end.");
			} else {
				logger.error("verify error.");
			}
		} catch (IOException e) {
			logger.error("verify exception.");
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}

	@RequestMapping(value = "/verifyWechat", method = RequestMethod.POST)
	public void post(HttpServletRequest request, HttpServletResponse response) {
		logger.info("post start.");

		String xmlMessge = autoReplyService.processRequest(request);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(xmlMessge);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Post exception.");
		} finally {
			writer.close();
			writer = null;
		}

		logger.info("post end.");
	}
}
