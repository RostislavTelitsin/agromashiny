package ru.agromashiny.service;

import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.agromashiny.models.*;
import ru.agromashiny.repo.CounterRepository;
import ru.agromashiny.repo.ImgRepository;
import ru.agromashiny.repo.NewsRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServ {
    @Autowired
    public ImgRepository imgRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CounterRepository counterRepository;

    public Integer saveImgFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            ImgFile imgFile = new ImgFile(filename, resize(file));
            imgRepository.save(imgFile);
            int iii = imgFile.getId();
            return iii;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NewsAndImg> getAllNewsAndImgs() {
        List<NewsAndImg> newsAndImgs = new ArrayList<NewsAndImg>();
        List<News> news = (List<News>) newsRepository.findAll();
        news.forEach(
                n -> {
                    NewsAndImg singleNews = new NewsAndImg(n);
                    List<String> listImg = new ArrayList<String>();
                    String json = n.imgLibJson;
                    Gson gson = new Gson();
                    List<Double> list = gson.fromJson(json, List.class);//it's double because gson returns double
                    list.forEach(
                            imIgd -> {
                                int ii = imIgd.intValue();//id should be integer
                                String imgDataString = getFile(ii).get().getImgData();
                                listImg.add(imgDataString);
                            }
                    );
                    singleNews.setImgs(listImg);
                    newsAndImgs.add(singleNews);

                }
        );
        return newsAndImgs;
    }

//This method returns last 3 news and images for them
    public List<NewsAndImg> getLastSeveralNewsAndImgs(Integer quantityOfNews) {
        List<NewsAndImg> newsAndImgs = new ArrayList<NewsAndImg>();
        List<News> news = (List<News>) newsRepository.findAll();
        int newsSize = news.size();
        for (int i = newsSize-1; (i>newsSize-6) & (i>-1); i--) {
            NewsAndImg singleNews = new NewsAndImg(news.get(i));
            List<String> listImg = new ArrayList<String>();
            String json = news.get(i).imgLibJson;
            Gson gson = new Gson();
            List<Double> list = gson.fromJson(json, List.class);//it's double because gson returns double

            list.forEach(
                    imIgd -> {
                        int ii = imIgd.intValue();//id should be integer
                        String imgDataString = getFile(ii).get().getImgData();
                        listImg.add(imgDataString);
                    }
            );
            singleNews.setImgs(listImg);
            newsAndImgs.add(singleNews);
        }
        return newsAndImgs;
    }

    public NewsAndImg getNewsAndImg(int id) {
        NewsAndImg newsAndImg = new NewsAndImg();
        Optional<News> news = newsRepository.findById(id);
        newsAndImg.setNews(news.get());
        List<String> listImg = new ArrayList<String>();
        String json = newsRepository.findById(id).get().imgLibJson;
        Gson gson = new Gson();
        List<Double> list = gson.fromJson(json, List.class);//it's double because gson returns double
        list.forEach(
                imIgd -> {
                    int ii = imIgd.intValue();//id should be integer
                    String imgDataString = getFile(ii).get().getImgData();
                    listImg.add(imgDataString);
                }
        );
        newsAndImg.setImgs(listImg);
        return newsAndImg;

    }

    public void deleteNews (int id) {
        Optional<News> news = newsRepository.findById(id);
        String json = news.get().imgLibJson;
        Gson gson = new Gson();
        List<Double> list = gson.fromJson(json, List.class);//it's double because gson returns double
        list.forEach(
                imIgd -> {
                    int ii = imIgd.intValue();//id should be integer
                    imgRepository.deleteById(ii);
                }
        );
        newsRepository.deleteById(id);
    }


    public Optional<ImgFile> getFile(Integer fileId) {
        return imgRepository.findById(fileId);
    }
//    public Optional<News> getNews(Integer id) {return newsRepository.findById(id);}


    public List<ImgFile> getFiles() {
        return imgRepository.findAll();
    }
//    public List<News> getNews() { return newsRepository.findAll(); }

    public byte[] resize (MultipartFile file) throws IOException {


        final int maxWidth = 1024;
        final int maxHeight = 768;
        boolean isAlbumType = true;

        BufferedImage inputImage = ImageIO.read(file.getInputStream());
        if (inputImage.getHeight() > maxHeight  | inputImage.getWidth() > maxWidth) {
            float currentWidth  = inputImage.getWidth();
            float currentHeight = inputImage.getHeight();
            float proportion = currentWidth/currentHeight;
            int newHeight;
            int newWidth;

            if (inputImage.getWidth() > inputImage.getHeight()) {
                newHeight = (int) (maxWidth/proportion);
                newWidth = maxWidth;
            } else {
                isAlbumType = false;

                newWidth = (int) (maxHeight * proportion);
                newHeight = maxHeight;
            }

            BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            ByteArrayOutputStream data = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "jpg", data);
            return data.toByteArray();


        } else return file.getBytes();
    }

    public Integer incrementVisitCounter() {
        Optional<VisitCounter> c = counterRepository.findById(1);
        Iterable<VisitCounter> checkTest = counterRepository.findAll();

        if (c.isPresent()) {
            int newValue = c.get().getValue() + 1;
            c.get().setValue(newValue);
            counterRepository.save(c.get());
            return newValue;
        } else {
            VisitCounter newVisitCounter = new VisitCounter();
            counterRepository.save(newVisitCounter);
            return 0;
        }
    }

}
