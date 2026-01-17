package com.boyninja1555.icecore.lib;

import com.boyninja1555.icecore.IceCore;
import io.papermc.paper.command.brigadier.BasicCommand;

public abstract class BaseC implements BasicCommand {
    protected final IceCore plugin;
    protected final String mainLabel;

    protected BaseC(IceCore plugin, String mainLabel) {
        this.plugin = plugin;
        this.mainLabel = mainLabel;
    }

    @Override
    public String permission() {
        return IcePerm.get(IcePermType.COMMAND, mainLabel);
    }
}
