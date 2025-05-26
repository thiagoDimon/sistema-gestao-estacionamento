package satc.estacionamento.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import satc.estacionamento.model.Bloco;
import satc.estacionamento.repository.BlocoRepository;
import satc.estacionamento.services.BlocoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocoServiceTest {

    @Mock
    private BlocoRepository blocoRepository;

    @InjectMocks
    private BlocoService blocoService;

    private Bloco bloco;
    private List<Bloco> blocos;

    @BeforeEach
    void setUp() {
        bloco = new Bloco();
        bloco.setId(1L);
        bloco.setNome("Bloco A");
        bloco.setSigla("BA");
        bloco.setVagasTotais(50L);
        bloco.setDescricao("Descrição do Bloco A");

        blocos = Arrays.asList(bloco);
    }

    @Test
    void listarTodos_DeveRetornarListaDeBlocos() {
        when(blocoRepository.findAll()).thenReturn(blocos);

        List<Bloco> resultado = blocoService.listarTodos();

        assertThat(resultado).isNotNull()
            .hasSize(1)
            .containsExactly(bloco);
        verify(blocoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_QuandoExiste_DeveRetornarOptionalComBloco() {
        when(blocoRepository.findById(1L)).thenReturn(Optional.of(bloco));

        Optional<Bloco> resultado = blocoService.buscarPorId(1L);

        assertThat(resultado).isPresent()
            .get()
            .isEqualTo(bloco);
        verify(blocoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_QuandoNaoExiste_DeveRetornarOptionalVazio() {
        when(blocoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Bloco> resultado = blocoService.buscarPorId(999L);

        assertThat(resultado).isNotPresent();
        verify(blocoRepository, times(1)).findById(999L);
    }

    @Test
    void salvar_DeveChamarSaveERetornarBloco() {
        when(blocoRepository.save(bloco)).thenReturn(bloco);

        Bloco resultado = blocoService.salvar(bloco);

        assertThat(resultado).isEqualTo(bloco);
        verify(blocoRepository, times(1)).save(bloco);
    }

    @Test
    void excluir_DeveChamarDeleteById() {
        // não retorna nada, apenas verifica interação
        doNothing().when(blocoRepository).deleteById(1L);

        blocoService.excluir(1L);

        verify(blocoRepository, times(1)).deleteById(1L);
    }
}
