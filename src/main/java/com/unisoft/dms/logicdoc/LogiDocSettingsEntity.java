package com.unisoft.dms.logicdoc;

import lombok.Data;

import javax.persistence.*;

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
