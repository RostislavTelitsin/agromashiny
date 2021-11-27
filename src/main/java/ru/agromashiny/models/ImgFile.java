package ru.agromashiny.models;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Entity
@Table(name = "imgs")
public class ImgFile {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private  String imgName;

    @Column(name="ItemImage")
    @Lob
    public byte[] data;

    public ImgFile() {}

    public ImgFile(String imgName, byte[] data) {
        super();
        this.imgName = imgName;
        this.data = data;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public byte[] getData() {
        return data;  }

    public String getImgData() {
        String enc = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(data);
        return enc;  }

    public void setData() { this.data = data;  }
}
