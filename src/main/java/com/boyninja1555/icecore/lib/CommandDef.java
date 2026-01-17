package com.boyninja1555.icecore.lib;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CommandDef(
        @NotNull List<String> labels,
        @NotNull String help,
        @NotNull Class<? extends BaseC> command
) {}
