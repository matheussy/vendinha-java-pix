package br.com.vendaj.venda.controllers;


import br.com.gerencianet.gnsdk.Gerencianet;
import br.com.gerencianet.gnsdk.exceptions.GerencianetException;
import br.com.vendaj.venda.dto.ProdutoDto;
import br.com.vendaj.venda.models.CategoriaModel;
import br.com.vendaj.venda.repositorys.CategoriaRepository;
import br.com.vendaj.venda.repositorys.ProdutoRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VendaController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@RequestMapping("/")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/produtos")
	public List<ProdutoDto> produtos() {
		return ProdutoDto.getProdutosDto(produtoRepository.findAll());
	}

	@RequestMapping("/categorias")
	public List<CategoriaModel> categorias() {
		return categoriaRepository.findAll();
	}

	@RequestMapping("/produtos/{id}")
	public ProdutoDto produto(@PathVariable int id) {
		return new ProdutoDto(produtoRepository.findById(id).orElseThrow());
	}

	@RequestMapping("/categorias/{categoria}")
	public List<ProdutoDto> produtosCategoria(@PathVariable String categoria) {
		return ProdutoDto.getProdutosDto(produtoRepository.getByCategory(categoriaRepository.findById(categoria).orElseThrow()));
	}

	@RequestMapping("/produtos/img/{id}")
	public String getProdutoImg(@PathVariable int id) {
		return produtoRepository.getImg(id);
	}

	@RequestMapping("/pix/{valor}")
	public static String pix(@PathVariable double valor) throws IOException {
		CredentialsPixController credentials = new CredentialsPixController();

		JSONObject options = new JSONObject();
		options.put("client_id", credentials.getClientId());
		options.put("client_secret", credentials.getClientSecret());
		options.put("pix_cert", credentials.getCertificateP12());
		options.put("sandbox", credentials.isSandBox());

		JSONObject body = new JSONObject();
		body.put("calendario", new JSONObject().put("expiracao", 3600));
		body.put("valor", new JSONObject().put("original", Double.toString(valor)));
		body.put("chave", credentials.getPixKey());
		body.put("solicitacaoPagador", "Serviço realizado.");

		JSONArray infoAdicionais = new JSONArray();
		infoAdicionais.put(new JSONObject().put("nome", "Campo 1").put("valor", "Informação Adicional1 do PSP-Recebedor"));
		infoAdicionais.put(new JSONObject().put("nome", "Campo 2").put("valor", "Informação Adicional2 do PSP-Recebedor"));
		body.put("infoAdicionais", infoAdicionais);

		JSONObject response;
		try {
			Gerencianet gn = new Gerencianet(options);
			response = gn.call("pixCreateImmediateCharge", new HashMap<>(), body);
			if (response == null) {
				throw new Exception();
			}
		} catch (GerencianetException e) {
			System.out.println(e.getError());
			System.out.println(e.getErrorDescription());
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		HashMap<String, Object> options2 = new HashMap<>();
		options2.put("client_id", credentials.getClientId());
		options2.put("client_secret", credentials.getClientSecret());
		options2.put("pix_cert", credentials.getCertificateP12());
		options2.put("sandbox", credentials.isSandBox());

		HashMap<String, String> params = new HashMap<>();
		params.put("id", response.getJSONObject("loc").get("id").toString());

		try {
			Gerencianet gn = new Gerencianet(options2);
			Map<String, Object> response2 = gn.call("pixGenerateQRCode", params, new HashMap<>());
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
