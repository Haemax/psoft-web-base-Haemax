package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.model.TIPO_SABOR;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
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
@DisplayName("Testes do controlador de Sabores")
public class SaborTest {

    private static final String URI_ESTABELECIMENTOS = "/estabelecimentos";
    private static final String URI_SABORES = "/estabelecimentos/{estabelecimentoId}/sabores";
    private static final String URI_SABORES_TIPO = "/estabelecimentos/{estabelecimentoId}/sabores/tipo";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private Estabelecimento estabelecimento;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        estabelecimento = new Estabelecimento();
        estabelecimento.setNome("Estabelecimento.java Teste");
        estabelecimento.setCodigoAcesso("123456");
        estabelecimentoRepository.save(estabelecimento);
    }

    @AfterEach
    void tearDown() {
        saborRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de teste para sabor")
    class SaborCrudTests {

        @Test
        @DisplayName("Quando criamos um sabor com dados válidos")
        void quandoCriamosUmSaborComDadosValidos() throws Exception {
            SaborPostPutRequestDTO dto = new SaborPostPutRequestDTO("Sabor Teste", TIPO_SABOR.DOCE, 5.0, 7.5, true);

            String jsonRequest = objectMapper.writeValueAsString(dto);

            String responseJsonString = mockMvc.perform(post(URI_SABORES, estabelecimento.getId())
                            .param("codigoDeAcesso", "123456")
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Verificar se o sabor foi realmente salvo
            Sabor createdSabor = objectMapper.readValue(responseJsonString, Sabor.class);
            Sabor foundSabor = saborRepository.findById(createdSabor.getId()).orElse(null);
            assertNotNull(foundSabor);
            assertEquals("Sabor Teste", foundSabor.getNome());
            assertEquals("doce", foundSabor.getTipo());
            assertEquals(5.0, foundSabor.getValorMedia());
            assertEquals(7.5, foundSabor.getValorGrande());
            assertTrue(foundSabor.isDisponivel());
        }

        @Test
        @DisplayName("Quando tentamos criar um sabor com código de acesso inválido")
        void quandoTentamosCriarUmSaborComCodigoDeAcessoInvalido() throws Exception {
            SaborPostPutRequestDTO dto = new SaborPostPutRequestDTO("Sabor Teste", TIPO_SABOR.DOCE, 5.0, 7.5, true);

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URI_SABORES, estabelecimento.getId())
                            .param("codigoDeAcesso", "12345") // Código de acesso inválido
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("O código de acesso do estabelecimento está incorreto"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando atualizamos um sabor com dados válidos")
        void quandoAtualizamosUmSaborComDadosValidos() throws Exception {
            Sabor sabor = new Sabor();
            sabor.setNome("Sabor Original");
            sabor.setTipo(TIPO_SABOR.DOCE);
            sabor.setValorMedia(5.0);
            sabor.setValorGrande(7.5);
            sabor.setDisponivel(true);
            sabor = saborRepository.save(sabor);

            SaborPostPutRequestDTO dto = new SaborPostPutRequestDTO("Sabor Atualizado", TIPO_SABOR.SALGADO, 6.0, 8.0, false);
            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(put(URI_SABORES + "/{id}", estabelecimento.getId(), sabor.getId())
                            .param("codigoDeAcesso", "123456")
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Sabor atualizado com sucesso."))
                    .andDo(print());

            Sabor updatedSabor = saborRepository.findById(sabor.getId()).orElse(null);
            assertNotNull(updatedSabor);
            assertEquals("Sabor Atualizado", updatedSabor.getNome());
            assertEquals("salgado", updatedSabor.getTipo());
            assertEquals(6.0, updatedSabor.getValorMedia());
            assertEquals(8.0, updatedSabor.getValorGrande());
            assertFalse(updatedSabor.isDisponivel());
        }

        @Test
        @DisplayName("Quando tentamos atualizar um sabor com código de acesso inválido")
        void quandoTentamosAtualizarUmSaborComCodigoDeAcessoInvalido() throws Exception {
            Sabor sabor = new Sabor();
            sabor.setNome("Sabor Original");
            sabor.setTipo(TIPO_SABOR.DOCE);
            sabor.setValorMedia(5.0);
            sabor.setValorGrande(7.5);
            sabor.setDisponivel(true);
            sabor = saborRepository.save(sabor);

            SaborPostPutRequestDTO dto = new SaborPostPutRequestDTO("Sabor Atualizado", TIPO_SABOR.SALGADO, 6.0, 8.0, false);
            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(put(URI_SABORES + "/{id}", estabelecimento.getId(), sabor.getId())
                            .param("codigoDeAcesso", "12345") // Código de acesso inválido
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isForbidden())
                    .andExpect(content().string("O código de acesso do estabelecimento está incorreto"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando listamos todos os sabores de um estabelecimento")
        void quandoListamosTodosOsSaboresDeUmEstabelecimento() throws Exception {
            Sabor sabor1 = new Sabor();
            sabor1.setNome("Sabor 1");
            sabor1.setTipo(TIPO_SABOR.DOCE);
            sabor1.setValorMedia(5.0);
            sabor1.setValorGrande(7.5);
            sabor1.setDisponivel(true);
            saborRepository.save(sabor1);

            Sabor sabor2 = new Sabor();
            sabor2.setNome("Sabor 2");
            sabor2.setTipo(TIPO_SABOR.SALGADO);
            sabor2.setValorMedia(6.0);
            sabor2.setValorGrande(8.0);
            sabor2.setDisponivel(false);
            saborRepository.save(sabor2);

            String responseJsonString = mockMvc.perform(get(URI_SABORES, estabelecimento.getId())
                            .param("codigoDeAcesso", "123456"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor[] sabores = objectMapper.readValue(responseJsonString, Sabor[].class);
            assertEquals(2, sabores.length);
        }

        @Test
        @DisplayName("Quando listamos sabores por tipo")
        void quandoListamosSaboresPorTipo() throws Exception {
            Sabor sabor1 = new Sabor();
            sabor1.setNome("Sabor Doce");
            sabor1.setTipo(TIPO_SABOR.DOCE);
            sabor1.setValorMedia(5.0);
            sabor1.setValorGrande(7.5);
            sabor1.setDisponivel(true);
            saborRepository.save(sabor1);

            Sabor sabor2 = new Sabor();
            sabor2.setNome("Sabor Salgado");
            sabor2.setTipo(TIPO_SABOR.SALGADO);
            sabor2.setValorMedia(6.0);
            sabor2.setValorGrande(8.0);
            sabor2.setDisponivel(false);
            saborRepository.save(sabor2);

            String responseJsonString = mockMvc.perform(get(URI_SABORES_TIPO, estabelecimento.getId())
                            .param("codigoDeAcesso", "123456")
                            .param("tipo", "doce"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor[] sabores = objectMapper.readValue(responseJsonString, Sabor[].class);
            assertEquals(1, sabores.length);
            assertEquals("Sabor Doce", sabores[0].getNome());
        }


        @Test
        @DisplayName("Quando tentamos criar um sabor com dados inválidos")
        void quandoTentamosCriarUmSaborComDadosInvalidos() throws Exception {

            SaborPostPutRequestDTO dto = new SaborPostPutRequestDTO("", TIPO_SABOR.DOCE, -5.0, 7.5, true);

            String jsonRequest = objectMapper.writeValueAsString(dto);

            mockMvc.perform(post(URI_SABORES, estabelecimento.getId())
                            .param("codigoDeAcesso", "123456")
                            .contentType("application/json")
                            .content(jsonRequest))
                    .andExpect(status().isBadRequest()) // Verifica se o status é 400 Bad Request
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando tentamos deletar um sabor que não existe")
        void quandoTentamosDeletarUmSaborQueNaoExiste() throws Exception {
            // Usar um ID que não existe
            long saborIdInexistente = 999L;

            mockMvc.perform(delete(URI_SABORES + "/{saborId}", estabelecimento.getId(), saborIdInexistente)
                            .param("codigoDeAcesso", "123456"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("O sabor informado não existe"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Quando não há sabores registrados para um estabelecimento")
        void quandoNaoHaSaboresRegistradosParaUmEstabelecimento() throws Exception {
            // Garantir que não há sabores antes do teste
            saborRepository.deleteAll();

            String responseJsonString = mockMvc.perform(get(URI_SABORES, estabelecimento.getId())
                            .param("codigoDeAcesso", "123456"))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string("Nenhum sabor cadastrado para o estabelecimento."))
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            assertEquals("Nenhum sabor cadastrado para o estabelecimento.", responseJsonString);
        }
    }
}
