import java.text.DateFormat;
import java.util.Date;
import java.util.logging.*;

public class JobLogger {
    private static boolean logToFile;
    private static boolean logToConsole;
    private static boolean logToDatabase;
    private static boolean logMessage;
    private static boolean logWarning;
    private static boolean logError;

    //private boolean initialized; se puede borrar ya que no se usa en ningun momento.
/*
    David Emmanuel Vidal
    Dejo aqui mi revision de codigo, ya modificada, me costo al principio el codigo, luego de un par de video de logs en java pude entenderlo,
    sin embargo todavia tengo varias dudas acerca de como cambiar completamente todo, ya que veo bastante raro el uso de tantos booleans,
    viendo en internet encontre otras formas de realizarlo pero no queria hacer un copy paste total de un proyecto. Aunque lo vi bastante completo
    https://github.com/raulreds/job-logger/tree/master/src/main/java/com/belatrix/logger/joblogger (link del Repo del codigo del cual intente guiarme un poco)
    El proyecto le estuve refactorizando con Diego Di Leo.
    Desde ya muchas gracias.
 */
    private static Logger logger;

    public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
                     boolean logMessageParam, boolean logWarningParam, boolean logErrorParam) {
        logger = Logger.getLogger("MyLog");
        logError = logErrorParam;
        logMessage = logMessageParam;
        logWarning = logWarningParam;
        logToDatabase = logToDatabaseParam;
        logToFile = logToFileParam;
        logToConsole = logToConsoleParam;
    }


    public static void LogMessage(String messageText, boolean message, boolean warning, boolean error,LogTo logTo) throws Exception {
            // se quita el trim ya que no es necesario

            if (messageText == null || messageText.length() == 0) {
            return;
            }

            if (!logToConsole && !logToFile && !logToDatabase) {
                throw new Exception("Invalid configuration");
            }

            if(!logError || !error ){
                throw new Exception("Error must be specified");

            }
            if(!logMessage || !message ){
            throw new Exception("Message must be specified");

            } if(!logWarning || !warning ){
            throw new Exception("Warning must be specified");

            }


            String logdate = null;

            if (error && logError) {
                logdate = logdate + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
            }

            if (warning && logWarning) {
                logdate = logdate + "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
            }

            if (message && logMessage) {
                logdate = logdate + "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
            }

        switch (logTo) {
            case logToFile -> {
                logger.addHandler(LogFile.getFileHandler());
                logger.log(Level.INFO, messageText);
            }
            case logToConsole -> {
                logger.addHandler(LogConsole.ch);
                logger.log(Level.INFO, messageText);
            }
            case logToDatabase -> {
                int flag= 0;

                if (message && logMessage) {
                    flag = 1;
                }
                if (error && logError) {
                    flag = 2;
                }
                if (warning && logWarning) {
                    flag = 3;
                }
                LogDB.getStmt().executeUpdate("insert into Log_Values('" + message + "', " + flag + ")");
            }
        }
    }
}

