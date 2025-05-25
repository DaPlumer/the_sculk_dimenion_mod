package net.daplumer.sculk_dimension.block.custom;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.block.ModScreenHandlerTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class EnchantmentDuplicationScreenHandler extends ForgingScreenHandler {

    public EnchantmentDuplicationScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }
    public EnchantmentDuplicationScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlerTypes.ENCHANTMENT_DUPLICATOR, syncId, playerInventory, context, createForgingSlotsManager());
    }
    private static ForgingSlotsManager createForgingSlotsManager(){
        return ForgingSlotsManager.builder()
                .input(0,33,28, ItemPredicate.Builder.create().items(Registries.ITEM,Items.BOOK).build())
                .input(1,53,28, (stack -> stack.getItem() instanceof SoulHolder))
                .input(2,43,48, ItemPredicate.Builder.create().items(Registries.ITEM, ModItems.RESINANT_WAX, ModItems.ENCHANTMENT_SPONGE).build())
                .output(3,113,38)
                .build();
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE);
        stack.onCraftByPlayer(player,stack.getCount());
        decrementStack(0);
        int requiredSouls = requiredSouls(this.input.getStack(2));
        SoulHolder.takeSouls(this.input.getStack(1),requiredSouls);
        if(player instanceof ServerPlayerEntity serverPlayer){
            serverPlayer.getAdvancementTracker().grantCriterion(
                    Advancement.Builder.create().build(Identifier.of(TheSculkDimension.MOD_ID,"duplicate_enchant")),
                    "use_duplicator");
        }
        updateResult();
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(ModBlocks.ENCHANTMENT_DUPLICATOR);
    }

    @Override
    public void updateResult() {
        ItemStack books = this.input.getStack(0);
        ItemStack souls = this.input.getStack(1);
        ItemStack resin = this.input.getStack(2);
        this.output.setStack(0,ItemStack.EMPTY);
        if(!books.isEmpty() && !souls.isEmpty() && !resin.isEmpty()){
            if(SoulHolder.getSouls(souls) >= requiredSouls(resin) &! resin.getOrDefault(DataComponentTypes.STORED_ENCHANTMENTS,ItemEnchantmentsComponent.DEFAULT).isEmpty()){
                this.output.setStack(0,Items.ENCHANTED_BOOK.getDefaultStack());
                this.output.getStack(0).set(DataComponentTypes.STORED_ENCHANTMENTS,resin.get(DataComponentTypes.STORED_ENCHANTMENTS));
            }
        }
    }

    public int requiredSouls(ItemStack stack){
        ItemEnchantmentsComponent component = stack.getOrDefault(DataComponentTypes.STORED_ENCHANTMENTS,ItemEnchantmentsComponent.DEFAULT);
        return component.getEnchantments().stream().mapToInt((entry)-> 1 << (component.getLevel(entry)-1)).sum();
    }

    @SuppressWarnings("SameParameterValue")
    private void decrementStack(int slot) {
        ItemStack itemStack = this.input.getStack(slot);
        if (!itemStack.isEmpty()) {
            itemStack.decrement(1);
            this.input.setStack(slot, itemStack);
        }
    }
}
