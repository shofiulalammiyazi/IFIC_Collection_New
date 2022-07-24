package com.unisoft.dms.logicdoc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Created by Tanmoy on 4/23/2017.
 */
@Data
@Entity
@Table(name = "los_tb_s_logidoc")
public class LogiDocSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String ip;

    private Integer port;
}
