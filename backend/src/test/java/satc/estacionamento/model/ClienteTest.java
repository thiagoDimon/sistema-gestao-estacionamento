package satc.estacionamento.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Cliente cliente = new Cliente();
        Long id = 1L;
        String nome = "João Silva";
        String telefone = "48999887766";
        String email = "joao@example.com";
        String endereco = "Rua das Flores, 123";
        LocalDate dataCadastro = LocalDate.now();

        // Act
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setEndereco(endereco);
        cliente.setDataCadastro(dataCadastro);

        // Assert
        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(telefone, cliente.getTelefone());
        assertEquals(email, cliente.getEmail());
        assertEquals(endereco, cliente.getEndereco());
        assertEquals(dataCadastro, cliente.getDataCadastro());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Cliente cliente = new Cliente();

        // Assert
        assertNull(cliente.getId());
        assertNull(cliente.getNome());
        assertNull(cliente.getTelefone());
        assertNull(cliente.getEmail());
        assertNull(cliente.getEndereco());
        assertNull(cliente.getDataCadastro());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String nome = "João Silva";
        String telefone = "48999887766";
        String email = "joao@example.com";
        String endereco = "Rua das Flores, 123";
        LocalDate dataCadastro = LocalDate.now();

        // Act
        Cliente cliente = new Cliente(nome, telefone, email, endereco, dataCadastro);
        cliente.setId(id);

        // Assert
        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(telefone, cliente.getTelefone());
        assertEquals(email, cliente.getEmail());
        assertEquals(endereco, cliente.getEndereco());
        assertEquals(dataCadastro, cliente.getDataCadastro());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("João Silva");
        cliente1.setTelefone("48999887766");
        cliente1.setEmail("joao@example.com");
        cliente1.setEndereco("Rua das Flores, 123");
        cliente1.setDataCadastro(LocalDate.of(2023, 1, 1));

        Cliente cliente2 = new Cliente();
        cliente2.setId(1L);
        cliente2.setNome("João Silva");
        cliente2.setTelefone("48999887766");
        cliente2.setEmail("joao@example.com");
        cliente2.setEndereco("Rua das Flores, 123");
        cliente2.setDataCadastro(LocalDate.of(2023, 1, 1));

        Cliente cliente3 = new Cliente();
        cliente3.setId(2L);
        cliente3.setNome("Maria Santos");
        cliente3.setTelefone("48988776655");
        cliente3.setEmail("maria@example.com");
        cliente3.setEndereco("Av. Principal, 456");
        cliente3.setDataCadastro(LocalDate.of(2023, 2, 1));

        // Assert
        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
        assertNotEquals(cliente1, cliente3);
        assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setTelefone("48999887766");
        cliente.setEmail("joao@example.com");
        cliente.setEndereco("Rua das Flores, 123");
        cliente.setDataCadastro(LocalDate.of(2023, 1, 1));

        // Act
        String toStringResult = cliente.toString();

        // Assert
        assertTrue(toStringResult.contains("nome=João Silva"));
        assertTrue(toStringResult.contains("telefone=48999887766"));
        assertTrue(toStringResult.contains("email=joao@example.com"));
        assertTrue(toStringResult.contains("endereco=Rua das Flores, 123"));
        assertTrue(toStringResult.contains("dataCadastro=2023-01-01"));
    }
}
