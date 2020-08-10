/*Sweet Potato, by teddyxlandlee
 *
 * Version: pre-alpha.1a
 * You can see this project on Github.
 */

package com.github.teddyxlandlee.sweet_potato;

import com.github.teddyxlandlee.annotation.Unused_InsteadOf;
import com.github.teddyxlandlee.sweet_potato.blocks.GrinderBlock;
import com.github.teddyxlandlee.sweet_potato.blocks.MagicCubeBlock;
import com.github.teddyxlandlee.sweet_potato.blocks.SeedUpdaterBlock;
import com.github.teddyxlandlee.sweet_potato.blocks.SweetPotatoesCropBlock;
import com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import com.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds.EnchantedSaplings;
import com.github.teddyxlandlee.sweet_potato.items.BakedSweetPotatoItem;
import com.github.teddyxlandlee.sweet_potato.items.EnchantedBlockItem;
import com.github.teddyxlandlee.sweet_potato.items.EnchantedPotatoItem;
import com.github.teddyxlandlee.sweet_potato.items.RawSweetPotatoBlockItem;
import com.github.teddyxlandlee.sweet_potato.recipe.GrinderRecipe;
import com.github.teddyxlandlee.sweet_potato.recipe.SeedUpdatingRecipe;
import com.github.teddyxlandlee.sweet_potato.screen.GrinderScreenHandler;
import com.github.teddyxlandlee.sweet_potato.screen.SeedUpdaterScreenHandler;
import com.github.teddyxlandlee.sweet_potato.util.Util;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.sapling.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ExampleMod implements ModInitializer {
	public static ExampleMod INSTANCE;
	public ExampleMod() {
		INSTANCE = this;
	}

	public static final String MODID = "sweet_potato";

	@Unused_InsteadOf @Deprecated
	public static final String SEED_UPDATER_TRANSLATION_KEY = net.minecraft.util.Util.createTranslationKey("container", new Identifier(
			MODID, "seed_updating"
	));

	public static final Material MATERIAL_STONE = new Material.Builder(MaterialColor.STONE).build();
	public static final Material MATERIAL_PLANT = new FabricMaterialBuilder(MaterialColor.FOLIAGE).allowsMovement().destroyedByPiston().lightPassesThrough().notSolid().build();

	private static <T extends Recipe<Inventory>> RecipeType<T> register_recipe_type(Identifier id) {
		return Registry.register(Registry.RECIPE_TYPE, id, new RecipeType<T>() {
			public String toString() {
				return id.toString();
			}
		});
	}

	// Items
	public static final Item PEEL;
	public static final Item BAKED_PEEL;

		// Baked Potatoes
	public static final Item BAKED_PURPLE_POTATO;
	public static final Item BAKED_RED_POTATO;
	public static final Item BAKED_WHITE_POTATO;
		// Raw Potatoes
	public static final Item PURPLE_POTATO;
	public static final Item RED_POTATO;
	public static final Item WHITE_POTATO;

		// Misc
	public static final Item POTATO_POWDER;
	public static final Item ENCHANTED_SWEET_POTATO;

	// Blocks
	public static final Block MAGIC_CUBE;
	public static final Block GRINDER;

	public static final Block SEED_UPDATER;

		// Crops
	public static final Block PURPLE_POTATO_CROP;
	public static final Block RED_POTATO_CROP;
	public static final Block WHITE_POTATO_CROP;

		// Saplings
	public static final Block ENCHANTED_OAK_SAPLING;
	public static final Block ENCHANTED_SPRUCE_SAPLING;
	public static final Block ENCHANTED_BIRCH_SAPLING;
	public static final Block ENCHANTED_JUNGLE_SAPLING;
	public static final Block ENCHANTED_ACACIA_SAPLING;
	public static final Block ENCHANTED_DARK_OAK_SAPLING;

	// Block Items
	public static final Item MAGIC_CUBE_ITEM;
	public static final Item GRINDER_ITEM;
	public static final Item SEED_UPDATER_ITEM;
	public static final Item ENCHANTED_OAK_SAPLING_ITEM;
	public static final Item ENCHANTED_SPRUCE_SAPLING_ITEM;
	public static final Item ENCHANTED_BIRCH_SAPLING_ITEM;
	public static final Item ENCHANTED_JUNGLE_SAPLING_ITEM;
	public static final Item ENCHANTED_ACACIA_SAPLING_ITEM;
	public static final Item ENCHANTED_DARK_OAK_SAPLING_ITEM;


	// -*- -*- MISC -*- -*- //

	// Screen Handlers
	public static final ScreenHandlerType<SeedUpdaterScreenHandler> SEED_UPDATER_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(
			new Identifier(MODID, "seed_updater"), SeedUpdaterScreenHandler::new
	);
	public static final ScreenHandlerType<GrinderScreenHandler> GRINDER_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(
			new Identifier(MODID, "grinder"), GrinderScreenHandler::new
	);

	// Recipe Serializer
	public static final RecipeSerializer<SeedUpdatingRecipe> SEED_UPDATING_RECIPE_SERIALIZER = SeedUpdatingRecipe.register_recipe_serializer(new Identifier(
			MODID, "seed_updating"
	), new SeedUpdatingRecipe.Serializer());
	public static final RecipeSerializer<GrinderRecipe> GRINDER_RECIPE_SERIALIZER = GrinderRecipe.register_recipe_serializer(new Identifier(
			MODID, "grinding"
	), new GrinderRecipe.Serializer());

	// Recipe Type
	public static final RecipeType<SeedUpdatingRecipe> SEED_UPDATING_RECIPE_TYPE = register_recipe_type(new Identifier(
			MODID, "seed_updating"
	));
	public static final RecipeType<GrinderRecipe> GRINDER_RECIPE_TYPE = register_recipe_type(new Identifier(
			MODID, "grinding"
	));


	// Block Entities
	// Moved to respective classes


	@Override
	public void onInitialize() {
		// Official Fabric Example Mod from Github notes:
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Successfully loaded Sweet Potato Mod!");
		// Screen Handler

		ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier(
				MODID, "seed_updating"
		), (syncId, identifier, player, buf) -> {
			final World world = player.world;
			final BlockPos pos = buf.readBlockPos();
			return world.getBlockState(pos).createScreenHandlerFactory(player.world, pos).createMenu(syncId, player.inventory, player);
		});


		// Not registries:
		Util.registerCompostableItem(0.65f, PURPLE_POTATO);
		Util.registerCompostableItem(0.65f, WHITE_POTATO);
		Util.registerCompostableItem(0.65f, RED_POTATO);
		Util.registerCompostableItem(0.3f, PEEL);
		Util.registerCompostableItem(0.85f, BAKED_PURPLE_POTATO);
		Util.registerCompostableItem(0.85f, BAKED_RED_POTATO);
		Util.registerCompostableItem(0.85f, BAKED_WHITE_POTATO);
		Util.registerCompostableItem(0.5f, BAKED_PEEL);
		Util.registerCompostableItem(1.0f/*0.65f*/, ENCHANTED_SWEET_POTATO);	// Peter says it'd be 0.65f instead.
	}

	static {
		// Item
		PEEL = Registry.register(Registry.ITEM, new Identifier(MODID, "peel"), new Item(new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)
		));
		BAKED_PEEL = Registry.register(Registry.ITEM, new Identifier(MODID, "baked_peel"), new Item(new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)
		));
		BAKED_PURPLE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "baked_purple_potato"
		), new BakedSweetPotatoItem(new Item.Settings()
				.food(BakedSweetPotatoItem.COMPONENT)
				.group(ItemGroup.FOOD)
				.maxCount(64)));
		BAKED_RED_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "baked_red_potato"
		), new BakedSweetPotatoItem(new Item.Settings()
			.food(BakedSweetPotatoItem.COMPONENT)
			.group(ItemGroup.FOOD)
			.maxCount(64)));
		BAKED_WHITE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "baked_white_potato"
		), new BakedSweetPotatoItem(new Item.Settings()
			.food(BakedSweetPotatoItem.COMPONENT)
			.group(ItemGroup.FOOD)
			.maxCount(64)));
		POTATO_POWDER = Registry.register(Registry.ITEM, new Identifier(
				MODID, "potato_powder"
		), new Item(new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)));
		ENCHANTED_SWEET_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_sweet_potato"
		), new EnchantedPotatoItem(new Item.Settings()
				.food(EnchantedPotatoItem.COMPONENT)
				.group(ItemGroup.FOOD)
				.maxCount(1)));

		// Block
		MAGIC_CUBE = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "magic_cube"
		), new MagicCubeBlock(FabricBlockSettings
				.of(MATERIAL_STONE)
				.hardness(10.0f/*5.0f*/)	// Obsidian variants are 50.0f
				.breakByTool(FabricToolTags.PICKAXES, 2)
				.requiresTool()
				.resistance(1200.0f)		// Same as the Obsidian variants
		));
		GRINDER = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "grinder"
		), new GrinderBlock(FabricBlockSettings
				.of(MATERIAL_STONE)
				.hardness(3.5f)
				.breakByTool(FabricToolTags.PICKAXES, 0)
				.requiresTool()
				.resistance(6.0f)
		));
		SEED_UPDATER = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "seed_updater"
		), new SeedUpdaterBlock(FabricBlockSettings
				.of(MATERIAL_STONE)
				.hardness(3.5f)
				.breakByTool(FabricToolTags.PICKAXES, 2)
				.requiresTool()
				.resistance(6.0f)
		));
		PURPLE_POTATO_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "purple_potatoes"
		), new SweetPotatoesCropBlock(Util.BlockSettings.GRASS_LIKE, SweetPotatoType.PURPLE));
		RED_POTATO_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "red_potatoes"
		), new SweetPotatoesCropBlock(Util.BlockSettings.GRASS_LIKE, SweetPotatoType.RED));
		WHITE_POTATO_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "white_potatoes"
		), new SweetPotatoesCropBlock(Util.BlockSettings.GRASS_LIKE, SweetPotatoType.WHITE));
			// Saplings
		ENCHANTED_OAK_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_oak_sapling"
		), new EnchantedSaplings(new OakSaplingGenerator(), Util.BlockSettings.GRASS_LIKE));
		ENCHANTED_SPRUCE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_spruce_sapling"
		), new EnchantedSaplings(new SpruceSaplingGenerator(), Util.BlockSettings.GRASS_LIKE));
		ENCHANTED_BIRCH_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_birch_sapling"
		), new EnchantedSaplings(new BirchSaplingGenerator(), Util.BlockSettings.GRASS_LIKE));
		ENCHANTED_JUNGLE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_jungle_sapling"
		), new EnchantedSaplings(new JungleSaplingGenerator(), Util.BlockSettings.GRASS_LIKE));
		ENCHANTED_ACACIA_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_acacia_sapling"
		), new EnchantedSaplings(new AcaciaSaplingGenerator(), Util.BlockSettings.GRASS_LIKE));
		ENCHANTED_DARK_OAK_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_dark_oak_sapling"
		), new EnchantedSaplings(new DarkOakSaplingGenerator(), Util.BlockSettings.GRASS_LIKE));

		// Block Items
		PURPLE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "purple_potato"
		), new RawSweetPotatoBlockItem(PURPLE_POTATO_CROP, new Item.Settings()
				.food(RawSweetPotatoBlockItem.COMPONENT)
				.group(ItemGroup.FOOD)
				.maxCount(64)
		));
		RED_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "red_potato"
		), new RawSweetPotatoBlockItem(RED_POTATO_CROP, new Item.Settings()
				.food(RawSweetPotatoBlockItem.COMPONENT)
				.group(ItemGroup.FOOD)
				.maxCount(64)
		));
		WHITE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "white_potato"
		), new RawSweetPotatoBlockItem(WHITE_POTATO_CROP, new Item.Settings()
				.food(RawSweetPotatoBlockItem.COMPONENT)
				.group(ItemGroup.FOOD)
				.maxCount(64)
		));

			// Functional Blocks' Items
		MAGIC_CUBE_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "magic_cube"
		), new BlockItem(
				MAGIC_CUBE, new Item.Settings().group(ItemGroup.DECORATIONS)
		));
		GRINDER_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "grinder"
		), new BlockItem(
				GRINDER, new Item.Settings().group(ItemGroup.DECORATIONS)
		));
		SEED_UPDATER_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "seed_updater"
		), new BlockItem(
				SEED_UPDATER, new Item.Settings().group(ItemGroup.DECORATIONS)
		));
		ENCHANTED_OAK_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_oak_sapling"
		), new EnchantedBlockItem(ENCHANTED_OAK_SAPLING, Util.ItemSettings.UNCDEC));
		ENCHANTED_SPRUCE_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_spruce_sapling"
		), new EnchantedBlockItem(ENCHANTED_SPRUCE_SAPLING, Util.ItemSettings.UNCDEC));
		ENCHANTED_BIRCH_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_birch_sapling"
		), new EnchantedBlockItem(ENCHANTED_BIRCH_SAPLING, Util.ItemSettings.UNCDEC));
		ENCHANTED_JUNGLE_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_jungle_sapling"
		), new EnchantedBlockItem(ENCHANTED_JUNGLE_SAPLING, Util.ItemSettings.UNCDEC));
		ENCHANTED_ACACIA_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_acacia_sapling"
		), new EnchantedBlockItem(ENCHANTED_ACACIA_SAPLING, Util.ItemSettings.UNCDEC));
		ENCHANTED_DARK_OAK_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_dark_oak_sapling"
		), new EnchantedBlockItem(ENCHANTED_DARK_OAK_SAPLING, Util.ItemSettings.UNCDEC));

		// Block Entity

		//GRINDER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, null, BlockEntityType.Builder.create(GrinderBlockEntity::new, GRINDER).build(null));
	}
}
