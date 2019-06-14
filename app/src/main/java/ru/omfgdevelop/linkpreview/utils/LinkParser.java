package ru.omfgdevelop.linkpreview.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.omfgdevelop.linkpreview.interfaces.LinkParserInterface;

public class LinkParser implements LinkParserInterface {
    @Override
    public String parse(String text) {
        Pattern pattern =
                Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        String s = "";
        if (matcher.find()) {
            return matcher.group(0);
        } else
            return null;
    }
}
