package org.sfnelson.sk.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sfnelson.sk.server.domain.ArmoryReference;
import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Server;

public class ArmoryService {

    private static final ArmoryService instance = new ArmoryService();

    public static ArmoryService getInstance() {
        return instance;
    }

    public Character getCharacter(Server server, String name) {

        String realm = server.getRealm().toLowerCase();
        String svr = server.getServer().toLowerCase();

        ArmoryReference armory = new ArmoryReference();

        try {
            URL url = new URL("http://" + realm + ".battle.net/wow/en/character/" + svr + "/" + name + "/simple");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            Pattern p = Pattern.compile("http://" + realm + ".battle.net/static-render/"
                    + realm + "/" + svr + "/[0-9]*/[0-9]*-profilemain.jpg?");

            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.find()) break;
            }

            if (line == null) return null;

            Pattern id_p = Pattern.compile("/([0-9]*)/([0-9]*)-");
            Matcher id_m = id_p.matcher(line);
            if (id_m.find()) {
                armory.setArmoryHash(Long.parseLong(id_m.group(1)));
                armory.setArmoryReference(Long.parseLong(id_m.group(2)));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        Character c = new Character();
        c.setName(name);
        c.setServer(server);
        c.setArmory(armory);
        return c;
    }


}
