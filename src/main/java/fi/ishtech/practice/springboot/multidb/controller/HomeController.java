package fi.ishtech.practice.springboot.multidb.controller;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.practice.springboot.multidb.SpringBootMultiDbApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for info URLs
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@Slf4j
public class HomeController {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${spring.profiles.active:#{null}}")
	private String activeProfile;

	@Value("${spring.datasource.name:}")
	private String ds;

	@Autowired
	private DataSource dataSource;

	@GetMapping(path = "/")
	public String index() {
		log.debug("In web root");
		return "Welcome to " + appName;
	}

	@GetMapping("/about")
	public ResponseEntity<Map<String, String>> about() {
		log.trace("Getting application details");

		Map<String, String> results = new LinkedHashMap<String, String>();

		results.put("applicationName", appName);
		results.put("activeProfile", activeProfile);

		results.put("version", appVersion());

		results.put("datasource", ds);

		results.putAll(dbMetaData());

		log.debug("About={}", results);

		return ResponseEntity.ok(results);
	}

	private String appVersion() {
		log.trace("Getting application version");

		String version;

		try {
			Manifest manifest = new Manifest(
					SpringBootMultiDbApplication.class.getResourceAsStream("/META-INF/MANIFEST.MF"));
			version = (String) manifest.getMainAttributes().get(Attributes.Name.IMPLEMENTATION_VERSION);
		} catch (IOException e) {
			log.error("Error in accessing Manifest", e);
			throw new RuntimeException(e);
		}

		return version;
	}

	private Map<String, String> dbMetaData() {
		log.trace("Getting DatabaseMetaData");

		Map<String, String> info = new LinkedHashMap<String, String>();

		try {
			DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
			info.put("dbProductName", metaData.getDatabaseProductName());
			info.put("dbProductVersion", metaData.getDatabaseProductVersion());
		} catch (SQLException e) {
			log.error("Error in accessing DatabaseMetaData", e);
			throw new RuntimeException(e);
		}

		return info;
	}

}