package com.unisoft.dms;

public class DmsDocumentProperty {

    private String id;

    private String url;

    private String type;

    private String name;

    public DmsDocumentProperty() {
    }

    public DmsDocumentProperty(String id, String url, String type, String name) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
