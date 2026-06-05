package com.filipe_bento.agendamento_barbearia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupLogger.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("=========================================================");
        log.info("🚀 APLICAÇÃO INICIADA COM SUCESSO!");
        log.info("📚 Swagger UI: http://localhost:8080/swagger-ui/index.html");
        log.info("🗄️ H2 Console:  http://localhost:8080/h2-console");
        log.info("=========================================================");
    }
}