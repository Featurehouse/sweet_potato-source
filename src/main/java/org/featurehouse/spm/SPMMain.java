package org.featurehouse.spm;

import org.featurehouse.spm.blocks.GrinderBlock;
import org.featurehouse.spm.blocks.MagicCubeBlock;
import org.featurehouse.spm.blocks.SeedUpdaterBlock;
import org.featurehouse.spm.blocks.SweetPotatoesCropBlock;
import org.featurehouse.spm.blocks.entities.GrinderBlockEntity;
import org.featurehouse.spm.blocks.entities.MagicCubeBlockEntity;
import org.featurehouse.spm.blocks.saplings_seeds.*;
import org.featurehouse.spm.items.*;
import org.featurehouse.spm.linkage.SPMLinkage;
import org.featurehouse.spm.loot.LootTables;
import org.featurehouse.spm.mixin.global.ChickenEntityAccessor;
import org.featurehouse.spm.mixin.global.ParrotEntityAccessor;
import org.featurehouse.spm.mixin.global.PigEntityAccessor;
import org.featurehouse.spm.recipe.SeedUpdatingRecipe;
import org.featurehouse.spm.resource.SPMDataPackFormats;
import org.featurehouse.spm.screen.GrinderScreenHandler;
import org.featurehouse.spm.screen.MagicCubeScreenHandler;
import org.featurehouse.spm.screen.SeedUpdaterScreenHandler;
import org.featurehouse.spm.structures.tree.gen.*;
import org.featurehouse.spm.util.properties.objects.BlockSettings;
import org.featurehouse.spm.util.properties.objects.ItemSettings;
import org.featurehouse.spm.util.properties.objects.Materials;
import org.featurehouse.spm.util.registries.ComposterHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.Set;

import static org.featurehouse.spm.util.properties.objects.BlockSettings.*;
import static org.featurehouse.spm.util.registries.RegistryHelper.*;

public class SPMMain implements ModInitializer {
	public static SPMMain INSTANCE;
	public SPMMain() {
		INSTANCE = this;
	}

	public static final String MODID = "sweet_potato";
	//public static final SPMVersion MODVERSION = SPMVersion.BETA_1_0_0;

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
	/** From Xmas 2020. Protected indeed, instead of public. */
	protected static final Item XMAS_TREATING_BOWL;

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
	public static final ScreenHandlerType<SeedUpdaterScreenHandler> SEED_UPDATER_SCREEN_HANDLER_TYPE;
	public static final ScreenHandlerType<GrinderScreenHandler> GRINDER_SCREEN_HANDLER_TYPE;
	public static final ScreenHandlerType<MagicCubeScreenHandler> MAGIC_CUBE_SCREEN_HANDLER_TYPE;

	// Recipe Serializer
	public static final RecipeSerializer<SeedUpdatingRecipe> SEED_UPDATING_RECIPE_SERIALIZER;

	// Recipe Type
	public static final RecipeType<SeedUpdatingRecipe> SEED_UPDATING_RECIPE_TYPE;

	// Block Entities
	public static final BlockEntityType<GrinderBlockEntity> GRINDER_BLOCK_ENTITY_TYPE;
	public static final BlockEntityType<MagicCubeBlockEntity> MAGIC_CUBE_BLOCK_ENTITY_TYPE;

	// Item Tags
	public static final Tag<Item> RAW_SWEET_POTATOES;
	public static final Tag<Item> ENCHANTED_SWEET_POTATOES;
	public static final Tag<Item> ALL_SWEET_POTATOES;
		// About Pigs & Parrots
	public static final Tag<Item> PIG_BREEDING_INGREDIENTS;
	public static final Tag<Item> CHICKEN_BREEDING_INGREDIENTS;

	// Sounds
	public static final SoundEvent AGROFORESTRY_TABLE_FINISH;
	public static final SoundEvent GRINDER_GRIND;
	public static final SoundEvent MAGIC_CUBE_ACTIVATE;
	public static final SoundEvent MAGIC_CUBE_DEACTIVATE;
	public static final SoundEvent MAGIC_CUBE_AMBIENT;

	// Stats
	public static final Identifier INTERACT_WITH_GRINDER;
	public static final Identifier INTERACT_WITH_AGRO;
	public static final Identifier CROP_UPGRADED;
	public static final Identifier SWEET_POTATO_EATEN;
	public static final Identifier INTERACT_WITH_MAGIC_CUBE;

