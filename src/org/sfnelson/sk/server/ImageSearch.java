package org.sfnelson.sk.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageSearch {

    public static String findCharacterAvatar(String server, String guild, String user) {

        try {
            URL url = new URL("http://us.battle.net/wow/en/character/" + server + "/" + user + "/simple");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            Pattern p = Pattern.compile("http://us.battle.net/static-render/us/" + server + "/[0-9]*/[0-9]*-profilemain.jpg?");

            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.find()) break;
            }

            if (line == null) return null;

            Pattern id_p = Pattern.compile("/[0-9]*/[0-9]*-");
            Matcher id_m = id_p.matcher(line);
            if (id_m.find()) {
                String id = id_m.group();
                return "http://us.battle.net/static-render/us/" + server + id + "avatar.jpg";
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
