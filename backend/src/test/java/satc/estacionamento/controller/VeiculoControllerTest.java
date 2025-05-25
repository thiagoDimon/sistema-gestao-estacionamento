package satc.estacionamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import satc.estacionamento.dto.VeiculoEstacionadoDTO;
import satc.estacionamento.model.Cliente;
import satc.estacionamento.model.Veiculo;
import satc.estacionamento.services.VeiculoEstacionadoService;
import satc.estacionamento.services.VeiculoService;

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

@WebMvcTest(VeiculoController.class)
public class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @MockBean
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Veiculo veiculo;
    private List<Veiculo> veiculos;
    private VeiculoEstacionadoDTO dto;
    private List<VeiculoEstacionadoDTO> dtos;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setCliente(cliente);
        veiculo.setPlaca("ABC1234");
        veiculo.setModelo("Modelo X");
        veiculo.setCor("Preto");
        veiculo.setDataCadastro(LocalDate.of(2025, 5, 20));

        veiculos = Arrays.asList(veiculo);

        dto = new VeiculoEstacionadoDTO();
        dto.setPlaca("ABC1234");

        dtos = Arrays.asList(dto);
    }

    @Test
    void listarTodos_DeveRetornarTodosVeiculos() throws Exception {
        when(veiculoService.listarTodos()).thenReturn(veiculos);

        mockMvc.perform(get("/veiculo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].placa", is("ABC1234")))
            .andExpect(jsonPath("$[0].modelo", is("Modelo X")))
            .andExpect(jsonPath("$[0].cor", is("Preto")))
            .andExpect(jsonPath("$[0].dataCadastro[0]", is(2025)))
            .andExpect(jsonPath("$[0].dataCadastro[1]", is(5)))
            .andExpect(jsonPath("$[0].dataCadastro[2]", is(20)))
            .andExpect(jsonPath("$[0].cliente.id", is(1)));

        verify(veiculoService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarVeiculo() throws Exception {
        when(veiculoService.buscarPorId(1L)).thenReturn(Optional.of(veiculo));

        mockMvc.perform(get("/veiculo/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.placa", is("ABC1234")))
            .andExpect(jsonPath("$.cor", is("Preto")))
            .andExpect(jsonPath("$.cliente.id", is(1)));

        verify(veiculoService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(veiculoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/veiculo/999"))
            .andExpect(status().isNotFound());

        verify(veiculoService, times(1)).buscarPorId(999L);
    }

    @Test
    void criar_DeveRetornarVeiculoCriado() throws Exception {
        when(veiculoService.salvar(any(Veiculo.class))).thenReturn(veiculo);
        String json = objectMapper.writeValueAsString(veiculo);

        mockMvc.perform(post("/veiculo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.placa", is("ABC1234")))
            .andExpect(jsonPath("$.cor", is("Preto")))
            .andExpect(jsonPath("$.cliente.id", is(1)));

        verify(veiculoService, times(1)).salvar(any(Veiculo.class));
    }

    @Test
    void alterar_QuandoExiste_DeveRetornarAtualizado() throws Exception {
        when(veiculoService.buscarPorId(1L)).thenReturn(Optional.of(veiculo));
        when(veiculoService.salvar(any(Veiculo.class))).thenReturn(veiculo);
        String json = objectMapper.writeValueAsString(veiculo);

        mockMvc.perform(put("/veiculo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.placa", is("ABC1234")));

        verify(veiculoService, times(1)).buscarPorId(1L);
        verify(veiculoService, times(1)).salvar(any(Veiculo.class));
    }

    @Test
    void alterar_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(veiculoService.buscarPorId(anyLong())).thenReturn(Optional.empty());
        String json = objectMapper.writeValueAsString(veiculo);

        mockMvc.perform(put("/veiculo/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());

        verify(veiculoService, times(1)).buscarPorId(999L);
        verify(veiculoService, never()).salvar(any());
    }

    @Test
    void excluir_QuandoExiste_DeveRetornarNoContent() throws Exception {
        when(veiculoService.buscarPorId(1L)).thenReturn(Optional.of(veiculo));
        doNothing().when(veiculoService).excluir(1L);

        mockMvc.perform(delete("/veiculo/1"))
            .andExpect(status().isNoContent());

        verify(veiculoService, times(1)).buscarPorId(1L);
        verify(veiculoService, times(1)).excluir(1L);
    }

    @Test
    void excluir_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(veiculoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/veiculo/999"))
            .andExpect(status().isNotFound());

        verify(veiculoService, times(1)).buscarPorId(999L);
        verify(veiculoService, never()).excluir(anyLong());
    }

    @Test
    void buscarPorPlaca_QuandoExiste_DeveRetornarVeiculo() throws Exception {
        when(veiculoService.buscarPorPlaca("ABC1234")).thenReturn(Optional.of(veiculo));

        mockMvc.perform(get("/veiculo/placa/ABC1234"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.placa", is("ABC1234")));

        verify(veiculoService, times(1)).buscarPorPlaca("ABC1234");
    }

    @Test
    void buscarPorPlaca_QuandoNaoExiste_DeveRetornarNotFound() throws Exception {
        when(veiculoService.buscarPorPlaca(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/veiculo/placa/ZZZ9999"))
            .andExpect(status().isNotFound());

        verify(veiculoService, times(1)).buscarPorPlaca("ZZZ9999");
    }
}
