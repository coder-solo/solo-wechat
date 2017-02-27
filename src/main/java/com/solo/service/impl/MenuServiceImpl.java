package com.solo.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.solo.service.MenuService;
import com.solo.util.Constants;
import com.solo.util.WechatUtil;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);

	@Override
	public void createMenu(String jsonMenu) {

		String accessToken = WechatUtil.getAccessToken();
		if (StringUtils.isEmpty(accessToken)) {
			logger.info("Get accessToken error.");
			return;
		}
		String url = Constants.URL_MENU_CREATE.replace("ACCESS_TOKEN", accessToken);
		logger.info("url:" + url);

		Map<String, Object> resultMap = null;
		try {
			resultMap = WechatUtil.postConnection(new URL(url));
		} catch (IOException e) {
			logger.info("createMenu error. Exception:" + e);
			e.printStackTrace();
			return;
		}

		int errcode = (int) resultMap.get("errcode");
		if (errcode == 0) {
			logger.info("createMenu success");
		} else {
			String errmsg = (String) resultMap.get("errmsg");
			logger.info("createMenu error. errcode:" + errcode + ", errmsg:" + errmsg);
		}

	}
}
