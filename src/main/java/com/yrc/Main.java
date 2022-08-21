package com.yrc;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.yrc.pojo.Cookies;
import com.yrc.utils.CommentUtils;
import com.yrc.utils.CommonUtils;
import com.yrc.utils.TopicListUtils;

public class Main {
    private static final String APPILICATION_NAME = "sstm签到工具";

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final List<String> REQUIRED_OPTIONS = ImmutableList.of("l", "m", "d", "s");

    private static final HelpFormatter helpFormatter = new HelpFormatter();

    private static Options options;

    public static void main(String[] args) {

        CommandLine commandLine = initArgs(args);
        if (commandLine == null) {
            return;
        }
        if (commandLine.hasOption("h")) {
            helpFormatter.printHelp(APPILICATION_NAME, options);
            return;
        }
        if (!checkRequiredOpt(commandLine)) {
            helpFormatter.printHelp(APPILICATION_NAME, options);
            return;
        }

        Proxy proxy;
        String message = commandLine.getOptionValue("e", CommentUtils.getDefaultMessage());
        if (commandLine.hasOption("n") && commandLine.hasOption("p")) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    commandLine.getOptionValue("n"), Integer.parseInt(commandLine.getOptionValue("p"))));
        } else {
            proxy = Proxy.NO_PROXY;
        }

        Cookies.getInstance().setIps4LoginKey(commandLine.getOptionValue("l"))
                .setIps4MemberId(commandLine.getOptionValue("m"))
                .setIps4DeviceKey(commandLine.getOptionValue("d"))
                .setIps4SsId(commandLine.getOptionValue("s"))
                .setIps4IPSSessionFront(CommonUtils.getSessionFront(proxy));

        log.info("获取Cookie信息：{}", Cookies.getInstance());
        String todayTopicUrl = TopicListUtils.getTodayTopicUrl(proxy);
        CommentUtils.sendComment(todayTopicUrl, message, proxy);
    }

    public static Boolean checkRequiredOpt(CommandLine commandLine) {
        for (String opt : REQUIRED_OPTIONS) {
            if (!commandLine.hasOption(opt)) {
                log.error("需要参数：{}", opt);
                return false;
            }
        }
        return true;
    }

    public static CommandLine initArgs(String[] args) {
        options = new Options();
        options.addOption("h", "help", false, "将此帮助消息输出到输出流")
                .addOption("l", "ips4_login_key", true, "cookie中的ips4_login_key")
                .addOption("m", "ips4_member_id", true, "cookie中的ips4_member_id")
                .addOption("d", "ips4_device_key", true, "cookie中的ips4_device_key")
                .addOption("s", "ips4_ss_id", true, "cookie中的ips4_ss_id")
                .addOption("e", "message", true, "你签到贴的内容，如未设置，则内容为\"签到+日期\"")
                .addOption("p", "port", true, "如果要设置代理，代理的端口")
                .addOption("n", "hostname", true, "如果要设置代理，代理的地址");

        CommandLine commandLine = null;
        try {
            CommandLineParser parser = new BasicParser();
            commandLine = parser.parse(options, args);
        } catch (MissingArgumentException e) {
            log.error("参数不完整");
            helpFormatter.printHelp(APPILICATION_NAME, options);
        } catch (ParseException e) {
            log.error("参数解析失败");
            helpFormatter.printHelp(APPILICATION_NAME, options);
        }
        return commandLine;
    }
}
