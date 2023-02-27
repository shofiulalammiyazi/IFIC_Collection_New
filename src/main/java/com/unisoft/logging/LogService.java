package com.unisoft.logging;

import com.unisoft.utillity.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

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
