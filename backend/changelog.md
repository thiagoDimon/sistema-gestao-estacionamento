# Changelog
Este documento registra todas as alterações significativas realizadas no projeto **Sistema Gestão Estacionamento** a partir da data 10/05/2025.

## [1] - 10/05/2025
### 🚀 Adicionado
- Modularização da estrutura do backend para melhorar a organização de pastas e código.

### 🛠️ Modificado
- Refatoração da pasta `controllers` para receber apenas solicitações *REST*.
- Refatoração das regras de negócios para a cada `service`.
- Refatoração de métodos complexos para tornar o código mais légivel e de fácil manutenção.
- Alteração do `repository` conforme necessidade da lógica implementada.
- Nomenclatura de variáveis e métodos para legibilidade.

## [2] - 18/05/2025
### 🛠️ Modificado
- Alterado o `RankEstacionamentoController` para dentro do `EstacionamentoController`.
- Alterado o `TarifaReservaController` para dentro do `TarifaController`.
- Alterado o `VeiculosEstacionadosController` para dentro do `VeiculosController`.
- Refatoração da pasta `getMapping` de solicitações *REST* para dentro da pasta `controllers`.
- Refatoração do método de **ranking** para ligibilidade.

### 🗑️ Removido
- Removido a pasta `getMapping`.

## [3] - 20/05/2025 e 21/05/2025
### 🚀 Adicionado
- Configuração do *checkstyle* para arquivos java.
- Integração de verificação de padrões utilizados ao solicitar um **pull request**.

## [4] - 23/05/2025
### 🚀 Adicionado
- Configuração do **swagger** para documentação das APIs.
- Conteinerização do **SonarQube** para análise e qualidade do código.
- Documentação de como iniciar o **SonarQube** e realizar a análise do projeto.

## [5] - 26/05/2025
