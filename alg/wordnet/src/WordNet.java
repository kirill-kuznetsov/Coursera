import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.TopologicalX;

import java.util.*;

/**
 * Created by kkuznets on 29.04.2016.
 */
public class WordNet {
    private Digraph net;
    private Set<String> nouns = new HashSet<>();
    private String[] synsets;
    // constructor takes the name of the two input files
    public WordNet(String synsetsPath, String hypernymsPath){
        if(synsetsPath == null || hypernymsPath == null){
            throw new NullPointerException();
        }
        In synsetsIn = new In(synsetsPath);
        List<String> sList = new ArrayList<>();
        while(synsetsIn.hasNextLine()){
            String synset = synsetsIn.readLine().split(",")[1];
            sList.add(synset);
            Collections.addAll(nouns, synset.split(" "));
        }
        synsetsIn.close();
        synsets =  sList.toArray(new String[sList.size()]);
        net = new Digraph(synsets.length);
        In hypernymsIn = new In(hypernymsPath);
        while(hypernymsIn.hasNextLine()){
            String[] h = hypernymsIn.readLine().split(",");
            if(h.length > 1) {
                int hyponym = Integer.parseInt(h[0]);
                for (int i = 1; i < h.length; i++){
                    net.addEdge(hyponym, Integer.parseInt(h[i]));
                }
            }
        }
        hypernymsIn.close();
        boolean rooted = false;
        for(int i = 0; i < net.V(); i ++){
            if(net.outdegree(i) == 0 ){
                if(rooted){
                    rooted = false;
                    break;
                } else {
                    rooted = true;
                }
            }
        }
        if(!rooted || !new TopologicalX(net).hasOrder()){
            throw new IllegalArgumentException();
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null){
            throw new NullPointerException();
        }
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA == null || nounB == null){
            throw new NullPointerException();
        }
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException ();
        }
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null){
            throw new NullPointerException();
        }
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException ();
        }
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args){
        String path = "wordnet\\";
        String synsetsPath = path + "synsets6.txt";
        String hypernymsPath = path + "hypernyms6TwoAncestors.txt";
        WordNet wn = new WordNet(synsetsPath, hypernymsPath);
        System.out.println(wn.nouns());
        System.out.println("a " + wn.isNoun("a"));
        System.out.println("c " + wn.isNoun("c"));
        System.out.println("asd " + wn.isNoun("asd"));
    }

}
