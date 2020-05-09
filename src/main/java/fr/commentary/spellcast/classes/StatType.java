package fr.commentary.spellcast.classes;

import fr.commentary.spellcast.spells.Spells;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum StatType {

    IMMOBILISER(0);

    private final int value;

    StatType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    static final Map<Integer, Spells> values = Arrays.stream(Spells.values())
            .collect(Collectors.toMap(Spells::getValue, Function.identity()));

    public static Spells fromValue(final int value) {
        return values.get(value);
    }

}
