package com.solo.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WechatUtil {

	private final static Logger logger = Logger.getLogger(WechatUtil.class);

	private final static long TWO_HOURS = 2 * 60 * 60 * 1000;

	private static long ACCESS_TOKEN_TIME = 0;

	private static String ACCESS_TOKEN = "";

	/**
	 * Get ACCESS_TOKEN from Wechat server or cache.
	 */
	public static String getAccessToken() {
		logger.info("getAccessToken start");

		// if the last time of getting accessToken is 2 hours before, get
		// accessToken again.
		if (!"".equals(ACCESS_TOKEN) && new Date().getTime() - ACCESS_TOKEN_TIME < TWO_HOURS) {
			logger.info("getAccessToken success, access_token:" + ACCESS_TOKEN);
			return ACCESS_TOKEN;
		}

		String url = Constants.URL_TOKEN.replace("APPID", Constants.APPID).replace("APPSECRET", Constants.SECRET);

		Map<String, Object> tokenMap = null;
		try {
			tokenMap = getConnection(new URL(url));
		} catch (MalformedURLException e) {
			logger.info("getConnection error, Exception:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("getConnection error, Exception:" + e.toString());
			e.printStackTrace();
		}

		String accessToken = (String) tokenMap.get("access_token");

		if (accessToken != null) {
			ACCESS_TOKEN = accessToken;
			ACCESS_TOKEN_TIME = new Date().getTime();
			logger.info("getAccessToken success, access_token:" + accessToken);
			return accessToken;
		} else {
			String errcode = (String) tokenMap.get("errcode");
			logger.info("getAccessToken error, errcode:" + errcode);
		}

		return "";
	}

	/**
	 * Connect to Wechat server(GET).
	 */
	public static Map<String, Object> getConnection(URL url) throws IOException {
		logger.info("getConnection start");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("GET");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		connection.connect();

		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = objectMapper.readValue(connection.getInputStream(), Map.class);

		connection.disconnect();

		logger.info("getConnection end");
		return resultMap;
	}

	/**
	 * Connect to Wechat server(POST).
	 */
	public static Map<String, Object> postConnection(URL url) throws IOException {
		logger.info("postConnection start");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		connection.connect();

		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> resultMap = objectMapper.readValue(connection.getInputStream(), Map.class);

		connection.disconnect();

		logger.info("postConnection end");
		return resultMap;
	}
}
