package com.ldcr.dlock;

import com.ldcr.dlock.util.DlockVersion;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author zhanghonglong
 * @date 2020/6/4 18:01
 */
public class DlockBanner implements Banner {
    private static final String BANNER =
        "   ___   __   ____   _____ __ __       ___   ____   ____  ______      \n" +
            "  / _ \\ / /  / __ \\ / ___// //_/      / _ ) / __ \\ / __ \\/_  __/  \n" +
            " / // // /__/ /_/ // /__ / ,<        / _  |/ /_/ // /_/ / / /         \n" +
            "/____//____/\\____/ \\___//_/|_|      /____/ \\____/ \\____/ /_/      ";

    private static final String VERSION_BANNER =
        "started dlock boot                            (version:" + DlockVersion.getVersion() + ")";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
        printStream.println();
        printStream.println(AnsiOutput.toString(AnsiColor.DEFAULT, BANNER));
        printStream.println();
        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, VERSION_BANNER));
        printStream.println();
    }
}
