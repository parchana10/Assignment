package com.jackson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.model.EventData;
import com.jackson.model.LogRecord;

public class Application {
	private static final Logger logger = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) {

		EventDao eventDao = new EventDao();
		// 1. Read server logs from a file named logs.txt
		ObjectMapper mapper = new ObjectMapper();
		Path filePath = FileSystems.getDefault().getPath("logs", "logs.txt");
		BufferedReader br = null;
		try {
			br = Files.newBufferedReader(filePath);
		} catch (FileNotFoundException e) {
			// File not found
			e.printStackTrace();
		} catch (IOException e) {
			// Error when reading the file
			e.printStackTrace();
		}
		List<String> list = br.lines().collect(Collectors.toList());

		// 2. Parse the text file and convert in to Java object/JSON
		List<LogRecord> logsList = new ArrayList<LogRecord>();
		Set<String> eventKeySet = new HashSet<String>();
		try {
			for (String logLine : list) {
				LogRecord logRecord = mapper.readValue(logLine, LogRecord.class);
				logsList.add(logRecord);
				eventKeySet.add(logRecord.id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3. Create a table in file-based HSQLDB
		try {
			eventDao.createTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// 4. Validate event data to find difference of start and finished timestamp
		List<EventData> eventDatalist = new ArrayList<EventData>();
		for (String eventKey : eventKeySet) {
			List<LogRecord> l = logsList.stream().filter(e -> e.getId().equalsIgnoreCase(eventKey))
					.collect(Collectors.toList());
			if (l.size() < 2) {
				logger.info("Started or finished event missing for event id"+ eventKey);
			} else { 
				OptionalDouble startTimeStamp = l.stream().filter(e -> e.getState().equalsIgnoreCase("STARTED"))
						.mapToDouble(e -> e.getTimestamp()).findFirst();
				OptionalDouble endTimeStamp = l.stream().filter(e -> e.getState().equalsIgnoreCase("FINISHED"))
						.mapToDouble(e -> e.getTimestamp()).findFirst();
				Double difference = endTimeStamp.getAsDouble() - startTimeStamp.getAsDouble();
				EventData eventData = new EventData();
				eventData.setId(eventKey);
				eventData.setDuration(difference.intValue());
				logger.error("Event with Event id {} took longer than 4ms"+ eventKey);
				if (difference >= 4) {
					eventData.setAlert(true);
				} else {
					eventData.setAlert(false);
				}
				try {
					//5. Insert the event details to HSQLDB database table event
					eventDao.insertRecord(eventData);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				eventDatalist.add(eventData);
			}
		}


	}
}
