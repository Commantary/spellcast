package fr.commentary.spellcast.spells;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Spells {
    //mobilicorpus
    CAPTORUM(0, ChatColor.GOLD+"1. Captorum"),
    WINGARDIUM_LEVIOSA(1, ChatColor.LIGHT_PURPLE+"2. Wingardium leviosa"),
    EVANESCO(2, ChatColor.RED+"3. Evanesco"),
    IMOBILIS(3, ChatColor.AQUA+"4. Imobilis"),
    FINITE(4, ChatColor.DARK_AQUA+"5. Finite");

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
