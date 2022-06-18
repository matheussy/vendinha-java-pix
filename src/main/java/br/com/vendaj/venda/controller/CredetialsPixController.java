package br.com.vendaj.venda.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInput;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class CredetialsPixController {

	private String clientId;
	private String clientSecret;
	private String certificateP12;
	private String pixKey;
	private boolean sandBox;
	private boolean debug;

	private static CredetialsPixController instance;

	public static CredetialsPixController gi() throws IOException {
		if (instance == null) {
			instance = new CredetialsPixController();
		}
		return instance;
	}

	public CredetialsPixController() throws IOException {
		FileReader credentialsFile = new FileReader(".\\credentials.json");
		JsonNode credentials = new ObjectMapper().readValue(credentialsFile, JsonNode.class);
		try {
			credentialsFile.close();
		} catch (IOException e) {
			System.out.println("Impossible to close file credentials.json");
		}

		this.clientId = credentials.get("client_id").asText();
		this.clientSecret = credentials.get("client_secret").asText();
		this.certificateP12 = credentials.get("pix_cert").asText();
		this.pixKey = credentials.get("pix_key").asText();
		this.sandBox = Boolean.parseBoolean(credentials.get("sandbox").asText());
		this.debug = Boolean.parseBoolean(credentials.get("debug").asText());
	}

	public JsonNode jsonCredentials() throws IOException {
		return new ObjectMapper().convertValue(CredetialsPixController.gi(), JsonNode.class);
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCertificateP12() {
		return certificateP12;
	}

	public String getPixKey() {
		return pixKey;
	}

	public void setPixKey(String pixKey) {
		this.pixKey = pixKey;
	}

	public void setCertificateP12(String certificateP12) {
		this.certificateP12 = certificateP12;
	}

	public boolean isSandBox() {
		return sandBox;
	}

	public void setSandBox(boolean sandBox) {
		this.sandBox = sandBox;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
