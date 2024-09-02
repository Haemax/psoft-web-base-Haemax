package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.entregador.AssociaEntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.service.estabelecimento.EstabelecimentoServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Estabelecimentos")
public class EntregadorTeste {

    private static final String URI_ESTABELECIMENTOS = "/estabelecimentos";
    private static final String URI_ENTREGADORES = "/entregadores";
    private static final String URI_SABORES = "/sabores";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoServiceImpl estabelecimentoServiceImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de teste para associação de entregador")
    class EntregadorAssociationTests {

        @Nested
        @DisplayName("Conjunto de casos de teste para criação e associação de entregador")
        class EntregadorTests {

            @Test
            @DisplayName("Quando criamos um entregador com dados válidos")
            void quandoCriamosEntregadorComDadosValidos() throws Exception {
                // Dados do entregador
                EntregadorPostPutRequestDTO entregadorDTO = new EntregadorPostPutRequestDTO(
                        "Entregador Teste", "ABC1D23", "Moto", "Azul", "654321");

                String entregadorJson = objectMapper.writeValueAsString(entregadorDTO);

                mockMvc.perform(post(URI_ENTREGADORES)
                                .contentType("application/json")
                                .content(entregadorJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.nome").value("Entregador Teste"))
                        .andExpect(jsonPath("$.placaVeiculo").value("ABC1D23"))
                        .andExpect(jsonPath("$.tipoVeiculo").value("Moto"))
                        .andExpect(jsonPath("$.corVeiculo").value("Azul"))
                        .andExpect(jsonPath("$.id").exists())
                        .andDo(print());
            }

            @Test
            @DisplayName("Quando associamos um entregador a um estabelecimento com dados válidos")
            void quandoAssociamosEntregadorAEstabelecimentoComDadosValidos() throws Exception {
                // Criação de um estabelecimento
                Estabelecimento estabelecimento = new Estabelecimento();
                estabelecimento.setNome("Estabelecimento.java Teste");
                estabelecimento.setCodigoAcesso("123456");
                estabelecimento = estabelecimentoRepository.save(estabelecimento);

                // Criação de um entregador
                EntregadorPostPutRequestDTO entregadorDTO = new EntregadorPostPutRequestDTO(
                        "Entregador Teste", "ABC1D23", "Moto", "Azul", "654321");

                String entregadorJson = objectMapper.writeValueAsString(entregadorDTO);

                // Envia a requisição para criar o entregador
                MvcResult result = mockMvc.perform(post("/entregadores")
                                .contentType("application/json")
                                .content(entregadorJson))
                        .andExpect(status().isCreated())
                        .andReturn();

                // Obtém o corpo da resposta
                String responseBody = result.getResponse().getContentAsString();

                // Usa ObjectMapper para parsear o JSON e obter o ID
                ObjectNode responseJson = (ObjectNode) objectMapper.readTree(responseBody);
                long entregadorId = responseJson.get("id").asLong();

                // Criação de uma associação
                AssociaEntregadorPostPutRequestDTO requestDTO = new AssociaEntregadorPostPutRequestDTO();
                requestDTO.setId(entregadorId);

                String associaJson = objectMapper.writeValueAsString(requestDTO);

                // Envia a requisição para associar o entregador ao estabelecimento
                mockMvc.perform(post("/estabelecimentos/" + estabelecimento.getId() + "/entregadores")
                                .param("codigoAcesso", "123456")
                                .contentType("application/json")
                                .content(associaJson))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Entregador associado ao estabelecimento."))
                        .andDo(print());

                // Verifica se o entregador foi associado ao estabelecimento
                Entregador entregador = entregadorRepository.findById(entregadorId).orElse(null);
                assertNotNull(entregador);
                assertEquals(estabelecimento.getId(), entregador.getEstabelecimento().getId());
            }


        }
    }
}