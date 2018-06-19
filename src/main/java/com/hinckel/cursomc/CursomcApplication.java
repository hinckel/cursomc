package com.hinckel.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hinckel.cursomc.domain.Categoria;
import com.hinckel.cursomc.domain.Cidade;
import com.hinckel.cursomc.domain.Cliente;
import com.hinckel.cursomc.domain.Endereco;
import com.hinckel.cursomc.domain.Estado;
import com.hinckel.cursomc.domain.ItemPedido;
import com.hinckel.cursomc.domain.Pagamento;
import com.hinckel.cursomc.domain.PagamentoComBoleto;
import com.hinckel.cursomc.domain.PagamentoComCartao;
import com.hinckel.cursomc.domain.Pedido;
import com.hinckel.cursomc.domain.Produto;
import com.hinckel.cursomc.domain.enums.EstadoPagamento;
import com.hinckel.cursomc.domain.enums.TipoCliente;
import com.hinckel.cursomc.repositories.CategoriaRepository;
import com.hinckel.cursomc.repositories.CidadeRepository;
import com.hinckel.cursomc.repositories.ClienteRepository;
import com.hinckel.cursomc.repositories.EnderecoRepository;
import com.hinckel.cursomc.repositories.EstadoRepository;
import com.hinckel.cursomc.repositories.ItemPedidoRepository;
import com.hinckel.cursomc.repositories.PagamentoRepository;
import com.hinckel.cursomc.repositories.PedidoRepository;
import com.hinckel.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRespository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRespository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
		cli1.getTelefone().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 11:36"), cli1, e1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 10:06"), cli1, e2);

		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);

		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2,
				sdf.parse("20/02/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);

		cli1.setPedidos(Arrays.asList(pedido1, pedido2));

		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		
		ItemPedido ip1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedido2, p2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(ip1, ip2));
		pedido2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
