package securedoc.eetrust.com.chenlong.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eetrust on 16/6/14.
 */
public class DownloadUtils {

    private DownLoadCall call;

    public void setListener(DownLoadCall call){
        this.call=call;
    }
    public void download(String url) throws IOException {
        URL httpurl=new URL(url);
       HttpURLConnection conn;
        InputStream is=null;
        conn = (HttpURLConnection) httpurl.openConnection();
        if(conn.getResponseCode()==200) {
            long contentLength=conn.getContentLength();
            is=conn.getInputStream();
            byte[] data=new byte[1024];
            int i=0;
            int currentbyte=0;
            long currenttime=System.currentTimeMillis();
            while ((i=is.read(data,0,data.length))!=-1){
                currentbyte+=i;
                if (System.currentTimeMillis()-currenttime>50){
                call.update(currentbyte,contentLength);
                currenttime=System.currentTimeMillis();
                }
            }

        }
    }


    public interface  DownLoadCall{
        public void update(long currentbyte,long totalbyte);
    }
}
