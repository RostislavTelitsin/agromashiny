package ru.agromashiny.models;

import java.util.List;

public class NewsAndImg {
    private News news;
    private List<String> imgs;


    public NewsAndImg() { }

    public NewsAndImg(News news) {
        this.news = news;
    }

    public News getNews() { return news; }

    public void setNews(News news) {
        this.news = news;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
