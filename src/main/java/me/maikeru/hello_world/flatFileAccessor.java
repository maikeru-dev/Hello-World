package me.maikeru.hello_world;

import org.bukkit.Bukkit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.UUID;

public class flatFileAccessor {
    final private String delimiter = ",";
    File file = new File("./plugins/Hello_World/playerArmorStands.dat");
    public void appendEntry(UUID playerId, String name, UUID armorStandId) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
        pw.println(playerId + delimiter + name + delimiter + armorStandId);
        pw.flush();

        pw.close();
        fw.close();
    }
    public boolean deleteEntry(String name, UUID entryUUID) {
        // Stream each line out of our file, then filter everything out into a temp file,
        // finally copy the temp file to the actual file and delete the temp
        File temp = new File("_temp_");
        try {
            PrintWriter out = new PrintWriter(new FileWriter(temp));
            Files.lines(file.toPath())
                    .filter(line -> !(line.contains(name + delimiter + entryUUID.toString())))
                    .forEach(out::println);
            out.flush();
            out.close();
            Files.move(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch(IOException e) {Bukkit.getLogger().severe(e.getMessage()); return false;}

        return true;
    }
    public UUID getValue(String name) throws invalidNameException {
        try {
            Scanner scanner = new Scanner(file);
            String[] line = null;
            boolean found = false;

            while(scanner.hasNext() && !found) {
                line = scanner.nextLine().split(delimiter);
                if (name.equals(line[1])){
                    found = true;
                    // must delete line after figuring out this is the data we need
                    // not this object's responsibility ^^
                }
            }

            scanner.close();
            if (found == false) throw new invalidNameException();

            return UUID.fromString(line[2]);

        } catch(IOException e){
            Bukkit.getLogger().severe(e.getMessage());
        }
        return null;
    }
    private class invalidNameException extends CustomException {
        public invalidNameException() {
            super("This name is invalid! Spelling error?");
        }

    }
}