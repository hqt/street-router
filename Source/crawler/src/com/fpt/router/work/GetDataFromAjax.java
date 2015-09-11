package com.fpt.router.work;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by datnt on 9/11/2015.
 */
public class GetDataFromAjax {

    public static final String url = "http://www.buyttphcm.com.vn/TTLT.aspx";

    public ArrayList<String> crawAjaxData(){

        WebClient webClient = new WebClient(); // Initialize webclient to get webpage
        ArrayList<String> tableDatas = new ArrayList<>(); // Initialize list table data to hold data from table

        try {
            HtmlPage htmlPage = webClient.getPage(url);

            while(true) {
                // get Form from page
                HtmlForm form = htmlPage.getForms().get(0);
                // get table from Form bu specific id table
                String tableData = form.getElementsByAttribute("table", "id", "ctl00_ContentPlaceHolder1_GridView1").get(0).asXml();
                // Add element to Array
                tableDatas.add(tableData);

                /*
                    Navigate page by using HtmlAnchor to handle click event
                    if cannot find click event, will break loop
                */
                HtmlAnchor anchor;
                try {
                    anchor =  htmlPage.getAnchorByHref("javascript:__doPostBack('ctl00$ContentPlaceHolder1$GridView1','Page$Next')");
                } catch (Exception ex) {
                    break;
                }
                anchor.click();

                // waiting for javascript load
                webClient.waitForBackgroundJavaScript(10000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return tableDatas;
    }

}
