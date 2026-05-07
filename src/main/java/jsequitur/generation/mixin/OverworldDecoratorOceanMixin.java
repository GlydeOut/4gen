package jsequitur.generation.mixin;

import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.overworld.ChunkDecoratorOverworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkDecoratorOverworld.class)
public class OverworldDecoratorOceanMixin {

	private static final int endChunk = 10;
	@Inject(method = "decorate", at = @At("HEAD"), cancellable = true)
	private void generateOcean(Chunk chunk, CallbackInfo ci) {
		int baseX = chunk.xPosition * 16;
		int baseZ = chunk.zPosition * 16;

		if (chunk.xPosition >= endChunk || chunk.xPosition <= -endChunk || chunk.zPosition >= endChunk || chunk.zPosition <= -endChunk) {
			ci.cancel();
		}

	}

}

