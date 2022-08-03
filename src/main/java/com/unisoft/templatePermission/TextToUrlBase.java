package com.unisoft.templatePermission;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TextToUrlBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private String url;

    @LazyCollection(value = LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "text_to_url_base_id", nullable = false)
    private List<TextToUrlChild> textToUrlChildren = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TextToUrlChild> getTextToUrlChildren() {
        return textToUrlChildren;
    }

    public void setTextToUrlChildren(List<TextToUrlChild> textToUrlChildren) {
        this.textToUrlChildren = textToUrlChildren;
    }
}
