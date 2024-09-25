package com.utn.springboot.billeteravirtual.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("consoleLog")
public class ConsolaLog implements Log {
    private final Logger logger = LoggerFactory.getLogger(ConsolaLog.class);

    @Override
    public void registrarAccion(CodigoLog codigoLog) {
        logger.info("Acción: " + codigoLog.getCodigo() + " - " + codigoLog.getDescripcion());
    }

    @Override
    public <T> void registrarAccion(CodigoLog codigoLog, T object) {
        logger.info("Acción: " + codigoLog.getCodigo() + " - " + object.toString());
    }
}
