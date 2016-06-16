package securedoc.eetrust.com.chenlong.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by android on 2016/6/16.
 */
public class HttpControler {
   private OkHttpClient client;
    private  HttpControler(){
      client =new OkHttpClient();
    }

      public  static class  HttpHolder{
          public static  HttpControler controler=new HttpControler();


      }
    public static HttpControler getInstance(){
        return  HttpHolder.controler;
    }

    public <T> Observable<T> getRequest(final String url, Class<T> c){

       return  Observable.create(new Observable.OnSubscribe<T>() {

           @Override
           public void call(Subscriber<? super T> subscriber) {
               Request request=new Request.Builder().url(url).build();
               try {
                  Response response= client.newCall(request).execute();
              ResponseBody body  = response.body();



               } catch (IOException e) {
                   //e.printStackTrace();
                   subscriber.onError(e);
               }

           }
       }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
