package satc.estacionamento.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoEstacionadoDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long idVeiculo = 1L;
        String placa = "ABC1234";
        String nomeCliente = "Cliente Teste";
        String statusReserva = "ATIVA";

        // Act
        VeiculoEstacionadoDTO dto = new VeiculoEstacionadoDTO(idVeiculo, placa, nomeCliente, statusReserva);

        // Assert
        assertEquals(idVeiculo, dto.getIdVeiculo());
        assertEquals(placa, dto.getPlaca());
        assertEquals(nomeCliente, dto.getNomeCliente());
        assertEquals(statusReserva, dto.getStatusReserva());
    }

    @Test
    void testSetters() {
        // Arrange
        VeiculoEstacionadoDTO dto = new VeiculoEstacionadoDTO();
        Long idVeiculo = 1L;
        String placa = "ABC1234";
        String nomeCliente = "Cliente Teste";
        String statusReserva = "ATIVA";

        // Act
        dto.setIdVeiculo(idVeiculo);
        dto.setPlaca(placa);
        dto.setNomeCliente(nomeCliente);
        dto.setStatusReserva(statusReserva);

        // Assert
        assertEquals(idVeiculo, dto.getIdVeiculo());
        assertEquals(placa, dto.getPlaca());
        assertEquals(nomeCliente, dto.getNomeCliente());
        assertEquals(statusReserva, dto.getStatusReserva());
    }

    @Test
    void testBuilder() {
        // Arrange
        Long idVeiculo = 1L;
        String placa = "ABC1234";
        String nomeCliente = "Cliente Teste";
        String statusReserva = "ATIVA";

        // Act
        VeiculoEstacionadoDTO dto = VeiculoEstacionadoDTO.builder()
                .idVeiculo(idVeiculo)
                .placa(placa)
                .nomeCliente(nomeCliente)
                .statusReserva(statusReserva)
                .build();

        // Assert
        assertEquals(idVeiculo, dto.getIdVeiculo());
        assertEquals(placa, dto.getPlaca());
        assertEquals(nomeCliente, dto.getNomeCliente());
        assertEquals(statusReserva, dto.getStatusReserva());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        VeiculoEstacionadoDTO dto1 = new VeiculoEstacionadoDTO(1L, "ABC1234", "Cliente Teste", "ATIVA");
        VeiculoEstacionadoDTO dto2 = new VeiculoEstacionadoDTO(1L, "ABC1234", "Cliente Teste", "ATIVA");
        VeiculoEstacionadoDTO dto3 = new VeiculoEstacionadoDTO();

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        // Arrange
        VeiculoEstacionadoDTO dto = new VeiculoEstacionadoDTO(1L, "ABC1234", "Cliente Teste", "ATIVA");

        // Act
        String toString = dto.toString();

        // Assert
        assertTrue(toString.contains("idVeiculo=1"));
        assertTrue(toString.contains("placa=ABC1234"));
        assertTrue(toString.contains("nomeCliente=Cliente Teste"));
        assertTrue(toString.contains("statusReserva=ATIVA"));
    }
}
