package com.example.seleniumtest.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Util {

	private static ChromeDriver chromeDriver;

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static int getAsInt(String str) {
		if (str == null) {
			return 0;
		}

		str = str.trim();

		if (str.length() == 0) {
			return 0;
		}

		str = str.replaceAll(",", "");

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}

	}

	public static String getNowDateStr() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(System.currentTimeMillis());
	}

	public static ChromeDriver getChromeDriver() {
		if (chromeDriver != null) {
			return chromeDriver;
		}

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

		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));

		chromeDriver = driver;
		return driver;
	}

	public static void makeDownloadsFoldersIfNotExists(String innerFolderName) {
		File downloadsFolder = new File("downloads/" + innerFolderName);
		if (!downloadsFolder.exists()) {
			downloadsFolder.mkdirs();
		}
	}

	public static void downloadImage(String src, String destFilePath) {
		BufferedImage saveImage = null;

		try {
			saveImage = ImageIO.read(new URL(src));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (saveImage != null) {
			try {
				ImageIO.write(saveImage, "jpg", new File(destFilePath));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
