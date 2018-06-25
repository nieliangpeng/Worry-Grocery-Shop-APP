package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.Mmsic;
import com.example.worrygroceryshop.bean.Video;

import java.util.List;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private List<Video> videoList;
    private int item_layout_id;
    private int[] ids;
    public VideoAdapter(Context context, List<Video> videoList, int item_layout_id, int[] ids) {
        this.context = context;
        this.videoList = videoList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder = null;
        if(null==convertView){
            viewHoder = new ViewHoder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
            viewHoder.webview = convertView.findViewById(ids[0]);
            viewHoder.videoname=convertView.findViewById(ids[1]);
            viewHoder.videoname1=convertView.findViewById(ids[2]);
            viewHoder.videotime = convertView.findViewById(ids[3]);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        final Video video = videoList.get(position);
        //加载视频
        WebSettings setting = viewHoder.webview.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setAllowFileAccess(true);
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        setting.setDatabaseEnabled(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setDefaultTextEncodingName("UTF-8");


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        viewHoder.webview.setWebChromeClient(new WebChromeClient());
        viewHoder.webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url) {
                view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                view.loadUrl(video.getVideo_resource());
                return true;
            }
        });

        viewHoder.webview.loadUrl(video.getVideo_resource());

        //来源
        String name=video.getVideo_fileName();
        String []nameList=name.split("-");
        viewHoder.videoname.setText(nameList[0]);
        viewHoder.videoname1.setText(nameList[1]);
        //时间
        viewHoder.videotime.setText(video.getVideo_time());
        return convertView;
    }
    public void refresh(List<Video> v){
        videoList=v;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        WebView webview;
        TextView videoname;
        TextView videoname1;
        TextView videotime;
    }

}
