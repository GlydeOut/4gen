package jsequitur.generation.mixin;

import jsequitur.generation.settings.FourGenSettingsOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public class PlayerMixin {

	@Unique
	private static final Minecraft mc = Minecraft.getMinecraft();

	@ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 0)
	private double modifyDX(double dx) {

		int size = ((FourGenSettingsOptions) mc.gameSettings).fourGen$worldSize().value;
		if (size == 4) {
			return dx;
		}
		int border = 0;
		switch (size) {
			case 0:
				border = 432;
				break;
			case 1:
				border = 512;
				break;
			case 2:
				border = 1536;
				break;
			case 3:
				border = 2560;
				break;
		}
		int MIN_X = -border;
		int MAX_X = border;


		Entity entity = (Entity)(Object)this;
		double newX = entity.x + dx;
		if (newX < MIN_X || newX > MAX_X) {
			return 0;
		}
		return dx;
	}
	@ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 2)
	private double modifyDZ(double dz) {

		int size = ((FourGenSettingsOptions) mc.gameSettings).fourGen$worldSize().value;
		if (size == 4) {
			return dz;
		}
		int border = 0;
		switch (size) {
			case 0:
				border = 432;
				break;
			case 1:
				border = 512;
				break;
			case 2:
				border = 1536;
				break;
			case 3:
				border = 2560;
				break;
		}
		int MIN_Z = -border;
		int MAX_Z = border;

		Entity entity = (Entity)(Object)this;
		double newZ = entity.z + dz;
		if (newZ < MIN_Z || newZ > MAX_Z) {
			return 0;
		}
		return dz;
	}
}
