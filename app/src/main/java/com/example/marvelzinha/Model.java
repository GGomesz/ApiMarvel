package com.example.marvelzinha;

public class Model {

    private int def_id;
        private String description;

    public Model() {
        this.def_id = def_id;
        this.description = description;
    }

    public Model(int customerID, String customerDef) {
        this.def_id = customerID;
        this.description = customerDef;
    }

    @Override
    public String toString() {
        return "Model{" +
                ", id=" + def_id +
                ", name='" + description + '\'' +
                '}';
    }
    public int getDef_id() { return def_id; }

    public void setDef_id(int def_id) { this.def_id = def_id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
