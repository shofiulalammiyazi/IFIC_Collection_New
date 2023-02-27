package com.unisoft.workflow.bpmn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpmnData {

    private int fileId;
    private String xml;
    private List<XMLProperties> properties;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public List<XMLProperties> getProperties() {
        return properties;
    }

    public void setProperties(List<XMLProperties> properties) {
        this.properties = properties;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "BpmnData [fileId=" + fileId + ", xml=" + xml + ", properties=" + properties + "]";
    }

}
