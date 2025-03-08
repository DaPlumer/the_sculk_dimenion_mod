package net.daplumer.sculk_dimension.item.custom.broken_echo;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;

public class EchoStageData {
    private final static Style italic = Style.EMPTY.withItalic(true);
    public static final Map<BrokenEcho.Stage, List<MutableText>> sanityToTooltip = Map.of(
            BrokenEcho.Stage.HINGED,   List.of(),
            BrokenEcho.Stage.SANE,     List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.sane_0"    ).setStyle(italic),
                                               Text.translatable("tooltips.sculk_dimension.broken_echo.sane_1"    ).setStyle(italic)),
            BrokenEcho.Stage.NORMAL,   List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.normal_0"  ).setStyle(italic),
                                               Text.translatable("tooltips.sculk_dimension.broken_echo.normal_1"  ).setStyle(italic)),
            BrokenEcho.Stage.WIERD,    List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.wierd_0"   ).setStyle(italic),
                                               Text.translatable("tooltips.sculk_dimension.broken_echo.wierd_1"   ).setStyle(italic)),
            BrokenEcho.Stage.UNHINGED, List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.unhinged_0").setStyle(italic),
                                               Text.translatable("tooltips.sculk_dimension.broken_echo.unhinged_1").setStyle(italic)),
            BrokenEcho.Stage.CRAZY,    List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.crazy_0"   ).setStyle(italic)),
            BrokenEcho.Stage.MAD,      List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.mad_0"     ).setStyle(italic)),
            BrokenEcho.Stage.INSANE,   List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.insane_0"  ).setStyle(italic)),
            BrokenEcho.Stage.ELDRITCH, List.of(Text.translatable("tooltips.sculk_dimension.broken_echo.eldritch_0").setStyle(Style.EMPTY.withObfuscated(true)),
                                               Text.translatable("tooltips.sculk_dimension.broken_echo.eldritch_1"))
    );
}
