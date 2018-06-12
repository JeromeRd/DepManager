package com.dm.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jrichard on 28/06/2017.
 */
public class CommandExecutor {

    private Runtime runtime;
    private File currentDir;

    public CommandExecutor() {
        runtime = Runtime.getRuntime();
    }

    public boolean changeDirectory(String directory) {
        File file = new File(directory);
        if (file.exists() && file.isDirectory()) {
            currentDir = file;
            System.out.println("Directory changed successfully : " + directory);
            return true;
        }
        return false;
    }

    public boolean executeCommand(String[] command, String directory, String... args) {
        Process process  = null;
        File dir = null;
        if (directory != null) {
            dir = new File(directory);
        }
        boolean errorOccurred = false;
        StringBuilder outputString = new StringBuilder();
        StringBuilder errorOutputString = new StringBuilder();
        try {
            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            arguments.add("JAVA_HOME=C:\\Program Files\\Java\\jdk1.8.0_121");

            System.out.println("Execute command : " + getCommand(command));
            process = runtime.exec(command, arguments.toArray(new String[arguments.size()]), dir);
            BufferedReader output = getOutput(process);
            BufferedReader error = getError(process);

            String line;

            while ((line = output.readLine()) != null) {
                outputString.append(line).append(" ");
            }

            while ((line = error.readLine()) != null) {
                errorOccurred = true;
                errorOutputString.append(line).append(" ");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            manageExecutingError(command, dir, args, process);
            return false;
        }
        if (!errorOccurred) {
            System.out.println(outputString);
            System.out.println("Command successfully executed : " + getCommand(command));
        } else {
            System.out.println(errorOutputString);
        }
        return true;
    }

    public boolean executeCommand(String[] command, String... args) {
        Process process  = null;
        try {
            System.out.println("Execute command : " + getCommand(command));
            process = runtime.exec(command, args, currentDir);
            BufferedReader output = getOutput(process);
            BufferedReader error = getError(process);
            String line;

            while ((line = output.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = error.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            manageExecutingError(command, null, args, process);
            return false;
        }
        System.out.println("Command successfully executed : " + getCommand(command));
        return true;
    }

    private void manageExecutingError(String[] command, File dir, String[] args, Process process) {
        String message = "An error occurred when executing command " + getCommand(command);
        String parameters = getParameters(args);
        if (parameters.length() > 0) {
            message += " with paramaters " + parameters;
        }
        if (dir != null) {
            message += " on directory " + dir.getPath();
        }

        System.out.println(message);
        printProcessErrors(process);
    }

    private String getCommand(String[] commands) {
        StringBuilder strCommands = new StringBuilder("[");
        if (commands.length > 0) {
            for (String command : commands) {
                strCommands.append(command).append(" ");
            }
            strCommands.append("]");
        }
        return strCommands.toString();
    }

    private String getParameters(String[] args) {
        StringBuilder strArgs = new StringBuilder();
        if (args.length > 0) {
            String separator = "[";
            for (String arg : args) {
                strArgs.append(separator).append(arg);
                separator = ",";
            }
            strArgs.append("]");
        }
        return strArgs.toString();
    }

    private void printProcessErrors(Process process) {
        if (process == null) return;

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String s = "";
        System.out.println("Process errors :");
        try {
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("An error occurred when reading process errors :" + e.getMessage());
        }
    }

    private static BufferedReader getOutput(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    private static BufferedReader getError(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }
}
