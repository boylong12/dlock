package com.ldcr.dlock;

import com.ldcr.dlock.util.DlockVersion;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author zhanghonglong
 * @date 2020/6/4 18:01
 */
public class DlockBanner implements Banner {
    private static final String[] BANNER = {
        "",
        "   ___   __   ____   _____ __ __       ___   ____   ____  ______",
        "  / _ \\ / /  / __ \\ / ___// //_/      / _ ) / __ \\ / __ \\/_  __/",
        " / // // /__/ /_/ // /__ / ,<        / _  |/ /_/ // /_/ / / /   ",
        "/____//____/\\____/ \\___//_/|_|      /____/ \\____/ \\____/ /_/    "};

    private static final String VERSION_BANNER = "dlock boot started";

    private static final int STRAP_LINE_SIZE = 64;

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        for (String line : BANNER) {
            printStream.println(line);
        }
        String version = DlockVersion.getVersion();
        version = (version != null) ? " (v" + version + ")" : "";
        StringBuilder padding = new StringBuilder();
        while (padding.length() < STRAP_LINE_SIZE - (version.length() + VERSION_BANNER.length())) {
            padding.append(" ");
        }

        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, VERSION_BANNER, AnsiColor.DEFAULT, padding.toString(),
            AnsiStyle.FAINT, version));
        printStream.println();
    }
}
