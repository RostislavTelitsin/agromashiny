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
import ru.agromashiny.service.StorageServ;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private StorageServ storageServ;
    private Object Gson;


    @GetMapping("/")
    public String index(Model model) {

        //model.addAttribute("newsAndImgs", storageServ.getLastThreeNewsAndImgs());

        return "index";
    }



    @GetMapping("/news")
    public String bolt(Model model) {
        return "news";
    }

    @GetMapping("/news_singlePage")
    public String news_singlePage(Model model) {
        return "news_singlePage";
    }

    @GetMapping("/service")
    public String service(Model model) {
        return "service";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        return "contacts";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("/login");

        return "login";
    }

    @GetMapping("/newsCheck")
    public @ResponseBody Iterable<News> getAllNews() {
        return newsRepository.findAll();

    }



    @GetMapping("/news/{idNews}")
    public String news(@PathVariable(value = "idNews") int id, Model model) {
        NewsAndImg nn = storageServ.getNewsAndImg(id);
        model.addAttribute("newsAndImg", nn);

        return "news";

    }

    @GetMapping("/newsadding")
    public String newsadding(Model model) {
        return "newsadding";
    }







}