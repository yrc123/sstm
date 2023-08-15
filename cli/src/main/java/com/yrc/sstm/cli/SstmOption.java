package com.yrc.sstm.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum SstmOption {
    HelpOption("h", "help", false, "将此帮助消息输出到输出流"),
    IPS4_LOGIN_KEY_OPTION("l", "ips4_login_key", true, "cookie中的ips4_login_key"),
    IPS4_MEMBER_ID_OPTION("m", "ips4_member_id", true, "cookie中的ips4_member_id"),
    IPS4_DEVICE_KEY_OPTION("d", "ips4_device_key", true, "cookie中的ips4_device_key"),
    IPS4_SS_ID_OPTION("s", "ips4_ss_id", true, "cookie中的ips4_ss_id"),
    MESSAGE_OPTION("e", "message", true, "你签到贴的内容，如未设置，则内容为\"签到+日期\""),
    PORT_OPTION("p", "port", true, "如果要设置代理，代理的端口"),
    HOSTNAME_OPTION("n", "hostname", true, "如果要设置代理，代理的地址");

    private static final Options OPTIONS = initOptions();
    private static final List<Option> REQUIRED_OPTIONS = List.of(
        IPS4_LOGIN_KEY_OPTION.toOption(),
        IPS4_MEMBER_ID_OPTION.toOption(),
        IPS4_DEVICE_KEY_OPTION.toOption(),
        IPS4_SS_ID_OPTION.toOption()
    );

    private final String opt;
    private final String longOpt;
    private final Boolean hasArg;
    private final String description;

    private static Options initOptions() {
        Options options = new Options();
        Arrays.stream(values())
            .forEach(it -> options.addOption(it.toOption()));
        return options;
    }

    public Option toOption() {
        return new Option(opt, longOpt, hasArg, description);
    }

    public static Options getOptions() {
        return OPTIONS;
    }

    public static List<Option> getRequiredOptions() {
        return REQUIRED_OPTIONS;
    }
}
