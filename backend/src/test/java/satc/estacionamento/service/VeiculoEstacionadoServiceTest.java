package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.dto.VeiculoEstacionadoDTO;
import satc.estacionamento.repository.ReservaRepository;
import satc.estacionamento.services.VeiculoEstacionadoService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoEstacionadoServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private VeiculoEstacionadoService service;

    private VeiculoEstacionadoDTO dto1;
    private VeiculoEstacionadoDTO dto2;

    @BeforeEach
    void setUp() {
        dto1 = new VeiculoEstacionadoDTO(1L, "ABC1234", "Cliente A", "ATV");
        dto2 = new VeiculoEstacionadoDTO(2L, "DEF5678", "Cliente B", "ATV");
    }

    @Test
    void listarVeiculosEstacionados_quandoSemResultados_retornaListaVazia() {
        when(reservaRepository.buscarVeiculosEstacionadosAtivos()).thenReturn(Collections.emptyList());

        List<VeiculoEstacionadoDTO> result = service.listarVeiculosEstacionados();

        assertThat(result).isEmpty();
        verify(reservaRepository, times(1)).buscarVeiculosEstacionadosAtivos();
    }

    @Test
    void listarVeiculosEstacionados_quandoExistem_retornaDTOs() {
        when(reservaRepository.buscarVeiculosEstacionadosAtivos()).thenReturn(Arrays.asList(dto1, dto2));

        List<VeiculoEstacionadoDTO> result = service.listarVeiculosEstacionados();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);
        verify(reservaRepository, times(1)).buscarVeiculosEstacionadosAtivos();
    }
}
