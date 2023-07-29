package com.example.seleniumtest.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {

	public static void main(String[] args) {
		printNaverNewsFlashLatestArticles();
		// printDCInsideTreeGalleryLatestArticles();
		// printPpomppuFreeLatestArticles();
		// downloadUnsplashNatureImages();
	}

	private static void printNaverNewsFlashLatestArticles() {
		String innerFolderName = "naverNewsFlash";
		makeDownloadsFoldersIfNotExists(innerFolderName);
		ChromeDriver driver = getChromeDriver();

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
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
				downloadImage(thumbUrl, "downloads/" + innerFolderName);
			}
			System.out.println(thumbUrl);
		}
	}

	private static void printDCInsideTreeGalleryLatestArticles() {
		ChromeDriver driver = getChromeDriver();

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://gall.dcinside.com/board/lists/?id=tree");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> elements = driver.findElements(By.cssSelector(".gall_list .us-post"));

		for (WebElement element : elements) {
			int id = Util.getAsInt(element.findElements(By.cssSelector(".gall_num")).get(0).getText());
			String title = element.findElements(By.cssSelector(".gall_tit")).get(0).getText().trim();
			String writer = element.findElements(By.cssSelector(".gall_writer .nickname")).get(0).getText().trim();
			List<WebElement> ipElements = element.findElements(By.cssSelector(".gall_writer .ip"));
			String ipStart = "";
			if (!ipElements.isEmpty()) {
				ipStart = ipElements.get(0).getText().trim();
			}
			String regDate = element.findElements(By.cssSelector(".gall_date")).get(0).getAttribute("title").trim();
			int hit =  Util.getAsInt(element.findElements(By.cssSelector(".gall_count")).get(0).getText());
			int recommendsCount = Util.getAsInt(element.findElements(By.cssSelector(".gall_recommend")).get(0).getText());

			// String regDate = element.findElements(By.cssSelector(".gall_date")).get(0).getText().trim();
			// if (regDate.contains(":")) {
			// 	regDate = Util.getNowDateStr().substring(0, 7);
			// } else {
			// 	regDate = regDate.replaceAll("\\.", ":");
			// 	regDate = "20" + regDate;
			// }

			DCInsideArticle article = new DCInsideArticle(id, title, writer, ipStart, regDate, hit, recommendsCount);
			System.out.println(article);
		}
	}

	private static void printPpomppuFreeLatestArticles() {
		ChromeDriver driver = getChromeDriver();

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://www.ppomppu.co.kr/zboard/zboard.php?id=freeboard");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> elements = driver.findElements(
			By.cssSelector("#revolution_main_table tbody tr:not(.list_notice) > td:nth-child(3) > a"));

		for (WebElement element : elements) {
			String title = element.getText().trim();
			System.out.println(title);
		}
	}

	private static void downloadUnsplashNatureImages() {
		String innerFolderName = "unsplash";
		makeDownloadsFoldersIfNotExists(innerFolderName);
		ChromeDriver driver = getChromeDriver();

		// 빈 탭 생성
		// driver.executeScript("window.open('about:black', '_blank');");

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://unsplash.com/ko/t/nature");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> imgElements = driver.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img[data-test=\"photo-grid-masonry-img\"]"));

		for (WebElement imgElement : imgElements) {
			String src = imgElement.getAttribute("src");

			downloadImage(src, "downloads/" + innerFolderName);

			System.out.println("src = " + src);
		}
	}

	private static ChromeDriver getChromeDriver() {
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본 브라우저 사용 안함

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);
		return driver;
	}

	private static void makeDownloadsFoldersIfNotExists(String innerFolderName) {
		File downloadsFolder = new File("downloads/" + innerFolderName);
		if (!downloadsFolder.exists()) {
			downloadsFolder.mkdirs();
		}
	}

	private static void downloadImage(String src, String folderName) {
		BufferedImage saveImage = null;

		try {
			saveImage = ImageIO.read(new URL(src));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (saveImage != null) {
			try {
				// String fileName = src.split("/")[3];
				String fileName = src.substring(src.lastIndexOf("/"), src.indexOf(".", src.lastIndexOf("/")));
				fileName = fileName.split("\\?")[0];
				ImageIO.write(saveImage, "jpg", new File(folderName + "/" + fileName + ".jpg"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
