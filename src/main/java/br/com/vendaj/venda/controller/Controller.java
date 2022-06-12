package br.com.vendaj.venda.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

@RestController
public class Controller {

	@RequestMapping("/")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/pix")
	public static String pixApi() throws IOException {
		String client_id = "Client_Id_21c3e0064df8b98b9831e31e3814c18670365c04";
		String client_secret = "Client_Secret_cdfbc775333f8946905aacfd052eb459bf7ebf51";
		String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));

		//Diretório em que seu certificado em formato .p12 deve ser inserido
		System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\Matheus Oliveira\\Downloads\\Certificado.p12");
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		URL url = new URL ("https://api-pix.gerencianet.com.br/oauth/token"); //Para ambiente de Desenvolvimento
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Basic "+ basicAuth);
		conn.setSSLSocketFactory(sslsocketfactory);
		String input = "{\"grant_type\": \"client_credentials\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		InputStreamReader reader = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(reader);

		StringBuilder response = new StringBuilder();
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			response.append(line);
		}

		conn.disconnect();
		br.close();

 		return response.toString();
	}
}
