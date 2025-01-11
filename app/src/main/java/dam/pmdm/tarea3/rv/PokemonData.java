package dam.pmdm.tarea3.rv;

import java.util.List;

public class PokemonData {

    private String numero;
    private String name;
    private String url;
    private List<String> tipo;
    private String altura;
    private String peso;
    private String imagen;

    public PokemonData(String numero, String name, String url) {
        this.numero = numero;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getNumero() {
        String[] urlPartes = url.split("/");
        return String.valueOf(urlPartes[urlPartes.length - 1]);
    }


}
