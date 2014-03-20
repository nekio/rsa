package analizador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ISC. Sinesio Ivan Carrillo Heredia
 */
public class AnalizadorTexto {

    public enum Alfabeto {
        A(true), B, C, D, E(true),
        F, G, H, I(true), J,
        K, L, M, N, Ñ,
        O(true), P, Q, R, S,
        T, U(true), V, W, X,
        Y, Z;
        
        private static final Alfabeto[] simbolosAlfabeto = values();
        private int acumuladorSimbolo;
        
        private boolean vocal;

        private Alfabeto() {
            this(false);
        }

        private Alfabeto(boolean vocal) {
            this.vocal = vocal;
            this.acumuladorSimbolo=0;
        }

        public static boolean isSimboloValido(char simbolo) {
            String simboloString = String.valueOf(simbolo).toUpperCase();
            
            for (Alfabeto simboloAlfabeto : simbolosAlfabeto) {
                if (simboloAlfabeto.name().equals(simboloString))
                    return true;
            }
            return false;
        }

        public boolean isVocal() {
            return vocal;
        }

        public char getMayuscula() {
            String mayuscula = this.name();
            return mayuscula.charAt(0);
        }

        public char getMinuscula() {
            String minuscula = this.name();
            return minuscula.charAt(0);
        }
        
        public void aumentarAcumuladorSimbolo(){
            this.acumuladorSimbolo++;
        }
        
        public int getAcumuladorSimbolo(){
            return this.acumuladorSimbolo;
        }
    };
    
    private String texto;
    private int simbolosValidos;
    private int simbolosNoValidos;
    private int simbolosTotalesIngresados;
    private List<Character> simbolosValidosDiferentesIngresados;
    private List<Character> simbolosNoValidosDiferentesIngresados;

    public AnalizadorTexto() {
        this(null);
    }

    public AnalizadorTexto(String texto) {
        this.texto = texto;
        this.simbolosValidos=0;
        this.simbolosNoValidos=0;
        this.simbolosTotalesIngresados=0;
        this.simbolosValidosDiferentesIngresados=new ArrayList<>();
        this.simbolosNoValidosDiferentesIngresados=new ArrayList<>();
        
        procesar();
        
        getRepeticiones();
        getSimbolosValidosDiferentes();
        getSimbolosNoValidosDiferentes();
        getEstadisticas();
    }
    
    public void procesar(){
        for(char simbolo:texto.toCharArray()){
            if(simbolo != ' '){
                simbolosTotalesIngresados++;
                
                if(Alfabeto.isSimboloValido(simbolo)){
                    //Incrementar el contador de simbolos validos
                    this.simbolosValidos++;
                    
                    //Incrementar el contador de simbolos validos diferentes si no existe en la lista respectiva
                    if(!simbolosValidosDiferentesIngresados.contains(simbolo))
                        simbolosValidosDiferentesIngresados.add(simbolo);
                    
                    //Aumentar el contador de cada simbolo encontrado
                    Alfabeto.valueOf(String.valueOf(simbolo).toUpperCase()).aumentarAcumuladorSimbolo();
                }else{
                    this.simbolosNoValidos++;
                    
                    //Incrementar el contador de simbolos no validos diferentes si no existe en la lista respectiva
                    if(!simbolosNoValidosDiferentesIngresados.contains(simbolo))
                        simbolosNoValidosDiferentesIngresados.add(simbolo);
                }
            }
        }
    }
    
    public void getRepeticiones() {     
        System.out.println("\n---REPETICIONES---");
        
        List<Alfabeto> listaSimbolos = new ArrayList<>();
        List<Integer> listaRepeticiones = new ArrayList<>();
        List<List> lista = new ArrayList<>();
        int repeticiones = 0;
        for (Alfabeto simbolo : Alfabeto.values()) {
            repeticiones = Alfabeto.valueOf(String.valueOf(simbolo).toUpperCase()).getAcumuladorSimbolo();
            System.out.println(simbolo+" : "+repeticiones);
            
            listaSimbolos.add(simbolo);
            listaRepeticiones.add(repeticiones);
        }
        lista.add(repeticiones, lista);
    }

    public void getSimbolosValidosDiferentes() {
        System.out.println("\n---SIMBOLOS VALIDOS DIFERENTES---");
        
        System.out.println("Total: "+this.simbolosValidosDiferentesIngresados.size());
        Collections.sort(simbolosValidosDiferentesIngresados);
        
        int indice=1;
        for(char simbolo:simbolosValidosDiferentesIngresados)
            System.out.println("\t("+(indice++)+") "+simbolo);
    }
    
    public void getSimbolosNoValidosDiferentes() {
        System.out.println("\n---SIMBOLOS NO VALIDOS DIFERENTES---");
        
        System.out.println("Total: "+this.simbolosNoValidosDiferentesIngresados.size());
        Collections.sort(simbolosNoValidosDiferentesIngresados);
        
        int indice=1;
        for(char simbolo:simbolosNoValidosDiferentesIngresados)
            System.out.println("\t("+(indice++)+") "+simbolo);
    }
    
    public void getEstadisticas(){
        System.out.println("\n---ESTADISTICAS---");
        System.out.println("Simbolos validos: "+this.simbolosValidos);
        System.out.println("Simbolos no validos: "+this.simbolosNoValidos);
        System.out.println("Total de Simbolos Ingresados: "+this.simbolosTotalesIngresados);
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
