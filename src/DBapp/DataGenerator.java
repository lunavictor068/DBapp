package DBapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lunav on 1/20/2016.
 */
public class DataGenerator {
    private static DBConnection dbConnection = new DBConnection("dbuser", "password");
    public static void main(String[] args) throws Exception {
        for (String keyword : args) {
            try {
                Document document = Jsoup.connect(
                        "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords="
                                + keyword).get();
                Elements links = document.select("a[class*=a-link-normal s-access-detail-page  a-text-normal]");
                searchProducts(links);
            }
            catch (Exception e){
                System.err.println("EXCEPTION: " + e.toString());
            }
        }
    }

    private static void searchProducts(Elements links) {
        for (Element element : links) {
            try {
            System.out.println();
            Thread.sleep(2100);
                String url = element.attr("href");
                System.out.println("URL: " + url);
                Element newDoc = Jsoup.connect(url).get().body();
                String title = newDoc.select("span[id*=productTitle]").text();
                if (!(title.contains("amazon") || title.contains("fire") ||
                        title.contains("Amazon") || title.contains("Fire")) || title.contains("Kindle") ||
                        title.contains("kindle")) {
                    System.out.println("\t Title: " + title);
                    String price = newDoc.select("span[id*=priceblock_ourprice]").text();
                    System.out.println("\t Price: " + price);
                    double newPRice = Double.parseDouble(price.substring(1));
                    System.out.println("New Price: " + newPRice);
                    String description = newDoc.select("div[class*=productDescriptionWrapper]").text();
                    System.out.println("\t Description: " + description);
                    System.out.println("Adding to database");
                    dbConnection.addProduct(title, description, newPRice);
                    System.out.println("Finished");
                    Thread.sleep(2100);
                }
            System.out.println("\033[2J]");
            } catch (org.jsoup.HttpStatusException e) {
                System.err.println("HTTP STATUS EXCEPTION: "+e.toString());
            } catch (IOException e) {
                System.err.println("IOEXCEPTION: " + e.toString());
            }
            catch (Exception e){
                System.err.println("EXCEPTION: " + e.toString());
            }
        }


    }
}
