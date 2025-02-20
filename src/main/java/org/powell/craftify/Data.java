package org.powell.craftify;

public class Data {
    private String key;
    private String ingredient;
    private String output;

    public Data(String key, String ingredient, String output) {
        this.key = key;
        this.ingredient = ingredient;
        this.output = output;
    }

    public String getKey() {
        return key;
    }
    public String getIngredient() {
        return ingredient;
    }
    public String getOutput() {
        return output;
    }
}
