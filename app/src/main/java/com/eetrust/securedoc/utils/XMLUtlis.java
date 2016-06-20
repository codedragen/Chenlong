package com.eetrust.securedoc.utils;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android on 2016/6/17.
 */
public class XMLUtlis {

    public static final int SHARE = 1;
    private XmlPullParser parser;
    public static final int LOGIN = 0;

    public static class XMLUtilsHolder {

        public static XMLUtlis instance = new XMLUtlis();
    }

    private XMLUtlis() {
        parser = Xml.newPullParser();
    }

    public static XMLUtlis getInstance() {
        return XMLUtilsHolder.instance;
    }


    public Map<String, Object> xml2Obj(String string, int flag) throws IllegalAccessException, InstantiationException, XmlPullParserException, NoSuchFieldException, IOException {
        Map<String, Object> map = null;
        switch (flag) {
            case LOGIN:
                map = norMalResult(string);
                break;
            case SHARE:
                map = shareResult(string);
                break;
        }
        return map;
    }


    private Map<String, Object> norMalResult(String result) throws XmlPullParserException, IOException {
        Map map = new HashMap<String, Object>();
        parser.setInput(new StringReader(result));
        String key = "";
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_TAG:
                    key = parser.getName();
                    break;
                case XmlPullParser.TEXT:
                    if (parser.getText() != null && !"".equals(parser.getText()))
                        map.put(key, parser.getText());
                    break;
            }
            event = parser.next();
        }
        return map;

    }


    private Map<String, Object> shareResult(String result) throws XmlPullParserException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> list = null;
        String key = "";
        Map listmap = null;
        parser.setInput(new StringReader(result));
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT)
            switch (event) {
            case XmlPullParser.START_TAG:
                key = parser.getName();
                break;
            case XmlPullParser.TEXT:
                if ("docs".equals(key)) {
                    list = new ArrayList<Map<String, String>>();
                } else if ("doc".equals(key)) {
                    listmap = new HashMap();
                } else if ("result".equals(key)) {
                    map.put(key, parser.getText());
                } else if ("error".equals(key)) {
                    map.put(key, parser.getText());
                } else if ("storeName".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("fileName".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("confidential".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("sendUserName".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("sendUserLoginName".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("sendTime".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("versionID".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("docID".equals(key)) {
                    listmap.put(key, parser.getText());
                } else if ("archivesID".equals(key)) {
                    listmap.put(key, parser.getText());
                }
                break;
            case XmlPullParser.END_TAG:
                if ("doc".equals(parser.getName())) {
                    list.add(listmap);
                }
                if ("docs".equals(parser.getName())) {
                    map.put("docs", list);
                }
                break;

        }
        return map;
    }


}
