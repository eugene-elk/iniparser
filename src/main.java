import IniParser.ConfigMap;
import IniParser.IniException;
import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) {

        try {
            ConfigMap configMap = ConfigMap.fromFile(new File("resources/config.ini"));

            int intvalue = configMap.getIntegerValue("NCMD", "TidPacketVersionForTidControlCommand");
            System.out.println(intvalue);

            double doublevalue = configMap.getDoubleValue("ADC_DEV", "SampleRate");
            System.out.println(doublevalue);

            String stringvalue = configMap.getValue("COMMON", "DiskCachePath");
            System.out.println(stringvalue);

        }
        catch (IOException | IniException e) {
            if (e instanceof IOException)
                System.out.println("Config file is empty or not found");
            else
                System.out.println(e.getMessage());
        }
    }
}