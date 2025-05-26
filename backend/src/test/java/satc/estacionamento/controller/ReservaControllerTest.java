package satc.estacionamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import satc.estacionamento.model.Reserva;
import satc.estacionamento.model.Bloco;
import satc.estacionamento.model.Veiculo;
import satc.estacionamento.model.Pagamento;
import satc.estacionamento.services.ReservaService;

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

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reserva reserva;
    private List<Reserva> reservas;

    @BeforeEach
    void setUp() {
        // montar entidade Reserva com dependências mínimas
        Bloco bloco = new Bloco();
        bloco.setId(1L);
        bloco.setNome("Bloco A");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setValor(20l);

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setBloco(bloco);
        reserva.setVeiculo(veiculo);
        reserva.setDataInicio(LocalDate.of(2025, 5, 1));
        reserva.setDataFim(LocalDate.of(2025, 5, 2));
        reserva.setStatus("ATIVA");
        reserva.setPagamento(pagamento);

        reservas = Arrays.asList(reserva);
    }

    @Test
    void listarTodos_DeveRetornarTodasReservas() throws Exception {
        when(reservaService.listarTodos()).thenReturn(reservas);

        mockMvc.perform(get("/reserva"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].status", is("ATIVA")))
            .andExpect(jsonPath("$[0].veiculo.placa", is("ABC1234")));

        verify(reservaService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarReserva() throws Exception {
        when(reservaService.buscarPorId(1L)).thenReturn(Optional.of(reserva));

        mockMvc.perform(get("/reserva/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.status", is("ATIVA")))
            .andExpect(jsonPath("$.bloco.id", is(1)));

        verify(reservaService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(reservaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/reserva/999"))
            .andExpect(status().isNotFound());

        verify(reservaService, times(1)).buscarPorId(999L);
    }

    @Test
    void criar_DeveRetornarReservaCriada() throws Exception {
        when(reservaService.salvar(any(Reserva.class))).thenReturn(reserva);
        String json = objectMapper.writeValueAsString(reserva);

        mockMvc.perform(post("/reserva")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.status", is("ATIVA")));

        verify(reservaService, times(1)).salvar(any(Reserva.class));
    }

    @Test
    void alterar_QuandoExiste_DeveRetornarAtualizada() throws Exception {
        when(reservaService.buscarPorId(1L)).thenReturn(Optional.of(reserva));
        when(reservaService.salvar(any(Reserva.class))).thenReturn(reserva);
        String json = objectMapper.writeValueAsString(reserva);

        mockMvc.perform(put("/reserva/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.status", is("ATIVA")));

        verify(reservaService, times(1)).buscarPorId(1L);
        verify(reservaService, times(1)).salvar(any(Reserva.class));
    }

    @Test
    void alterar_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(reservaService.buscarPorId(anyLong())).thenReturn(Optional.empty());
        String json = objectMapper.writeValueAsString(reserva);

        mockMvc.perform(put("/reserva/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());

        verify(reservaService, times(1)).buscarPorId(999L);
        verify(reservaService, never()).salvar(any());
    }

    @Test
    void excluir_QuandoExiste_DeveRetornarNoContent() throws Exception {
        when(reservaService.buscarPorId(1L)).thenReturn(Optional.of(reserva));
        doNothing().when(reservaService).excluir(1L);

        mockMvc.perform(delete("/reserva/1"))
            .andExpect(status().isNoContent());

        verify(reservaService, times(1)).buscarPorId(1L);
        verify(reservaService, times(1)).excluir(1L);
    }

    @Test
    void excluir_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(reservaService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/reserva/999"))
            .andExpect(status().isNotFound());

        verify(reservaService, times(1)).buscarPorId(999L);
        verify(reservaService, never()).excluir(anyLong());
    }

    @Test
    void listarReservasAtivasPorPlaca_DeveRetornarLista() throws Exception {
        when(reservaService.listarReservasAtivaPorPlaca("ABC1234")).thenReturn(reservas);

        mockMvc.perform(get("/reserva/ativas/ABC1234"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].veiculo.placa", is("ABC1234")));

        verify(reservaService, times(1)).listarReservasAtivaPorPlaca("ABC1234");
    }

    @Test
    void listarReservasAtivasPorPlaca_QuandoBadRequest() throws Exception {
        when(reservaService.listarReservasAtivaPorPlaca("INVALID"))
            .thenThrow(new IllegalArgumentException("placa inválida"));

        mockMvc.perform(get("/reserva/ativas/INVALID"))
            .andExpect(status().isBadRequest());

        verify(reservaService, times(1)).listarReservasAtivaPorPlaca("INVALID");
    }

    @Test
    void listarReservasAtivasPorPlaca_QuandoErroInterno() throws Exception {
        when(reservaService.listarReservasAtivaPorPlaca("ABC1234"))
            .thenThrow(new RuntimeException("erro qualquer"));

        mockMvc.perform(get("/reserva/ativas/ABC1234"))
            .andExpect(status().isInternalServerError());

        verify(reservaService, times(1)).listarReservasAtivaPorPlaca("ABC1234");
    }
}
