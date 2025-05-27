# 📦 Refatoração do Projeto

## 🛠️ Objetivo

Este documento tem como objetivo apresentar os **principais problemas detectados no código-fonte atual** e definir a **estratégia de refatoração** a ser aplicada. A refatoração visa melhorar a legibilidade, manutenibilidade e qualidade geral do projeto.

---

## 🏛️ Modelo de Dados e Visão Geral do Projeto

O projeto consiste em um sistema de gerenciamento de estacionamento. Ele permite administrar múltiplos estacionamentos, seus blocos, vagas, clientes, veículos, tipos de sócios, reservas e pagamentos associados.

A seguir, uma descrição detalhada de cada entidade do sistema:

### Entidades Principais

Todas as entidades principais herdam de `MasterEntity`, que fornece um campo `id` único gerado automaticamente.

* **`Estacionamento`**: Representa um estacionamento físico.
    * `nome`: Nome do estacionamento (ex: "Estacionamento Central").
    * `sigla`: Sigla identificadora (ex: "EST-CTR").
    * `vagasTotais`: Número total de vagas no estacionamento.
    * Relaciona-se com `Bloco` (um estacionamento pode ter vários blocos).


* **`Bloco`**: Representa uma seção ou setor dentro de um `Estacionamento`.
    * `estacionamento`: Referência ao `Estacionamento` ao qual o bloco pertence.
    * `nome`: Nome do bloco (ex: "Setor A").
    * `sigla`: Sigla identificadora do bloco (ex: "A").
    * `vagasTotais`: Número total de vagas neste bloco.
    * `descricao`: Descrição adicional sobre o bloco.
    * Relaciona-se com `Reserva` (um bloco pode ter várias reservas) e `Tarifa` (um bloco pode ter várias tarifas).


* **`Cliente`**: Representa um cliente do estacionamento.
    * `nome`: Nome completo do cliente.
    * `telefone`: Número de telefone.
    * `email`: Endereço de e-mail.
    * `endereco`: Endereço residencial.
    * `dataCadastro`: Data em que o cliente foi registrado.
    * Relaciona-se com `Veiculo` (um cliente pode ter vários veículos) e `Socio` (um cliente pode ser um sócio).


* **`Veiculo`**: Representa um veículo pertencente a um `Cliente`.
    * `cliente`: Referência ao `Cliente` proprietário do veículo.
    * `placa`: Placa do veículo (única e obrigatória).
    * `modelo`: Modelo do veículo (ex: "Fiat Palio").
    * `cor`: Cor do veículo.
    * `dataCadastro`: Data de cadastro do veículo no sistema.
    * Relaciona-se com `Reserva` (um veículo pode ter várias reservas).


* **`TipoSocio`**: Define os diferentes tipos de planos de sócio disponíveis.
    * `nome`: Nome do tipo de sócio (ex: "Mensalista Ouro").
    * `valor`: Valor ou custo associado a este tipo de plano.
    * Relaciona-se com `Socio` (um tipo de sócio pode ser atribuído a vários sócios).


* **`Socio`**: Representa um `Cliente` que aderiu a um plano de sócio.
    * `cliente`: Referência ao `Cliente` que é sócio (relação um-para-um).
    * `tipo`: Referência ao `TipoSocio` do plano ao qual o sócio aderiu.
    * `status`: Situação atual do plano do sócio (ex: "Ativo", "Inativo").
    * `dataInicio`: Data de início da vigência do plano.
    * `dataFim`: Data de término da vigência do plano.
    * Relaciona-se com `Pagamento` (um sócio pode realizar vários pagamentos, possivelmente de mensalidades ou reservas).


* **`Reserva`**: Gerencia as reservas de vagas de estacionamento.
    * `bloco`: Referência ao `Bloco` onde a vaga foi reservada.
    * `veiculo`: Referência ao `Veiculo` para o qual a reserva foi feita.
    * `dataInicio`: Data e hora de início da reserva.
    * `dataFim`: Data e hora de término da reserva.
    * `status`: Situação da reserva (ex: "Confirmada", "Cancelada", "Em Uso").
    * `pagamento`: Referência ao `Pagamento` associado a esta reserva (relação um-para-um).


