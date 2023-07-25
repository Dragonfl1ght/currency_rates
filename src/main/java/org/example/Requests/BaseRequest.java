package org.example.Requests;

import org.example.Currencies.Currency;

import java.util.ArrayList;

public abstract class BaseRequest {
    protected String currency;
    protected String date;

    protected String url;

    BaseRequest(String url, String currency, String date){
        this.url = url;
        this.currency = currency;
        this.date = date.replace("-", "/");
    }

    BaseRequest(String url, String currency){
        this.url = url;
        this.currency = currency;
        this.date = null;
    }

    protected void addParams(){
        if (this.date != null)
            this.url = this.url + "?date_req=" + this.date;
    }

    abstract protected Currency findCurr(ArrayList<Currency> curs);
}
