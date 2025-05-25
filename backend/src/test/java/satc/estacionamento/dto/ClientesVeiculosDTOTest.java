package satc.estacionamento.dto;

import org.junit.jupiter.api.Test;
import satc.estacionamento.model.Cliente;
import satc.estacionamento.model.Veiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientesVeiculosDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        Veiculo veiculo1 = new Veiculo();
        veiculo1.setId(1L);
        veiculo1.setPlaca("ABC1234");

        Veiculo veiculo2 = new Veiculo();
        veiculo2.setId(2L);
        veiculo2.setPlaca("DEF5678");

        List<Veiculo> veiculos = Arrays.asList(veiculo1, veiculo2);

        // Act
        ClientesVeiculosDTO dto = new ClientesVeiculosDTO(cliente, veiculos);

        // Assert
        assertEquals(cliente, dto.getCliente());
        assertEquals(veiculos, dto.getVeiculos());
        assertEquals(2, dto.getVeiculos().size());
    }

    @Test
    void testSetters() {
        // Arrange
        ClientesVeiculosDTO dto = new ClientesVeiculosDTO();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        List<Veiculo> veiculos = new ArrayList<>();
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");
        veiculos.add(veiculo);

        // Act
        dto.setCliente(cliente);
        dto.setVeiculos(veiculos);

        // Assert
        assertEquals(cliente, dto.getCliente());
        assertEquals(veiculos, dto.getVeiculos());
    }

    @Test
    void testBuilder() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        List<Veiculo> veiculos = new ArrayList<>();
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");
        veiculos.add(veiculo);

        // Act
        ClientesVeiculosDTO dto = ClientesVeiculosDTO.builder()
                .cliente(cliente)
                .veiculos(veiculos)
                .build();

        // Assert
        assertEquals(cliente, dto.getCliente());
        assertEquals(veiculos, dto.getVeiculos());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        List<Veiculo> veiculos = new ArrayList<>();
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setPlaca("ABC1234");
        veiculos.add(veiculo);

        ClientesVeiculosDTO dto1 = new ClientesVeiculosDTO(cliente, veiculos);
        ClientesVeiculosDTO dto2 = new ClientesVeiculosDTO(cliente, veiculos);
        ClientesVeiculosDTO dto3 = new ClientesVeiculosDTO();

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }
}