	@Override
	public void onInitialize() {
		System.out.println("Successfully loaded Sweet Potato Mod!");
		// Copyright
		// Agent in Minecraft China can write his or her name into this copyright AS AN AGENT instead of an author.
		System.out.printf("%s, by %s\nContributors:\n%s\n", "Sweet Potato Mod", "Pigeonia Featurehouse", "- Teddy Li (bilibili: teddyxlandlee)\n- Ray Chen (bilibili: 一颗水晶Rayawa)\n- Peter Yang (bilibili: 印度大米饭)");

		FabricLoader.getInstance().getEntrypoints("sweet_potato", SPMLinkage.class).forEach(SPMLinkage::init);
		ComposterHelper.register();

		LootTables.init();
		SPMDataPackFormats.init();

		// Fuel
		//Util.registerFurnaceFuel(null, Items.AIR, -1);
		PigEntityAccessor.setBreedingIngredient(Ingredient.fromTag(PIG_BREEDING_INGREDIENTS));
		ChickenEntityAccessor.setBreedingIngredient(Ingredient.fromTag(CHICKEN_BREEDING_INGREDIENTS));
		Set<Item> parrotTamingIngredients = ParrotEntityAccessor.getTamingIngredients();
		parrotTamingIngredients.add(ENCHANTED_BEETROOT_SEEDS); parrotTamingIngredients.add(ENCHANTED_WHEAT_SEEDS);
	}

