package com.example.a09_3okhtt_xml;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int GET_XML_DATA_SUCCESS = 1;//联网请求xml数据成功


    private List<AppBean> apps;

    private String url = "http://192.168.1.108:8080/Demo/data.xml";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_XML_DATA_SUCCESS:
                    String result = (String) msg.obj;
                    tvOrgData.setText(result);
                    //parseXmlDataByPull(result);

                    //2. 使用sax解析
                    parseXmlDataBySax(result);


                    break;
            }

        }
    };

    /**
     * 2. 使用SAX解析xml 数据
     */
    private void parseXmlDataBySax(String xmlStr) {
        tvParseData.setText("");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        com.example.a09_3okhtt_xml.ContentHandler contentHandler = new ContentHandler();
        XMLReader reader = null;

        try {
            reader = factory.newSAXParser().getXMLReader();


            //为reader 设置contenthandler
            reader.setContentHandler(contentHandler);

            reader.parse(new InputSource(new StringReader(xmlStr)));



        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        //展示数据
        if (contentHandler.beans.size() > 0) {
            for (AppBean bean : contentHandler.beans) {
                tvParseData.append(bean.toString());
            }


        }



    }


    /**
     * 解析xml数据
     *
     * @param result
     */
    private void parseXmlDataByPull(String result) {
        //1. 使用pull 解析

        try {
            withPull(result);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //2. 显示数据
        tvParseData.setText("");

        for (AppBean app : apps) {
            tvParseData.append(app.toString());
        }


    }


    /**
     * 1. 使用pull 方式解析xml
     *
     * @param result
     */
    private void withPull(String result) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        XmlPullParser parser = factory.newPullParser();

        parser.setInput(new StringReader(result));

        int eventType = parser.getEventType();

        String id = "";
        String name = "";
        String version = "";


        apps = new ArrayList<>();


        while (eventType != XmlPullParser.END_DOCUMENT) {

            String nodeName = parser.getName();
            switch (eventType) {

                case XmlPullParser.START_TAG: {
                    if ("id".equals(nodeName)) {
                        id = parser.nextText();
                    } else if ("name".equals(nodeName)) {
                        name = parser.nextText();
                    } else if ("version".equals(nodeName)) {
                        version = parser.nextText();
                    }
                }
                break;


                case XmlPullParser.END_TAG://完成某个节点的解析
                    if ("app".equals(nodeName)) {
                        apps.add(new AppBean(id, name, version));
                    }
                    break;

            }
            eventType = parser.next();


        }


    }

    @butterknife.BindView(R.id.bt_reqeust)
    Button btReqeust;
    @butterknife.BindView(R.id.tv_org_data)
    TextView tvOrgData;
    @butterknife.BindView(R.id.tv_parse_data)
    TextView tvParseData;
    @butterknife.BindView(R.id.tv_reqeust_url)
    TextView tvReqeustUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_reqeust)
    void buttonOnclick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();

                    String result = response.body().string();
                    if (!TextUtils.isEmpty(result)) {

                        Message msg = Message.obtain();
                        msg.what = GET_XML_DATA_SUCCESS;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }


                } catch (IOException e) {
                    e.printStackTrace();

                }


            }
        }).start();

    }


}
