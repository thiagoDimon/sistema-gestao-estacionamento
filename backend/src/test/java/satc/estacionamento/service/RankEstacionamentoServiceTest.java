package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.dto.RelatorioReservaDTO;
import satc.estacionamento.model.*;
import satc.estacionamento.repository.ReservaRepository;
import satc.estacionamento.repository.TarifaRepository;
import satc.estacionamento.services.RankEstacionamentoService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RankEstacionamentoServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private TarifaRepository tarifaRepository;

    @InjectMocks
    private RankEstacionamentoService rankService;

    private Reserva res1;
    private Reserva res2;
    private Tarifa tarifaA;
    private Tarifa tarifaB;

    @BeforeEach
    void setUp() {
        // Cliente
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("Cliente A");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Cliente B");

        // Veiculos
        Veiculo veic1 = new Veiculo();
        veic1.setId(1L);
        veic1.setPlaca("AAA1111");
        veic1.setModelo("Modelo1");
        veic1.setCliente(cliente1);

        Veiculo veic2 = new Veiculo();
        veic2.setId(2L);
        veic2.setPlaca("BBB2222");
        veic2.setModelo("Modelo2");
        veic2.setCliente(cliente2);

        // Bloco (same for both)
        Bloco bloco = new Bloco();
        bloco.setId(1L);

        // Reservas
        res1 = new Reserva();
        res1.setId(1L);
        res1.setBloco(bloco);
        res1.setVeiculo(veic1);
        res1.setDataInicio(LocalDate.of(2025,5,1));
        res1.setDataFim(LocalDate.of(2025,5,3)); // 2 days -> 48h

        res2 = new Reserva();
        res2.setId(2L);
        res2.setBloco(bloco);
        res2.setVeiculo(veic2);
        res2.setDataInicio(LocalDate.of(2025,5,2));
        res2.setDataFim(LocalDate.of(2025,5,3)); // 1 day -> 24h

        // Tarifas
        tarifaA = new Tarifa();
        tarifaA.setPrecoHora(10L);
        tarifaB = new Tarifa();
        tarifaB.setPrecoHora(20L);
    }

    @Test
    void obterRankEstacionamento_quandoNaoHaReservas_retornaListaVazia() {
        when(reservaRepository.findReservasComClienteEVeiculoComPagamento(any(), any()))
            .thenReturn(Collections.emptyList());

        List<RelatorioReservaDTO> result = rankService.obterRankEstacionamento(
            LocalDate.now(), LocalDate.now());

        assertThat(result).isEmpty();
        verify(reservaRepository, times(1)).findReservasComClienteEVeiculoComPagamento(any(), any());
        verifyNoInteractions(tarifaRepository);
    }
}
