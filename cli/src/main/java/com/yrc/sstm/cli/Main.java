package com.yrc.sstm.cli;

import com.yrc.sstm.core.SstmConfig;
import com.yrc.sstm.core.SstmCookies;
import com.yrc.sstm.core.SstmHomePage;
import com.yrc.sstm.core.SstmProxy;
import com.yrc.sstm.core.utils.CommentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.Optional;

import static com.yrc.sstm.cli.SstmOption.*;

@Slf4j
public class Main {
    private static final String APPLICATION_NAME = "sstm签到工具";
    private static final HelpFormatter helpFormatter = new HelpFormatter();

    public static void main(String[] args) {
        CommandLine cli = initArgs(args);
        if (cli == null) {
            return;
        }

        SstmConfig sstmConfig = buildSstmConfig(cli);
        SstmHomePage homePage = new SstmHomePage(sstmConfig);
        homePage.login();
        homePage.checkIn();
    }

    private static SstmConfig buildSstmConfig(CommandLine cli) {
        SstmProxy proxy = buildSstmProxy(cli);
        SstmCookies cookies = buildSstmCookies(cli);
        String message = buildMessage(cli);
        return SstmConfig.builder()
            .proxy(proxy)
            .cookie(cookies)
            .message(message)
            .build();
    }

    private static String buildMessage(CommandLine cli) {
        return cli.getOptionValue(MESSAGE_OPTION.getOpt(), CommentUtils.getDefaultMessage());
    }

    private static SstmProxy buildSstmProxy(CommandLine cli) {
        Integer port = Optional.ofNullable(cli.getOptionValue(PORT_OPTION.getOpt()))
            .map(Integer::parseInt)
            .orElse(null);
        return SstmProxy.builder()
            .hostname(cli.getOptionValue(HOSTNAME_OPTION.getOpt()))
            .port(port)
            .build();
    }

    private static SstmCookies buildSstmCookies(CommandLine cli) {
        return SstmCookies.builder()
            .ips4LoginKey(cli.getOptionValue(IPS4_LOGIN_KEY_OPTION.getOpt()))
            .ips4MemberId(cli.getOptionValue(IPS4_MEMBER_ID_OPTION.getOpt()))
            .ips4DeviceKey(cli.getOptionValue(IPS4_DEVICE_KEY_OPTION.getOpt()))
            .ips4SsId(cli.getOptionValue(IPS4_SS_ID_OPTION.getOpt()))
            .build();
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
