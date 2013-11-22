/* 
 * Universidade Federal de São Carlos - Campus Sorocaba
 * Projeto: RIPEmulator
 * 
 * Professora: Yeda Regina Venturini
 * 
 * Autores: Adriano Rodrigues
 *          Arthur Pessoa
 *          João Eduardo
 *          Victor Marucci
 * 
 */

package emulator;


public class Node1 {
    private RoutinePacket rpkt = new RoutinePacket();
    private DistanceTable costs = new DistanceTable();
    private int dirCost1[] = new int[4];
    
    public Node1(){
        
        
        //inicializa o custo minimo de cada como infinito
        rpkt.setMincost(0, 999);
        rpkt.setMincost(1, 999);
        rpkt.setMincost(2, 999);
        rpkt.setMincost(3, 999);
                
        //inicializa o custo de cada nó como infinito
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                costs.setCost(i, j, 999);
            }
        }
        
        //insere o custo direto do nó 1 para os demais
        costs.setCost(0, 0, 1);
        costs.setCost(1, 1, 0);
        costs.setCost(2, 2, 1);
        costs.setCost(3, 3, 999);

        dirCost1[0] = 1;
        dirCost1[1] = 0;
        dirCost1[2] = 1;
        dirCost1[3] = 999;
        
       //se o custo mínimo for maior do que o custo direto atualiza o custo minimo
       for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(rpkt.getMinCost(i) > costs.getCost(i, j)){
                    rpkt.setMincost(i, costs.getCost(i, j));
                }
            }
        }
    }
    
    public void rtinit1(){//inicializa o nó

        // Envia os pacotes para os outros nós que possuem caminho direto (custo minimo < 999)
        if(dirCost1[0] < 999){
            rpkt.setSourceid(1);
            rpkt.setDestid(0);
            
            NetworkEmulator.toLayer2(rpkt);
        }

        if(dirCost1[2] < 999){
            rpkt.setSourceid(1);
            rpkt.setDestid(2);
            
            NetworkEmulator.toLayer2(rpkt);
        }
        
        if(dirCost1[3] < 999){
            rpkt.setSourceid(1);
            rpkt.setDestid(3);
            
            NetworkEmulator.toLayer2(rpkt);
        }
        System.out.print("Inserting Node1...\n");
        
        printDt1();
    }
    
    public void printDt1(){//imprime a tabela de distancias para o nó 1
        System.out.printf("\n");
        System.out.printf("   N1 | 0\t2\t3\n");
        System.out.printf("  ----|----------------------------\n");
        System.out.printf("    0 | " + this.costs.getCost(0, 0) +"\t" + this.costs.getCost(0, 2)+ "\t" + this.costs.getCost(0, 3)+"\n");
        System.out.printf("    2 | "+ this.costs.getCost(2, 0) + "\t" + this.costs.getCost(2, 2)+ "\t" + this.costs.getCost(2, 3)+"\n");
        System.out.printf("    3 | "+ this.costs.getCost(3, 0) + "\t" + this.costs.getCost(3, 2)+ "\t" + this.costs.getCost(3, 3)+"\n");
        System.out.printf("\n");
    }
        
    public void rtupdate1(RoutinePacket rcvdpkt){//atualiza os valores com base no pacote recebido
        
        //inicializa uma flag como falso
        boolean flag = false;
        int i, j;
        
        //print tempo 
        //caso o custo atual for maior do que a soma do custo atualizado com o valor para chegar a tal nó
        //atualiza a tabela de custos e marca a flag como verdadeiro
        for(i=0;i<4;i++){
            if(costs.getCost(i, rcvdpkt.getSourceid()) > rcvdpkt.getMinCost(i) + costs.getCost(rcvdpkt.getSourceid(), rcvdpkt.getSourceid())){
                costs.setCost(i, rcvdpkt.getSourceid(), rcvdpkt.getMinCost(i) + costs.getCost(rcvdpkt.getSourceid(), rcvdpkt.getSourceid()));
                flag = true;
            }
        }
        
        //caso a flag seja verdadeira e o custo minimo for maior do que o custo, atualiza o custo minimo
        if(flag){
            for(i=0;i<4;i++){
                for(j=0;j<4;j++){
                    if(rpkt.getMinCost(i) > costs.getCost(i, j)){
                        rpkt.setMincost(i, costs.getCost(i, j));
                    }
                }
            }
        
        //caso tenha atualizado envia os pacotes para todos os nós que possuem ligação
        if(dirCost1[0] < 999){
            rpkt.setSourceid(1);
            rpkt.setDestid(0);
            NetworkEmulator.toLayer2(rpkt);
        }

        if(dirCost1[2] < 999){
            rpkt.setSourceid(1);
            rpkt.setDestid(2);
            NetworkEmulator.toLayer2(rpkt);
        }
        
        if(dirCost1[3] < 999){
            rpkt.setSourceid(1);
            rpkt.setDestid(3);
            NetworkEmulator.toLayer2(rpkt);
        }
        
    
            
        System.out.print("updating Node1...\n");
        printDt1();
        }
    }
    
}
