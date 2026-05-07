package jsequitur.generation.mixin;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.nether.SurfaceGeneratorNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurfaceGeneratorNether.class)
public class NetherBorderMixin {

	private static final int borderLocation = 144;

	@Shadow
	private World world;


	@Inject(method = "generateSurface", at = @At("TAIL"))
	private void generateBorder(Chunk chunk, ChunkGeneratorResult result, CallbackInfo ci) {

		int minY = this.world.getWorldType().getMinY();
		int maxY = this.world.getWorldType().getMaxY();

		int baseX = chunk.xPosition * 16;
		int baseZ = chunk.zPosition * 16;

		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				int worldX = baseX + x;
				int worldZ = baseZ + z;

				if (worldX == -borderLocation && worldZ < borderLocation && worldZ > -borderLocation) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 1) {
							result.setBlock(x + 1, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2) {
							result.setBlock(x + 2, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x + 3, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 4) {
							result.setBlock(x + 4, y, z, Blocks.BEDROCK.id());
						}
					}
				}
				if (worldX == borderLocation - 1 && worldZ < borderLocation && worldZ > -borderLocation) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 1) {
							result.setBlock(x - 1, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2) {
							result.setBlock(x - 2, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x - 3, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 4) {
							result.setBlock(x - 4, y, z, Blocks.BEDROCK.id());
						}
					}
				}
				if (worldZ == -borderLocation && worldX < borderLocation && worldX > -borderLocation) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 1) {
							result.setBlock(x, y, z + 1, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2) {
							result.setBlock(x, y, z + 2, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x, y, z + 3, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 4) {
							result.setBlock(x, y, z + 4, Blocks.BEDROCK.id());
						}
					}
				}
				if (worldZ == borderLocation - 1 && worldX < borderLocation && worldX > -borderLocation) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 1) {
							result.setBlock(x, y, z - 1, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2) {
							result.setBlock(x, y, z - 2, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x, y, z - 3, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 4) {
							result.setBlock(x, y, z - 4, Blocks.BEDROCK.id());
						}
					}
				}
			}
		}


	}


}
