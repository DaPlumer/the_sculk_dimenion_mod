package net.daplumer.sculk_dimension.block.custom;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class EnchantmentDuplicationScreen extends ForgingScreen<EnchantmentDuplicationScreenHandler> {
    private final Identifier BOOK_SLOT_TEXTURE  = Identifier.of(TheSculkDimension.MOD_ID, "container/slot/empty_slot_book");
    private final Identifier SOUL_SLOT_TEXTURE  = Identifier.of(TheSculkDimension.MOD_ID, "container/slot/empty_slot_crystalized_soul");
    private final Identifier RESIN_SLOT_TEXTURE = Identifier.of(TheSculkDimension.MOD_ID, "container/slot/empty_slot_resinant_wax");
    private final CyclingSlotIcon booksSlotIcon = new CyclingSlotIcon(0);
    private final CyclingSlotIcon soulsSlotIcon = new CyclingSlotIcon(1);
    private final CyclingSlotIcon resinSlotIcon = new CyclingSlotIcon(2);

    public EnchantmentDuplicationScreen(EnchantmentDuplicationScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, Identifier.of(TheSculkDimension.MOD_ID, "textures/gui/container/enchantment_duplicator.png"));
        this.titleX = 37;
        this.titleY = 10;
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {

        super.drawBackground(context, deltaTicks, mouseX, mouseY);
        this.booksSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
        this.soulsSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
        this.resinSlotIcon.render(this.handler, context, deltaTicks, this.x, this.y);
    }

    @Override
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {

    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        this.booksSlotIcon.updateTexture(List.of(BOOK_SLOT_TEXTURE ));
        this.soulsSlotIcon.updateTexture(List.of(SOUL_SLOT_TEXTURE ));
        this.resinSlotIcon.updateTexture(List.of(RESIN_SLOT_TEXTURE));
    }
}
