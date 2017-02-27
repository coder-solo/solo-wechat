package com.solo.message.resp;

import com.solo.util.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BaseRespMessage {

	// 发送方帐号（一个OpenID）
	@XStreamCDATA
	@XStreamAlias("ToUserName")
	private String toUserName;

	// 开发者微信号
	@XStreamCDATA
	@XStreamAlias("FromUserName")
	private String fromUserName;

	// 消息创建时间 （整型）
	@XStreamAlias("CreateTime")
	private long createTime;

	// test
	@XStreamCDATA
	@XStreamAlias("MsgType")
	private String msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}
