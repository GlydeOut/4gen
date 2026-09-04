package jsequitur.generation.mixin;

import jsequitur.generation.settings.FourJenSettingsOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dimension.class)
public class WorldScaleMixin {

	@Inject(
		method = "getCoordScale",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void modifyCoordScale(
		Dimension oldDim,
		Dimension newDim,
		CallbackInfoReturnable<Float> cir
	) {
		Minecraft mc = Minecraft.getMinecraft();

		if (mc == null || mc.gameSettings == null) {
			return;
		}

		int size = ((FourJenSettingsOptions) mc.gameSettings).fourJen$worldSize().value;

		float classicRatio = 0.333F;
		float smallRatio = 0.333F;
		float mediumRatio = 0.167F;
		float largeRatio = 0.125F;
		float infiniteRatio = 0.125F;

		float ratio;

		switch (size) {
			case 0:
				ratio = classicRatio;
				break;
			case 1:
				ratio = smallRatio;
				break;
			case 2:
				ratio = mediumRatio;
				break;
			case 3:
				ratio = largeRatio;
				break;
			default:
				ratio = infiniteRatio;
				break;
		}

		cir.setReturnValue(ratio);

	}
}
