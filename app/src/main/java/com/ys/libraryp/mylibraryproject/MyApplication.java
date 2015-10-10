package com.ys.libraryp.mylibraryproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**   全局上下文
 * Created by ys on 2015/10/8 0008.
 */
public class MyApplication extends Application{

    private static MyApplication instance;
    public RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化请求队列
        requestQueue = Volley.newRequestQueue(this);
        //初始化图片加载工具
        initImageLoader(this);
    }

    public static MyApplication getInstance(){
        return instance;
    }

    /**
     *   向请求队列添加请求
     * @param request
     * @param tag
     */
    public void addRequest(Request request,String tag){
        request.setTag(tag);
        requestQueue.add(request);
    }

    /**
     *  取消此标签的请求
     * @param tag
     */
    public void cancleRequest(String tag){
        requestQueue.cancelAll(tag);
    }

    /**
     *  初始化 UIL 图片加载
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
       File cacheDir = StorageUtils.getOwnCacheDirectory(instance, "MoreView/imageloader/Cache");

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.memoryCacheExtraOptions(480, 800);// max width, max height，即保存的每个缓存文件的最大长宽
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCache(new UnlimitedDiskCache(cacheDir));
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.memoryCacheSize(2 * 1024 * 1024);
//        config.memoryCacheSizePercentage(90);
        config.diskCacheSize(10 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}

