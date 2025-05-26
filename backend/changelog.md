# Changelog

Este documento registra todas as alterações significativas realizadas no projeto **Sistema de Gestão de Estacionamento** a partir de 10/05/2025.

## [1.0] - 10/05/2025

### 🚀 Adicionado
- Desmembramento de métodos complexos em métodos menores e mais legíveis.
- Modularização da estrutura do backend para melhorar a organização de pastas e do código.

### 🛠️ Modificado
- Alterações nos `repositories` conforme a necessidade da lógica implementada.
- Refatoração da pasta `controllers` para lidar apenas com solicitações *REST*.
- Transferência das regras de negócio para a camada `service`.
- Renomeação de variáveis e métodos para melhorar a legibilidade.

---

## [1.1] - 18/05/2025

### 🚀 Adicionado
- Interface fluente para construção de objetos.

### 🛠️ Modificado
- `RankEstacionamentoController` incorporado ao `EstacionamentoController`.
- `TarifaReservaController` incorporado ao `TarifaController`.
- `VeiculosEstacionadosController` incorporado ao `VeiculosController`.
- Refatoração da pasta `getMapping`, movendo as rotas *REST* para a pasta `controllers`.
- Refatoração do método de **ranking** para melhorar a legibilidade.

### 🗑️ Removido
- Pasta `getMapping` removida.

---

## [1.2] - 20/05/2025 e 21/05/2025

### 🚀 Adicionado
- Configuração do *Checkstyle* para análise de formatação em arquivos Java.
- Integração de verificação automática de formatação ao solicitar um **pull request**.

---

## [1.3] - 23/05/2025

### 🚀 Adicionado
- Configuração do **Swagger** para documentação das APIs.
- Conteinerização do **SonarQube** para análise estática e verificação da qualidade do código.
- Documentação explicando como iniciar o **SonarQube** e realizar a análise do projeto.

---

## [1.4] - 26/05/2025

### 🚀 Adicionado
- Testes unitários.
- Integração do **JaCoCo** com o **SonarCloud** para análise de cobertura de testes.
