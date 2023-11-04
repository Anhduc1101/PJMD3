package ra.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getCurrentDateTime() {
        Date currentDateTime = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateTimeFormat.format(currentDateTime);
    }
}
