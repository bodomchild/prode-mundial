FROM maven:amazoncorretto
WORKDIR /prode-mundial
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run