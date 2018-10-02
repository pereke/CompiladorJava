package afd;

import org.antlr.v4.runtime.Token;

import java.io.File;
import java.util.List;

public class AFD {
    private GraphViz gv;
    private String extensaoArquivo;
    private String tipoDeRepresentacao;
    private List<Token> listaTokens;

    public AFD(List<Token> listaTokens, String extensaoArquivo) {
        gv = new GraphViz();
        this.extensaoArquivo = extensaoArquivo;
        tipoDeRepresentacao = "dot";
        this.listaTokens = listaTokens;

        construirAFD();

        criarArquivo();
    }

    private void construirAFD() {
        gv.addln(gv.start_graph());

        gv.addln("Inicio [shape=plaintext]");
        gv.addln("qf [shape=doublecircle]\n");

        int tamLista = listaTokens.size();
        
        if (tamLista > 0) {
            gv.addln("Inicio -> q0;");
            int numV = 0
            for (int i = 0; i < (tamLista - 2); i++) {
                if (listaTokens.get(i).getType() == 106){
                    gv.addln("q" + numV + " -> q" + (numV + 1) + " [label=\letra + \];");
                    gv.addln("q" + (numV+1) + " -> q" + (numV+1 ) + " [label=\letra ou digito + \];");
                    numV = numV + 1;
                }
                gv.addln("q" + numV + " -> q" + (numV + 1) + " [label=\"" + listaTokens.get(i).getText() + "\"];");
                numV = numV + 1;
                
            }
            gv.addln("q" + (numV) + " -> qf [label=\"" + listaTokens.get(tamLista - 2).getText() + "\"];");
        }
        else {
            gv.addln("Inicio -> qf[label=\"  .\"];");
        }

        gv.addln(gv.end_graph());
        gv.increaseDpi();
    }

    private void criarArquivo() {
        File arquivoSaida = new File("D:/CompiladorJava/afd." + extensaoArquivo);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), extensaoArquivo, tipoDeRepresentacao), arquivoSaida);
    }
}
