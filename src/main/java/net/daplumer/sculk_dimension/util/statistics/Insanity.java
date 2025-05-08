package net.daplumer.sculk_dimension.util.statistics;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;

import static net.daplumer.sculk_dimension.util.statistics.ModStatistics.INSANITY;

public class Insanity {
    public static final int max = 100;

    private static void createScoreIfNull(Scoreboard scoreboard){
        if (!scoreboard.getObjectiveNames().contains("insanity")){
          executeAddObjective(scoreboard, Stats.CUSTOM.getOrCreateStat(INSANITY),Text.translatable("scoreboard.sculk_dimension.insanity"));
        }

    }
    private static void executeAddObjective(Scoreboard scoreboard, ScoreboardCriterion criteria, Text displayName){
        scoreboard.addObjective("insanity", criteria, displayName, criteria.getDefaultRenderType(), false, null);
        scoreboard.getObjectives();
    }
    public static void add(int amount, PlayerEntity entity){
        if (!entity.getWorld().isClient) {
            Scoreboard scoreboard = entity.getScoreboard();
            createScoreIfNull(scoreboard);
            int insanity = get(entity);
            if (insanity + amount > max){
                entity.increaseStat(INSANITY, max - insanity);
            }else {
                entity.increaseStat(INSANITY, amount);
            }
        }
    }
    public static void increment(PlayerEntity entity){
        add(1, entity);
    }
    public static int get(PlayerEntity entity){
        Scoreboard scoreboard = entity.getScoreboard();
        createScoreIfNull(scoreboard);
        return scoreboard.getOrCreateScore(entity, scoreboard.getNullableObjective("insanity")).getScore();
    }
    //TODO: Fix insanity method to use data packets
    //TODO: make insanity incremented by more item usages
}
