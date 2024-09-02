package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Estabelecimentos")
public class EstabelecimentoControllerTest {

    private static final String URI_ESTABELECIMENTOS = "/estabelecimentos";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de teste para estabelecimento")
    class EstabelecimentoCrudTests {

        @Test
        @DisplayName("Quando criamos um estabelecimento com dados válidos")
        void quandoCriamosUmEstabelecimentoComDadosValidos() throws Exception {

            EstabelecimentoRecordDTO dto = new EstabelecimentoRecordDTO("Estabelecimento.java Teste", "123456");

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URI_ESTABELECIMENTOS)
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Estabelecimento.java criado com sucesso."))
                    .andDo(print());

            // Verificar se o estabelecimento foi realmente salvo
            EstabelecimentoModel foundEstabelecimento = estabelecimentoRepository.findAll().stream()
                    .filter(e -> e.getNome().equals("Estabelecimento.java Teste"))
                    .findFirst()
                    .orElse(null);

            assertNotNull(foundEstabelecimento, "O estabelecimento não foi encontrado.");
            assertEquals("Estabelecimento.java Teste", foundEstabelecimento.getNome());
            assertEquals("123456", foundEstabelecimento.getCodigoDeAcesso());
        }

        @Test
        @DisplayName("Quando tentamos criar um estabelecimento com código de acesso inválido")
        void quandoTentamosCriarUmEstabelecimentoComCodigoDeAcessoInvalido() throws Exception {
            EstabelecimentoRecordDTO dto = new EstabelecimentoRecordDTO("Estabelecimento.java Teste", "12345");

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URI_ESTABELECIMENTOS)
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando atualizamos um estabelecimento existente com dados válidos")
        void quandoAtualizamosUmEstabelecimentoExistenteComDadosValidos() throws Exception {
            // Primeiro, cria um estabelecimento
            EstabelecimentoModel estabelecimento = new EstabelecimentoModel();
            estabelecimento.setNome("Estabelecimento.java Antigo");
            estabelecimento.setCodigoDeAcesso("codigoAntigo");
            estabelecimento = estabelecimentoRepository.save(estabelecimento);

            // Atualizar o estabelecimento
            EstabelecimentoRecordDTO dto = new EstabelecimentoRecordDTO("Estabelecimento.java Atualizado", "654321");

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andDo(print());

            // Verificar se o estabelecimento foi atualizado
            EstabelecimentoModel updatedEstabelecimento = estabelecimentoRepository.findById(estabelecimento.getId()).orElse(null);
            assertNotNull(updatedEstabelecimento);
            assertEquals("Estabelecimento.java Atualizado", updatedEstabelecimento.getNome());
            assertEquals("654321", updatedEstabelecimento.getCodigoDeAcesso());
        }

        @Test
        @DisplayName("Quando tentamos apagar um estabelecimento que não existe")
        void quandoTentamosApagarUmEstabelecimentoQueNaoExiste() throws Exception {
            // Id que não existe no banco de dados
            Long idInexistente = 999L;

            mockMvc.perform(delete(URI_ESTABELECIMENTOS + "/" + idInexistente))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertEquals("Estabelecimento.java não encontrado", result.getResponse().getContentAsString()))
                    .andDo(print());
        }


        @Test
        @DisplayName("Quando tentamos atualizar um estabelecimento com dados inválidos")
        void quandoTentamosAtualizarUmEstabelecimentoComDadosInvalidos() throws Exception {
            // Primeiro, cria um estabelecimento
            EstabelecimentoModel estabelecimento = new EstabelecimentoModel();
            estabelecimento.setNome("Estabelecimento.java Original");
            estabelecimento.setCodigoDeAcesso("codigoOriginal");
            estabelecimento = estabelecimentoRepository.save(estabelecimento);

            // Atualizar o estabelecimento com dados inválidos
            EstabelecimentoRecordDTO dto = new EstabelecimentoRecordDTO("Estabelecimento.java Atualizado", "12345"); // Código de acesso inválido

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando apagamos um estabelecimento existente")
        void quandoApagamosUmEstabelecimentoExistente() throws Exception {
            // Primeiro, cria um estabelecimento
            EstabelecimentoModel estabelecimento = new EstabelecimentoModel();
            estabelecimento.setNome("Estabelecimento.java Para Deletar");
            estabelecimento.setCodigoDeAcesso("codigoDeletar");
            estabelecimento = estabelecimentoRepository.save(estabelecimento);

            // Apagar o estabelecimento
            mockMvc.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId()))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertEquals("Estabelecimento.java deletado com sucesso.", result.getResponse().getContentAsString()))
                    .andDo(print());

            // Verificar se o estabelecimento foi realmente apagado
            EstabelecimentoModel deletedEstabelecimento = estabelecimentoRepository.findById(estabelecimento.getId()).orElse(null);
            assertNull(deletedEstabelecimento);
        }

        @Test
        @DisplayName("Quando tentamos apagar um estabelecimento com um ID inválido")
        void quandoTentamosApagarUmEstabelecimentoComIdInvalido() throws Exception {
            // ID que não existe no banco de dados
            Long idInexistente = 999L;

            mockMvc.perform(delete(URI_ESTABELECIMENTOS + "/" + idInexistente))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertEquals("Estabelecimento.java não encontrado", result.getResponse().getContentAsString()))
                    .andDo(print());
        }


        @Test
        @DisplayName("Quando tentamos atualizar um estabelecimento com um ID inexistente")
        void quandoTentamosAtualizarUmEstabelecimentoComIdInexistente() throws Exception {
            // Dados para atualizar
            EstabelecimentoRecordDTO dto = new EstabelecimentoRecordDTO("Nome Atualizado", "654321");

            String jsonRequest = objectMapper.writeValueAsString(dto);

            // ID que não existe no banco de dados
            Long idInexistente = 999L;

            mockMvc.perform(put(URI_ESTABELECIMENTOS + "/" + idInexistente)
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertEquals("Estabelecimento.java não encontrado.", result.getResponse().getContentAsString()))
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando tentamos criar um estabelecimento com campos obrigatórios faltando")
        void quandoTentamosCriarUmEstabelecimentoComCamposFaltando() throws Exception {
            // Dados inválidos (faltando nome)
            EstabelecimentoRecordDTO dto = new EstabelecimentoRecordDTO(null, "123456");

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URI_ESTABELECIMENTOS)
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }




    }
}
