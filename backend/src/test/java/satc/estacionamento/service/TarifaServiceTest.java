package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.model.*;
import satc.estacionamento.repository.TarifaRepository;
import satc.estacionamento.repository.ReservaRepository;
import satc.estacionamento.repository.BlocoRepository;
import satc.estacionamento.services.TarifaService;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private BlocoRepository blocoRepository;

    @InjectMocks
    private TarifaService tarifaService;

    private Reserva reserva;
    private Bloco bloco;
    private Tarifa tarifa;

    @BeforeEach
    void setUp() {
        bloco = new Bloco();
        bloco.setId(1L);

        reserva = new Reserva();
        reserva.setId(10L);
        reserva.setBloco(bloco);
        reserva.setDataInicio(LocalDate.of(2025, 5, 1));
        reserva.setDataFim(LocalDate.of(2025, 5, 1).plusDays(2)); // two hours later

        tarifa = new Tarifa();
        tarifa.setId(100L);
        tarifa.setBloco(bloco);
        tarifa.setPrecoHora(60L);
    }

    @Test
    void listarTodos_deveRetornarListaDeTarifas() {
        List<Tarifa> list = Arrays.asList(tarifa);
        when(tarifaRepository.findAll()).thenReturn(list);

        List<Tarifa> result = tarifaService.listarTodos();

        assertThat(result).isEqualTo(list);
        verify(tarifaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_quandoExiste_retornaOptionalComTarifa() {
        when(tarifaRepository.findById(100L)).thenReturn(Optional.of(tarifa));

        Optional<Tarifa> opt = tarifaService.buscarPorId(100L);

        assertThat(opt).isPresent().get().isEqualTo(tarifa);
        verify(tarifaRepository).findById(100L);
    }

    @Test
    void buscarPorId_quandoNaoExiste_retornaOptionalVazio() {
        when(tarifaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Tarifa> opt = tarifaService.buscarPorId(999L);

        assertThat(opt).isNotPresent();
        verify(tarifaRepository).findById(999L);
    }

    @Test
    void calcularValorCentavos_quandoReservaNaoEncontrada_lancaEntityNotFound() {
        when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarifaService.calcularValorCentavos(999L, 1L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Reserva não encontrada");
    }

    @Test
    void calcularValorCentavos_quandoBlocoDiferente_lancaIllegalArgument() {
        // reserva bloco id=1, but pass 2
        reserva.setBloco(bloco);
        when(reservaRepository.findById(10L)).thenReturn(Optional.of(reserva));

        assertThatThrownBy(() -> tarifaService.calcularValorCentavos(10L, 2L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("O bloco informado não corresponde ao bloco da reserva");
    }

    @Test
    void calcularValorCentavos_quandoBlocoNaoEncontrado_lancaEntityNotFound() {
        when(reservaRepository.findById(10L)).thenReturn(Optional.of(reserva));
        when(blocoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarifaService.calcularValorCentavos(10L, 1L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Bloco não encontrado");
    }

    @Test
    void calcularValorCentavos_quandoSemTarifas_lancaEntityNotFound() {
        when(reservaRepository.findById(10L)).thenReturn(Optional.of(reserva));
        when(blocoRepository.findById(1L)).thenReturn(Optional.of(bloco));
        when(tarifaRepository.findByBloco(bloco)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> tarifaService.calcularValorCentavos(10L, 1L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Tarifa não encontrada para o bloco informado");
    }

    @Test
    void salvar_deveChamarSaveERetornarTarifa() {
        when(tarifaRepository.save(tarifa)).thenReturn(tarifa);

        Tarifa result = tarifaService.salvar(tarifa);

        assertThat(result).isEqualTo(tarifa);
        verify(tarifaRepository).save(tarifa);
    }

    @Test
    void excluir_deveChamarDeleteById() {
        doNothing().when(tarifaRepository).deleteById(100L);

        tarifaService.excluir(100L);

        verify(tarifaRepository).deleteById(100L);
    }

    @Test
    void listarTarifasPorBloco_deveRetornarLista() {
        List<Tarifa> list = Arrays.asList(tarifa);
        when(tarifaRepository.findByBloco(bloco)).thenReturn(list);

        List<Tarifa> result = tarifaService.listarTarifasPorBloco(bloco);

        assertThat(result).isEqualTo(list);
        verify(tarifaRepository).findByBloco(bloco);
    }
}
