package com.eetrust.securedoc.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by eetrust on 16/6/14.
 */
public class DownloadUtils {

    private  Context context;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void addTask(MyTask task) {
        executorService.submit(task);


    }

    public DownloadUtils(Context context){
        this.context=context;
    }

    public interface DownLoadCall {
        public void update(long currentbyte, long totalbyte);
        public void error(Exception e);
    }

    private class MyTask implements Runnable {

        private static final int ERROR = 0;
        private final String url;
        private final DownLoadCall call;
        private final String path;
        private final String name;
        public static final int UPDATE=1;
        private Handler handler=new Handler(context.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case UPDATE:
                        if (call!=null)
                        call.update(msg.arg1,msg.arg2);
                        break;
                    case ERROR:
                        if (call!=null)
                            call.error((Exception) msg.obj);
                        break;
                }
            }
        };

        public void setCancel(boolean cancel) {
            isCancel = cancel;
            handler.removeCallbacksAndMessages(null);
        }

        public boolean isCancel() {
            return isCancel;
        }

        private boolean isCancel;

        public MyTask(String url, String path, String name, DownLoadCall call) {
            this.url = url;
            this.call = call;
            this.path = path;
            this.name = name;
        }



        @Override
        public void run() {

            URL httpurl = null;
            HttpURLConnection conn;
            InputStream is = null;
            FileOutputStream fos = null;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, name);


            try {
                httpurl = new URL(url);
                conn = (HttpURLConnection) httpurl.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK&&conn.getContentLength()!=-1) {
                    int totalbytes = conn.getContentLength();
                    is = conn.getInputStream();
                    fos = new FileOutputStream(file);
                    byte b[]=new byte[1024];
                    int read;
                    int currentbyte=0;
                    long cureentTiem=System.currentTimeMillis();
                    while (-1!=(read=is.read(b,0,b.length))&&!isCancel){
                        fos.write(b,0,read);
                        currentbyte+=read;
                        if (System.currentTimeMillis()-cureentTiem>200){
                            Message msg=Message.obtain();
                            msg.what=UPDATE;
                            msg.arg1=currentbyte;
                            msg.arg2=totalbytes;
                            handler.sendMessage(msg);
                            cureentTiem=System.currentTimeMillis();
                        }
                    }
                }
            } catch (MalformedURLException e) {
                Message msg=Message.obtain();
                msg.obj=e;
                msg.what=ERROR;
              handler.sendMessage(msg);
            } catch (IOException e) {
                Message msg=Message.obtain();
                msg.obj=e;
                msg.what=ERROR;
                handler.sendMessage(msg);
            }


        }
    }
}
