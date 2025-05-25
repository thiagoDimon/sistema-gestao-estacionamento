package satc.estacionamento.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RelatorioReservaDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long tempoDecorridoTotal = 120L;
        String nomeCliente = "Cliente Teste";
        String modelo = "Modelo Teste";
        String placa = "ABC1234";
        Long valor = 50L;
        Long rank = 1L;

        // Act
        RelatorioReservaDTO dto = new RelatorioReservaDTO(tempoDecorridoTotal, nomeCliente, modelo, placa, valor, rank);

        // Assert
        assertEquals(tempoDecorridoTotal, dto.getTempoDecorridoTotal());
        assertEquals(nomeCliente, dto.getNomeCliente());
        assertEquals(modelo, dto.getModelo());
        assertEquals(placa, dto.getPlaca());
        assertEquals(valor, dto.getValor());
        assertEquals(rank, dto.getRank());
    }

    @Test
    void testSetters() {
        // Arrange
        RelatorioReservaDTO dto = new RelatorioReservaDTO();
        Long tempoDecorridoTotal = 120L;
        String nomeCliente = "Cliente Teste";
        String modelo = "Modelo Teste";
        String placa = "ABC1234";
        Long valor = 50L;
        Long rank = 1L;

        // Act
        dto.setTempoDecorridoTotal(tempoDecorridoTotal);
        dto.setNomeCliente(nomeCliente);
        dto.setModelo(modelo);
        dto.setPlaca(placa);
        dto.setValor(valor);
        dto.setRank(rank);

        // Assert
        assertEquals(tempoDecorridoTotal, dto.getTempoDecorridoTotal());
        assertEquals(nomeCliente, dto.getNomeCliente());
        assertEquals(modelo, dto.getModelo());
        assertEquals(placa, dto.getPlaca());
        assertEquals(valor, dto.getValor());
        assertEquals(rank, dto.getRank());
    }

    @Test
    void testBuilder() {
        // Arrange
        Long tempoDecorridoTotal = 120L;
        String nomeCliente = "Cliente Teste";
        String modelo = "Modelo Teste";
        String placa = "ABC1234";
        Long valor = 50L;
        Long rank = 1L;

        // Act
        RelatorioReservaDTO dto = RelatorioReservaDTO.builder()
                .tempoDecorridoTotal(tempoDecorridoTotal)
                .nomeCliente(nomeCliente)
                .modelo(modelo)
                .placa(placa)
                .valor(valor)
                .rank(rank)
                .build();

        // Assert
        assertEquals(tempoDecorridoTotal, dto.getTempoDecorridoTotal());
        assertEquals(nomeCliente, dto.getNomeCliente());
        assertEquals(modelo, dto.getModelo());
        assertEquals(placa, dto.getPlaca());
        assertEquals(valor, dto.getValor());
        assertEquals(rank, dto.getRank());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        RelatorioReservaDTO dto1 = new RelatorioReservaDTO(120L, "Cliente Teste", "Modelo Teste", "ABC1234", 50L, 1L);
        RelatorioReservaDTO dto2 = new RelatorioReservaDTO(120L, "Cliente Teste", "Modelo Teste", "ABC1234", 50L, 1L);
        RelatorioReservaDTO dto3 = new RelatorioReservaDTO();

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        // Arrange
        RelatorioReservaDTO dto = new RelatorioReservaDTO(120L, "Cliente Teste", "Modelo Teste", "ABC1234", 50L, 1L);

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("tempoDecorridoTotal=120"));
        assertTrue(toString.contains("nomeCliente=Cliente Teste"));
        assertTrue(toString.contains("modelo=Modelo Teste"));
        assertTrue(toString.contains("placa=ABC1234"));
        assertTrue(toString.contains("valor=50"));
        assertTrue(toString.contains("rank=1"));
    }
}
