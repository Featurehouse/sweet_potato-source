package io.github.teddyxlandlee.sweet_potato;

import io.github.teddyxlandlee.annotation.Unused_InsteadOf;
import io.github.teddyxlandlee.sweet_potato.blocks.GrinderBlock;
import io.github.teddyxlandlee.sweet_potato.blocks.MagicCubeBlock;
import io.github.teddyxlandlee.sweet_potato.blocks.SeedUpdaterBlock;
import io.github.teddyxlandlee.sweet_potato.blocks.SweetPotatoesCropBlock;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.MagicCubeBlockEntity;
import io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds.*;
import io.github.teddyxlandlee.sweet_potato.items.*;
import io.github.teddyxlandlee.sweet_potato.recipe.GrinderRecipe;
import io.github.teddyxlandlee.sweet_potato.recipe.SeedUpdatingRecipe;
import io.github.teddyxlandlee.sweet_potato.screen.GrinderScreenHandler;
import io.github.teddyxlandlee.sweet_potato.screen.SeedUpdaterScreenHandler;
import io.github.teddyxlandlee.sweet_potato.util.BlockSettings;
import io.github.teddyxlandlee.sweet_potato.util.ItemSettings;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.sapling.*;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SPMMain implements ModInitializer {
	public static SPMMain INSTANCE;
	public SPMMain() {
		INSTANCE = this;
	}

	public static final String MODID = "sweet_potato";
	//public static final SPMVersion MODVERSION = SPMVersion.BETA_1_0_0;

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
	//public static final Item BAKED_PEEL;

		// Baked Potatoes
	public static final Item BAKED_PURPLE_POTATO;
	public static final Item BAKED_RED_POTATO;
	public static final Item BAKED_WHITE_POTATO;
		// Raw Potatoes
	public static final Item PURPLE_POTATO;
	public static final Item RED_POTATO;
	public static final Item WHITE_POTATO;
		// Enchanted Potatoes
	public static final Item ENCHANTED_PURPLE_POTATO;
	public static final Item ENCHANTED_RED_POTATO;
	public static final Item ENCHANTED_WHITE_POTATO;

		// Misc
	public static final Item POTATO_POWDER;

	// Blocks
	public static final Block MAGIC_CUBE;
	public static final Block GRINDER;

	public static final Block SEED_UPDATER;

		// Crops
	public static final Block PURPLE_POTATO_CROP;
	public static final Block RED_POTATO_CROP;
	public static final Block WHITE_POTATO_CROP;

	public static final Block ENCHANTED_WHEAT_CROP;
	public static final Block ENCHANTED_BEETROOTS_CROP;
	public static final Block ENCHANTED_VANILLA_POTATOES_CROP;
	public static final Block ENCHANTED_CARROTS_CROP;

	public static final Block ENCHANTED_SUGAR_CANE;

		// Saplings
	public static final Block ENCHANTED_OAK_SAPLING;
	public static final Block ENCHANTED_SPRUCE_SAPLING;
	public static final Block ENCHANTED_BIRCH_SAPLING;
	public static final Block ENCHANTED_JUNGLE_SAPLING;
	public static final Block ENCHANTED_ACACIA_SAPLING;
	public static final Block ENCHANTED_DARK_OAK_SAPLING;

		// Potted Saplings
	public static final Block POTTED_ENCHANTED_SPRUCE_SAPLING;
	public static final Block POTTED_ENCHANTED_BIRCH_SAPLING;
	public static final Block POTTED_ENCHANTED_JUNGLE_SAPLING;
	public static final Block POTTED_ENCHANTED_ACACIA_SAPLING;
	public static final Block POTTED_ENCHANTED_OAK_SAPLING;
	public static final Block POTTED_ENCHANTED_DARK_OAK_SAPLING;

		// Enchanted Leaves
	public static final Block ENCHANTED_OAK_LEAVES;
	public static final Block ENCHANTED_ACACIA_LEAVES;
	public static final Block ENCHANTED_BIRCH_LEAVES;
	public static final Block ENCHANTED_DARK_OAK_LEAVES;
	public static final Block ENCHANTED_JUNGLE_LEAVES;
	public static final Block ENCHANTED_SPRUCE_LEAVES;

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
	public static final Item ENCHANTED_WHEAT_SEEDS;
	public static final Item ENCHANTED_BEETROOT_SEEDS;
	public static final Item ENCHANTED_VANILLA_POTATO_ITEM;
	public static final Item ENCHANTED_CARROT_ITEM;
	public static final Item ENCHANTED_OAK_LEAVES_ITEM;
	public static final Item ENCHANTED_ACACIA_LEAVES_ITEM;
	public static final Item ENCHANTED_BIRCH_LEAVES_ITEM;
	public static final Item ENCHANTED_DARK_OAK_LEAVES_ITEM;
	public static final Item ENCHANTED_JUNGLE_LEAVES_ITEM;
	public static final Item ENCHANTED_SPRUCE_LEAVES_ITEM;
	public static final Item ENCHANTED_SUGAR_CANE_ITEM;


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
	public static final BlockEntityType<GrinderBlockEntity> GRINDER_BLOCK_ENTITY_TYPE;
	public static final BlockEntityType<MagicCubeBlockEntity> MAGIC_CUBE_BLOCK_ENTITY_TYPE;

	// Item Tags
	public static final Tag<Item> RAW_SWEET_POTATOES;
	public static final Tag<Item> ENCHANTED_SWEET_POTATOES;
		// About Pigs & Parrots
	public static final Tag<Item> PIG_BREEDING_INGREDIENTS;

	@Override
	public void onInitialize() {
		System.out.println("Successfully loaded Sweet Potato Mod!");
		// Copyright
		// Agent in Minecraft China can write his or her name into this copyright AS AN AGENT instead of an author.
		System.out.printf("%s, by %s\nAuthors:\n%s\n", "Sweet Potato Mod", "Pigeonia Featurehouse", "- Teddy Li (bilibili: teddyxlandlee)\n- Ray MH Chen (bilibili: 一颗水晶Rayawa)\n- Dennis Lin (bilibili: 小林AWA)");

		Util.registerCompostableItem(0.3f, PEEL);/*
		Util.registerCompostableItem(0.65f, PURPLE_POTATO);
		Util.registerCompostableItem(0.65f, WHITE_POTATO);
		Util.registerCompostableItem(0.65f, RED_POTATO);
		Util.registerCompostableItem(0.85f, BAKED_PURPLE_POTATO);
		Util.registerCompostableItem(0.85f, BAKED_RED_POTATO);
		Util.registerCompostableItem(0.85f, BAKED_WHITE_POTATO);*/
		//Util.registerCompostableItem(0.5f, BAKED_PEEL);
		//Util.registerCompostableItem(1.0f/*0.65f*/, ENCHANTED_SWEET_POTATO);	// Peter says it'd be 0.65f instead.
		Util.registerCompostableItem(0.3f, ENCHANTED_OAK_SAPLING_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_SPRUCE_SAPLING_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_BIRCH_SAPLING_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_JUNGLE_SAPLING_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_ACACIA_SAPLING_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_DARK_OAK_SAPLING_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_ACACIA_LEAVES_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_BIRCH_LEAVES_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_DARK_OAK_LEAVES_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_JUNGLE_LEAVES_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_OAK_LEAVES_ITEM);
		Util.registerCompostableItem(0.3f, ENCHANTED_SPRUCE_LEAVES_ITEM);

		for (SweetPotatoType type: SweetPotatoType.values()) {
			for (SweetPotatoStatus status: SweetPotatoStatus.values()) {
				if (type.getComponent(status) != null) {
					type.getComponent(status).registerCompostableItem(type, status);
					type.getComponent(status).registerGrindableItem(type, status);
				}
			}

			/*type.getComponent(SweetPotatoStatus.RAW).registerCompostableItem(type, SweetPotatoStatus.RAW);
			type.getComponent(SweetPotatoStatus.BAKED).registerCompostableItem(type, SweetPotatoStatus.BAKED);
			type.getComponent(SweetPotatoStatus.ENCHANTED).registerCompostableItem(type, SweetPotatoStatus.ENCHANTED);
			type.getComponent(SweetPotatoStatus.RAW).registerGrindableItem(type, SweetPotatoStatus.RAW);
			type.getComponent(SweetPotatoStatus.BAKED).registerGrindableItem(type, SweetPotatoStatus.BAKED);
			type.getComponent(SweetPotatoStatus.ENCHANTED).registerGrindableItem(type, SweetPotatoStatus.ENCHANTED);*/
		}

		// Fuel
		//Util.registerFurnaceFuel(null, Items.AIR, -1);
		PigEntity.BREEDING_INGREDIENT = Ingredient.fromTag(PIG_BREEDING_INGREDIENTS);
		ParrotEntity.TAMING_INGREDIENTS.add(ENCHANTED_WHEAT_SEEDS);
		ParrotEntity.TAMING_INGREDIENTS.add(ENCHANTED_BEETROOT_SEEDS);
	}

	static {
		// Item
		PEEL = Registry.register(Registry.ITEM, new Identifier(MODID, "peel"), new Item(new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)
		));
		//BAKED_PEEL = Registry.register(Registry.ITEM, new Identifier(MODID, "baked_peel"), new Item(new Item.Settings()
		//		.group(ItemGroup.MISC)
		//		.maxCount(64)
		//));
		// TODO: after beta-1.0.0 releases, get the settings into SweetPotatoItem.java
		BAKED_PURPLE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "baked_purple_potato"
		), new BakedSweetPotatoItem(new Item.Settings()
				//.food(SweetPotatoType.PURPLE, SweetPotatoStatus.BAKED)
				.group(ItemGroup.FOOD)
				.maxCount(64), SweetPotatoType.PURPLE));
		BAKED_RED_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "baked_red_potato"
		), new BakedSweetPotatoItem(new Item.Settings()
			//.food(SweetPotatoType.RED, SweetPotatoStatus.BAKED)
			.group(ItemGroup.FOOD)
			.maxCount(64), SweetPotatoType.RED));
		BAKED_WHITE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "baked_white_potato"
		), new BakedSweetPotatoItem(new Item.Settings()
			//.food(SweetPotatoType.WHITE, SweetPotatoStatus.BAKED)
			.group(ItemGroup.FOOD)
			.maxCount(64), SweetPotatoType.WHITE));
		POTATO_POWDER = Registry.register(Registry.ITEM, new Identifier(
				MODID, "potato_powder"
		), new Item(new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)));
		/*ENCHANTED_SWEET_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_sweet_potato"
		), new EnchantedSweetPotatoItem(new Item.Settings()
				.food(EnchantedSweetPotatoItem.COMPONENT)
				.group(ItemGroup.FOOD)
				.maxCount(1)));*/
		ENCHANTED_PURPLE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_purple_potato"
		), new EnchantedSweetPotatoItem(new Item.Settings()
				//.food(SweetPotatoType.PURPLE, SweetPotatoStatus.ENCHANTED)
				.group(ItemGroup.FOOD)
				.maxCount(1), SweetPotatoType.PURPLE));
		ENCHANTED_RED_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_red_potato"
		), new EnchantedSweetPotatoItem(new Item.Settings()
				//.food(SweetPotatoType.RED, SweetPotatoStatus.ENCHANTED)
				.group(ItemGroup.FOOD)
				.maxCount(1), SweetPotatoType.RED));
		ENCHANTED_WHITE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_white_potato"
		), new EnchantedSweetPotatoItem(new Item.Settings()
				//.food(SweetPotatoType.WHITE, SweetPotatoStatus.ENCHANTED)
				.group(ItemGroup.FOOD)
				.maxCount(1), SweetPotatoType.WHITE));

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
				MODID, "agroforestry_table"
		), new SeedUpdaterBlock(FabricBlockSettings
				.of(MATERIAL_STONE)
				.hardness(3.5f)
				.breakByTool(FabricToolTags.PICKAXES, 2)
				.requiresTool()
				.resistance(6.0f)
		));
		    // Crops
		PURPLE_POTATO_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "purple_potatoes"
		), new SweetPotatoesCropBlock(BlockSettings.GRASS_LIKE, SweetPotatoType.PURPLE));
		RED_POTATO_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "red_potatoes"
		), new SweetPotatoesCropBlock(BlockSettings.GRASS_LIKE, SweetPotatoType.RED));
		WHITE_POTATO_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "white_potatoes"
		), new SweetPotatoesCropBlock(BlockSettings.GRASS_LIKE, SweetPotatoType.WHITE));
		ENCHANTED_WHEAT_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_wheat"
		), new EnchantedWheatBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_BEETROOTS_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_beetroots"
		), new EnchantedBeetrootsBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_VANILLA_POTATOES_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_potatoes"
		), new EnchantedVanillaPotatoesBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_CARROTS_CROP = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_carrots"
		), new EnchantedCarrotsBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_SUGAR_CANE = Registry.register(Registry.BLOCK, new Identifier(
		        MODID, "enchanted_sugar_cane"
        ), new EnchantedSugarCaneBlock(BlockSettings.GRASS));
			// Saplings
		ENCHANTED_OAK_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_oak_sapling"
		), new EnchantedSaplings(new OakSaplingGenerator(), BlockSettings.GRASS_LIKE));
		ENCHANTED_SPRUCE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_spruce_sapling"
		), new EnchantedSaplings(new SpruceSaplingGenerator(), BlockSettings.GRASS_LIKE));
		ENCHANTED_BIRCH_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_birch_sapling"
		), new EnchantedSaplings(new BirchSaplingGenerator(), BlockSettings.GRASS_LIKE));
		ENCHANTED_JUNGLE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_jungle_sapling"
		), new EnchantedSaplings(new JungleSaplingGenerator(), BlockSettings.GRASS_LIKE));
		ENCHANTED_ACACIA_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_acacia_sapling"
		), new EnchantedSaplings(new AcaciaSaplingGenerator(), BlockSettings.GRASS_LIKE));
		ENCHANTED_DARK_OAK_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_dark_oak_sapling"
		), new EnchantedSaplings(new DarkOakSaplingGenerator(), BlockSettings.GRASS_LIKE));
		POTTED_ENCHANTED_OAK_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "potted_enchanted_oak_sapling"
		), new FlowerPotBlock(ENCHANTED_OAK_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
		POTTED_ENCHANTED_SPRUCE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "potted_enchanted_spruce_sapling"
		), new FlowerPotBlock(ENCHANTED_SPRUCE_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
		POTTED_ENCHANTED_BIRCH_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "potted_enchanted_birch_sapling"
		), new FlowerPotBlock(ENCHANTED_BIRCH_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
		POTTED_ENCHANTED_JUNGLE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "potted_enchanted_jungle_sapling"
		), new FlowerPotBlock(ENCHANTED_JUNGLE_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
		POTTED_ENCHANTED_ACACIA_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "potted_enchanted_acacia_sapling"
		), new FlowerPotBlock(ENCHANTED_ACACIA_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
		POTTED_ENCHANTED_DARK_OAK_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "potted_enchanted_dark_oak_sapling"
		), new FlowerPotBlock(ENCHANTED_DARK_OAK_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
		ENCHANTED_ACACIA_LEAVES = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_acacia_leaves"
		), Util.createEnchantedLeavesBlock());
		ENCHANTED_BIRCH_LEAVES = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_birch_leaves"
		), Util.createEnchantedLeavesBlock());
		ENCHANTED_DARK_OAK_LEAVES = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_dark_oak_leaves"
		), Util.createEnchantedLeavesBlock());
		ENCHANTED_OAK_LEAVES = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_oak_leaves"
		), Util.createEnchantedLeavesBlock());
		ENCHANTED_JUNGLE_LEAVES = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_jungle_leaves"
		), Util.createEnchantedLeavesBlock());
		ENCHANTED_SPRUCE_LEAVES = Registry.register(Registry.BLOCK, new Identifier(
				MODID, "enchanted_spruce_leaves"
		), Util.createEnchantedLeavesBlock());

		// Block Items
		PURPLE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "purple_potato"
		), new RawSweetPotatoBlockItem(PURPLE_POTATO_CROP, new Item.Settings()
				//.food(SweetPotatoType.PURPLE, SweetPotatoStatus.RAW)
				.group(ItemGroup.FOOD)
				.maxCount(64), SweetPotatoType.PURPLE
		));
		RED_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "red_potato"
		), new RawSweetPotatoBlockItem(RED_POTATO_CROP, new Item.Settings()
				//.food(SweetPotatoType.RED, SweetPotatoStatus.RAW)
				.group(ItemGroup.FOOD)
				.maxCount(64), SweetPotatoType.RED
		));
		WHITE_POTATO = Registry.register(Registry.ITEM, new Identifier(
				MODID, "white_potato"
		), new RawSweetPotatoBlockItem(WHITE_POTATO_CROP, new Item.Settings()
				//.food(SweetPotatoType.WHITE, SweetPotatoStatus.RAW)
				.group(ItemGroup.FOOD)
				.maxCount(64), SweetPotatoType.WHITE
		));
		ENCHANTED_WHEAT_SEEDS = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_wheat_seeds"
		), new AliasedEnchantedItem(ENCHANTED_WHEAT_CROP, new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)
		));
		ENCHANTED_BEETROOT_SEEDS = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_beetroot_seeds"
		), new AliasedEnchantedItem(ENCHANTED_BEETROOTS_CROP, new Item.Settings()
				.group(ItemGroup.MISC)
				.maxCount(64)
		));
		ENCHANTED_VANILLA_POTATO_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_potato"
		), new AliasedEnchantedItem(ENCHANTED_VANILLA_POTATOES_CROP, new Item.Settings()
				.group(ItemGroup.MISC)	// Need to confirm
				.maxCount(64)
				.food(FoodComponents.POTATO)
		));
		ENCHANTED_CARROT_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_carrot"
		), new AliasedEnchantedItem(ENCHANTED_CARROTS_CROP, new Item.Settings()
				.group(ItemGroup.MISC)	// Need to confirm, too
				.maxCount(64)
				.food(FoodComponents.CARROT)
		));
		ENCHANTED_SUGAR_CANE_ITEM = Registry.register(Registry.ITEM, new Identifier(
		        MODID, "enchanted_sugar_cane"
        ), new EnchantedBlockItem(ENCHANTED_SUGAR_CANE, new Item.Settings()
                .group(ItemGroup.DECORATIONS)
                .maxCount(64)
        ));

		ENCHANTED_ACACIA_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_acacia_leaves"
		), new EnchantedBlockItem(ENCHANTED_ACACIA_LEAVES, new Item.Settings()
				//.group(null)
				.maxCount(64)
		));
		ENCHANTED_BIRCH_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_birch_leaves"
		), new EnchantedBlockItem(ENCHANTED_BIRCH_LEAVES, new Item.Settings()
				//.group(null)
				.maxCount(64)
		));
		ENCHANTED_DARK_OAK_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_dark_oak_leaves"
		), new EnchantedBlockItem(ENCHANTED_DARK_OAK_LEAVES, new Item.Settings()
				//.group(null)
				.maxCount(64)
		));
		ENCHANTED_JUNGLE_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_jungle_leaves"
		), new EnchantedBlockItem(ENCHANTED_JUNGLE_LEAVES, new Item.Settings()
				//.group(null)
				.maxCount(64)
		));
		ENCHANTED_OAK_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_oak_leaves"
		), new EnchantedBlockItem(ENCHANTED_OAK_LEAVES, new Item.Settings()
				//.group(null)
				.maxCount(64)
		));
		ENCHANTED_SPRUCE_LEAVES_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_spruce_leaves"
		), new EnchantedBlockItem(ENCHANTED_SPRUCE_LEAVES, new Item.Settings()
				//.group(null)
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
				MODID, "agroforestry_table"
		), new BlockItem(
				SEED_UPDATER, new Item.Settings().group(ItemGroup.DECORATIONS)
		));
		ENCHANTED_OAK_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_oak_sapling"
		), new EnchantedBlockItem(ENCHANTED_OAK_SAPLING, ItemSettings.UNCDEC));
		ENCHANTED_SPRUCE_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_spruce_sapling"
		), new EnchantedBlockItem(ENCHANTED_SPRUCE_SAPLING, ItemSettings.UNCDEC));
		ENCHANTED_BIRCH_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_birch_sapling"
		), new EnchantedBlockItem(ENCHANTED_BIRCH_SAPLING, ItemSettings.UNCDEC));
		ENCHANTED_JUNGLE_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_jungle_sapling"
		), new EnchantedBlockItem(ENCHANTED_JUNGLE_SAPLING, ItemSettings.UNCDEC));
		ENCHANTED_ACACIA_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_acacia_sapling"
		), new EnchantedBlockItem(ENCHANTED_ACACIA_SAPLING, ItemSettings.UNCDEC));
		ENCHANTED_DARK_OAK_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
				MODID, "enchanted_dark_oak_sapling"
		), new EnchantedBlockItem(ENCHANTED_DARK_OAK_SAPLING, ItemSettings.UNCDEC));

		// Block Entity
		GRINDER_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(
				MODID, "grinder"
		), BlockEntityType.Builder.create(GrinderBlockEntity::new, GRINDER).build(null));
		MAGIC_CUBE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(
				MODID, "magic_cube"
		), BlockEntityType.Builder.create(MagicCubeBlockEntity::new, MAGIC_CUBE).build(null));

		// Item Tags
		RAW_SWEET_POTATOES = TagRegistry.item(new Identifier(
				MODID, "raw_sweet_potatoes"
		));
		ENCHANTED_SWEET_POTATOES = TagRegistry.item(new Identifier(
				MODID, "enchanted_sweet_potatoes"
		));
			// About pig food & parrot food
		PIG_BREEDING_INGREDIENTS = TagRegistry.item(new Identifier(
				MODID, "pig_breeding_ingredients"
		));
	}
}
