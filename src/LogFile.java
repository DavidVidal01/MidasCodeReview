
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

public class LogFile {

    private static String path;
    private static LogFile fileHandler = null;


    //LOG POR ARCHIVO

    public static FileHandler getFileHandler(){
        path = LogDB.getDbParams().get("logFileFolder") + "/logFile.txt";
        File logFile = new File(path);
        try{
            if (!logFile.exists()) {
                logFile.createNewFile();// devuelve true si File no existe y fue creado, y false si ya esta creado anteriormente
            }
            FileHandler fh = new FileHandler(path);
            return fh;

        }catch (IOException | SecurityException e){
            throw new RuntimeException("Error creating file",e);
        }
    }


    //FileHandler fh = new FileHandler(Conexion.getDbParams().get("logFileFolder") + "/logFile.txt");
}