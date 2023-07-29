package com.example.seleniumtest.test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

	public void run() {
		printNaverNewsFlashLatestArticles();
		printDCInsideTreeGalleryLatestArticles();
		printPpomppuFreeLatestArticles();
		downloadUnsplashNatureImages();
	}

	private void printNaverNewsFlashLatestArticles() {
		String innerFolderName = "naverNewsFlash";
		Util.makeDownloadsFoldersIfNotExists(innerFolderName);
		ChromeDriver driver = Util.getChromeDriver();
		driver.get("https://news.naver.com/main/list.naver?mode=LSD&mid=sec&sid1=001");

		List<WebElement> elements = driver.findElements(By.cssSelector(".type06_headline li"));
		// List<WebElement> elements = driver.findElements(By.cssSelector(".type06 li"));

		for (WebElement element : elements) {
			WebElement aElement = element.findElement(By.cssSelector("dt > a"));
			String href = aElement.getAttribute("href");
			String code = href.substring(href.lastIndexOf("/") + 1, href.indexOf("?"));
			String title = element.findElement(By.cssSelector("dt:not(.photo) > a")).getText().trim();
			String summary = element.findElement(By.cssSelector("dd > .lede")).getText().trim();
			String channel = element.findElement(By.cssSelector("dd > .writing")).getText().trim();
			List<WebElement> thumbElements = element.findElements(By.cssSelector("dt.photo > a > img"));
			String thumbUrl = "";
			if (!thumbElements.isEmpty()) {
				thumbUrl = thumbElements.get(0).getAttribute("src");
				Util.downloadImage(thumbUrl,"downloads/" + innerFolderName + "/" + code + ".jpg");
			}

			NaverNewsArticle article = new NaverNewsArticle(code, title, summary, channel, thumbUrl);
			System.out.println(article);
		}
	}

	private void printDCInsideTreeGalleryLatestArticles() {
		ChromeDriver driver = Util.getChromeDriver();
		driver.get("https://gall.dcinside.com/board/lists/?id=tree");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> elements = driver.findElements(By.cssSelector(".gall_list .us-post"));

		for (WebElement element : elements) {
			int id = Util.getAsInt(element.findElement(By.cssSelector(".gall_num")).getText());
			String title = element.findElement(By.cssSelector(".gall_tit")).getText().trim();
			String writer = element.findElement(By.cssSelector(".gall_writer .nickname")).getText().trim();
			List<WebElement> ipElements = element.findElements(By.cssSelector(".gall_writer .ip"));
			String ipStart = "";
			if (!ipElements.isEmpty()) {
				ipStart = ipElements.get(0).getText().trim();
			}
			String regDate = element.findElement(By.cssSelector(".gall_date")).getAttribute("title").trim();
			int hit =  Util.getAsInt(element.findElement(By.cssSelector(".gall_count")).getText());
			int recommendsCount = Util.getAsInt(element.findElement(By.cssSelector(".gall_recommend")).getText());

			DCInsideArticle article = new DCInsideArticle(id, title, writer, ipStart, regDate, hit, recommendsCount);
			System.out.println(article);
		}
	}

	private void printPpomppuFreeLatestArticles() {
		ChromeDriver driver = Util.getChromeDriver();
		driver.get("https://www.ppomppu.co.kr/zboard/zboard.php?id=freeboard");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> elements = driver.findElements(
			By.cssSelector("#revolution_main_table tbody tr:not(.list_notice) > td:nth-child(3) > a"));

		for (WebElement element : elements) {
			String title = element.getText().trim();
			System.out.println(title);
		}
	}

	private void downloadUnsplashNatureImages() {
		String innerFolderName = "unsplash";
		Util.makeDownloadsFoldersIfNotExists(innerFolderName);
		ChromeDriver driver = Util.getChromeDriver();

		// 빈 탭 생성
		// driver.executeScript("window.open('about:black', '_blank');");
		driver.get("https://unsplash.com/ko/t/nature");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> imgElements = driver.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img[data-test=\"photo-grid-masonry-img\"]"));

		for (WebElement imgElement : imgElements) {
			String src = imgElement.getAttribute("src");

			String fileName = src.substring(src.lastIndexOf("/"), src.indexOf(".", src.lastIndexOf("/")));
			fileName = fileName.split("\\?")[0];
			Util.downloadImage(src,"downloads/" + innerFolderName + "/" + fileName + ".jpg");

			System.out.println("src = " + src);
		}
	}


}
