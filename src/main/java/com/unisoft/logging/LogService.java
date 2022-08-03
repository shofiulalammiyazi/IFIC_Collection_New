package com.unisoft.logging;

import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.unisoft.utillity.JsonHandler;

@Component
public class LogService {

	@Autowired
	private LoggerRepository logRepo;

	private static LoggerRepository repo;

	@PostConstruct
	private LoggerRepository setLogService() {
		return this.repo = logRepo;
	}

	@Async
	public static CompletableFuture<Void> saveLog(long refId, String exeBy, ActionType action, Object obj,
			String tableName) {
		String data = JsonHandler.convertToJSON(obj);
		Log l = new Log(refId, data, exeBy, action, tableName);
		repo.save(l);
		return null;
	}

}
