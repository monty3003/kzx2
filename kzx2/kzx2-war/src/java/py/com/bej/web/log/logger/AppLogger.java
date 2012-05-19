package py.com.bej.web.log.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import py.com.bej.web.log.formatter.MyHtmlFormatter;

public class AppLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {
        // Create Logger
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.WARNING);
        fileTxt = new FileHandler("C:\\kzx2\\docs\\logs\\Logging.txt");
        fileHTML = new FileHandler("C:\\kzx2\\docs\\logs\\Logging.html");
//        fileTxt = new FileHandler("/var/kzx2/Logging.txt");
//        fileHTML = new FileHandler("/var/www/html/20120516/bej-motos-web-logging.html");

        // Create txt Formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // Create HTML Formatter
        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }
}
