package com.eetrust.securedoc.http;

import android.content.Context;
import android.util.Log;

import com.eetrust.securedoc.cookie.CookiesManager;
import com.eetrust.securedoc.utils.XMLUtlis;

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
    private XMLUtlis xmlparseutlis;
    public static volatile HttpControler controler;

    private HttpControler(Context context) {
        client = new OkHttpClient.Builder().cookieJar(new CookiesManager(context)).build();
        xmlparseutlis = XMLUtlis.getInstance();


    }

    //单例模式
    public static HttpControler getInstance(Context context) {
        if (controler == null) {
            synchronized (HttpControler.class) {
                if (controler == null) {
                    controler = new HttpControler(context);
                }
            }
        }

        return controler;
    }

    /**
     * 发送POST请求，
     *
     * @param flag xml解析的标识符
     * @param url  请求地址
     */
    public Observable<Map<String, Object>> sendGetRequest(final String url, final int flag) {
        return Observable.create(new Observable.OnSubscribe<Map<String, Object>>() {

            @Override
            public void call(Subscriber<? super Map<String, Object>> subscriber) {
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    String result = body.string();
                    Map<String, Object> map = xmlparseutlis.xml2Obj(result, flag);
                    subscriber.onNext(map);
                } catch (IOException e) {
                    subscriber.onError(e);
                } catch (IllegalAccessException e) {
                    subscriber.onError(e);
                } catch (XmlPullParserException e) {
                    subscriber.onError(e);
                } catch (InstantiationException e) {
                    subscriber.onError(e);
                } catch (NoSuchFieldException e) {
                    subscriber.onError(e);
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送POST请求，
     *
     * @param flag xml解析的标识符
     * @param body 请求体
     * @param url  请求地址
     */
    public Observable sendPostRequest(final String url, final RequestBody body, final int flag) {
        return Observable.create(new Observable.OnSubscribe<Map<String, Object>>() {
            @Override
            public void call(Subscriber<? super Map<String, Object>> subscriber) {
                Request request = null;
                try {
                    request = new Request.Builder().url(url).post(body).build();
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    String result = body.string();
                    Map<String, Object> o = xmlparseutlis.xml2Obj(result, flag);
                    subscriber.onNext(o);
                } catch (IOException e) {
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

    //测试方法
    public Observable test(final String url, final RequestBody body) {
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
                    Map<String,Object>map=xmlparseutlis.xml2Obj(result,XMLUtlis.LOGIN);
                    Log.i("RESULT", result);
                    subscriber.onNext(map);
                } catch (IOException e) {
                  subscriber.onError(e);
                } catch (IllegalAccessException e) {
                    subscriber.onError(e);
                } catch (XmlPullParserException e) {
                    subscriber.onError(e);
                } catch (InstantiationException e) {
                    subscriber.onError(e);
                } catch (NoSuchFieldException e) {
                    subscriber.onError(e);
                }


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
