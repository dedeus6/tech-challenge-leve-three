package br.com.fiap.challenge.diner.core.application.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String FORMATO_DATA = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String ZONA_PADRAO = "America/Sao_Paulo";

    public static String formataData(String data) {
        String originalDate = "2015-03-25T10:04:58.396-04:00";
        ZonedDateTime parsedDate = ZonedDateTime.parse(originalDate);
        ZonedDateTime brazilDate = parsedDate.withZoneSameInstant(ZoneId.of(ZONA_PADRAO));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_DATA);
        return brazilDate.format(formatter);
    }

}
