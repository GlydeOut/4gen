package jsequitur.generation.mixin;

import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public class PlayerMixin {

	private static final int MIN_X = -432;
	private static final int MAX_X = 432;
	private static final int MIN_Z = -432;
	private static final int MAX_Z = 432;

	@ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 0)
	private double modifyDX(double dx) {
		Entity entity = (Entity)(Object)this;
		double newX = entity.x + dx;
		if (newX < MIN_X || newX > MAX_X) {
			return 0;
		}
		return dx;
	}
	@ModifyVariable(method = "move", at = @At("HEAD"), ordinal = 2)
	private double modifyDZ(double dz) {
		Entity entity = (Entity)(Object)this;
		double newZ = entity.z + dz;
		if (newZ < MIN_Z || newZ > MAX_Z) {
			return 0;
		}
		return dz;
	}
}
