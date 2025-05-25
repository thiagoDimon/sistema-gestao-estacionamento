package satc.estacionamento.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import satc.estacionamento.dto.ClientesVeiculosDTO;
import satc.estacionamento.model.Cliente;
import satc.estacionamento.model.Veiculo;
import satc.estacionamento.services.ClienteService;
import satc.estacionamento.services.VeiculoClienteService;
import satc.estacionamento.services.VeiculoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private VeiculoService veiculoService;

    @MockBean
    private VeiculoClienteService veiculoClienteService;

    private Cliente cliente;
    private List<Cliente> clientes;
    private Veiculo veiculo;
    private List<Veiculo> veiculos;
    private ClientesVeiculosDTO clientesVeiculosDTO;
    private List<ClientesVeiculosDTO> clientesVeiculosDTOs;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setTelefone("12345678901");
        cliente.setEmail("cliente@teste.com");
        cliente.setEndereco("Rua Teste, 123");
        cliente.setDataCadastro(java.time.LocalDate.now());

        clientes = Arrays.asList(cliente);

        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");
        veiculo.setModelo("Modelo Teste");
        veiculo.setCliente(cliente);

        veiculos = Arrays.asList(veiculo);

        clientesVeiculosDTO = new ClientesVeiculosDTO();
        // Set properties for clientesVeiculosDTO if needed

        clientesVeiculosDTOs = Arrays.asList(clientesVeiculosDTO);
    }

    @Test
    void listarTodos_DeveRetornarTodosClientes() throws Exception {
        when(clienteService.listarTodos()).thenReturn(clientes);

        mockMvc.perform(get("/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Cliente Teste")))
                .andExpect(jsonPath("$[0].telefone", is("12345678901")))
                .andExpect(jsonPath("$[0].email", is("cliente@teste.com")))
                .andExpect(jsonPath("$[0].endereco", is("Rua Teste, 123")));

        verify(clienteService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_QuandoClienteExiste_DeveRetornarCliente() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Cliente Teste")))
                .andExpect(jsonPath("$.telefone", is("12345678901")))
                .andExpect(jsonPath("$.email", is("cliente@teste.com")))
                .andExpect(jsonPath("$.endereco", is("Rua Teste, 123")));

        verify(clienteService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_QuandoClienteNaoExiste_DeveRetornarNotFound() throws Exception {
        when(clienteService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/cliente/999"))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(999L);
    }

    @Test
    void criar_DeveRetornarClienteCriado() throws Exception {
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Cliente Teste\",\"telefone\":\"12345678901\",\"email\":\"cliente@teste.com\",\"endereco\":\"Rua Teste, 123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Cliente Teste")))
                .andExpect(jsonPath("$.telefone", is("12345678901")))
                .andExpect(jsonPath("$.email", is("cliente@teste.com")))
                .andExpect(jsonPath("$.endereco", is("Rua Teste, 123")));

        verify(clienteService, times(1)).salvar(any(Cliente.class));
    }

    @Test
    void alterar_QuandoClienteExiste_DeveRetornarClienteAtualizado() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/cliente/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"nome\":\"Cliente Teste\",\"telefone\":\"12345678901\",\"email\":\"cliente@teste.com\",\"endereco\":\"Rua Teste, 123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Cliente Teste")))
                .andExpect(jsonPath("$.telefone", is("12345678901")))
                .andExpect(jsonPath("$.email", is("cliente@teste.com")))
                .andExpect(jsonPath("$.endereco", is("Rua Teste, 123")));

        verify(clienteService, times(1)).buscarPorId(1L);
        verify(clienteService, times(1)).salvar(any(Cliente.class));
    }

    @Test
    void alterar_QuandoClienteNaoExiste_DeveRetornarNotFound() throws Exception {
        when(clienteService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/cliente/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":999,\"nome\":\"Cliente Teste\",\"cpf\":\"12345678901\"}"))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(999L);
        verify(clienteService, never()).salvar(any(Cliente.class));
    }

    @Test
    void excluir_QuandoClienteExiste_DeveRetornarNoContent() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).excluir(1L);

        mockMvc.perform(delete("/cliente/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).buscarPorId(1L);
        verify(clienteService, times(1)).excluir(1L);
    }

    @Test
    void excluir_QuandoClienteNaoExiste_DeveRetornarNotFound() throws Exception {
        when(clienteService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/cliente/999"))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(999L);
        verify(clienteService, never()).excluir(anyLong());
    }

    @Test
    void listarVeiculos_QuandoClienteExiste_DeveRetornarVeiculos() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(veiculoService.buscarVeiculosCliente(cliente)).thenReturn(veiculos);

        mockMvc.perform(get("/cliente/1/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].placa", is("ABC1234")))
                .andExpect(jsonPath("$[0].modelo", is("Modelo Teste")));

        verify(clienteService, times(1)).buscarPorId(1L);
        verify(veiculoService, times(1)).buscarVeiculosCliente(cliente);
    }

    @Test
    void listarVeiculos_QuandoClienteNaoExiste_DeveRetornarNotFound() throws Exception {
        when(clienteService.buscarPorId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/cliente/999/veiculos"))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(999L);
        verify(veiculoService, never()).buscarVeiculosCliente(any(Cliente.class));
    }

    @Test
    void listaTodosClientesVeiculos_DeveRetornarClientesVeiculos() throws Exception {
        when(veiculoClienteService.listarVeiculosCliente()).thenReturn(clientesVeiculosDTOs);

        mockMvc.perform(get("/cliente/listaTodosClientesVeiculos"))
                .andExpect(status().isOk());

        verify(veiculoClienteService, times(1)).listarVeiculosCliente();
    }
}
