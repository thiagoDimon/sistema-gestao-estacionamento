# 📘 Documentação do Projeto - Estacionamento

## 🧱 Tecnologias Utilizadas

- Java 17
- Spring Boot 2.7
- PostgreSQL (via Docker Compose)
- Maven
- Checkstyle
- JUnit (testes unitários)
- SonarCloud (opcional: SonarQube local via Docker)
- Swagger UI (documentação de API)

---

## 🚀 Pré-requisitos

- *Java 17 SDK*
- *Maven 3.8+*
- *Docker e Docker Compose*
- IDE (ex: *IntelliJ IDEA* ou *Eclipse*)

---

## 🐘 Subir Banco de Dados e SonarQube (opcional)

1. Acesse a pasta `docker/`:
   ```bash
   cd docker/
   ```

2. Execute o Docker Compose:
   ```bash
   docker-compose up -d
   ```

> 💡 O SonarQube local é opcional. Se você já utiliza o SonarCloud, não precisa subir o serviço `sonarqube`.

### Banco de Dados PostgreSQL

- **Host:** `localhost`
- **Porta:** `5432`
- **Usuário:** `postgres`
- **Senha:** `postgres`
- **Banco:** `estacionamento`

### SonarQube Local (opcional)

- **URL:** http://localhost:9000
- **Banco:** Conectado via `postgres-db-sonarqube`

---

## 💻 Rodando pela IDE

### IntelliJ IDEA

1. Importe o projeto como *Maven Project*.
2. Selecione o SDK *Java 17*.
3. Execute a classe principal `Application.java`.
4. Acesse a aplicação: *http://localhost:8080*

### Eclipse

1. Importe como *Existing Maven Project*.
2. Configure o JDK 17.
3. Execute `Application.java`.

---

## 🛠️ Rodando pela Linha de Comando

### Build do Projeto

```bash
mvn clean install
```

### Rodar a Aplicação

```bash
mvn spring-boot:run
```

---

## ✅ Rodando Testes Unitários

```bash
mvn test
```

---

## ✔️ Checkstyle

Verifica se o código está conforme as regras de estilo:

```bash
mvn checkstyle:check
```

> Certifique-se de que o `checkstyle.xml` está corretamente configurado no `pom.xml` ou em `config/checkstyle/checkstyle.xml`.

---

## 📊 SonarCloud (ou SonarQube Local)

### Rodando análise com SonarCloud

Verifique se seu `pom.xml` está configurado com o plugin do SonarScanner e as propriedades do SonarCloud.

```bash
mvn verify sonar:sonar \
  -Dsonar.projectKey=SEU_PROJECT_KEY \
  -Dsonar.organization=SEU_ORG \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=SEU_TOKEN
```

### Rodando com SonarQube Local (opcional)

1. Suba o SonarQube local via Docker.
2. Configure o `sonar.properties` ou `pom.xml` apontando para o `localhost:9000`.

---

## 📁 Estrutura do Projeto

```text
.
src/
├── main/
│   ├── java/
│   │   └── satc.estacionamento/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── services/
│   │       ├── CorsConfiguration.java
│   │       └── Estacionamento.java
│   └── resources/
│       ├── application.properties
│       ├── banner.txt
│       └── sonar-project.properties
├── test/
│   └── java/
│       └── satc.estacionamento/
│           ├── controller/
│           ├── dto/
│           ├── model/
│           └── service/
docker/
└── docker-compose.yml
```

---

## 📚 Swagger - Documentação da API

Após iniciar a aplicação, acesse a documentação interativa da API via Swagger:

🔗 **Swagger UI:** [http://localhost:5555/swagger-ui.html](http://localhost:5555/swagger-ui.html)

> Certifique-se de que a porta `5555` esteja corretamente configurada no `application.properties` com:
>
> ```properties
> server.port=5555
> ```

Caso esteja usando outra porta (ex: 8080), atualize o endereço do Swagger para refletir a porta configurada.

---

## 📦 Comando Completo de Inicialização

```bash
# Subir serviços
cd docker/ && docker-compose up -d && cd ..

# Rodar testes e checkstyle
mvn clean verify checkstyle:check

# Rodar aplicação
mvn spring-boot:run
```

---

## 📝 Observações

- A porta `5432` do PostgreSQL deve estar livre.
- SonarQube local roda na porta `9000`, e PostgreSQL (do Sonar) na `5433`.
- SonarCloud exige configuração de token no Maven ou ambiente.
- Acesse a interface do Swagger em: [http://localhost:5555/swagger-ui.html](http://localhost:5555/swagger-ui.html)

---

