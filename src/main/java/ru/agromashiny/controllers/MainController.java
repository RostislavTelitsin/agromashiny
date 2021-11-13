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

        model.addAttribute("newsAndImgs", storageServ.getLastThreeNewsAndImgs());

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
            imgIdList.add(storageServ.saveImgFile(file));
        }
        Gson gson = new Gson();
        String libId = gson.toJson(imgIdList);
        n.setImgLibJson(libId);
        newsRepository.save(n);

        return "redirect:/";
    }

    @GetMapping("/newsedit")
    public String newsedit(Model model) {

        model.addAttribute("newsAndImgs", storageServ.getAllNewsAndImgs());
        return "newsedit";

    }


    @GetMapping("/test")
    public String get(Model model) throws UnsupportedEncodingException {
        List<ImgFile> imgs = storageServ.getFiles();

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
        List<ImgFile> imgs = storageServ.getFiles();
        model.addAttribute("imgs", imgs);
        return "imgadd";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile (@PathVariable Integer fileId) {
        ImgFile img = storageServ.getFile(fileId).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + img.getImgName() + "\"")
                .body(new ByteArrayResource(img.getData()));
    }

    @PostMapping("/deletenews/{idNews}")
    public String deletenews(@PathVariable(value = "idNews") int id) {
        storageServ.deleteNews(id);
        return "redirect:/newsedit";
    }



    @PostMapping("/imgadd")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file: files) {
            storageServ.saveImgFile(file);
        }
        return "imgadd";
    }


}