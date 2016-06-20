package com.eetrust.securedoc.utils;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by android on 2016/6/16.
 */
public class HttpControler {
   private OkHttpClient client;
    private XMLUtlis utlis;
    private  HttpControler(){
      client =new OkHttpClient();
        utlis=XMLUtlis.getInstance();

    }

      public  static class  HttpHolder{
          public static  HttpControler controler=new HttpControler();


      }
    public static HttpControler getInstance(){
        return  HttpHolder.controler;
    }

    public <T> Observable<T> sendGetRequest(final String url, Class<T> c){
       return  Observable.create(new Observable.OnSubscribe<T>() {

           @Override
           public void call(Subscriber<? super T> subscriber) {
               Request request=new Request.Builder().url(url).build();
               try {
                  Response response= client.newCall(request).execute();
                 ResponseBody body  = response.body();
                String result= body.string();
               } catch (IOException e){
                   subscriber.onError(e);
               }

           }
       }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public  Observable sendPostRequest(final String url, final RequestBody body, final int flag){
        return  Observable.create(new Observable.OnSubscribe<Map<String,Object>>() {
            @Override
            public void call(Subscriber<? super Map<String,Object>> subscriber) {
                Request request=null;
                try {
                    request=new Request.Builder().url(url).post(body).build();
                    Response response= client.newCall(request).execute();
                    ResponseBody body  = response.body();
                    String result= body.string();
                    Map<String,Object> o=utlis.xml2Obj(result,flag);
                     subscriber.onNext(o);
                } catch (IOException e){
                    subscriber.onError(e);
                } catch (XmlPullParserException e) {
                    subscriber.onError(e);
                } catch (IllegalAccessException e) {
                    subscriber.onError(e);
                } catch (InstantiationException e) {
                    subscriber.onError(e);
                } catch (NoSuchFieldException e) {
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public  Observable test(final String url, final RequestBody body) {
        return Observable.create(new Observable.OnSubscribe<Map<String, Object>>() {
            @Override
            public void call(Subscriber<? super Map<String, Object>> subscriber) {
                Request request = null;

                request = new Request.Builder().url(url).post(body).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    String result = body.string();
                    Log.i("RESULT",result);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
