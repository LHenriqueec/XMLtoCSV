package util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DAOUtils {

    private static Path dir = Paths.get("C:\\Users\\LuizS\\Desktop\\Backup Flag\\Notas Compra\\Backup");


    public static DirectoryStream<Path> getDirectoryStream() {
        DirectoryStream<Path> directory = null;
        try {
            directory = Files.newDirectoryStream(dir, "*.xml");
        } catch(IOException e) {
            e.printStackTrace();
        }
        return directory;
    }
}
