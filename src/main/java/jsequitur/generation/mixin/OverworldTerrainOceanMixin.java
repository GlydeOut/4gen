package jsequitur.generation.mixin;

import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;
import net.minecraft.core.world.generate.chunk.perlin.overworld.TerrainGeneratorOverworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TerrainGeneratorOverworld.class)
public class OverworldTerrainOceanMixin {

	private static final int endChunk = 10;
	@Inject(method = "getBlockAt", at = @At("HEAD"), cancellable = true)
	private void generateOcean(int x, int y, int z, double density, CallbackInfoReturnable<Integer> cir) {

		if (x >= endChunk * 16 || x <= -endChunk * 16 || z >= endChunk * 16 || z <= -endChunk * 16) {
			cir.cancel();
		}

	}

}

