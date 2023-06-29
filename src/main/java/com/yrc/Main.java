package com.yrc;

import com.yrc.pojo.Cookies;
import com.yrc.utils.CommentUtils;
import com.yrc.utils.TopicListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Optional;

import static com.yrc.pojo.SstmOption.*;

@Slf4j
public class Main {
    private static final String APPLICATION_NAME = "sstm签到工具";
    private static final HelpFormatter helpFormatter = new HelpFormatter();

    public static void main(String[] args) {
        CommandLine cli = initArgs(args);
        if (cli == null) {
            return;
        }

        Proxy proxy = getProxy(cli);
        String message = cli.getOptionValue(MESSAGE_OPTION.getOpt(), CommentUtils.getDefaultMessage());

        Cookies currentUserCookies = getCurrentUserCookies(cli, proxy);
        String todayTopicUrl = TopicListUtils.getTodayTopicUrl(proxy);
        CommentUtils.sendComment(todayTopicUrl, message, proxy, currentUserCookies);
    }

    private static Proxy getProxy(CommandLine commandLine) {
        Proxy proxy;
        if (commandLine.hasOption(HOSTNAME_OPTION.getOpt())
            && commandLine.hasOption(PORT_OPTION.getOpt())) {
            proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(
                    commandLine.getOptionValue(HOSTNAME_OPTION.getOpt()),
                    Integer.parseInt(commandLine.getOptionValue(PORT_OPTION.getOpt()))
                )
            );
        } else {
            proxy = Proxy.NO_PROXY;
        }
        return proxy;
    }

    private static Cookies getCurrentUserCookies(CommandLine cli, Proxy proxy) {
        Cookies currentUserCookies = Cookies.builder()
            .ips4LoginKey(cli.getOptionValue(IPS4_LOGIN_KEY_OPTION.getOpt()))
            .ips4MemberId(cli.getOptionValue(IPS4_MEMBER_ID_OPTION.getOpt()))
            .ips4DeviceKey(cli.getOptionValue(IPS4_DEVICE_KEY_OPTION.getOpt()))
            .ips4SsId(cli.getOptionValue(IPS4_SS_ID_OPTION.getOpt()))
            .build();
        currentUserCookies.login(proxy);
        log.info("获取Cookie信息：{}", currentUserCookies);
        return currentUserCookies;
    }

    public static Boolean checkRequiredOpt(CommandLine cli) {
        Optional<Option> absentOption = getRequiredOptions().stream()
            .filter(it -> !cli.hasOption(it.getOpt()))
            .findFirst();
        if (absentOption.isPresent()) {
            log.error("需要参数：{}", absentOption.get().getOpt());
            return false;
        }
        return true;
    }

    public static CommandLine initArgs(String[] args) {
        CommandLine cli = null;
        try {
            cli = new BasicParser().parse(getOptions(), args);
            if (cli.hasOption(HelpOption.getOpt()) || !checkRequiredOpt(cli)) {
                helpFormatter.printHelp(APPLICATION_NAME, getOptions());
                return null;
            }
        } catch (MissingArgumentException e) {
            log.error("参数不完整");
            helpFormatter.printHelp(APPLICATION_NAME, getOptions());
        } catch (ParseException e) {
            log.error("参数解析失败");
            helpFormatter.printHelp(APPLICATION_NAME, getOptions());
        }
        return cli;
    }
}
