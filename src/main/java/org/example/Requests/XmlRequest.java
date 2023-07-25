package org.example.Requests;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.Currencies.Currency;
import org.example.Currencies.CurrencyList;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class XmlRequest extends BaseRequest {
    protected DocumentBuilder builder;
    public XmlRequest(String url, String currency, String date) throws ParserConfigurationException {
        super(url, currency, date);
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public XmlRequest(String url, String currency) throws ParserConfigurationException {
        super(url, currency);
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public Currency getCurrencies() throws IOException, JAXBException {// Замените на ваш URL API
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.addParams();
        HttpGet httpGet = new HttpGet(this.url);
        HttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "windows-1251"))) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyList.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    CurrencyList currencyList = (CurrencyList) jaxbUnmarshaller.unmarshal(reader);
                    return this.findCurr(currencyList.getCurrencies());
                }
            }
        }

        return null; // Вернуть пустой список, если запрос не удался или ответ не содержит валют
    }

    protected Currency findCurr(ArrayList<Currency> curs){
        for (Currency cur : curs){
            if (cur.getCharCode().equals(this.currency))
                return cur;
        }
        return null;
    }
}
