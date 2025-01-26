package dam.pmdm.tarea3.bd;

import java.util.ArrayList;

public class PokemonBd {

    private String email;

    public PokemonBd() {
    }

    public PokemonBd(ArrayList types, Float height, Float weight, String url, String name, String id, String email) {
        this.types = types;
        this.height = height;
        this.weight = weight;
        this.url = url;
        this.name = name;
        this.id = id;
        this.email = email;
    }

    private String id;
    private String name;
    private String url;
    private Float weight;
    private Float height;
    private ArrayList types;

    public PokemonBd(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }



    public Float getWeight() {
        return weight;
    }

    public Float getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList getTypes() {
        return types;
    }

    public String getEmail() {
        return email;
    }

}
