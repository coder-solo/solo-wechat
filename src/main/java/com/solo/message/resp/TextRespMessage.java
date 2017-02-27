package com.solo.message.resp;

import com.solo.util.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class TextRespMessage extends BaseRespMessage {

	// 文本消息内容
	@XStreamCDATA
	@XStreamAlias("Content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
