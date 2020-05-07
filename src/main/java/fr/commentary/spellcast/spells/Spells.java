package fr.commentary.spellcast.spells;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Spells {

    CAPTORUM(0, ChatColor.GOLD+"Captorum"),
    WINGARDIUM_LEVIOSA(1, ChatColor.LIGHT_PURPLE+"Wingardium leviosa");

    private final int value;
    private final String name;

    Spells(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    static final Map<String, Spells> names = Arrays.stream(Spells.values())
            .collect(Collectors.toMap(Spells::getName, Function.identity()));
    static final Map<Integer, Spells> values = Arrays.stream(Spells.values())
            .collect(Collectors.toMap(Spells::getValue, Function.identity()));

    public static Spells fromName(final String name) {
        return names.get(name);
    }

    public static Spells fromValue(final int value) {
        return values.get(value);
    }
}
