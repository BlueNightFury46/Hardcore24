package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.UUID;

public class PlayerDeathData {
    @NotNull public int deathMonth;
    @NotNull public int deathDayOfYear;
    @NotNull public int deathSecond;

    @NotNull public int deathHour;

    @NotNull public int deathMinute;
    @NotNull public int deathYear;
    @NotNull public int deathDayOfMonth;



    @NotNull public String world;

    public PlayerDeathData(World w, LocalDateTime t){
       t = t.plusHours(Hardcore24.DEATHBAN_TIME_HRS);
       t = t.plusMinutes(Hardcore24.DEATHBAN_TIME_MINS);
       t = t.plusDays(Hardcore24.DEATHBAN_TIME_DAYS);

        deathSecond =t.getSecond();
        deathMinute = t.getMinute();
        deathHour = t.getHour();
        deathYear = t.getYear();
        deathDayOfYear = t.getDayOfYear();
        deathMonth = t.getMonthValue();
        deathDayOfMonth = t.getDayOfMonth();



        world = w.getName();

    }

    public PlayerDeathData(String w, LocalDateTime t){
        t = t.plusHours(Hardcore24.DEATHBAN_TIME_HRS);
        t = t.plusMinutes(Hardcore24.DEATHBAN_TIME_MINS);
        t = t.plusDays(Hardcore24.DEATHBAN_TIME_DAYS);

        deathSecond =t.getSecond();
        deathMinute = t.getMinute();
        deathHour = t.getHour();
        deathYear = t.getYear();
        deathDayOfYear = t.getDayOfYear();
        deathMonth = t.getMonthValue();
        deathDayOfMonth = t.getDayOfMonth();



        world = w;

    }
}
