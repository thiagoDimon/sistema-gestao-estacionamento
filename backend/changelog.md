# Changelog
Este documento registra todas as alterações significativas realizadas no projeto **Sistema Gestão Estacionamento** a partir da data 10/05/2025.

## [1.0] - 10/05/2025
### 🚀 Adicionado
- Desmebração de métodos complexos em métodos menores e legíveis.
- Modularização da estrutura do backend para melhorar a organização de pastas e código.

### 🛠️ Modificado
- Alteração do `repository` conforme necessidade da lógica implementada.
- Refatoração da pasta `controllers` para receber apenas solicitações *REST*.
- Refatoração das regras de negócios para a camada `service`.
- Nomenclatura de variáveis e métodos para legibilidade.

## [1.1] - 18/05/2025
### 🚀 Adicionado
- Interface fluente para construção de objetos.

### 🛠️ Modificado
- Alterado o `RankEstacionamentoController` para dentro do `EstacionamentoController`.
- Alterado o `TarifaReservaController` para dentro do `TarifaController`.
- Alterado o `VeiculosEstacionadosController` para dentro do `VeiculosController`.
- Refatoração da pasta `getMapping` de solicitações *REST* para dentro da pasta `controllers`.
- Refatoração do método de **ranking** para ligibilidade.

### 🗑️ Removido
- Removido a pasta `getMapping`.

## [1.2] - 20/05/2025 e 21/05/2025
### 🚀 Adicionado
- Configuração do *checkstyle* para arquivos java.
- Integração de verificação de padrões utilizados ao solicitar um **pull request**.

## [1.3] - 23/05/2025
### 🚀 Adicionado
- Configuração do **swagger** para documentação das APIs.
- Conteinerização do **SonarQube** para análise e qualidade do código.
- Documentação de como iniciar o **SonarQube** e realizar a análise do projeto.

## [1.4] - 26/05/2025
### 🚀 Adicionado
- Testes unitários
- Integração jacoco com sonarcloud