	static {
		// Item
		PEEL = defaultItem("peel", ItemSettings.MISC);
		BAKED_PURPLE_POTATO = item("baked_purple_potato", new BakedSweetPotatoItem(ItemSettings.GROUP_FOOD, SweetPotatoType.PURPLE));
		BAKED_RED_POTATO = item("baked_red_potato", new BakedSweetPotatoItem(ItemSettings.GROUP_FOOD, SweetPotatoType.RED));
		BAKED_WHITE_POTATO = item("baked_white_potato", new BakedSweetPotatoItem(ItemSettings.GROUP_FOOD, SweetPotatoType.WHITE));
		POTATO_POWDER = defaultItem("potato_powder", ItemSettings.MISC);
		XMAS_TREATING_BOWL = defaultItem("treating_bowl", ItemSettings.EASTER_EGG);
		ENCHANTED_PURPLE_POTATO = item("enchanted_purple_potato", new EnchantedSweetPotatoItem(ItemSettings.ONE_FOOD, SweetPotatoType.PURPLE));
		ENCHANTED_RED_POTATO = item("enchanted_red_potato", new EnchantedSweetPotatoItem(ItemSettings.ONE_FOOD, SweetPotatoType.RED));
		ENCHANTED_WHITE_POTATO = item("enchanted_white_potato", new EnchantedSweetPotatoItem(ItemSettings.ONE_FOOD, SweetPotatoType.WHITE));

		// Block
		MAGIC_CUBE = block("magic_cube", new MagicCubeBlock(functionalMinable(Materials.MATERIAL_STONE, 10.0F, 1200.0F, 2)));
		GRINDER = block("grinder", new GrinderBlock(functionalMinable(Materials.MATERIAL_STONE, 3.5F, 6.0F, 0)));
		SEED_UPDATER = block("agroforestry_table", new SeedUpdaterBlock(functionalMinable(Materials.MATERIAL_STONE, 3.5F, 6.0F, 2)));
		    // Crops
		PURPLE_POTATO_CROP = block("purple_potatoes", new SweetPotatoesCropBlock(BlockSettings.GRASS_LIKE, SweetPotatoType.PURPLE));
		RED_POTATO_CROP = block("red_potatoes", new SweetPotatoesCropBlock(BlockSettings.GRASS_LIKE, SweetPotatoType.RED));
		WHITE_POTATO_CROP = block("white_potatoes", new SweetPotatoesCropBlock(BlockSettings.GRASS_LIKE, SweetPotatoType.WHITE));
		ENCHANTED_WHEAT_CROP = block("enchanted_wheat", new EnchantedWheatBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_BEETROOTS_CROP = block("enchanted_beetroots", new EnchantedBeetrootsBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_VANILLA_POTATOES_CROP = block("enchanted_potatoes", new EnchantedVanillaPotatoesBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_CARROTS_CROP = block("enchanted_carrots", new EnchantedCarrotsBlock(BlockSettings.GRASS_LIKE));
		ENCHANTED_SUGAR_CANE = block("enchanted_sugar_cane", new EnchantedSugarCaneBlock(BlockSettings.GRASS));
			// Saplings
		ENCHANTED_OAK_SAPLING = createEnchantedSapling("enchanted_oak_sapling", EnchantedOakSaplingGen::new);
		ENCHANTED_SPRUCE_SAPLING = createEnchantedSapling("enchanted_spruce_sapling", EnchantedSpruceSaplingGen::new);
		ENCHANTED_BIRCH_SAPLING = createEnchantedSapling("enchanted_birch_sapling", EnchantedBirchSaplingGen::new);
		ENCHANTED_JUNGLE_SAPLING = createEnchantedSapling("enchanted_jungle_sapling", EnchantedJungleSaplingGen::new);
		ENCHANTED_ACACIA_SAPLING = createEnchantedSapling("enchanted_acacia_sapling", EnchantedAcaciaSaplingGen::new);
		ENCHANTED_DARK_OAK_SAPLING = createEnchantedSapling("enchanted_dark_oak_sapling", EnchantedDarkOakSaplingGen::new);
			// Potted
		POTTED_ENCHANTED_OAK_SAPLING = createPotted("potted_enchanted_oak_sapling", ENCHANTED_OAK_SAPLING);
		POTTED_ENCHANTED_SPRUCE_SAPLING = createPotted("potted_enchanted_spruce_sapling", ENCHANTED_SPRUCE_SAPLING);
		POTTED_ENCHANTED_BIRCH_SAPLING = createPotted("potted_enchanted_birch_sapling", ENCHANTED_BIRCH_SAPLING);
		POTTED_ENCHANTED_JUNGLE_SAPLING = createPotted("potted_enchanted_jungle_sapling", ENCHANTED_JUNGLE_SAPLING);
		POTTED_ENCHANTED_ACACIA_SAPLING = createPotted("potted_enchanted_acacia_sapling", ENCHANTED_ACACIA_SAPLING);
		POTTED_ENCHANTED_DARK_OAK_SAPLING = createPotted("potted_enchanted_dark_oak_sapling", ENCHANTED_DARK_OAK_SAPLING);
			// Leaves
		ENCHANTED_ACACIA_LEAVES = createLeaves("enchanted_acacia_leaves");
		ENCHANTED_BIRCH_LEAVES = createLeaves("enchanted_birch_leaves");
		ENCHANTED_DARK_OAK_LEAVES = createLeaves("enchanted_dark_oak_leaves");
		ENCHANTED_OAK_LEAVES = createLeaves("enchanted_oak_leaves");
		ENCHANTED_JUNGLE_LEAVES = createLeaves("enchanted_jungle_leaves");
		ENCHANTED_SPRUCE_LEAVES = createLeaves("enchanted_spruce_leaves");

		// Block Items
		PURPLE_POTATO = item("purple_potato", new RawSweetPotatoBlockItem(PURPLE_POTATO_CROP, ItemSettings.GROUP_FOOD, SweetPotatoType.PURPLE));
		RED_POTATO = item("red_potato", new RawSweetPotatoBlockItem(RED_POTATO_CROP, ItemSettings.GROUP_FOOD, SweetPotatoType.RED));
		WHITE_POTATO = item("white_potato", new RawSweetPotatoBlockItem(WHITE_POTATO_CROP, ItemSettings.GROUP_FOOD, SweetPotatoType.WHITE));

		ENCHANTED_WHEAT_SEEDS = AliasedEnchantedItem.of("enchanted_wheat_seeds", ENCHANTED_WHEAT_CROP);
		ENCHANTED_BEETROOT_SEEDS = AliasedEnchantedItem.of("enchanted_beetroot_seeds", ENCHANTED_BEETROOTS_CROP);
		ENCHANTED_VANILLA_POTATO_ITEM = AliasedEnchantedItem.ofMiscFood("enchanted_potato", ENCHANTED_VANILLA_POTATOES_CROP, FoodComponents.POTATO);
		ENCHANTED_CARROT_ITEM = AliasedEnchantedItem.ofMiscFood("enchanted_carrot", ENCHANTED_CARROTS_CROP, FoodComponents.CARROT);
		//ENCHANTED_SUGAR_CANE_ITEM = AliasedEnchantedItem.of("enchanted_sugar_cane", ENCHANTED_SUGAR_CANE, ItemGroup.DECORATIONS);
		ENCHANTED_SUGAR_CANE_ITEM = EnchantedBlockItem.of("enchanted_sugar_cane", ENCHANTED_SUGAR_CANE, ItemSettings.DECORATIONS);

		ENCHANTED_ACACIA_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_acacia_leaves", ENCHANTED_ACACIA_LEAVES, ItemSettings.DECORATIONS);
		ENCHANTED_BIRCH_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_birch_leaves", ENCHANTED_BIRCH_LEAVES, ItemSettings.DECORATIONS);
		ENCHANTED_DARK_OAK_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_dark_oak_leaves", ENCHANTED_DARK_OAK_LEAVES, ItemSettings.DECORATIONS);
		ENCHANTED_JUNGLE_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_jungle_leaves", ENCHANTED_JUNGLE_LEAVES, ItemSettings.DECORATIONS);
		ENCHANTED_OAK_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_oak_leaves", ENCHANTED_OAK_LEAVES, ItemSettings.DECORATIONS);
		ENCHANTED_SPRUCE_LEAVES_ITEM = EnchantedBlockItem.of("enchanted_spruce_leaves", ENCHANTED_SPRUCE_LEAVES, ItemSettings.DECORATIONS);

			// Functional Blocks' Items
		MAGIC_CUBE_ITEM = blockItem("magic_cube", MAGIC_CUBE, ItemSettings.DECORATIONS);
		GRINDER_ITEM = blockItem("grinder", GRINDER, ItemSettings.DECORATIONS);
		SEED_UPDATER_ITEM = blockItem("agroforestry_table", SEED_UPDATER, ItemSettings.DECORATIONS);
		ENCHANTED_OAK_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_oak_sapling", ENCHANTED_OAK_SAPLING, ItemSettings.UNCDEC);
		ENCHANTED_SPRUCE_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_spruce_sapling", ENCHANTED_SPRUCE_SAPLING, ItemSettings.UNCDEC);
		ENCHANTED_BIRCH_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_birch_sapling", ENCHANTED_BIRCH_SAPLING, ItemSettings.UNCDEC);
		ENCHANTED_JUNGLE_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_jungle_sapling", ENCHANTED_JUNGLE_SAPLING, ItemSettings.UNCDEC);
		ENCHANTED_ACACIA_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_acacia_sapling", ENCHANTED_ACACIA_SAPLING, ItemSettings.UNCDEC);
		ENCHANTED_DARK_OAK_SAPLING_ITEM = EnchantedBlockItem.of("enchanted_dark_oak_sapling", ENCHANTED_DARK_OAK_SAPLING, ItemSettings.UNCDEC);

		// Screen Handler
		SEED_UPDATER_SCREEN_HANDLER_TYPE = simpleScreenHandler("seed_updater", SeedUpdaterScreenHandler::new);
		GRINDER_SCREEN_HANDLER_TYPE = simpleScreenHandler("grinder", GrinderScreenHandler::new);
		MAGIC_CUBE_SCREEN_HANDLER_TYPE = extendedScreenHandler("magic_cube", MagicCubeScreenHandler::new);

		// Recipe Serializer
		SEED_UPDATING_RECIPE_SERIALIZER = recipeSerializer("seed_updating", SeedUpdatingRecipe.Serializer::new);

		// Recipe Type
		SEED_UPDATING_RECIPE_TYPE = recipeType("seed_updating");

		// Block Entity
		GRINDER_BLOCK_ENTITY_TYPE = blockEntity("grinder", GrinderBlockEntity::new, GRINDER);
		MAGIC_CUBE_BLOCK_ENTITY_TYPE = blockEntity("magic_cube", MagicCubeBlockEntity::new, MAGIC_CUBE);

		// Item Tags
		RAW_SWEET_POTATOES = itemTag("raw_sweet_potatoes");
		ENCHANTED_SWEET_POTATOES = itemTag("enchanted_sweet_potatoes");
		ALL_SWEET_POTATOES = itemTag("sweet_potatoes");
			// About pig food, parrot food and chicken food
		PIG_BREEDING_INGREDIENTS = itemTag("pig_breeding_ingredients");
		CHICKEN_BREEDING_INGREDIENTS = itemTag("chicken_breeding_ingredients");

		// Sounds
		AGROFORESTRY_TABLE_FINISH = sound("block.agroforestry_table.finish");
		GRINDER_GRIND = sound("block.grinder.grind");
		MAGIC_CUBE_ACTIVATE = sound("block.magic_cube.activate");
		MAGIC_CUBE_DEACTIVATE = sound("block.magic_cube.deactivate");
		MAGIC_CUBE_AMBIENT = sound("block.magic_cube.ambient");

		// Stats
		INTERACT_WITH_GRINDER = stat("interact_with_grinder");
		INTERACT_WITH_AGRO = stat("interact_with_agroforestry_table");
		CROP_UPGRADED = stat("crop_upgraded");
		SWEET_POTATO_EATEN = stat("sweet_potato_eaten");
		INTERACT_WITH_MAGIC_CUBE = stat("interact_with_magic_cube");
	}
}
