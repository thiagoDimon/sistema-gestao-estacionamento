package satc.estacionamento.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlocoTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Bloco bloco = new Bloco();
        Long id = 1L;
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Central");
        String nome = "Bloco A";
        String sigla = "BA";
        Long vagasTotais = 100L;
        String descricao = "Bloco próximo à entrada principal";

        // Act
        bloco.setId(id);
        bloco.setEstacionamento(estacionamento);
        bloco.setNome(nome);
        bloco.setSigla(sigla);
        bloco.setVagasTotais(vagasTotais);
        bloco.setDescricao(descricao);

        // Assert
        assertEquals(id, bloco.getId());
        assertEquals(estacionamento, bloco.getEstacionamento());
        assertEquals(nome, bloco.getNome());
        assertEquals(sigla, bloco.getSigla());
        assertEquals(vagasTotais, bloco.getVagasTotais());
        assertEquals(descricao, bloco.getDescricao());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Bloco bloco = new Bloco();

        // Assert
        assertNull(bloco.getId());
        assertNull(bloco.getEstacionamento());
        assertNull(bloco.getNome());
        assertNull(bloco.getSigla());
        assertNull(bloco.getVagasTotais());
        assertNull(bloco.getDescricao());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Central");
        String nome = "Bloco A";
        String sigla = "BA";
        Long vagasTotais = 100L;
        String descricao = "Bloco próximo à entrada principal";

        // Act
        Bloco bloco = new Bloco(estacionamento, nome, sigla, vagasTotais, descricao);
        bloco.setId(id);

        // Assert
        assertEquals(id, bloco.getId());
        assertEquals(estacionamento, bloco.getEstacionamento());
        assertEquals(nome, bloco.getNome());
        assertEquals(sigla, bloco.getSigla());
        assertEquals(vagasTotais, bloco.getVagasTotais());
        assertEquals(descricao, bloco.getDescricao());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Central");

        Bloco bloco1 = new Bloco();
        bloco1.setId(1L);
        bloco1.setEstacionamento(estacionamento);
        bloco1.setNome("Bloco A");
        bloco1.setSigla("BA");
        bloco1.setVagasTotais(100L);
        bloco1.setDescricao("Bloco próximo à entrada principal");

        Bloco bloco2 = new Bloco();
        bloco2.setId(1L);
        bloco2.setEstacionamento(estacionamento);
        bloco2.setNome("Bloco A");
        bloco2.setSigla("BA");
        bloco2.setVagasTotais(100L);
        bloco2.setDescricao("Bloco próximo à entrada principal");

        Bloco bloco3 = new Bloco();
        bloco3.setId(2L);
        bloco3.setEstacionamento(estacionamento);
        bloco3.setNome("Bloco B");
        bloco3.setSigla("BB");
        bloco3.setVagasTotais(150L);
        bloco3.setDescricao("Bloco próximo à saída");

        // Assert
        assertEquals(bloco1, bloco2);
        assertEquals(bloco1.hashCode(), bloco2.hashCode());
        assertNotEquals(bloco1, bloco3);
        assertNotEquals(bloco1.hashCode(), bloco3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setId(1L);
        estacionamento.setNome("Estacionamento Central");

        Bloco bloco = new Bloco();
        bloco.setId(1L);
        bloco.setEstacionamento(estacionamento);
        bloco.setNome("Bloco A");
        bloco.setSigla("BA");
        bloco.setVagasTotais(100L);
        bloco.setDescricao("Bloco próximo à entrada principal");

        // Act
        String toStringResult = bloco.toString();

        // Assert
        assertTrue(toStringResult.contains("nome=Bloco A"));
        assertTrue(toStringResult.contains("sigla=BA"));
        assertTrue(toStringResult.contains("vagasTotais=100"));
        assertTrue(toStringResult.contains("descricao=Bloco próximo à entrada principal"));
        // The estacionamento object will be included in the toString, but we don't check its exact format
        assertTrue(toStringResult.contains("estacionamento"));
    }
}
