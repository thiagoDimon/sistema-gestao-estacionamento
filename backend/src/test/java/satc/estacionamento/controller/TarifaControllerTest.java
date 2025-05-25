package satc.estacionamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import satc.estacionamento.model.Bloco;
import satc.estacionamento.model.Tarifa;
import satc.estacionamento.services.BlocoService;
import satc.estacionamento.services.TarifaService;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarifaController.class)
public class TarifaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarifaService tarifaService;

    @MockBean
    private BlocoService blocoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Bloco bloco;
    private Tarifa tarifa;
    private List<Tarifa> tarifas;

    @BeforeEach
    void setUp() {
        bloco = new Bloco();
        bloco.setId(1L);
        bloco.setNome("Bloco A");

        tarifa = new Tarifa();
        tarifa.setId(1L);
        tarifa.setBloco(bloco);
        tarifa.setDescricao("Hora Cheia");
        tarifa.setPrecoHora(500L);

        tarifas = Arrays.asList(tarifa);
    }

    @Test
    void listarTodos_DeveRetornarTodasTarifas() throws Exception {
        when(tarifaService.listarTodos()).thenReturn(tarifas);

        mockMvc.perform(get("/tarifa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].descricao", is("Hora Cheia")))
            .andExpect(jsonPath("$[0].precoHora", is(500)));

        verify(tarifaService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarTarifa() throws Exception {
        when(tarifaService.buscarPorId(1L)).thenReturn(Optional.of(tarifa));

        mockMvc.perform(get("/tarifa/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.descricao", is("Hora Cheia")));

        verify(tarifaService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(tarifaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/tarifa/999"))
            .andExpect(status().isNotFound());

        verify(tarifaService, times(1)).buscarPorId(999L);
    }

    @Test
    void criar_DeveRetornarTarifaCriada() throws Exception {
        when(tarifaService.salvar(any(Tarifa.class))).thenReturn(tarifa);
        String json = objectMapper.writeValueAsString(tarifa);

        mockMvc.perform(post("/tarifa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.descricao", is("Hora Cheia")));

        verify(tarifaService, times(1)).salvar(any(Tarifa.class));
    }

    @Test
    void alterar_QuandoExiste_DeveRetornarAtualizada() throws Exception {
        when(tarifaService.buscarPorId(1L)).thenReturn(Optional.of(tarifa));
        when(tarifaService.salvar(any(Tarifa.class))).thenReturn(tarifa);
        String json = objectMapper.writeValueAsString(tarifa);

        mockMvc.perform(put("/tarifa/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.descricao", is("Hora Cheia")));

        verify(tarifaService, times(1)).buscarPorId(1L);
        verify(tarifaService, times(1)).salvar(any(Tarifa.class));
    }

    @Test
    void alterar_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(tarifaService.buscarPorId(anyLong())).thenReturn(Optional.empty());
        String json = objectMapper.writeValueAsString(tarifa);

        mockMvc.perform(put("/tarifa/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());

        verify(tarifaService, times(1)).buscarPorId(999L);
        verify(tarifaService, never()).salvar(any());
    }

    @Test
    void excluir_QuandoExiste_DeveRetornarNoContent() throws Exception {
        when(tarifaService.buscarPorId(1L)).thenReturn(Optional.of(tarifa));
        doNothing().when(tarifaService).excluir(1L);

        mockMvc.perform(delete("/tarifa/1"))
            .andExpect(status().isNoContent());

        verify(tarifaService, times(1)).buscarPorId(1L);
        verify(tarifaService, times(1)).excluir(1L);
    }

    @Test
    void excluir_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(tarifaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/tarifa/999"))
            .andExpect(status().isNotFound());

        verify(tarifaService, times(1)).buscarPorId(999L);
        verify(tarifaService, never()).excluir(anyLong());
    }

    @Test
    void listarTarifasPorBloco_QuandoExiste_DeveRetornarLista() throws Exception {
        when(blocoService.buscarPorId(1L)).thenReturn(Optional.of(bloco));
        when(tarifaService.listarTarifasPorBloco(bloco)).thenReturn(tarifas);

        mockMvc.perform(get("/tarifa/bloco/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].bloco.id", is(1)));

        verify(blocoService, times(1)).buscarPorId(1L);
        verify(tarifaService, times(1)).listarTarifasPorBloco(bloco);
    }

    @Test
    void listarTarifasPorBloco_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(blocoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/tarifa/bloco/999"))
            .andExpect(status().isNotFound());

        verify(blocoService, times(1)).buscarPorId(999L);
        verify(tarifaService, never()).listarTarifasPorBloco(any());
    }

    @Test
    void calcularValorTarifa_Sucesso_DeveRetornarValor() throws Exception {
        when(tarifaService.calcularValorCentavos(1L, 1L)).thenReturn(1500L);

        mockMvc.perform(get("/tarifa/valorTarifaPassandoReservaBloco/1/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(1500)));

        verify(tarifaService, times(1)).calcularValorCentavos(1L, 1L);
    }

    @Test
    void calcularValorTarifa_NotFound_QuandoEntityNotFound() throws Exception {
        when(tarifaService.calcularValorCentavos(1L, 1L))
            .thenThrow(new EntityNotFoundException("não encontrado"));

        mockMvc.perform(get("/tarifa/valorTarifaPassandoReservaBloco/1/1"))
            .andExpect(status().isNotFound());

        verify(tarifaService, times(1)).calcularValorCentavos(1L, 1L);
    }

    @Test
    void calcularValorTarifa_BadRequest_QuandoIllegalArgument() throws Exception {
        when(tarifaService.calcularValorCentavos(1L, 1L))
            .thenThrow(new IllegalArgumentException("argumento inválido"));

        mockMvc.perform(get("/tarifa/valorTarifaPassandoReservaBloco/1/1"))
            .andExpect(status().isBadRequest());

        verify(tarifaService, times(1)).calcularValorCentavos(1L, 1L);
    }

    @Test
    void calcularValorTarifa_InternalError_QuandoRuntimeException() throws Exception {
        when(tarifaService.calcularValorCentavos(1L, 1L))
            .thenThrow(new RuntimeException("erro interno"));

        mockMvc.perform(get("/tarifa/valorTarifaPassandoReservaBloco/1/1"))
            .andExpect(status().isInternalServerError());

        verify(tarifaService, times(1)).calcularValorCentavos(1L, 1L);
    }
}
