# 🤖 Autobots App

Uma aplicação Spring Boot para gerenciamento de usuários, empresas e vendas, com autenticação por login/senha e múltiplos níveis de acesso.

## ✅ Requisitos

- Java 17+
- Maven (se for compilar)
- app.jar já gerado (via `mvn clean package` ou disponível na raiz)

## 🚀 Como Executar

Para rodar o aplicativo:

```bash
java -jar app.jar
```

A aplicação documentada pode ser acessada em:

```
http://localhost:8080/swagger-ui/index.html#
```
## Acessando as rotas

- Ao realizar um login bem-sucedido o app irá retornar um json da seguinte maneira:
```json
{
  "access_token": "seu_token",
  "token_type": "Bearer"
}
```

- Agora copie o token para o clipboard e clique no botão "Authorize" no canto superior direito da página:

![imagem1](/doc/imgs/image-1.png)

- Em seguida apenas copie o token no campo "Value" e clique "Authorize", agora está logado e poderá testar as rotas e permissões!

![imagem2](/doc/imgs/image.png)

## 🔐 Login Padrão de Usuários

Use o endpoint:

```http
POST /auth/login
Content-Type: application/json
```

### 1. ADMIN
```json
{
  "tipo": "SENHA",
  "login": "ana.ribeiro",
  "senha": "SenhaAna123!"
}
```

### 2. GERENTE
```json
{
  "tipo": "SENHA",
  "login": "carlos.eduardo",
  "senha": "SenhaCadu456!"
}
```

### 3. VENDEDOR
```json
{
  "tipo": "SENHA",
  "login": "beatriz.silva",
  "senha": "SenhaBia789!"
}
```

### 4. CLIENTE
```json
{
  "tipo": "SENHA",
  "login": "daniel.costa",
  "senha": "SenhaDani321!"
}
```