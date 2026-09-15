<div align="center">

# 𓍯 Hangman 𖨆

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Architecture](https://img.shields.io/badge/Architecture-Clean-blue?style=for-the-badge) ![Patterns](https://img.shields.io/badge/Design%20Patterns-GoF-success?style=for-the-badge) ![License](https://img.shields.io/badge/License-Academic-lightgrey?style=for-the-badge)

> Uma implementação do clássico **Jogo da Forca** desenvolvida em **Java**, aplicando **Clean Architecture**, **Domain-Driven Design (DDD)** e **Design Patterns (GoF)**.

</div>

---

## 🎯 Sobre o Projeto

Este projeto consiste em uma implementação do **Jogo da Forca** para terminal, desenvolvida com foco na aplicação de boas práticas de engenharia de software.

A arquitetura foi projetada para manter **baixo acoplamento** e **alta coesão**, permitindo substituir implementações de persistência (memória ou banco relacional) e representação gráfica (texto ou imagem) sem alterar as regras de negócio.

Todo o projeto segue o princípio **Open/Closed Principle (OCP)**, mantendo o domínio independente de detalhes de infraestrutura.

---

## 🛠️ Tecnologias

| Tecnologia | Descrição |
|------------|-----------|
| Java | Linguagem principal |
| Orientação a Objetos | Paradigma utilizado |
| Lombok | Redução de código boilerplate |
| VS Code / Eclipse | Ambiente de desenvolvimento |

---

## 📐 Arquitetura

O sistema foi organizado nas seguintes camadas:

- **Domain**
- **Repositories**
- **Factories**
- **Application Services**
- **Application Bootstrap**

Essa organização isola completamente as regras de negócio das implementações de infraestrutura.

---

## 🧩 Design Patterns

Os seguintes padrões de projeto do **GoF (Gang of Four)** foram implementados:

| Padrão | Utilizado em | Aplicação |
|---------|--------------|-----------|
| **Abstract Factory** | `ElementoGraficoFactory` | Criação das famílias de elementos gráficos (Texto/Imagem). |
| **Facade** | `PalavraAppService` e `RodadaAppService` | Atuam como portas de entrada seguras para o sistema, orquestrando Repositórios e Fábricas para que os controladores da interface gráfica não precisem conhecer a complexidade interna do domínio. |
| **Flyweight** | `LetraFactoryImpl` | Otimização do uso de memória. Letras (como 'A', 'B' ou caracteres encobertos '_') são instanciadas apenas uma vez e cacheadas em um *pool* para reaproveitamento durante a renderização das palavras. |
| **Singleton / Parameterized Singleton** | Factories, Repositories e `Aplicacao` | Garantem instâncias únicas para Fábricas e Repositórios, controlando o ciclo de vida dos objetos em memória. |
| **Composition Root** (Injeção de Dependências) | `Aplicacao` | Centraliza a configuração das dependências, conectando implementações concretas às abstrações utilizadas pelo domínio. |

---

## 💻 Como Executar

### Pré-requisitos

- Java JDK instalado

### Passos

```bash
git clone <url-do-repositorio>

cd jogo-da-forca/backend
```

Abra o projeto em sua IDE preferida e execute:

```
src/Main.java
```

O jogo iniciará utilizando dados mockados previamente cadastrados.

**Jogador padrão**

```
Nog
```

**Tema padrão**

```
Tecnologia
```

---

## 📂 Estrutura do Projeto

```text
jogo-da-forca
└───backend
    ├───bin/ (Arquivos compilados .class)
    ├───lib/
    │       lombok.jar
    └───src/
        │   Main.java
        ├───bancodepalavras/ (Domínio relacionado ao dicionário do jogo)
        │   └───dominio/
        │       ├───letra/
        │       ├───palavra/
        │       └───tema/
        ├───dominio/ (Classes base de Domínio)
        ├───factory/ (Interfaces e Fábricas genéricas)
        ├───jogoforca/ (Regras e Orquestração principal da partida)
        │   ├───aplicacao/
        │   ├───dominio/
        │   │   ├───boneco/
        │   │   ├───jogador/
        │   │   └───rodada/
        │   ├───embdr/ (Repositórios Relacionais)
        │   ├───emmemoria/ (Repositórios em Memória/Mock)
        │   ├───imagem/ (Gráficos baseados em Imagens)
        │   └───texto/ (Gráficos baseados em Texto)
        └───repository/ (Contratos e Exceções de banco de dados)
```

---

<div align="center">

**Mateus Carvalho Costa Nogueira**

Bacharel em Sistemas de Informação - Instituto Federal Fluminense

</div>