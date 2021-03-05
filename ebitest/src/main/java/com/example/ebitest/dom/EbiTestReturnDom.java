package com.example.ebitest.dom;

public class EbiTestReturnDom {
    private final String geneName;
    private final String species;

    public EbiTestReturnDom(String geneName, String species) {
        this.geneName = geneName;
        this.species = species;
    }

    public String getGeneName() {
        return geneName;
    }

    public String getSpecies() {
        return species;
    }

}
