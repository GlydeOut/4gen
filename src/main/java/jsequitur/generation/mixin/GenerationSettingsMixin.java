package jsequitur.generation.mixin;

import jsequitur.generation.settings.FourJenSettingsOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Environment(EnvType.CLIENT)
@Mixin(GameSettings.class)
public abstract class GenerationSettingsMixin implements FourJenSettingsOptions {

	@SuppressWarnings("DataFlowIssue")
	@Unique
	private final OptionRange worldSize = new OptionRange((GameSettings) (Object) this, "fourJen.worldSize", 4, 0, 4);
	@Override
	public OptionRange fourJen$worldSize() {
		return worldSize;
	}

	@SuppressWarnings("DataFlowIssue")
	@Unique
	private final OptionBoolean flatNether = new OptionBoolean((GameSettings) (Object) this, "fourJen.flatNether", false);
	@Override
	public OptionBoolean fourJen$flatNether() {
		return flatNether;
	}

	@Inject(method = "getDisplayString", at = @At("RETURN"), cancellable = true)
	private void changeDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {

		if (option == worldSize) {
			int size = Integer.parseInt(option.value.toString());
			switch (size) {
				case 0:
					cir.setReturnValue("Classic");
					break;
				case 1:
					cir.setReturnValue("Small");
					break;
				case 2:
					cir.setReturnValue("Medium");
					break;
				case 3:
					cir.setReturnValue("Large");
					break;
				case 4:
					cir.setReturnValue("Infinite");
					break;
			}
		}
	}
}
