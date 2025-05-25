package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.dto.ClientesVeiculosDTO;
import satc.estacionamento.model.Cliente;
import satc.estacionamento.model.Veiculo;
import satc.estacionamento.repository.ClienteRepository;
import satc.estacionamento.repository.VeiculoRepository;
import satc.estacionamento.services.VeiculoClienteService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoClienteService service;

    private Cliente cliente1;
    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @BeforeEach
    void setUp() {
        cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente Teste");
        cliente1.setTelefone("12345678901");
        cliente1.setEmail("cliente@teste.com");
        cliente1.setEndereco("Rua X, 100");
        cliente1.setDataCadastro(LocalDate.of(2025,5,20));

        veiculo1 = new Veiculo();
        veiculo1.setId(10L);
        veiculo1.setPlaca("ABC1234");
        veiculo1.setModelo("Modelo A");
        veiculo1.setCliente(cliente1);

        veiculo2 = new Veiculo();
        veiculo2.setId(11L);
        veiculo2.setPlaca("DEF5678");
        veiculo2.setModelo("Modelo B");
        veiculo2.setCliente(cliente1);
    }

    @Test
    void listarVeiculosCliente_quandoSemClientes_retornaListaVazia() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        List<ClientesVeiculosDTO> result = service.listarVeiculosCliente();

        assertThat(result).isEmpty();
        verify(clienteRepository, times(1)).findAll();
        verifyNoInteractions(veiculoRepository);
    }

    @Test
    void listarVeiculosCliente_quandoClientesExistem_retornaDTOS() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1));
        when(veiculoRepository.findByCliente(cliente1)).thenReturn(Arrays.asList(veiculo1, veiculo2));

        List<ClientesVeiculosDTO> result = service.listarVeiculosCliente();

        assertThat(result).hasSize(1);
        ClientesVeiculosDTO dto = result.get(0);
        assertThat(dto.getCliente()).isEqualTo(cliente1);
        assertThat(dto.getVeiculos()).containsExactly(veiculo1, veiculo2);

        verify(clienteRepository, times(1)).findAll();
        verify(veiculoRepository, times(1)).findByCliente(cliente1);
    }
}
