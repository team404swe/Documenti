/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glossariocheck;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class GlossarioCheck {
    
    private ArrayList<String> file1;
    private ArrayList<String> file2;
    private final String mezzo;
    private  String nome1;
    private  String nome2;
    private final String regex1;
    private final String regex2;
    private ArrayList<Lista> lista1;
    private ArrayList<Lista> lista2;
    
    
    public GlossarioCheck(){
        this.mezzo = "([._[^\\}]]*)";
        this.regex1 = "\\\\inglos\\{"+mezzo+"\\}";
        this.regex2 = "\\\\itemglos\\{"+mezzo+"\\}";
        this.lista1 = new ArrayList<>();
        this.lista2 = new ArrayList<>();
    }
    public GlossarioCheck(String f1) throws IOException{
        this();
        this.nome1 = f1;
        this.file1 = this.fileToString(f1); 
        this.lista1 = this.Colletta(this.file1, regex1);
    }
    public GlossarioCheck(String f1,String f2) throws IOException{
        this(f1);
        this.nome2 = f2;
        this.file2 = this.fileToString(f2);  
        this.lista2 = this.Colletta(this.file2, regex2);
    }
    
    private ArrayList<String> fileToString(String path) throws IOException{        
        ArrayList<String> content = readFile(path, Charset.defaultCharset());
        return content;
    }
    /**
     * controlla se i termini nel file 1 compaiono nel file 2
     */
    public void check(){
        String success = " ****RISULTATI POSITIVI******* \n";
        String fail = "**********RISULTATI NEGATIVI******** \n";
        int index = -1;
        for(Lista lista:this.lista1){
            index++;
            //String rgx = this.regex2.replace(mezzo,lista.parola );
            int index2 = -1;
            boolean trovato = false;
            for(Lista glos: this.lista2 ){
                index2++;
                if(lista.parola.equalsIgnoreCase(glos.parola)){
                    trovato = true;
                    
                    break;
                }
            }
            if(trovato == true){
                success += "Parola Trovata!! => ' " +lista.parola.toString()+" '  \n";
            }else{
                fail += "Parola mancante in riga ' "+index+" ' => ' "
                        +lista.parola.toString()+" '\n";
            }                
        }
        System.out.println("***FILE INPUT  => "+ this.nome1);
        System.out.println("***FILE OUTPUT => "+ this.nome2);
        System.out.println(success);
        System.out.println(fail);
    }

    private ArrayList<Lista> Colletta(ArrayList<String> file1, String regex1) {
        
        ArrayList<Lista>  lista = new ArrayList<>();
        for(String riga: file1){
            lista.addAll( this.Colletta(riga, regex1));            
        }
        return lista;
    }
    /**
     * cerca nel file le parole dell'espressione regolare e le ritorna nella lista
     * @param text
     * @param regex
     * @return
     */
    private  ArrayList<Lista> Colletta(String text, String regex){
        ArrayList<Lista> lista = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int i = 0;
        while(matcher.find()) {
            i++;
            lista.add(new Lista(matcher.start(), matcher.end(), matcher.group(1)));            
        }
        return lista;
    }
    
    public void tester(){
        String text =   "\\inglos{MaterializeCss} creato da "
                        +"\\inglos{Google} e basato sul \\inglos{Material  "
                        +"Design} \n e compatibile con i maggiori browser. "
                        +"MaterializeCss è un semplice framework di interfaccia"
                        +" utente \\inglos{frontend} per la costruzione per la "
                        +"realizzazione di progetti web \\inglos{responsive e stupido 2.0} e \n"
                        +"\\inglos{user-friendly}. Per usarlo, basterà inserire"
                        +" all'interno delle pagine \\inglos{HTML} principali "
                        +"del seguente codice:";
        Pattern patt = Pattern.compile(regex1);
        //Pattern patt = Pattern.compile("\\\\inglos\\{([a-zA-Z_0-9_ _.]*)\\}");
        Matcher match = patt.matcher(text);
        int i = 0;
        while(match.find()) {
            i++;
            System.out.println("Sottosequenza : "+match.group());
            System.out.println("Start: "+match.start() + "\t End: "+match.end()+ "."); 
            System.out.println("Sottogruppo 1 : "+match. group(1));
        }
        System.out.println("Match trovati : "+i);
    }

    public static ArrayList<String> readFile(String path, Charset encoding) 
                                        throws IOException {
       // byte[] encoded = Files.readAllBytes(Paths.get(path));
        
        //List<String> lines =
         return     (ArrayList<String>) Files.readAllLines(Paths.get(path), encoding);
        //return new String(encoded, encoding);
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */    
    public static void main(String[] args) throws IOException {
        GlossarioCheck Checker; // = new GlossarioCheck();
        // TODO code application logic here
    //    if (args.length == 2) {
            Checker = new GlossarioCheck(args[0], args[1]);
    //    } else if(args.length == 3) {
    //        Checker = new GlossarioCheck("Norme_di_Progetto_2.0.tex", "template.tex");
    //    }
        
    //    Checker.tester();
        Checker.check();
        
        
    }

    
    
    class Lista{
        public int inizio;
        public int fine;
        public String parola;        
        public Lista(){
        this.inizio = -1; this.fine = -1; this.parola = "";
        }
        public Lista(int i,int f,String p){
            this.inizio = i; this.fine = f; this.parola = p;
        }
        @Override
        public String toString(){            
            return "la parola '"+this.parola+"' dalla colonna: '"+this.inizio+"' a col. '"+this.fine+"' \n";
        }
    }
}


