package dam.pmdm.tarea3.rv;


import java.util.List;

public class PokemonData {

    private int numero;
    private String name;
    private String imagen;
    private List<String> types;
    private int weight;
    private int height;
    private String url;

    public PokemonData(int numero, String name, String url) {
        this.numero = numero;
        this.name = name;
        this.url = url;
    }

    public PokemonData(String url, int height, int weight, List<String> types, String imagen, String name, int numero) {
        this.url = url;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.imagen = imagen;
        this.name = name;
        this.numero = numero;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getNumero() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }


}
