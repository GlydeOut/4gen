package jsequitur.generation.mixin;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SurfaceGeneratorOverworld.class)
public class OceanBorderMixin {

	@Final
	@Shadow
	private World world;

	@Unique
	private final int border = 432;


	@Inject(method = "generateSurface", at = @At("HEAD"), cancellable = true)
	private void generateOcean(Chunk chunk, ChunkGeneratorResult result, CallbackInfo ci) {

		int minY = this.world.getWorldType().getMinY();
		int maxY = this.world.getWorldType().getMaxY();
		int oceanY = this.world.getWorldType().getOceanY();
		int oceanBlock = this.world.getWorldType().getOceanBlockId();
		int worldFillBlock = this.world.getWorldType().getFillerBlockId();

		int baseX = chunk.xPosition * 16;
		int baseZ = chunk.zPosition * 16;
		boolean outside = baseX > border - 1 || baseX + 15 < -border || baseZ > border - 1|| baseZ + 15 < -border;

		if (outside) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {

					for (int y = minY; y < maxY; y++) {

						if (y <= minY + oceanY - 10) {
							result.setBlock(x, y, z, worldFillBlock); // fill
						}
						else if (y <= minY + oceanY - 1) {
							result.setBlock(x, y, z, oceanBlock); // water
						} else {
							result.setBlock(x, y, z, 0); // air above sea
						}
					}
				}
			}
			ci.cancel();
		}
	}

	@Inject (method = "generateSurface", at = @At("TAIL"))
	private void lowerGround(Chunk chunk, ChunkGeneratorResult result, CallbackInfo ci) {
		int fadeStart = border - 32;
		int baseX = chunk.xPosition * 16;
		int baseZ = chunk.zPosition * 16;

		boolean lowerPosX = baseX >= fadeStart;
		boolean lowerNegX = baseX < -fadeStart;
		boolean lowerPosZ = baseZ >= fadeStart;
		boolean lowerNegZ = baseZ < -fadeStart;

		boolean lowerPosX2 = baseX >= fadeStart + 16;
		boolean lowerNegX2 = baseX < -fadeStart - 16;
		boolean lowerPosZ2 = baseZ >= fadeStart + 16;
		boolean lowerNegZ2 = baseZ < -fadeStart - 16;



		int oceanY = world.getWorldType().getOceanY();
		int oceanBlock = this.world.getWorldType().getOceanBlockId();

		if (lowerPosX) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					int originalSurfaceHeight = findSurfaceHeight(x, z, result);
					int chunkNum = 0;
					if (lowerPosX2) {
						chunkNum = 1;
					}
					for (int y = 0; y < 4; y++) {
						int target = originalSurfaceHeight - 4 + y - x - chunkNum * 16;
						int source = originalSurfaceHeight - 4 + y + 1;
						result.setBlock(x, target, z, result.getBlock(x, source, z));
						if (y == 3 && target + 1 <= oceanY) {
							result.setBlock(x, target, z, oceanBlock);
						}
					}
					for (int i = 0; i < x + chunkNum * 16; i++) {
						int target = originalSurfaceHeight - i - 1;
						if (target < oceanY) {
							result.setBlock(x, target, z, oceanBlock);
						}
						else {
							result.setBlock(x, target, z, 0);
						}
					}
				}
			}
		}
		if (lowerNegX) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					int originalSurfaceHeight = findSurfaceHeight(15 - x, 15 - z, result);
					int chunkNum = 0;
					if (lowerNegX2) {
						chunkNum = 1;
					}
					for (int y = 0; y < 4; y++) {
						int target = originalSurfaceHeight - 4 + y - x - chunkNum * 16;
						int source = originalSurfaceHeight - 4 + y + 1;
						result.setBlock((15 - x), target, (15 - z), result.getBlock((15 - x), source, (15 - z)));
						if (y == 3 && target + 1 <= oceanY) {
							result.setBlock((15 - x), target, (15 - z), oceanBlock);
						}
					}
					for (int i = 0; i < x + chunkNum * 16; i++) {
						int target = originalSurfaceHeight - i - 1;
						if (target < oceanY) {
							result.setBlock((15 - x), target, (15 - z), oceanBlock);
						} else {
							result.setBlock((15 - x), target, (15 - z), 0);
						}
					}
				}
			}
		}
		if (lowerPosZ) {
			for (int z = 0; z < 16; z++) {
				for (int x = 0; x < 16; x++) {
					int originalSurfaceHeight = findSurfaceHeight(x, z, result);
					int chunkNum = 0;
					if (lowerPosZ2) {
						chunkNum = 1;
					}
					for (int y = 0; y < 4; y++) {
						int target = originalSurfaceHeight - 4 + y - z - chunkNum * 16;
						int source = originalSurfaceHeight - 4 + y + 1;
						result.setBlock(x, target, z, result.getBlock(x, source, z));
						if (y == 3 && target + 1 <= oceanY) {
							result.setBlock(x, target, z, oceanBlock);
						}
					}
					for (int i = 0; i < z + chunkNum * 16; i ++) {
						int target = originalSurfaceHeight - i - 1;
						if (target < oceanY) {
							result.setBlock(x, target, z, oceanBlock);
						}
						else {
							result.setBlock(x, target, z, 0);
						}
					}
				}
			}
		}
		if (lowerNegZ) {
			for (int z = 0; z < 16; z++) {
				for (int x = 0; x < 16; x++) {
					int originalSurfaceHeight = findSurfaceHeight(15 - x, 15 - z, result);
					int chunkNum = 0;
					if (lowerNegZ2) {
						chunkNum = 1;
					}
					for (int y = 0; y < 4; y++) {
						int target = originalSurfaceHeight - 4 + y - z - chunkNum * 16;
						int source = originalSurfaceHeight - 4 + y + 1;
						result.setBlock((15 - x), target, (15 - z), result.getBlock((15 - x), source, (15 - z)));
						if (y == 3 && target + 1 <= oceanY) {
							result.setBlock((15 - x), target, (15 - z), oceanBlock);
						}
					}
					for (int i = 0; i < z + chunkNum * 16; i++) {
						int target = originalSurfaceHeight - i - 1;
						if (target < oceanY) {
							result.setBlock((15 - x), target, (15 - z), oceanBlock);
						} else {
							result.setBlock((15 - x), target, (15 - z), 0);
						}
					}
				}
			}
		}
	}
	@Unique
	private int findSurfaceHeight(int x, int z, ChunkGeneratorResult result) {
		int aboveOcean = 0;
		int oceanY = world.getWorldType().getOceanY();
		while (result.getBlock(x, oceanY + aboveOcean, z) != 0) {
			aboveOcean++;
		}
		return oceanY + aboveOcean;
	}
}

