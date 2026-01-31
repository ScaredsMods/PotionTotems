package io.github.scaredsmods.potion_totems.config;

import io.github.scaredsmods.potion_totems.PotionTotems;
import me.fzzyhmstrs.fzzy_config.annotations.ClientModifiable;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.annotations.WithPerms;
import me.fzzyhmstrs.fzzy_config.api.FileType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.screen.entry.ConfigEntry;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Version(version = 1)
public class ModCommonConfig extends Config {

    public ModCommonConfig() {
        super(PotionTotems.id("common"));
    }

    public InfusedTotemSection infusedTotemSection = new InfusedTotemSection();
    public static class InfusedTotemSection extends ConfigSection {

        public InfusedTotemSection() {
            super();
        }

        public ValidatedInt duration = new ValidatedInt(400, 5000, 20, ValidatedNumber.WidgetType.SLIDER);
        public ValidatedInt amplifier = new ValidatedInt(0, 4, 0, ValidatedNumber.WidgetType.SLIDER);
    }


    @Override
    public @NotNull FileType fileType() {
        return FileType.JSON5;
    }
}
