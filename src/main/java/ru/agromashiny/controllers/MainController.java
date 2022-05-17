package ru.agromashiny.controllers;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ru.agromashiny.models.*;
import ru.agromashiny.repo.NewsRepository;
import ru.agromashiny.service.AgroEmailService;
import ru.agromashiny.service.StorageServ;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {


    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private StorageServ storageServ;
    private Object Gson;



    @Autowired
    private AgroEmailService agroEmailService;


    @GetMapping("/")
    public String index(Model model) {
        Message message = new Message();
        model.addAttribute("message", message);
        model.addAttribute("counter", storageServ.incrementVisitCounter());
        model.addAttribute("newsAndImgs", storageServ.getLastSeveralNewsAndImgs(5));
        return "index";
    }



    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("newsAndImgs", storageServ.getLastSeveralNewsAndImgs(7));
        return "news";
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
    public String newsSingle(@PathVariable(value = "idNews") int id, Model model) {
        NewsAndImg nn = storageServ.getNewsAndImg(id);
        model.addAttribute("newsAndImg", nn);

        return "news_singlePage";
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
        com.google.gson.Gson gson = new Gson();
        String libId = gson.toJson(imgIdList);
        n.setImgLibJson(libId);
        newsRepository.save(n);

        return "redirect:/newsedit";
    }

    @GetMapping("/newsedit")
    public String newsedit(Model model) {

        model.addAttribute("newsAndImgs", storageServ.getAllNewsAndImgs());
        return "newsedit";

    }

    @PostMapping("/deletenews/{idNews}")
    public String deletenews(@PathVariable(value = "idNews") int id) {
        storageServ.deleteNews(id);
        return "redirect:/newsedit";
    }



    @PostMapping("/sendmessage")
    public @ResponseBody HttpStatus sending(@RequestBody Message message) throws MessagingException, IOException, GeneralSecurityException {
        if (agroEmailService.send(message)) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }




}