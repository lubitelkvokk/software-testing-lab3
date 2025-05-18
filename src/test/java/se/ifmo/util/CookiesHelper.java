package se.ifmo.util;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;

public class CookiesHelper {

    private static final String COOKIE_FILE_PATH = "src/test/resources/auth_cookies.data";

    // Сохранение кук в файл
    public static void saveCookies(WebDriver driver) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COOKIE_FILE_PATH))) {
            for (Cookie cookie : driver.manage().getCookies()) {
                writer.write(cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";" +
                        cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure());
                writer.newLine();
            }
            System.out.println("Куки сохранены.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Загрузка кук из файла
    public static void loadCookies(WebDriver driver) {
        try (BufferedReader reader = new BufferedReader(new FileReader(COOKIE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cookieData = line.split(";");
                String name = cookieData[0];
                String value = cookieData[1];
                String domain = cookieData[2];
                String path = cookieData[3];

                Cookie cookie = new Cookie(name, value, domain, path, null, false);
                driver.manage().addCookie(cookie);
            }
            System.out.println("Куки загружены.");
            driver.navigate().refresh();
        } catch (IOException e) {
            System.out.println("Не удалось загрузить куки.");
            e.printStackTrace();
        }
    }

    public static void clearCookiesFile() throws FileNotFoundException {
        File file = new File(COOKIE_FILE_PATH);
        PrintWriter pw = new PrintWriter(file);
        pw.print("");
        pw.close();
    }
}
