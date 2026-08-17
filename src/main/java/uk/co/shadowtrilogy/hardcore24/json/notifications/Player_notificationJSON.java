package uk.co.shadowtrilogy.hardcore24.json.notifications;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Player_notificationJSON {
    public static playernotificationContainer jsonInit(File JSON_FILE, HashMap<UUID, String> defaultMap) {


        try {
            if (JSON_FILE!=null){


                if(!JSON_FILE.exists()){
                    throw new FileNotFoundException();
                }


                Scanner scan = new Scanner(JSON_FILE);

                String tempstr = "";
                while(scan.hasNextLine()){
                    tempstr+=scan.nextLine();
                }

                scan.close();

                if(tempstr.isEmpty()){
                    return new playernotificationContainer(defaultMap);

                }


                playernotificationContainer data = new Gson().fromJson(tempstr, playernotificationContainer.class);

                return data;


                //END OF IF STATEMENT BY THE WAY (Java is impossible to read)
            }

        }
        catch(NullPointerException e){
            Hardcore24.plugin.getLogger().severe("JsonFileNullError, plugin shutting down");
            return new playernotificationContainer(defaultMap);
        }



        catch(ExceptionInInitializerError | FileNotFoundException e){
            return new playernotificationContainer(defaultMap);

        }


        return new playernotificationContainer(defaultMap);
    }

    public static void jsonSave(playernotificationContainer toJson, File JSON_FILE){

        try {
            if (JSON_FILE!=null){

                if(!JSON_FILE.exists()){
                    JSON_FILE.createNewFile();
                }

                Gson gson = new GsonBuilder().setPrettyPrinting().create();


                String str_data = gson.toJson(toJson);

                FileWriter writer = new FileWriter(JSON_FILE);

                writer.write(str_data);

                writer.close();


                //END OF IF STATEMENT BY THE WAY (Java is impossible to read)
            }

        }
        catch(NullPointerException e){
            Hardcore24.plugin.getLogger().severe("JsonFileNullError, plugin shutting down");
        }

        catch(ReadOnlyFileSystemException e){
            Hardcore24.plugin.getLogger().severe("Json file is read only... Shutting down (How did you do that then?)");
        }

        catch (FileNotFoundException e) {
            JSON_FILE.mkdirs();

        } catch (IOException e) {
            Hardcore24.plugin.getLogger().severe("Failed to create JSON file...");
        }
    }
}
