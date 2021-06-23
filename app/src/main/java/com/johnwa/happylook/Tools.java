package com.johnwa.happylook;

import android.os.Message;
import android.util.Log;

import com.johnwa.happylook.bean.Pictures;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.johnwa.happylook.MainActivity.TAG;

/**
 * Created by qzh.
 * Date: 2021/4/17
 */
public class Tools {

    public static String webUrl = "https://699pic.com";  //摄图网首页链接
    public static String photoUrl = webUrl + "/photo/";  //摄图网照片链接
    private static List<Pictures> list = new ArrayList<>();
    private static List<String> mList = new ArrayList<>();


    /**
     * EventBus发送消息
     * @param what 事件标签
     */
    public static void sendMessage(int what){
        Message message = new Message();
        message.what = what;
        EventBus.getDefault().post(message);
    }


    /**
     * EventBus发送消息
     * @param what 事件标签
     * @param obj 数据对象
     */
    public static void sendMessage(int what , Object obj){
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        EventBus.getDefault().post(message);
    }


    /**
     * 初始化网页数据
     * @param mUrl 解析链接
     * @param type 解析类型：0是首页，1是图片详情页
     */
    public static void initData( String mUrl , int type){
        new Thread(() -> {
            try {
                Document document = Jsoup.connect(mUrl).timeout(5000).get();
                if (0 == type){
                    if (list != null){
                        list.clear();
                    }
                    getMainData(document);
                }else if (1 == type){
                    if (mList != null){
                        mList.clear();
                    }
                    getPicData(document);
                }
            }catch (Exception e){
                e.printStackTrace();
                sendMessage(1008613);
            }
        }).start();
    }


    /**
     * 获取该类图片的分页链接
     * @param document Jsoup文档类解析网页body
     */
    public static List<String> getPageLink(Document document){
        List<String> pageList = new ArrayList<>();
        Elements elements = document.select("div.pager-linkPage").select("a");
        for (Element element : elements){
            String link = element.select("a").attr("href");
            pageList.add(webUrl+link);
            Log.d(TAG, "getPageLink: "+link);
        }
        Log.d(TAG, "剩余页数 == "+pageList.size());
        return pageList;
    }




    /**
     * 获取所有照片数据
     * 若当前类图片存在翻页，则循环获取其他页数照片数据
     * @param document Jsoup文档类解析网页body
     */
    private static void getPicData(Document document) {
        mList = getPicSrc(document);
        List<String> linkList = getPageLink(document);
        if (linkList.size() != 0 ){
            for (int i = 0 ; i < linkList.size() ; i++){
                try {
                    Document doc = Jsoup.connect(linkList.get(i)).timeout(5000).get();
                    mList.addAll(getPicSrc(doc));
                }catch (Exception e){
                    e.printStackTrace();
                    sendMessage(1008613);
                }
            }
        }
        Log.d(TAG, "照片数量: "+ mList.size());
        sendMessage(1008611 , mList);
    }


    /**
     * 定位网页标签，获取照片链接
     * @param document Jsoup文档类解析网页body
     */
    private static List<String> getPicSrc(Document document){
        List<String> mmList = new ArrayList<>();
        Elements elements = document.select("li.list");
        for (Element element : elements){
            String picUrl = element.select("img").attr("data-original");
            mmList.add(picUrl);
        }
        return mmList;
    }


    /**
     * 定位首页元素，获取分类预览封面、标题、链接
     * 生成 Pictures 实例
     * @param document Jsoup文档类解析网页body
     */
    private static void getMainData(Document document ){
        Elements elements = document.select("div.pl-list");
            for (Element element : elements){
                String preImg = element.select("img").attr("data-original");
                String name = element.select("img").attr("alt");
                String url = element.select("a").attr("href");
                Pictures pictures = new Pictures();
                pictures.setPreViewImg(preImg);
                pictures.setPicName(name);
                pictures.setPicUrl(url);
                list.add(pictures);
            }
        sendMessage(10086, list);
    }

}
