package br.com.vendaj.venda.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mifmif.common.regex.Generex;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;


@RestController
public class PixController {

	private static final String urlApi = "https://api-pix.gerencianet.com.br/"; //Para ambiente de Desenvolvimento

	@RequestMapping("/pixToken")
	public static JsonNode pixToken() throws IOException {
		String client_id = "Client_Id_21c3e0064df8b98b9831e31e3814c18670365c04";
		String client_secret = "Client_Secret_cdfbc775333f8946905aacfd052eb459bf7ebf51";
		String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));

		//Diretório em que seu certificado em formato .p12 deve ser inserido
		System.setProperty("javax.net.ssl.keyStore", ".\\Certificado.p12");
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		URL url = new URL (urlApi + "oauth/token");
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

		return new ObjectMapper().readValue(conn.getInputStream(), JsonNode.class);
	}

	@RequestMapping("/pix")
	public static JsonNode pix() throws IOException {
		String payload = """
				{
				  "calendario": {
				    "expiracao": 3600
				  },
				  "devedor": {
				    "cpf": "12345678909",
				    "nome": "Francisco da Silva"
				  },
				  "valor": {
				    "original": "123.45"
				  },
				  "chave": "71cdf9ba-c695-4e3c-b010-abb521a3f1be",
				  "solicitacaoPagador": "Informe o número ou identificador do pedido."
				}
				""";
		JsonNode token = pixToken();
		String txid = new Generex("[a-zA-Z0-9]{26,35}").random();
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		URL url = new URL(urlApi + "v2/cob/" + txid);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", token.get("token_type") + " " + token.get("access_token"));
		conn.setSSLSocketFactory(sslsocketfactory);

		OutputStream os = conn.getOutputStream();
		os.write(payload.getBytes());
		os.flush();

		return new ObjectMapper().readValue(conn.getInputStream(), JsonNode.class);
	}

}
