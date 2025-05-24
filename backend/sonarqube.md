# 📊 Como acessar e configurar o SonarQube

Este guia simples explica como acessar o SonarQube instalado localmente e configurar a análise do projeto **satc-estacionamento**.

---

## 1️⃣ Acessar o SonarQube

- **1.1** Abra seu navegador e vá para:
   ```
   http://localhost:9000
   ```

- **1.2**. A seguir, realize o login com as seguintes credenciais:
   - **Usuário:** `admin`
   - **Senha:** `admin`

- **1.3**. Altere a senha quando for solicitado. Utilize qualquer senha que atenda aos requisitos mínimos de segurança.

---

## 2️⃣ Criar o projeto no SonarQube para realização do Scanner

- **2.1** Com a guia **"Projects"** aberta, clique em **"Create a local project"**.

- **2.2** No campo **"Project display name"**, digite:
   ```
   satc-estacionamento
   ```
  e clique em em **"Next"**.

- **2.4** Na sequência, selecione a opção:
   ```
   Define a specific setting for this project
   ```

- **2.5** Consequentemente, escolha também a opção:
   ```
   Reference branch
   ```
   e clique em **"Create project"**.

---

## 3️⃣ Gerar o token de acesso

- **3.1** Quando perguntado sobre o método de análise, escolha:
   ```
   Locally
   ```

- **3.2** Clique em **"Generate"** para criar um token.

- **3.3** Copie o token gerado e salve.

---

## 4️⃣ Executar o comando para analisar o projeto

- **4.1** No terminal, na pasta do projeto, execute o comando abaixo. **Lembre-se:** troque `SEU_TOKEN_AQUI` pelo token gerado na etapa anterior.
```bash
./mvnw clean verify sonar:sonar \
  -Dsonar.projectKey=satc-estacionamento \
  -Dsonar.projectName='satc-estacionamento' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=SEU_TOKEN_AQUI
```

---

✅ **Pronto!** A análise do projeto será enviada automaticamente para o SonarQube e você poderá visualizar os resultados acessando o projeto na interface web.
