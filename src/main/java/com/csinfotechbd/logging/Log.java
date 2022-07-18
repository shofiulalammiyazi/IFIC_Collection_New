package com.csinfotechbd.logging;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long refNo;
	@Lob
	private String data;
	private String exeBy;
	@Enumerated(EnumType.STRING)
	private ActionType action;
	@CreatedDate
	@Column(updatable = false)
	private Date exeTime;
	private String tableName;

	public Log() {

	}

	public Log(long refNo, String data, String exeBy, ActionType action, String tableName) {

		this.refNo = refNo;
		this.data = data;
		this.exeBy = exeBy;
		this.action = action;
		this.tableName = tableName;
	}

}
