package br.com.vendaj.venda.controller;


import br.com.vendaj.venda.models.ProdutoModel;
import br.com.vendaj.venda.repositorys.ProdutoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.List;

@RestController
public class Controller {


	@Autowired
	private ProdutoRepository produtoRepository;

	@RequestMapping("/")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/produtos")
	public List<ProdutoModel> produtos() {
		return produtoRepository.findAll();
	}

	@RequestMapping("/produto/{id}")
	public ProdutoModel produto(@PathVariable int id) {
		return produtoRepository.findById(id).orElseGet(ProdutoModel::new);
	}

	@RequestMapping("/produtos/categoria/{categoria}")
	public List<ProdutoModel> produtosCategoria(@PathVariable String categoria) {
		return produtoRepository.getByCategory(categoria);
	}

	@RequestMapping("/pix")
	public static JsonNode pixApi() throws IOException {
		String client_id = "Client_Id_21c3e0064df8b98b9831e31e3814c18670365c04";
		String client_secret = "Client_Secret_cdfbc775333f8946905aacfd052eb459bf7ebf51";
		String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));

		//Diretório em que seu certificado em formato .p12 deve ser inserido
		System.setProperty("javax.net.ssl.keyStore", ".\\Certificado.p12");
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

		InputStream responseStream = conn.getInputStream();
		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(responseStream, JsonNode.class);
	}


}
