package IniParser;

import java.io.*;
import java.util.*;

public class ConfigMap {

    private final Map<String, Map<String, String>> config = new HashMap<>();

    public static ConfigMap fromFile(File file) throws IOException {
        ConfigMap parsedConfig = parse(file);
        if (parsedConfig == null) {
            throw new IOException("Wrong configuration file");
        }
        return parsedConfig;
    }

    private static ConfigMap parse(File file) {
        ConfigMap configuration = new ConfigMap();
        try {
            Scanner scanner = new Scanner(file);
            String section_name = null;
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                // удаление коммента
                int comment_pos = line.indexOf(';');
                if (comment_pos != -1){
                    line = line.substring(0, comment_pos);
                }

                if (line.length() == 0) {
                    continue;
                }

                int open_bracket_pos = line.indexOf('[');
                if (open_bracket_pos != -1) {
                    // добавление новой секции
                    int close_bracket_pos = line.indexOf(']');
                    section_name = line.substring(open_bracket_pos + 1, close_bracket_pos);
                    configuration.addSection(section_name);
                } else {
                    int equal_pos = line.indexOf('=');
                    if (equal_pos != -1) {
                        // добавление нового параметра
                        String parameter_name = line.substring(0, equal_pos - 1).trim(); // выделение имени с удалением пробелов
                        String parameter_value = line.substring(equal_pos + 1).trim();  // выделение значения
                        configuration.addParameter(section_name, parameter_name, parameter_value);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return configuration;
    }

    private void addSection(String s_name){
        HashMap<String, String> section_map = new HashMap<>();
        config.put(s_name, section_map);
    }

    private void addParameter(String s_name, String p_name, String p_value){
        config.get(s_name).put(p_name, p_value);
    }

    public String getValue(String s_name, String p_name) throws IniException{
        try {
            return config.get(s_name).get(p_name);
        }
        catch (NullPointerException e) {
            throw new IniException("There is no section: " + s_name, e);
        }
    }

    // ClassName
    // methodName
    // variableName
    // CONST_NAME
    public int getIntegerValue(String s_name, String p_name) throws IniException {
        try {
            return Integer.parseInt(this.getValue(s_name, p_name));
        }
        catch (NumberFormatException e) {
            throw new IniException(String.format("Wrong parameter (%s.%s)", s_name, p_name), e);
        }
    }

    public double getDoubleValue(String s_name, String p_name) throws IniException{
        try {
            return Double.parseDouble(this.getValue(s_name, p_name));
        }
        catch (NumberFormatException e) {
            throw new IniException(String.format("Wrong parameter (%s.%s)", s_name, p_name), e);
        }
    }


}