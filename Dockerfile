# Estágio 1: Build (Compilação do projeto)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copia o arquivo de dependências e baixa o que for necessário
COPY pom.xml .
# Copia o código fonte
COPY src ./src
# Compila o projeto ignorando os testes para ser mais rápido
RUN mvn clean package -DskipTests

# Estágio 2: Execução (Imagem final super leve)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia apenas o arquivo .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar
# Libera a porta 8080, padrão do Spring Boot
EXPOSE 8080
# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]