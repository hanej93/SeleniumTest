package com.example.seleniumtest.test;

public class DCInsideArticle {
	private int id;
	private String title;
	private String writer;
	private String ipStart;
	private String regDate;
	private int hit;
	private int recommendsCount;

	public DCInsideArticle(int id, String title, String writer, String ipStart, String regDate, int hit,
		int recommendsCount) {
		this.id = id;
		this.title = title;
		this.writer = writer;
		this.ipStart = ipStart;
		this.regDate = regDate;
		this.hit = hit;
		this.recommendsCount = recommendsCount;
	}

	@Override
	public String toString() {
		return "DCInsideArticle{" +
			"id=" + id +
			", title='" + title + '\'' +
			", writer='" + writer + '\'' +
			", ipStart='" + ipStart + '\'' +
			", regDate='" + regDate + '\'' +
			", hit=" + hit +
			", recommendsCount=" + recommendsCount +
			'}';
	}
}
