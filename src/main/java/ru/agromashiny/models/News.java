package ru.agromashiny.models;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.agromashiny.service.ImgStorageServ;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
public class News {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private  String titleNews;
    private  String announcementNews;

    @Column(length = 2028)
    private  String contentNews;
    public   String imgLibJson;




    public News() {}

    public News(String titleNews, String announcementNews, String contentNews) {
        this.titleNews = titleNews;
        this.announcementNews = announcementNews;
        this.contentNews = contentNews;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleNews() {
        return titleNews;
    }

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    public String getAnnouncementNews() {
        return announcementNews;
    }

    public void setAnnouncementNews(String announcementNews) {
        this.announcementNews = announcementNews;
    }

    public String getContentNews() {
        return contentNews;
    }

    public void setContentNews(String contentNews) {
        this.contentNews = contentNews;
    }

    public String getImgLibJson() { return imgLibJson; }

    public void setImgLibJson(String imgLibJson) { this.imgLibJson = imgLibJson; }

//    public List<String> getNewsImages() {
//
//        List<String> newsImages = new ArrayList<String>();
//        Gson gson = new Gson();
//        List<Integer> lib = gson.fromJson(this.imgLibJson, List.class);
//        lib.forEach(
//                i -> {
//                    Optional<ImgFile> file = Optional.of(new ImgFile());
//                    file = Optional.of(imgStorageServ.getFile(i).get());
//                    newsImages.add(file.get().getImgData());
//                }
//        );
//        return newsImages;
//    }
}
