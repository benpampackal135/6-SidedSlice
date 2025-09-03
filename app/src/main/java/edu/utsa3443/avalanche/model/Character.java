package edu.utsa3443.avalanche.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Character.
 */
public class Character {
    /**
     * The Name.
     */
    public String name;
    /**
     * The Health.
     */
    public int health;
    /**
     * The Atk.
     */
    public int atk;
    /**
     * The Def.
     */
    public int def;
    /**
     * The Evd.
     */
    public int evd;

    /**
     * Instantiates a new Character.
     *
     * @param name   the name
     * @param health the health
     * @param atk    the atk
     * @param def    the def
     * @param evd    the evd
     */
    public Character(String name, int health, int atk, int def, int evd) {
        this.name = name;
        this.health = health;
        this.atk = atk;
        this.def = def;
        this.evd = evd;
    }
    /*// Constructor for random character generation without a name
    public Character(int health, int atk, int def, int evd) {
        this("Random Character", health, atk, def, evd);  // Use a default name if no name is provided
    }*/

    /**
     * Gets preset characters.
     *
     * @return the preset characters
     */
// Get the six preset characters
    public static List<Character> getPresetCharacters() {
        List<Character> presets = new ArrayList<>();
        presets.add(new Character("QP", 5, 0, 0, 0));
        presets.add(new Character("Yuki", 5, 2, -1, -1));
        presets.add(new Character("Aru",5, -1, -1, 2));
        presets.add(new Character("Kai",5, 1, 0, 0));
        presets.add(new Character("Hime",5, 1, -1, 1));
        presets.add(new Character("Kyousuke",5, -1, 2, 0));
        return presets;
    }

    @Override
    public String toString() {
        return "Health: " + health + "\nATK: " + atk + "\nDEF: " + def + "\nEVD: " + evd;
    }
}
