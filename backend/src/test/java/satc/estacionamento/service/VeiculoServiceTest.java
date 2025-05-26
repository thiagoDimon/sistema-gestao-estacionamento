package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.model.Cliente;
import satc.estacionamento.model.Veiculo;
import satc.estacionamento.repository.VeiculoRepository;
import satc.estacionamento.services.VeiculoService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    private Cliente cliente;
    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        veiculo1 = new Veiculo();
        veiculo1.setId(10L);
        veiculo1.setCliente(cliente);
        veiculo1.setPlaca("ABC1234");
        veiculo1.setModelo("Modelo A");
        veiculo1.setCor("Preto");
        veiculo1.setDataCadastro(LocalDate.of(2025,5,20));

        veiculo2 = new Veiculo();
        veiculo2.setId(11L);
        veiculo2.setCliente(cliente);
        veiculo2.setPlaca("DEF5678");
        veiculo2.setModelo("Modelo B");
        veiculo2.setCor("Branco");
        veiculo2.setDataCadastro(LocalDate.of(2025,5,21));
    }

    @Test
    void listarTodos_deveRetornarListaDeVeiculos() {
        List<Veiculo> list = Arrays.asList(veiculo1, veiculo2);
        when(veiculoRepository.findAll()).thenReturn(list);

        List<Veiculo> result = veiculoService.listarTodos();

        assertThat(result).isEqualTo(list);
        verify(veiculoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_quandoExiste_retornaOptionalComVeiculo() {
        when(veiculoRepository.findById(10L)).thenReturn(Optional.of(veiculo1));

        Optional<Veiculo> opt = veiculoService.buscarPorId(10L);

        assertThat(opt).isPresent().get().isEqualTo(veiculo1);
        verify(veiculoRepository).findById(10L);
    }

    @Test
    void buscarPorId_quandoNaoExiste_retornaOptionalVazio() {
        when(veiculoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Veiculo> opt = veiculoService.buscarPorId(999L);

        assertThat(opt).isNotPresent();
        verify(veiculoRepository).findById(999L);
    }

    @Test
    void salvar_deveChamarSaveERetornarVeiculo() {
        when(veiculoRepository.save(veiculo1)).thenReturn(veiculo1);

        Veiculo result = veiculoService.salvar(veiculo1);

        assertThat(result).isEqualTo(veiculo1);
        verify(veiculoRepository).save(veiculo1);
    }

    @Test
    void excluir_deveChamarDeleteById() {
        doNothing().when(veiculoRepository).deleteById(10L);

        veiculoService.excluir(10L);

        verify(veiculoRepository).deleteById(10L);
    }

    @Test
    void buscarVeiculosCliente_deveRetornarListaDeVeiculosDoCliente() {
        List<Veiculo> list = Arrays.asList(veiculo1, veiculo2);
        when(veiculoRepository.findByCliente(cliente)).thenReturn(list);

        List<Veiculo> result = veiculoService.buscarVeiculosCliente(cliente);

        assertThat(result).isEqualTo(list);
        verify(veiculoRepository).findByCliente(cliente);
    }

    @Test
    void buscarVeiculosCliente_quandoNenhumVeiculo_retornaListaVazia() {
        when(veiculoRepository.findByCliente(cliente)).thenReturn(Collections.emptyList());

        List<Veiculo> result = veiculoService.buscarVeiculosCliente(cliente);

        assertThat(result).isEmpty();
        verify(veiculoRepository).findByCliente(cliente);
    }

    @Test
    void buscarPorPlaca_quandoExiste_retornaOptionalComVeiculo() {
        when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo1));

        Optional<Veiculo> opt = veiculoService.buscarPorPlaca("ABC1234");

        assertThat(opt).isPresent().get().isEqualTo(veiculo1);
        verify(veiculoRepository).findByPlaca("ABC1234");
    }

    @Test
    void buscarPorPlaca_quandoNaoExiste_retornaOptionalVazio() {
        when(veiculoRepository.findByPlaca(anyString())).thenReturn(Optional.empty());

        Optional<Veiculo> opt = veiculoService.buscarPorPlaca("ZZZ9999");

        assertThat(opt).isNotPresent();
        verify(veiculoRepository).findByPlaca("ZZZ9999");
    }
}
