package com.csinfotechbd.workflow.bpmn;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "lms_tb_m_bpmn_status")
@EntityListeners(AuditingEntityListener.class)
public class BpmnStatus extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private boolean updated;
    private boolean deployed;
    private int version;

    public BpmnStatus() {
    }

    public BpmnStatus(int id, String name, boolean updated, boolean deployed, int version) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.deployed = deployed;
        this.version = version;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean update) {
        this.updated = update;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    @Override
    public String toString() {
        return "BpmnStatus [id=" + id + ", name=" + name + ", updated=" + updated + ", deployed=" + deployed
                + ", version=" + version + "]";
    }

}
