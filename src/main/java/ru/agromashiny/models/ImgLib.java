package ru.agromashiny.models;

import java.util.List;

public class ImgLib {

    private List<Integer> imgIdList;

    public ImgLib() {}

    public ImgLib(List<Integer> imgIdList) {
        this.imgIdList = imgIdList;
    }

    public List<Integer> getImgIdList() {
        return imgIdList;
    }

    public void setImgIdList(List<Integer> imgIdList) {
        this.imgIdList = imgIdList;
    }

    public void addItem(Integer newId) {
        this.imgIdList.add(newId);
    }
}
