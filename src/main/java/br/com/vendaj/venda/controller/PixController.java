package br.com.vendaj.venda.controller;

import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@RestController
public class PixController {

	private static final String urlApi = "https://api-pix.gerencianet.com.br/"; //Para ambiente de Desenvolvimento

	@RequestMapping("/pixToken")
	public static JsonNode pixToken() throws IOException {
		CredetialsPixController credentials = new CredetialsPixController();

		String client_id = credentials.getClientId();
		String client_secret = credentials.getClientSecret();
		String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));

		//Diretório em que seu certificado em formato .p12 deve ser inserido
		System.setProperty("javax.net.ssl.keyStore", credentials.getCertificateP12());
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

	@RequestMapping("/pix/{valor}")
	public static String pix(@PathVariable double valor) throws IOException {
		CredetialsPixController credentials = new CredetialsPixController();

		JSONObject options = new JSONObject();
		options.put("client_id", credentials.getClientId());
		options.put("client_secret", credentials.getClientSecret());
		options.put("pix_cert", credentials.getCertificateP12());
		options.put("sandbox", credentials.isSandBox());


		JSONObject body = new JSONObject();
		body.put("calendario", new JSONObject().put("expiracao", 3600));
		body.put("devedor", new JSONObject().put("cpf", "94271564656").put("nome", "Gorbadoc Oldbuck"));
		body.put("valor", new JSONObject().put("original", Double.toString(valor)));
		body.put("chave", "0952e433-ec5d-49ed-a108-ac979f527683");
		body.put("solicitacaoPagador", "Serviço realizado.");

		JSONArray infoAdicionais = new JSONArray();
		infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
		infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
		body.put("infoAdicionais", infoAdicionais);

		JSONObject response = null;
		try {
			Gerencianet gn = new Gerencianet(options);
			response = gn.call("pixCreateImmediateCharge", new HashMap<String, String>(), body);
			if (response == null) {
				throw new Exception();
			}
			System.out.println(response);
		} catch (GerencianetException e) {
			System.out.println(e.getError());
			System.out.println(e.getErrorDescription());
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		HashMap<String, Object> options2 = new HashMap<String, Object>();
		options2.put("client_id", credentials.getClientId());
		options2.put("client_secret", credentials.getClientSecret());
		options2.put("pix_cert", credentials.getCertificateP12());
		options2.put("sandbox", credentials.isSandBox());

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", response.getJSONObject("loc").get("id").toString());

		try {
			Gerencianet gn = new Gerencianet(options2);
			Map<String, Object> response2 = gn.call("pixGenerateQRCode", params, new HashMap<String, Object>());
			return ((String) response2.get("imagemQrcode")).split(",")[1];

		} catch (GerencianetException e) {
			System.out.println(e.getError());
			System.out.println(e.getErrorDescription());
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
