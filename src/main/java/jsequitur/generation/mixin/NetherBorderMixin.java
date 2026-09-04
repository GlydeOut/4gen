package jsequitur.generation.mixin;

import jsequitur.generation.settings.FourJenSettingsOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.nether.SurfaceGeneratorNether;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurfaceGeneratorNether.class)
public class NetherBorderMixin {

	@Shadow
	@Final
	private World world;

	@Unique
	private static final Minecraft mc = Minecraft.getMinecraft();



	@Inject(method = "generateSurface", at = @At("TAIL"))
	private void flattenNether(Chunk chunk, ChunkGeneratorResult result, CallbackInfo ci) {
		boolean flattened = ((FourJenSettingsOptions) mc.gameSettings).fourJen$flatNether().value;

		if (flattened) {
			int minY = 127;
			int maxY = 255;
			int worldFillBlock = this.world.getWorldType().getFillerBlockId();

			int netherrackHeight = 6;

			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					for (int y = minY; y < maxY; y++) {
						if (y == minY) {
							result.setBlock(x, y, z, Blocks.BEDROCK.id());
						}
						else if (y <= minY + netherrackHeight || y > maxY - netherrackHeight - 1) {
							result.setBlock(x, y, z, worldFillBlock); // fill
						}
						else {
							result.setBlock(x, y, z, 0); // air above sea
						}
					}
				}
			}
		}
	}


	@Inject(method = "generateSurface", at = @At("TAIL"))
	private void generateBorder(Chunk chunk, ChunkGeneratorResult result, CallbackInfo ci) {

		int size = ((FourJenSettingsOptions) mc.gameSettings).fourJen$worldSize().value;
		int border = 0;
		switch (size) {
			case 0:
				border = 144;
				break;
			case 1:
				border = 176;
				break;
			case 2:
				border = 256;
				break;
			case 3:
				border = 320;
				break;
			case 4:
				return;
		}


		int minY = this.world.getWorldType().getMinY();
		int maxY = this.world.getWorldType().getMaxY();

		int baseX = chunk.xPosition * 16;
		int baseZ = chunk.zPosition * 16;

		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				int worldX = baseX + x;
				int worldZ = baseZ + z;

				if (worldX == -border && worldZ < border && worldZ > -border) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 2) {
							result.setBlock(x + 1, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2.5) {
							result.setBlock(x + 2, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x + 3, y, z, Blocks.BEDROCK.id());
						}
					}
				}
				if (worldX == border - 1 && worldZ < border && worldZ > -border) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 2) {
							result.setBlock(x - 1, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2.5) {
							result.setBlock(x - 2, y, z, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x - 3, y, z, Blocks.BEDROCK.id());
						}
					}
				}
				if (worldZ == -border && worldX < border && worldX > -border) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 2) {
							result.setBlock(x, y, z + 1, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2.5) {
							result.setBlock(x, y, z + 2, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x, y, z + 3, Blocks.BEDROCK.id());
						}
					}
				}
				if (worldZ == border - 1 && worldX < border && worldX > -border) {
					for (int y = minY; y < maxY; y++) {
						result.setBlock(x, y, z, Blocks.BEDROCK.id());
						if (5 * Math.random() > 2) {
							result.setBlock(x, y, z - 1, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 2.5) {
							result.setBlock(x, y, z - 2, Blocks.BEDROCK.id());
						}
						if (5 * Math.random() > 3) {
							result.setBlock(x, y, z - 3, Blocks.BEDROCK.id());
						}
					}
				}
			}
		}


	}


}
