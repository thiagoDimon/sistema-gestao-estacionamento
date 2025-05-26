package satc.estacionamento.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class VeiculoTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Veiculo veiculo = new Veiculo();
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        String placa = "ABC1234";
        String modelo = "Gol";
        String cor = "Preto";
        LocalDate dataCadastro = LocalDate.now();

        // Act
        veiculo.setId(id);
        veiculo.setCliente(cliente);
        veiculo.setPlaca(placa);
        veiculo.setModelo(modelo);
        veiculo.setCor(cor);
        veiculo.setDataCadastro(dataCadastro);

        // Assert
        assertEquals(id, veiculo.getId());
        assertEquals(cliente, veiculo.getCliente());
        assertEquals(placa, veiculo.getPlaca());
        assertEquals(modelo, veiculo.getModelo());
        assertEquals(cor, veiculo.getCor());
        assertEquals(dataCadastro, veiculo.getDataCadastro());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Veiculo veiculo = new Veiculo();

        // Assert
        assertNull(veiculo.getId());
        assertNull(veiculo.getCliente());
        assertNull(veiculo.getPlaca());
        assertNull(veiculo.getModelo());
        assertNull(veiculo.getCor());
        assertNull(veiculo.getDataCadastro());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        String placa = "ABC1234";
        String modelo = "Gol";
        String cor = "Preto";
        LocalDate dataCadastro = LocalDate.now();

        // Act
        Veiculo veiculo = new Veiculo(cliente, placa, modelo, cor, dataCadastro);
        veiculo.setId(id);

        // Assert
        assertEquals(id, veiculo.getId());
        assertEquals(cliente, veiculo.getCliente());
        assertEquals(placa, veiculo.getPlaca());
        assertEquals(modelo, veiculo.getModelo());
        assertEquals(cor, veiculo.getCor());
        assertEquals(dataCadastro, veiculo.getDataCadastro());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");

        Veiculo veiculo1 = new Veiculo();
        veiculo1.setId(1L);
        veiculo1.setCliente(cliente);
        veiculo1.setPlaca("ABC1234");
        veiculo1.setModelo("Gol");
        veiculo1.setCor("Preto");
        veiculo1.setDataCadastro(LocalDate.of(2023, 1, 1));

        Veiculo veiculo2 = new Veiculo();
        veiculo2.setId(1L);
        veiculo2.setCliente(cliente);
        veiculo2.setPlaca("ABC1234");
        veiculo2.setModelo("Gol");
        veiculo2.setCor("Preto");
        veiculo2.setDataCadastro(LocalDate.of(2023, 1, 1));

        Veiculo veiculo3 = new Veiculo();
        veiculo3.setId(2L);
        veiculo3.setCliente(cliente);
        veiculo3.setPlaca("DEF5678");
        veiculo3.setModelo("Palio");
        veiculo3.setCor("Branco");
        veiculo3.setDataCadastro(LocalDate.of(2023, 2, 1));

        // Assert
        assertEquals(veiculo1, veiculo2);
        assertEquals(veiculo1.hashCode(), veiculo2.hashCode());
        assertNotEquals(veiculo1, veiculo3);
        assertNotEquals(veiculo1.hashCode(), veiculo3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setCliente(cliente);
        veiculo.setPlaca("ABC1234");
        veiculo.setModelo("Gol");
        veiculo.setCor("Preto");
        veiculo.setDataCadastro(LocalDate.of(2023, 1, 1));

        // Act
        String toStringResult = veiculo.toString();

        // Assert
        assertTrue(toStringResult.contains("placa=ABC1234"));
        assertTrue(toStringResult.contains("modelo=Gol"));
        assertTrue(toStringResult.contains("cor=Preto"));
        assertTrue(toStringResult.contains("dataCadastro=2023-01-01"));
        // The cliente object will be included in the toString, but we don't check its exact format
        assertTrue(toStringResult.contains("cliente"));
    }
}
