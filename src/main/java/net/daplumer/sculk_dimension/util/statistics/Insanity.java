package net.daplumer.sculk_dimension.util.statistics;

public interface Insanity {
    int maxInsanity = 100;
    void setInsanity(int insanity);
    int getInsanity();
    default void addInsanity(int amount){
        this.setInsanity(clampInsanity(this.getInsanity() + amount));
    }
    default void incrementInsanity(){
        this.addInsanity(1);
    }
    static int clampInsanity(int insanity){
        return Math.max(Math.min(insanity,maxInsanity),0);
    }
    //TODO: make insanity incremented by more item usages
}
