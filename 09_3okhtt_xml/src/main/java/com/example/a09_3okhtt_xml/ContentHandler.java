package com.example.a09_3okhtt_xml;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/8/20.
 */

public class ContentHandler extends DefaultHandler {
    private  String nodeName;
    private  StringBuilder id;
    private  StringBuilder name;
    private  StringBuilder version;

    public List<AppBean> beans;


    @Override
    public void startDocument() throws SAXException {

        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
        beans = new ArrayList<>();


    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.nodeName = localName;

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if(nodeName.equals("id")) {
            id.append(ch,start,length);
        }else if(nodeName.equals("name")) {
         
            name.append(ch,start,length);
        }else if(nodeName.equals("version")) {
            version.append(ch,start,length);
        }
                
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
        Log.d("ContentHandler", nodeName);
        if(localName.equals("app")) {
            String id = this.id.toString().trim();
            String name = this.name.toString().trim();
            String versioin = this.version.toString().trim();

            Log.d("ContentHandler", id);
            Log.d("ContentHandler", name);
            Log.d("ContentHandler", versioin);
            this.beans.add(new AppBean(id,name,versioin));

            this.id.setLength(0);
            this.name.setLength(0);
            this.version.setLength(0);


        }

    }
}
