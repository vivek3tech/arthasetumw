package com.fincodefusion.arthasetu.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

public class DateTimeTools {

    @Tool(name="getCurrentDateTime", description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime() {
        System.out.println("getCurrentDateTime called");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

}