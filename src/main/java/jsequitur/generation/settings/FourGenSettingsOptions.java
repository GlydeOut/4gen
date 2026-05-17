package jsequitur.generation.settings;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;

public interface FourGenSettingsOptions {
	OptionRange fourGen$worldSize();
	OptionBoolean fourGen$flatNether();
}