* **`Pagamento`**: Registra as transações financeiras, tipicamente associadas a `Reserva`.
    * `reserva`: Referência à `Reserva` pela qual este pagamento foi feito (relação um-para-um).
    * `socio`: Referência ao `Socio` que efetuou o pagamento. Isso sugere que pagamentos de reservas podem ser feitos por sócios.
    * `valor`: Montante do pagamento.


* **`Tarifa`**: Define os preços para utilização das vagas, podendo variar por `Bloco`.
    * `bloco`: Referência ao `Bloco` ao qual esta tarifa se aplica.
    * `descricao`: Descrição da tarifa (ex: "Tarifa Hora Cheia", "Tarifa Diária").
    * `precoHora`: O valor cobrado por hora para esta tarifa.

Este modelo de dados permite uma gestão detalhada das operações de um sistema de estacionamento, desde a configuração física dos locais até o gerenciamento de clientes, suas associações e transações financeiras.

---

## 🚨 Principais Problemas Identificados

### Gerais

- Comentários desnecessários no código
- Comprimento de linha excedendo 120 colunas
- Métodos que executam múltiplas responsabilidades
- Métodos com mais de 20 linhas
- Falta de separação de responsabilidades entre controllers e services
- Ausência de métodos Java para encapsular lógica das queries SQL
- Inexistência de testes unitários
- Uso de nomes fora do padrão (abreviações, nomes mistos em português/inglês)
- Estruturas excessivas de if/else
- Falta de configuração de linter no projeto
- Ausência de tratamento de erros (exceptions)
- Uso de indentação via tab, em vez da convenção recomendada (espaços)
- Controllers acessam diretamente a lógica de dados, violando o princípio de separação de responsabilidades
- Injeções de dependência diretas de Jdbc e Repository nos controllers
- Métodos REST retornando diretamente o objeto em vez do código http e objeto no body
- Ausência de uso de @ControllerAdvice para captura e tratamento centralizado de exceções

---

## 🔧 Estratégia de Refatoração (Planejado)
- Criar arquivo de configuração do linter a ser utilizado
- Criar camada de Service para retirar lógica dos Controllers
- Remover regras de negócio das queries SQL e movê-las para métodos Java
- Reduzir e simplificar métodos longos
- Criar testes unitários com **JUnit**
- Padronizar nomes de variáveis e arquivos
- Substituir estruturas if/else por lógica mais limpa
- Adotar um único idioma em todo o código 🇧🇷
- Implementar tratamento global de exceções com @ControllerAdvice
- Padronizar a indentação para 2 espaços

---

## 🧪 Ferramentas Utilizadas

| Ferramenta            | Finalidade                                      |
|-----------------------|-------------------------------------------------|
| **JUnit**             | Testes unitários                                |
| **JaCoCo**            | Medição de cobertura de testes                  |
| **Checkstyle**        | Linter e verificação local de código            |
| **SonarQube**         | Análise estática de código                      |

---

## 🪄 Interface Fluente

- Será realizada a implementação de interface fluente em um método

---

## 🧹 Processo

- Criar branches específicas para cada etapa da refatoração
- Registrar todas as mudanças realizadas no changelog.md
- Refatorações devem ser feitas de forma incremental e versionada

---

## 📚 Documentação Adicional

Consulte os seguintes documentos para mais informações:

- **[Inicialização e Execução do Projeto](./initialize.md)**: Instruções sobre como configurar e rodar o projeto.
- **[Changelog](./changelog.md)**: Registro detalhado de todas as alterações realizadas no projeto.
- **[Configuração do SonarQube Local](./sonarqube.md)**: Guia para configurar e executar o SonarQube localmente para análise de código.
