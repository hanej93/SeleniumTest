package com.example.seleniumtest.test;

public class NaverNewsArticle {

	private String code;
	private String title;
	private String summary;
	private String channel;
	private String thumbUrl;

	public NaverNewsArticle(String code, String title, String summary, String channel, String thumbUrl) {
		this.code = code;
		this.title = title;
		this.summary = summary;
		this.channel = channel;
		this.thumbUrl = thumbUrl;
	}

	@Override
	public String toString() {
		return "NaverNewsArticle{" +
			"code='" + code + '\'' +
			", title='" + title + '\'' +
			", summary='" + summary + '\'' +
			", channel='" + channel + '\'' +
			", thumbUrl='" + thumbUrl + '\'' +
			'}';
	}
}
