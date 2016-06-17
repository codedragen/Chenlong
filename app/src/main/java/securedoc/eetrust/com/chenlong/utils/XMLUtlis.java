package securedoc.eetrust.com.chenlong.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import securedoc.eetrust.com.chenlong.bean.UserInfo;

/**
 * Created by android on 2016/6/17.
 */
public class XMLUtlis {

    XmlPullParser parser;
    public static final int LOGIN=0;
    public static class XMLUtilsHolder{

        public static XMLUtlis instance=new XMLUtlis();
    }

    private XMLUtlis(){
       parser= Xml.newPullParser();
    }

    public static XMLUtlis getInstance(){
        return XMLUtilsHolder.instance;
    }


    public Map<String,Object> xml2Obj(String string,int flag) throws IllegalAccessException, InstantiationException, XmlPullParserException, NoSuchFieldException, IOException {
        Map<String,Object> map = null;
         switch (flag){
             case LOGIN:
                map= str2UserInfo(string);
                 break;
         }

        return map;
    }

    public Map<String, Object>  str2UserInfo(String string) throws XmlPullParserException, IOException {

        UserInfo info=new UserInfo();
        Map<String,Object> map=new HashMap<String, Object>();
        parser.setInput(new StringReader(string));
      int event=  parser.getEventType();
         while (event!=XmlPullParser.END_DOCUMENT){
             switch (event){
                 case XmlPullParser.START_TAG:
                     map.put(parser.getName(),parser.nextText());
                     if (parser.getName().equals("result")){
                        event= parser.next();
                         map.put("result",parser.getText());
                     }else if (parser.getName().equals("loginName")){
                         event= parser.next();
                         map.put("loginName",parser.getText());
                     }else if (parser.getName().equals("userName")) {
                         event = parser.next();
                         map.put("userName",parser.getText());
                     }else if (parser.getName().equals("configdential")) {
                         event = parser.next();
                         map.put("configdential",parser.getText());
                     }else if (parser.getName().equals("email")) {
                         event = parser.next();
                         map.put("email",parser.getText());
                     }else if (parser.getName().equals("phone")) {
                         event = parser.next();
                         map.put("phone",parser.getText());
                     }else if (parser.getName().equals("error")) {
                         event = parser.next();
                         map.put("error",parser.getText());
                     }
                     break;
                 case XmlPullParser.TEXT:

                     break;
             }

            event= parser.next();
         }
        return  map;
    }




}
