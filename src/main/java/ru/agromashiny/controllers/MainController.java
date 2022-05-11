package ru.agromashiny.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.agromashiny.models.*;
import ru.agromashiny.service.AgroEmailService;
import ru.agromashiny.service.StorageServ;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class MainController {


    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private StorageServ storageServ;
    private Object Gson;

    @Autowired
    private AgroEmailService agroEmailService;

    @GetMapping("/indextest")
    public String index(Model model) {

        //model.addAttribute("newsAndImgs", storageServ.getLastThreeNewsAndImgs());

        return "index";
    }

    @GetMapping("/")
    public String indextest(Model model) {
        Message message = new Message();
        model.addAttribute("message", message);
        return "indextest";
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

    @GetMapping("/sendmessage")
    public String sendmessage(Model model) {
        return "sendmessage";
    }

/*    @PostMapping("/")
    public String sending(@RequestParam String name, @RequestParam String email, @RequestParam String content, Model model) {
        String n = name;
        String e = email;
        String c = content;

        return "redirect:/sendmessage";
    }*/

    @PostMapping("/")
    public String sending(@ModelAttribute Message message, Model model) throws MessagingException, IOException, GeneralSecurityException {

        if (agroEmailService.send(message)) {
            return "redirect:/sendmessage";
        }

        return "redirect:/";
    }






}