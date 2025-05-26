package satc.estacionamento.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EstacionamentoTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Estacionamento estacionamento = new Estacionamento();
        Long id = 1L;
        String nome = "Estacionamento Central";
        String sigla = "EC";
        Long vagasTotais = 500L;

        // Act
        estacionamento.setId(id);
        estacionamento.setNome(nome);
        estacionamento.setSigla(sigla);
        estacionamento.setVagasTotais(vagasTotais);

        // Assert
        assertEquals(id, estacionamento.getId());
        assertEquals(nome, estacionamento.getNome());
        assertEquals(sigla, estacionamento.getSigla());
        assertEquals(vagasTotais, estacionamento.getVagasTotais());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Estacionamento estacionamento = new Estacionamento();

        // Assert
        assertNull(estacionamento.getId());
        assertNull(estacionamento.getNome());
        assertNull(estacionamento.getSigla());
        assertNull(estacionamento.getVagasTotais());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String nome = "Estacionamento Central";
        String sigla = "EC";
        Long vagasTotais = 500L;

        // Act
        Estacionamento estacionamento = new Estacionamento(nome, sigla, vagasTotais);
        estacionamento.setId(id);

        // Assert
        assertEquals(id, estacionamento.getId());
        assertEquals(nome, estacionamento.getNome());
        assertEquals(sigla, estacionamento.getSigla());
        assertEquals(vagasTotais, estacionamento.getVagasTotais());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Estacionamento estacionamento1 = new Estacionamento();
        estacionamento1.setId(1L);
        estacionamento1.setNome("Estacionamento Central");
        estacionamento1.setSigla("EC");
        estacionamento1.setVagasTotais(500L);

        Estacionamento estacionamento2 = new Estacionamento();
        estacionamento2.setId(1L);
        estacionamento2.setNome("Estacionamento Central");
        estacionamento2.setSigla("EC");
        estacionamento2.setVagasTotais(500L);

        Estacionamento estacionamento3 = new Estacionamento();
        estacionamento3.setId(2L);
        estacionamento3.setNome("Estacionamento Norte");
        estacionamento3.setSigla("EN");
        estacionamento3.setVagasTotais(300L);

        // Assert
        assertEquals(estacionamento1, estacionamento2);
        assertEquals(estacionamento1.hashCode(), estacionamento2.hashCode());
        assertNotEquals(estacionamento1, estacionamento3);
        assertNotEquals(estacionamento1.hashCode(), estacionamento3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Central");
        estacionamento.setSigla("EC");
        estacionamento.setVagasTotais(500L);

        // Act
        String toStringResult = estacionamento.toString();

        // Assert
        assertTrue(toStringResult.contains("nome=Estacionamento Central"));
        assertTrue(toStringResult.contains("sigla=EC"));
        assertTrue(toStringResult.contains("vagasTotais=500"));
    }
}
