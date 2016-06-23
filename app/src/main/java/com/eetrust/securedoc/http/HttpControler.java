package com.eetrust.securedoc.http;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;

import com.eetrust.securedoc.cookie.CookiesManager;
import com.eetrust.securedoc.utils.XMLUtlis;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by android on 2016/6/16.
 */
public class HttpControler {

    private OkHttpClient client;
    private XMLUtlis xmlparseutlis;
    private Request.Builder requestbuild;
    public static volatile HttpControler controler;

    private HttpControler(Context context) {
        client = new OkHttpClient.Builder().cookieJar(new CookiesManager(context)).build();
        xmlparseutlis = XMLUtlis.getInstance();
        requestbuild=new Request.Builder();

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
                Request request =requestbuild.url(url).build();

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
                    request =requestbuild.url(url).post(body).build();
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
                    ResponseBody responsebody = response.body();
                    String result = responsebody.string();
                    Log.i("test",response.headers().toString());
                    Log.e("RESULT", responsebody==null?"true":"false");
                    Log.e("RESULT", "dfsdfdsfdsf"+result);
                } catch (IOException e) {
                  subscriber.onError(e);
                }


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 下载方法
     * @param url 下载地址
     * @param path 保存路径
     * @param name 保存名字
     */
    public void download(final String url, final String path, final String name){
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                InputStream is;
                FileOutputStream fos;
                File file;
                RequestBody body=new FormBody.Builder().add("storeName",name).build();
                   Request requset=requestbuild.url(url).post(body).build();
                try {
                   ResponseBody response= client.newCall(requset).execute().body();
                   long length= response.contentLength();
                    is=response.byteStream();
                    file=new File(path,name);
                  fos=new FileOutputStream(file);
                    byte b[]=new byte[1024];
                    int read;
                    int currentbyte=0;
                    long cureentTiem=System.currentTimeMillis();
                    while (-1!=(read=is.read(b,0,b.length))){
                        fos.write(b,0,read);
                        currentbyte+=read;
                        if (System.currentTimeMillis()-cureentTiem>50){
                            int progress= (int) (currentbyte*1.0f/length*100);
                             subscriber.onNext(progress);
                        }
                    }
                } catch (IOException e) {
                   subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    /**
     *
     * @param url 上传文件地址
     * @param body 请求体
     * @param call  进度回掉接口
     * @return
     */
    public Observable< Map<String,Object>> updateFile(final String url, final RequestBody body, final updateFileCallBack call){
        return Observable.create(new Observable.OnSubscribe< Map<String,Object>>() {
            @Override
            public void call(final Subscriber<? super  Map<String,Object>> subscriber) {
                CountingRequestBody wrapbody=new CountingRequestBody(body, new CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long bytesWritten, long contentLength) {
                         int progress= (int) (bytesWritten*1.0f/contentLength);
                         call.progress(progress);
                    }
                });
              Request request=  requestbuild.post(wrapbody).url(url).build();
                try {
                    Response response=client.newCall(request).execute();
                    String result=response.body().string();
                  Map<String,Object> map=  xmlparseutlis.xml2Obj(result,XMLUtlis.LOGIN);
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
     * 文件上传回调接口
     */
    interface updateFileCallBack{
        void progress(int progress);
    }

}
