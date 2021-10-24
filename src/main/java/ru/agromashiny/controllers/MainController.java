package ru.agromashiny.controllers;

import com.google.gson.Gson;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ru.agromashiny.models.ImgFile;
import ru.agromashiny.models.News;
import ru.agromashiny.models.NewsAndImg;
import ru.agromashiny.models.NewsRepository;
import ru.agromashiny.service.ImgStorageServ;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ImgStorageServ imgStorageServ;
    private Object Gson;


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/bolt")
    public String bolt(Model model) {
        return "bolt";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/news")
    public @ResponseBody Iterable<News> getAllNews() {
        return newsRepository.findAll();
    }

    @GetMapping("/newsadding")
    public String newsadding(Model model) {
        return "newsadding";
    }


    @PostMapping("/newsadding")
    public String newsAdd(@RequestParam String title, @RequestParam String announcement, @RequestParam String content, @RequestParam("files") MultipartFile[] files, Model model) {
        News n = new News(title, announcement, content);
        List<Integer> imgIdList = new ArrayList<Integer>();
        for (MultipartFile file: files) {
            imgIdList.add(imgStorageServ.saveImgFile(file));
        }
        Gson gson = new Gson();
        String libId = gson.toJson(imgIdList);
        n.setImgLibJson(libId);
        newsRepository.save(n);
//        String jsonText = n.getImgLibJson();
//        List<Integer> xxx = gson.fromJson(jsonText, List.class);
        return "redirect:/";
    }

    @GetMapping("/newsedit")
    public String newsedit(Model model) {
        List<NewsAndImg> newsAndImgs = new ArrayList<NewsAndImg>();
        List<String> myTest = new ArrayList<String>();
        List<News> news = (List<News>) newsRepository.findAll();
        String chImg = new String();
        news.forEach(
                n -> {
                    NewsAndImg nai = new NewsAndImg(n);
                    List<String> listImg = new ArrayList<String>();
                    String json = n.imgLibJson;
                    Gson gson = new Gson();
                    List<Double> list = gson.fromJson(json, List.class);
                    list.forEach(
                            i -> {
                                int ii = i.intValue();
                                String myyTestString = imgStorageServ.getFile(ii).get().getImgData();
                                listImg.add(myyTestString);
                            }
                    );
                    nai.setImgs(listImg);
                    newsAndImgs.add(nai);


                }
        );

//Example:        String vvv = newsAndImgs.get(1).getNews().getTitleNews();
//        String iii = newsAndImgs.get(1).getImgs(1).getdata;

//to delete later
        myTest = newsAndImgs.get(1).getImgs();
        int x = 1;
        model.addAttribute("newsAndImgs", newsAndImgs);
        model.addAttribute("myTest", myTest);
        return "newsedit";
    }


    @GetMapping("/test")
    public String get(Model model) throws UnsupportedEncodingException {
        List<ImgFile> imgs = imgStorageServ.getFiles();

        byte[] xxx = imgs.get(4).data;

//        byte[] yyy = org.springframework.security.crypto.codec.Base64.encode(xxx);
        String enc = "data:image/jpeg;base64," + Base64.getUrlEncoder().encodeToString(xxx);


        if (enc == null)
            enc = "хуй";

//        String xxx = new String(enc, "UTF-8");
//        String xxx = new String(imgs.get(0).getId());
        model.addAttribute("imgs", imgs);
        model.addAttribute("enc", enc);

        return "test";}

    @GetMapping("/imgadd")
    public String imgadd(Model model) {
        List<ImgFile> imgs = imgStorageServ.getFiles();
        model.addAttribute("imgs", imgs);
        return "imgadd";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile (@PathVariable Integer fileId) {
        ImgFile img = imgStorageServ.getFile(fileId).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + img.getImgName() + "\"")
                .body(new ByteArrayResource(img.getData()));
    }


    @PostMapping("/imgadd")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            imgStorageServ.saveImgFile(file);
        }
        return "imgadd";
    }


}