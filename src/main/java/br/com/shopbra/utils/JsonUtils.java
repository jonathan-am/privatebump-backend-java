package br.com.shopbra.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonUtils {
	
	private static ObjectMapper objectMapper = new ObjectMapper()
			.configure(Feature.IGNORE_UNDEFINED, true)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
	public static String readFileToString(String uri) {
		try {
			File file = new ClassPathResource(uri).getFile();
			return new String(Files.readAllBytes(Paths.get(file.getPath())));
		} catch (IOException e) {
			log.error("Error while reading file: [{}]", e.getMessage());
			return null;
		}
	}
	
	

}
