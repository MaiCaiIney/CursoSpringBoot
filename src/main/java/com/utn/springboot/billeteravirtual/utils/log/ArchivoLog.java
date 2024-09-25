package com.utn.springboot.billeteravirtual.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("fileLog")
public class ArchivoLog implements Log {
    private final Logger logger = LoggerFactory.getLogger(ConsolaLog.class);

    @Override
    public void registrarAccion(CodigoLog codigoLog) {
        String logMessage = "FileLog: Acción: " + codigoLog.getCodigo() + " - " + codigoLog.getDescripcion();
        escribirEnArchivo(logMessage);
    }

    @Override
    public <T> void registrarAccion(CodigoLog codigoLog, T object) {
        String logMessage = "FileLog: Acción: " + codigoLog.getCodigo() + " - " + codigoLog.getDescripcion() + " - " + object.toString();
        escribirEnArchivo(logMessage);
    }

    private void escribirEnArchivo(String mensaje) {
        logger.error(mensaje);
    }
}
