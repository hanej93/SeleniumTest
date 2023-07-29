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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getIpStart() {
		return ipStart;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getRecommendsCount() {
		return recommendsCount;
	}

	public void setRecommendsCount(int recommendsCount) {
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
