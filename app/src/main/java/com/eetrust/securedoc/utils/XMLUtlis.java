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
    public static final int  QUERYFILEFOLDER=2;
    public static final int QUERYDEPTTREE=3;

    public static class XMLUtilsHolder {

        public static XMLUtlis instance = new XMLUtlis();
    }

    private XMLUtlis() {
        parser = Xml.newPullParser();
    }

    public static XMLUtlis getInstance() {
        return XMLUtilsHolder.instance;
    }

    /**
     * @param string 要解析的xml字符串
     * @param flag   标识符，根据不同的标识符调用不同的解析方法
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws XmlPullParserException
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public Map<String, Object> xml2Obj(String string, int flag) throws IllegalAccessException, InstantiationException, XmlPullParserException, NoSuchFieldException, IOException {
        Map<String, Object> map = null;
        switch (flag) {
            case LOGIN:
                map = norMalResult(string);
                break;
            case SHARE:
                map = shareResult(string);
                break;
            case QUERYFILEFOLDER:
                map = queryFileFolder(string);
                break;
            case QUERYDEPTTREE:
                map = queryDeptTree(string);
                break;
        }
        return map;
    }

    //普通的数据解析
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

    //分享接口的数据解析
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


    private Map<String, Object> queryFileFolder(String result) throws XmlPullParserException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String> itemMap = null;
        List<Map<String, String>> list = null;
        String key = "";
        parser.setInput(new StringReader(result));
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_TAG:
                    key = parser.getName();
                    if ("items".equals(key)) {
                        list = new ArrayList<Map<String, String>>();
                    }
                    if ("item".equals(key)) {
                        itemMap = new HashMap<String, String>();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            itemMap.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                        }
                    }
                    break;
                case XmlPullParser.TEXT:
                    if ("result".equals(key)) {
                        map.put(key, parser.getText());
                    } else if ("error".equals(key)) {
                        map.put(key, parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())) {
                        list.add(itemMap);
                    }
                    if ("items".equals(parser.getName())) {
                        map.put("items", list);
                    }
                    break;
            }
            event = parser.next();
        }
        return map;
    }

    private Map<String,Object> queryDeptTree(String result) throws XmlPullParserException {
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String, String> itemMap = null;
        List<Map<String, String>> userList = null;
        List<Map<String, String>> deptList = null;
        String key = "";
        parser.setInput(new StringReader(result));
        int event=parser.getEventType();
        while (event!=XmlPullParser.END_DOCUMENT){
            switch (event){
                case XmlPullParser.START_TAG:
                    key=parser.getName();
                     if ("users".equals(key)){
                         userList=new ArrayList<Map<String, String>>();
                     }else if ("user".equals(key)){
                         itemMap = new HashMap<String, String>();
                         for (int i = 0; i < parser.getAttributeCount(); i++) {
                             itemMap.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                         }
                     }else if ("depts".equals(key)){
                         deptList=new ArrayList<Map<String, String>>();
                     }else if ("dept".equals(key)){
                         itemMap = new HashMap<String, String>();
                         for (int i = 0; i < parser.getAttributeCount(); i++) {
                             itemMap.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                         }
                     }
                    break;
                case XmlPullParser.TEXT:
                    if ("result".equals(key)){
                        map.put(key,parser.getText());
                    }
                    if ("error".equals(key)){
                        map.put(key,parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("users".equals(parser.getName())){
                        map.put("users",userList);
                    }else if ("user".equals(parser.getName())){
                       userList.add(itemMap);

                    }else if ("depts".equals(key)){
                       map.put("depts",deptList);
                    }else if ("dept".equals(key)){
                        deptList.add(itemMap);
                    }
                    break;
            }
        }
        return map;
    }

}
