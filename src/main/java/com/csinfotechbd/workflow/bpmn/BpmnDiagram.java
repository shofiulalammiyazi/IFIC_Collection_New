package com.csinfotechbd.workflow.bpmn;

public class BpmnDiagram {
    private String fileName;
    private String sourceUrl;

    public BpmnDiagram() {
    }

    public BpmnDiagram(String fileName, String sourceUrl) {
        this.fileName = fileName;
        this.sourceUrl = sourceUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "BpmnDiagram [fileName=" + fileName + ", sourceUrl=" + sourceUrl + "]";
    }

}
