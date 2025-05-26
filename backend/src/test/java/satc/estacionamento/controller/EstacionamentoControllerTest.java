package satc.estacionamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import satc.estacionamento.dto.RelatorioReservaDTO;
import satc.estacionamento.model.Estacionamento;
import satc.estacionamento.services.EstacionamentoService;
import satc.estacionamento.services.RankEstacionamentoService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstacionamentoController.class)
public class EstacionamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstacionamentoService estacionamentoService;

    @MockBean
    private RankEstacionamentoService rankEstacionamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Estacionamento estacionamento;
    private List<Estacionamento> estacionamentos;
    private RelatorioReservaDTO relatorio;
    private List<RelatorioReservaDTO> relatorios;

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estac A");
        estacionamento.setSigla("EA");
        estacionamento.setVagasTotais(100L);

        estacionamentos = Arrays.asList(estacionamento);

        relatorio = new RelatorioReservaDTO();
        // configure campos de relatorio, por exemplo:
        relatorio.setPlaca("ABC1234");
        relatorio.setNomeCliente("Cliente X");
        relatorio.setModelo("Modelo Y");
        relatorio.setTempoDecorridoTotal(5L);
        relatorio.setValor(50l);
        relatorio.setRank(1l);

        relatorios = Arrays.asList(relatorio);
    }

    @Test
    void listarTodos_DeveRetornarTodosEstacionamentos() throws Exception {
        when(estacionamentoService.listarTodos()).thenReturn(estacionamentos);

        mockMvc.perform(get("/estacionamento"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].nome", is("Estac A")))
            .andExpect(jsonPath("$[0].sigla", is("EA")))
            .andExpect(jsonPath("$[0].vagasTotais", is(100)));

        verify(estacionamentoService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarEstacionamento() throws Exception {
        when(estacionamentoService.buscarPorId(1L)).thenReturn(Optional.of(estacionamento));

        mockMvc.perform(get("/estacionamento/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nome", is("Estac A")))
            .andExpect(jsonPath("$.sigla", is("EA")))
            .andExpect(jsonPath("$.vagasTotais", is(100)));

        verify(estacionamentoService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(estacionamentoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/estacionamento/999"))
            .andExpect(status().isNotFound());

        verify(estacionamentoService, times(1)).buscarPorId(999L);
    }

    @Test
    void criar_DeveRetornarEstacionamentoCriado() throws Exception {
        when(estacionamentoService.salvar(any(Estacionamento.class)))
            .thenReturn(estacionamento);

        String json = objectMapper.writeValueAsString(estacionamento);

        mockMvc.perform(post("/estacionamento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nome", is("Estac A")))
            .andExpect(jsonPath("$.sigla", is("EA")))
            .andExpect(jsonPath("$.vagasTotais", is(100)));

        verify(estacionamentoService, times(1)).salvar(any(Estacionamento.class));
    }

    @Test
    void alterar_QuandoExiste_DeveRetornarAtualizado() throws Exception {
        when(estacionamentoService.buscarPorId(1L)).thenReturn(Optional.of(estacionamento));
        when(estacionamentoService.salvar(any(Estacionamento.class)))
            .thenReturn(estacionamento);

        String json = objectMapper.writeValueAsString(estacionamento);

        mockMvc.perform(put("/estacionamento/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nome", is("Estac A")));

        verify(estacionamentoService, times(1)).buscarPorId(1L);
        verify(estacionamentoService, times(1)).salvar(any(Estacionamento.class));
    }

    @Test
    void alterar_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(estacionamentoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        String json = objectMapper.writeValueAsString(estacionamento);

        mockMvc.perform(put("/estacionamento/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());

        verify(estacionamentoService, times(1)).buscarPorId(999L);
        verify(estacionamentoService, never()).salvar(any());
    }

    @Test
    void excluir_QuandoExiste_DeveRetornarNoContent() throws Exception {
        when(estacionamentoService.buscarPorId(1L)).thenReturn(Optional.of(estacionamento));
        doNothing().when(estacionamentoService).excluir(1L);

        mockMvc.perform(delete("/estacionamento/1"))
            .andExpect(status().isNoContent());

        verify(estacionamentoService, times(1)).buscarPorId(1L);
        verify(estacionamentoService, times(1)).excluir(1L);
    }

    @Test
    void excluir_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(estacionamentoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/estacionamento/999"))
            .andExpect(status().isNotFound());

        verify(estacionamentoService, times(1)).buscarPorId(999L);
        verify(estacionamentoService, never()).excluir(anyLong());
    }
}
