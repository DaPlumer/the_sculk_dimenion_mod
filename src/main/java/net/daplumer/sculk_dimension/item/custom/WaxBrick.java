package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.block.ModBlockTags;
import net.daplumer.sculk_dimension.component.ModProperties;
import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.WorldEvents;

public class WaxBrick extends Item {
    public WaxBrick(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null) {
            return ActionResult.PASS;
        }
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.isIn(ModBlockTags.VIBRATOR)){
            if (!state.get(ModProperties.WAXED)) {
                if (context.getPlayer() instanceof ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)context.getPlayer(), context.getBlockPos(), context.getStack());
                }

                context.getWorld().setBlockState(context.getBlockPos(),context.getWorld().getBlockState(context.getBlockPos()).with(ModProperties.WAXED, true));
                context.getStack().decrementUnlessCreative(1, context.getPlayer());
                if(!context.getPlayer().isCreative()) context.getPlayer().giveOrDropStack(ModItems.MOSSY_BAG.getDefaultStack());
                context.getWorld().syncWorldEvent(context.getPlayer(), WorldEvents.BLOCK_WAXED, context.getBlockPos(), 0);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void postProcessComponents(ItemStack stack) {
        super.postProcessComponents(stack);
        if (stack.hasEnchantments()) {
            stack.set(DataComponentTypes.MAX_STACK_SIZE,1);
            ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(stack.getOrDefault(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT));
            ItemEnchantmentsComponent enchantments = stack.getEnchantments();
            for (RegistryEntry<Enchantment> entry : enchantments.getEnchantments()) {
                builder.add(entry, enchantments.getLevel(entry));
            }
            stack.set(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
            stack.set(DataComponentTypes.STORED_ENCHANTMENTS, builder.build());

        }
    }
}
