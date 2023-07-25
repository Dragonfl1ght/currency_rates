package org.example;

import org.example.Currencies.Currency;
import org.example.Requests.XmlRequest;

import jakarta.xml.bind.JAXBException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException, ParserConfigurationException, jakarta.xml.bind.JAXBException {
        String url = "http://www.cbr.ru/scripts/XML_daily.asp";
        if (args.length > 0) {
            String code = null;
            String date = null;

            // Parse the command-line arguments
            for (String arg : args) {
                if (arg.startsWith("--code=")) {
                    code = arg.substring(7);
                } else if (arg.startsWith("--date=")) {
                    date = arg.substring(7);
                }
            }
            assert code != null;
            if (date != null)
                date = convertDateFormat(date, "yyyy-MM-dd", "dd-MM-yyyy");
            XmlRequest request = new XmlRequest(url, code, date);
            Currency cur = request.getCurrencies();
            if (cur != null) {
                System.out.println(cur);
            } else {
                System.out.println("Валюта не найдена");
            }
        }
        else {
            System.out.println("Аргументы не заданы");
        }
    }
    private static String convertDateFormat(String dateString, String originalFormat, String targetFormat) {
        SimpleDateFormat originalDateFormat = new SimpleDateFormat(originalFormat);
        SimpleDateFormat targetDateFormat = new SimpleDateFormat(targetFormat);

        try {
            Date date = originalDateFormat.parse(dateString);
            return targetDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}