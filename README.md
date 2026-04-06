# QR Code Generator API (AWS + Docker)

API REST desenvolvida em **Java com Spring Boot** para geração de QR Codes e upload automático para o **Amazon S3**, utilizando boas práticas de arquitetura backend e containerização com Docker.

---

##  Tecnologias utilizadas

*  Java 21
*  Spring Boot
*  Docker
*  Amazon S3 (AWS)
*  Maven

---

##  Funcionalidades

*  Geração de QR Code a partir de texto ou URL
*  Upload automático da imagem para o S3
*  Retorno da URL pública do QR Code
*  API REST para integração com frontend

---

##  Arquitetura

O projeto foi estruturado utilizando conceitos de:

* Clean Architecture
* Arquitetura Hexagonal (Ports & Adapters)

### Estrutura:

* **Controller** → Camada de entrada (API REST)
* **Service** → Regras de negócio
* **Port** → Interface de comunicação
* **Adapter (S3)** → Integração com AWS

---

##  Como executar o projeto

###  1. Build da aplicação

```bash
mvn clean package
```

---

###  2. Build da imagem Docker

```bash
docker build -t qrcode-generator .
```

---

###  3. Executar container

```bash
docker run -p PORTA \
-e AWS_REGION=SEU_BUCKET_REGION \
-e AWS_BUCKET_NAME=SEU_BUCKET \
-e AWS_ACCESS_KEY_ID=SUA_ACCESS_KEY \
-e AWS_SECRET_ACCESS_KEY=SUA_SECRET_KEY \
qrcode-generator
```

---

##  Testando a API

###  Endpoint

```http
POST /qrcode
```

###  Request (JSON)

```json
{
  "text": "https://google.com"
}
```

###  Response (exemplo)

```json
{
  "url": "https://seu-bucket.s3.us-east-2.amazonaws.com/qrcode.png"
}
```

---

## Integração com AWS

* Upload de arquivos no Amazon S3
* Uso de variáveis de ambiente para segurança
* Configuração de região e bucket


##  Aprendizados

Este projeto foi desenvolvido com foco em prática real de mercado:

* Integração com serviços da AWS (S3)
* Dockerização de aplicações Java
* Debug de problemas reais (porta, container, AWS, autenticação)
* Organização de código com arquitetura limpa

---

## Melhorias futuras

*  Autenticação com JWT
*  Deploy em AWS (EC2 / ECS)
*  Cache de QR Codes
*  Processamento assíncrono
