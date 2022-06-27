package me.service.cron.util;


import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 执行脚本工具类
 *
 * @author zzpp
 */
@Slf4j
public class CommandProcess {

    private final String[] commandPrefix;

    private String charsets;

    public CommandProcess() {
        if (isWindows()) {
            this.commandPrefix = new String[]{"cmd", "/C"};
        } else {
            this.commandPrefix = new String[]{"/bin/bash", "-c"};
        }
        setCharsets();
    }

    public String[] getCommandPrefix() {
        return commandPrefix;
    }

    public CommandProcess(String[] commandPrefix) {
        this.commandPrefix = commandPrefix;
        setCharsets();
    }

    private void setCharsets() {
        if (isWindows()) {
            this.charsets = "GBK";
        } else {
            this.charsets = "UTF-8";
        }
    }

    public Pair<Integer, List<String>> execute(String[] env, String command) {
        Process process = null;
        try {
            log.info("execute starting command:{}", command);
            //执行终端命令
            process = Runtime.getRuntime().exec(analysisCommand(command), env);
            List<String> result = streamExport(process);
            int i = process.waitFor();
            log.info("execute completed command:{}", command);
            return new Pair<>(i, result);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage(), e);
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
        log.info("execute fail command:{}", command);
        return new Pair<>(-1, new ArrayList<>());
    }

    public Pair<Integer, String> execute(String command) {
        return execute(null, "\n", command);
    }

    public Pair<Integer, String> execute(String delimiter, String command) {
        return execute(null, delimiter, command);
    }


    public Pair<Integer, String> execute(String[] env, String delimiter, String command) {
        Pair<Integer, List<String>> execute = execute(env, command);
        return new Pair<>(execute.getKey(), String.join(delimiter, execute.getValue()));
    }

    private String[] analysisCommand(String cmd) {
        List<String> command = commandPrefix != null && commandPrefix.length > 0 ?
                new ArrayList<>(Arrays.asList(commandPrefix)) : new ArrayList<>();
        command.add(cmd);
        return command.toArray(new String[0]);
    }

    private boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    private synchronized List<String> streamExport(Process process) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<String> result = Collections.synchronizedList(new ArrayList<>());
        executorService.execute(() -> {
            try (BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName(charsets)))) {
                String line;
                while ((line = read.readLine()) != null) {
                    System.out.println("command execute:"+line);
                    result.add(line);
                }
            } catch (Exception ignore) {
            }
        });
        executorService.execute(() -> {
            try (BufferedReader readError = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName(charsets)));) {
                String lineError;
                while ((lineError = readError.readLine()) != null) {
                    System.err.println("command execute:"+lineError);
                    result.add(lineError);
                }
            } catch (Exception ignore) {
            }
        });
        return result;
    }

}
