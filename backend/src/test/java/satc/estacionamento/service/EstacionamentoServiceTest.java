package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.model.Estacionamento;
import satc.estacionamento.repository.EstacionamentoRepository;
import satc.estacionamento.services.EstacionamentoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstacionamentoServiceTest {

    @Mock
    private EstacionamentoRepository estacionamentoRepository;

    @InjectMocks
    private EstacionamentoService estacionamentoService;

    private Estacionamento estacionamento;
    private List<Estacionamento> estacionamentos;

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estac A");
        estacionamento.setSigla("EA");
        estacionamento.setVagasTotais(100L);

        estacionamentos = Arrays.asList(estacionamento);
    }

    @Test
    void listarTodos_DeveRetornarListaDeEstacionamentos() {
        when(estacionamentoRepository.findAll()).thenReturn(estacionamentos);

        List<Estacionamento> resultado = estacionamentoService.listarTodos();

        assertThat(resultado)
            .isNotNull()
            .hasSize(1)
            .containsExactly(estacionamento);
        verify(estacionamentoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarOptionalComEstacionamento() {
        when(estacionamentoRepository.findById(1L)).thenReturn(Optional.of(estacionamento));

        Optional<Estacionamento> resultado = estacionamentoService.buscarPorId(1L);

        assertThat(resultado)
            .isPresent()
            .get()
            .isEqualTo(estacionamento);
        verify(estacionamentoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarOptionalVazio() {
        when(estacionamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Estacionamento> resultado = estacionamentoService.buscarPorId(999L);

        assertThat(resultado).isNotPresent();
        verify(estacionamentoRepository, times(1)).findById(999L);
    }

    @Test
    void salvar_DeveChamarSaveERetornarEstacionamento() {
        when(estacionamentoRepository.save(estacionamento)).thenReturn(estacionamento);

        Estacionamento resultado = estacionamentoService.salvar(estacionamento);

        assertThat(resultado).isEqualTo(estacionamento);
        verify(estacionamentoRepository, times(1)).save(estacionamento);
    }

    @Test
    void excluir_DeveChamarDeleteById() {
        doNothing().when(estacionamentoRepository).deleteById(1L);

        estacionamentoService.excluir(1L);

        verify(estacionamentoRepository, times(1)).deleteById(1L);
    }
}
