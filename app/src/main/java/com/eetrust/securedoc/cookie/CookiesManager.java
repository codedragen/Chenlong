package com.eetrust.securedoc.cookie;

import android.content.Context;

import com.eetrust.securedoc.cookie.PersistentCookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public  class CookiesManager implements CookieJar {


    public CookiesManager(Context context){
        cookieStore = new PersistentCookieStore(context);

    }
        private  PersistentCookieStore  cookieStore ;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }