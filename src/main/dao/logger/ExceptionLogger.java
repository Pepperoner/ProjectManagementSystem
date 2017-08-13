package dao.logger;

import java.io.*;
import java.util.Date;

public class ExceptionLogger {

    public static void initLogger(String exMessage) {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src\\resources\\LoggerFiles\\Logger.txt"), true)))) {
            Date nowDateTime = new Date();
            bw.write(nowDateTime.toString() + "\n" + exMessage + "\n");
            bw.newLine();
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
