module currency.rates {
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires jakarta.xml.bind;
    opens org.example.Currencies to jakarta.xml.bind;
}