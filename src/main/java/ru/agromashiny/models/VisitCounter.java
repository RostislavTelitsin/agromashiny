package ru.agromashiny.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VisitCounter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    private Integer value;


    public VisitCounter() {
        this.value = 0;
    }
    public Integer getValue() {
        if (value==null) {
            return 0;
        } else return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
