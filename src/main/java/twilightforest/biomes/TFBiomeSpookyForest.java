package twilightforest.biomes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFEntities;
import twilightforest.enums.PlantVariant;

import java.util.Random;

public class TFBiomeSpookyForest extends TFBiomeBase {

	private final WorldGenerator tfGenWebs = new TFGenWebs();
	private final WorldGenerator tfGenLeaf = new TFGenFallenLeaves();
	private final WorldGenerator tfGenLampposts = new TFGenLampposts(Blocks.LIT_PUMPKIN.getDefaultState());
	private final WorldGenerator worldGenMushgloom = new TFGenTallGrass(TFBlocks.twilight_plant.getDefaultState().with(BlockTFPlant.VARIANT, PlantVariant.MUSHGLOOM));
	private final WorldGenerator worldGenDeadBush = new TFGenTallGrass(TFBlocks.twilight_plant.getDefaultState().with(BlockTFPlant.VARIANT, PlantVariant.DEADBUSH), 8);
	private final WorldGenerator graveyardGen = new TFGenGraveyard();
	private final WorldGenerator worldGenPumpkin = new WorldGenPumpkin();

	public TFBiomeSpookyForest(Builder props) {
		super(props);

		getTFBiomeDecorator().setFlowersPerChunk(1);
		getTFBiomeDecorator().setGrassPerChunk(4);
		getTFBiomeDecorator().setTreesPerChunk(2);
		getTFBiomeDecorator().hasCanopy = false;

		addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.BAT, 20, 8, 8));

		addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 50, 1, 4));
		addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 20, 1, 4));
		addSpawn(EntityClassification.MONSTER, new SpawnListEntry(TFEntities.skeleton_druid.get(), 5, 1, 1));
	}

    //TODO: Move to feature decorator
	@Override
	public void decorate(World world, Random rand, BlockPos pos) {
		super.decorate(world, rand, pos);

		float canopyPerChunk = 1.7F;

		// add canopy trees
		int nc = (int) canopyPerChunk + ((rand.nextFloat() < (canopyPerChunk - (int) canopyPerChunk)) ? 1 : 0);
		for (int i = 0; i < nc; i++) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			BlockPos genPos = new BlockPos(rx, world.getHeight(rx, rz), rz);
			((TFGenCanopyTree) getTFBiomeDecorator().canopyTreeGen).generate(world, rand, genPos, false);
		}

		// shroom
		if (rand.nextInt(24) == 0) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			int ry = TFWorld.getGroundLevel(world, rx, rz);
			// mushglooms
			this.worldGenMushgloom.generate(world, rand, new BlockPos(rx, ry, rz));
		}

		// webs
		for (int i = 0; i < 36; i++) {
			int rx = pos.getX() + rand.nextInt(30) + 2;
			int rz = pos.getZ() + rand.nextInt(30) + 2;
			int ry = TFWorld.SEALEVEL + rand.nextInt(TFWorld.CHUNKHEIGHT - TFWorld.SEALEVEL);

			this.tfGenWebs.generate(world, rand, new BlockPos(rx, ry, rz));
		}

		// lamps with PUMPKINSSSSSS
		if (rand.nextInt(2) == 0) {
			int rx = pos.getX() + rand.nextInt(30) + 2;
			int rz = pos.getZ() + rand.nextInt(30) + 2;
			int ry = TFWorld.getGroundLevel(world, rx, rz);

			this.tfGenLampposts.generate(world, rand, new BlockPos(rx, ry, rz));
		}

		// extra pumpkins
		if (rand.nextInt(16) == 0) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			int ry = TFWorld.getGroundLevel(world, rx, rz);

			this.worldGenPumpkin.generate(world, rand, new BlockPos(rx, ry, rz));
		}

		// Dried Bush
		for (int i = 0; i < 6; i++) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			worldGenDeadBush.generate(world, rand, new BlockPos(rx, world.getHeight(rx, rz), rz));
		}

		// Leaf Piles
		for (int i = 0; i < 6; i++) {
			int rx = pos.getX() + rand.nextInt(25) + 2;
			int rz = pos.getZ() + rand.nextInt(25) + 2;
			tfGenLeaf.generate(world, rand, new BlockPos(rx, world.getHeight(rx, rz), rz));
		}

		// graveyards for spooky forests
		if (rand.nextFloat() < 0.05F) {
			int rx = pos.getX() + rand.nextInt(16) + 8;
			int rz = pos.getZ() + rand.nextInt(16) + 8;
			graveyardGen.generate(world, rand, new BlockPos(rx, world.getHeight(rx, rz), rz));
		}
	}

	@Override
	public int getGrassColorAt(double p_225528_1_, double p_225528_3_) {
		return 0xC45123;
	}

	@Override
	public int getFoliageColor() {
		return 0xFF8501;
	}
}
