package Nmap.nmap;


import Nmap.model.Command;
import Nmap.model.ExecutionObjectFactory;
import Nmap.model.Scan;
import Nmap.model.ScriptHelp;
import Nmap.util.TransInfoHtml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nmap {
    private Command cmd;
    private String tempPath = System.getProperty("java.io.tmpdir") + "";
    private Thread commandThread;

    public boolean execute(final String tempFileName) {
        String[] command = composeCommand();
        try {
            Process p = Runtime.getRuntime().exec(command);
            final InputStream stream = p.getInputStream();
            final InputStream errors = p.getErrorStream();
            commandThread = new Thread(new Runnable() {
                public void run() {
                    BufferedReader reader = null;
                    BufferedReader errorReader = null;
                    try {
                        boolean firstLine = true;

                        File file = new File(tempFileName);


                        InputStream fileStream = new FileInputStream(file);

                        reader = new BufferedReader(new InputStreamReader(fileStream));


                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            line = escape(line);
                            if (line.contains(" open "))
                                line = "<span class=\"open\">" + line + "</span>";
                            else if (line.contains(" closed "))
                                line = "<span class=\"closed\">" + line + "</span>";
                            else if (line.contains(" filtered "))
                                line = "<span class=\"filtered\">" + line + "</span>";
                            String jump = "\n";
                            if (firstLine)
                                jump = "";
                            cmd.getOutput().setText(cmd.getOutput().getText() + jump + line);
                            firstLine = false;
                            System.out.println(cmd.getOutput().getText());
                        }
                        errorReader = new BufferedReader(new InputStreamReader(errors));
                        while ((line = errorReader.readLine()) != null) {
                            line = escape(line);
                            line = "<span class=\"closed\">" + line + "</span>";
                            String jump = "\n";
                            if (firstLine)
                                jump = "";
                            cmd.getOutput().setText(cmd.getOutput().getText() + jump + "<i>" + line + "</i>");
                            firstLine = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
//                        try {
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        readXML();
                        cmd.setFinished(true);
                        System.out.println("notifyEnd()");
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                cmd.setFinished(true);
                                System.out.println("notifyEnd()");
                            }
                        }
                    }
                }
            });
            commandThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void readXML() {
        StringBuilder sb = new StringBuilder();
        System.out.println(tempPath);
        try (BufferedReader br = new BufferedReader(new FileReader(tempPath))) {
            tempPath = System.getProperty("java.io.tmpdir") + "";
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(ExecutionObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(sb.toString());
            Object execution = unmarshaller.unmarshal(reader);
            cmd.getOutput().setXml(TransInfoHtml.transformToHtml(sb.toString()));
            if (execution instanceof Scan)
                cmd.getOutput().setScan((Scan) execution);
            else if (execution instanceof ScriptHelp)
                cmd.getOutput().setScriptHelp((ScriptHelp) execution);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String escape(String str) {
        String line = str;
        line = line.replace("&", "&amp;");
        line = line.replace("\"", "&quot;");
        line = line.replace("<", "&lt;");
        line = line.replace(">", "&gt;");
        return line;
    }

    private String[] composeCommand() {

        String filename = "Nmap-scan_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                .format(new Date()) + ".xml";

        this.cmd.getOutput().setFilename(filename);
        tempPath = tempPath + filename;
        System.out.println(tempPath);
        List<String> commandList = new ArrayList<String>();
        commandList.add("nmap");
        commandList.addAll(splitOptions());
        commandList.addAll(Arrays.asList(new String[]{"-oX", getTempPath(), "--webxml"}));

        return commandList.toArray(new String[]{});

    }

    public String getTempPath() {
        return tempPath;
    }

    public Command getCmd() {
        return cmd;
    }


    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }


    private List<String> splitOptions() {
        List<String> options = new ArrayList<>();
        //Splits string by spaces other than the ones in substring quotes
        Matcher matcher = Pattern.compile("\\s*([^(\"|\')]\\S*|\".+?\"|\'.+?\')\\s*").matcher(cmd.getText());
        while (matcher.find())
            options.add(matcher.group(1));

        return options;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\DZKD\\AppData\\Local\\Temp\\Nmap-scan_2019-11-01_16-05-48_10.59.13.137.txt";
        Nmap application = new Nmap();
        Command command = new Command("cmd");
        application.setCmd(command);
        System.out.println(application.execute(fileName));

    }
}

