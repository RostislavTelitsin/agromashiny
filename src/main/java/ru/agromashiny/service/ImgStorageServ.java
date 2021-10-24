package ru.agromashiny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.agromashiny.models.ImgFile;
import ru.agromashiny.models.ImgRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImgStorageServ {
    @Autowired
    public ImgRepository imgRepository;

    public Integer saveImgFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            ImgFile imgFile = new ImgFile(filename, file.getBytes());
            imgRepository.save(imgFile);
            int iii = imgFile.getId();
            return iii;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Optional<ImgFile> getFile(Integer fileId) {
        return imgRepository.findById(fileId);
    }


    public List<ImgFile> getFiles() {
        return imgRepository.findAll();
    }

}
