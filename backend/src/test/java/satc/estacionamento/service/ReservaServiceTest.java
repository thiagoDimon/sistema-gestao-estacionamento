package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.model.*;
import satc.estacionamento.repository.ReservaRepository;
import satc.estacionamento.repository.VeiculoRepository;
import satc.estacionamento.services.ReservaService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Veiculo veiculo;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        // Criar um veículo para usar nos testes
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");
        veiculo.setModelo("Modelo X");
        veiculo.setCor("Branco");
        veiculo.setDataCadastro(LocalDate.of(2025,5,1));
        veiculo.setCliente(cliente);

        // Criar uma reserva associada
        Bloco bloco = new Bloco();
        bloco.setId(1L);

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setBloco(bloco);
        reserva.setVeiculo(veiculo);
        reserva.setDataInicio(LocalDate.of(2025,5,1));
        reserva.setDataFim(LocalDate.of(2025,5,2));
        reserva.setStatus("A");
    }

    @Test
    void listarTodos_DeveRetornarTodasReservas() {
        List<Reserva> lista = Arrays.asList(reserva);
        when(reservaRepository.findAll()).thenReturn(lista);

        List<Reserva> resultado = reservaService.listarTodos();

        assertThat(resultado).isEqualTo(lista);
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarOptionalComReserva() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> opt = reservaService.buscarPorId(1L);

        assertThat(opt).isPresent().get().isEqualTo(reserva);
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarOptionalVazio() {
        when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Reserva> opt = reservaService.buscarPorId(999L);

        assertThat(opt).isNotPresent();
        verify(reservaRepository, times(1)).findById(999L);
    }

    @Test
    void salvar_DeveChamarSaveERetornarReserva() {
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva result = reservaService.salvar(reserva);

        assertThat(result).isEqualTo(reserva);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void excluir_DeveChamarDeleteById() {
        doNothing().when(reservaRepository).deleteById(1L);

        reservaService.excluir(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }

    @Test
    void listarReservasAtivaPorPlaca_QuandoPlacaInexistente_DeveLancarIllegalArgument() {
        when(veiculoRepository.findByPlaca("ZZZ9999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.listarReservasAtivaPorPlaca("ZZZ9999"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Placa não encontrada");

        verify(veiculoRepository, times(1)).findByPlaca("ZZZ9999");
        verifyNoInteractions(reservaRepository);
    }

    @Test
    void listarReservasAtivaPorPlaca_QuandoPlacaValida_DeveRetornarLista() {
        when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo));
        List<Reserva> lista = Arrays.asList(reserva);
        when(reservaRepository.findByStatusAndVeiculo("A", veiculo)).thenReturn(lista);

        List<Reserva> resultado = reservaService.listarReservasAtivaPorPlaca("ABC1234");

        assertThat(resultado).isEqualTo(lista);
        verify(veiculoRepository, times(1)).findByPlaca("ABC1234");
        verify(reservaRepository, times(1)).findByStatusAndVeiculo("A", veiculo);
    }

    @Test
    void listarReservasAtivaPorPlaca_QuandoNenhumaAtiva_DeveRetornarListaVazia() {
        when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo));
        when(reservaRepository.findByStatusAndVeiculo("A", veiculo)).thenReturn(Collections.emptyList());

        List<Reserva> resultado = reservaService.listarReservasAtivaPorPlaca("ABC1234");

        assertThat(resultado).isEmpty();
        verify(veiculoRepository, times(1)).findByPlaca("ABC1234");
        verify(reservaRepository, times(1)).findByStatusAndVeiculo("A", veiculo);
    }
}
