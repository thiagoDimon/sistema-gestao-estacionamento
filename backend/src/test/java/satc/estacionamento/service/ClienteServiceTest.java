package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.model.Cliente;
import satc.estacionamento.repository.ClienteRepository;
import satc.estacionamento.services.ClienteService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private List<Cliente> clientes;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setTelefone("12345678901");
        cliente.setEmail("cliente@teste.com");
        cliente.setEndereco("Rua Exemplo, 123");
        cliente.setDataCadastro(LocalDate.of(2025, 5, 20));

        clientes = Arrays.asList(cliente);
    }

    @Test
    void listarTodos_DeveRetornarListaDeClientes() {
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarTodos();

        assertThat(resultado)
            .isNotNull()
            .hasSize(1)
            .containsExactly(cliente);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarOptionalComCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.buscarPorId(1L);

        assertThat(resultado)
            .isPresent()
            .get()
            .isEqualTo(cliente);
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarOptionalVazio() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.buscarPorId(999L);

        assertThat(resultado).isNotPresent();
        verify(clienteRepository, times(1)).findById(999L);
    }

    @Test
    void salvar_DeveChamarSaveERetornarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.salvar(cliente);

        assertThat(resultado).isEqualTo(cliente);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void excluir_DeveChamarDeleteById() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.excluir(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
