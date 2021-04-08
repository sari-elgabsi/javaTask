package com.model;

import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "urls")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "originalLongURL")
    private String originalLongURL;

    public URL() {
    }

    public URL(long id, String originalLongURL) {
        this.id = id;
        this.originalLongURL = originalLongURL;
    }
}
