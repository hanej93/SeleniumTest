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

		// 빈 탭 생성
		driver.executeScript("window.open('about:black', '_blank');");

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://unsplash.com/ko/t/nature");

		Util.sleep(1000); // 웹페이지가 띄워지는 시간 대기

		List<WebElement> imgElements = driver.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img[data-test=\"photo-grid-masonry-img\"]"));

		makeDownloadsFoldersIfNotExists();

		for (WebElement imgElement : imgElements) {
			String src = imgElement.getAttribute("src");

			downloadImage(src);

			System.out.println("src = " + src);
		}
	}

	private static void makeDownloadsFoldersIfNotExists() {
		File downloadsFolder = new File("downloads");
		if (!downloadsFolder.exists()) {
			downloadsFolder.mkdirs();
		}
	}

	private static void downloadImage(String src) {
		BufferedImage saveImage = null;

		try {
			saveImage = ImageIO.read(new URL(src));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (saveImage != null) {
			try {
				String fileName = src.split("/")[3];
				fileName = fileName.split("\\?")[0];
				ImageIO.write(saveImage, "jpg", new File("downloads/" + fileName + ".jpg"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
