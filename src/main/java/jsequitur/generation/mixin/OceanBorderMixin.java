package jsequitur.generation.mixin;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SurfaceGeneratorOverworld.class)
public class OceanBorderMixin {

	@Final
	@Shadow
	private World world;


	@Inject(method = "generateSurface", at = @At("HEAD"), cancellable = true)
	private void generateOcean(Chunk chunk, ChunkGeneratorResult result, CallbackInfo ci) {

		int minY = this.world.getWorldType().getMinY();
		int maxY = this.world.getWorldType().getMaxY();
		int oceanY = this.world.getWorldType().getOceanY();
		int oceanBlock = this.world.getWorldType().getOceanBlockId();
		int worldFillBlock = this.world.getWorldType().getFillerBlockId();


		int border = 160;
		int baseX = chunk.xPosition * 16;
		int baseZ = chunk.zPosition * 16;
		boolean outside = baseX > border - 1 || baseX + 15 < -border || baseZ > border - 1|| baseZ + 15 < -border;

		if (outside) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {

					for (int y = minY; y < maxY; y++) {

						if (y <= minY + oceanY - 5) {
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

}

