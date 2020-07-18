import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://meteo.by/minsk/";
        Document page = Jsoup.parse(new URL(url), 100000);
        return page;
    }

    private static String getDateFromString(String StringDate) throws Exception {
        Pattern pattern0 = Pattern.compile("\\s[а-я]*");
        Pattern pattern = Pattern.compile("\\d{2}");

        Matcher matcher = pattern.matcher(StringDate);
        Matcher matcher1 = pattern0.matcher(StringDate);
        if ((matcher.find()) && (matcher1.find())) {
            return matcher.group() + matcher1.group();
        }
        throw new Exception("Can't extract date from String");
    }

    private static void printFourValues(Elements values, int index) {

        for (int i = index; i < index + 4; i++) {

            Element valueLine = values.get(i);
            for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "    ");

            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        //css query language
        Element table = page.select("ul[class=b-weather]").first();
        Elements names = table.select("p[class=date]");
        Elements values = table.select("tr[class=time]");
        int index = 0;

        for (Element name : names) {
            System.out.println();
            String StringDate = name.select("p[class=date]").text();
            String date = getDateFromString(StringDate);

            System.out.println(date);
            System.out.println("   температура   явление    давление   влажность   ветер");

            printFourValues(values, index);
            index = index + 4;

        }

    }
}
