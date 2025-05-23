package net.daplumer.sculk_dimension.item.custom;

public class SculkenSoulBag extends SoulBag{
    public SculkenSoulBag(Settings settings) {
        super(settings);
    }

    @Override
    public int maxSouls() {
        return 2048;
    }
}
