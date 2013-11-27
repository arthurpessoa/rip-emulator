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

import emulator.network.RoutinePacket;


public class Node2 {
    private RoutinePacket rpkt = new RoutinePacket();
    private DistanceTable costs = new DistanceTable();
    private int dirCost2[] = new int[4];
    
    public Node2(){
        
            
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
        
        //insere o custo direto do nó 2 para os demais
        costs.setCost(0, 0, 3);
        costs.setCost(1, 1, 1);
        costs.setCost(2, 2, 0);
        costs.setCost(3, 3, 2);
        
        dirCost2[0] = 3;
        dirCost2[1] = 1;
        dirCost2[2] = 0;
        dirCost2[3] = 2;
        
       //se o custo mínimo for maior do que o custo direto atualiza o custo minimo
       for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(rpkt.getMinCost(i) > costs.getCost(i, j)){
                    rpkt.setMincost(i, costs.getCost(i, j));
                }
            }
        }
    }
    
    public void rtinit2(){//inicializa o nó
        if(RIP.TRACE>0){
            System.out.println("\n\n-------------------------------------------------------------------------------------------------");
            System.out.print("DEBUG: Inserting Node2...\n");
        }
        if(RIP.TRACE==1||RIP.TRACE>2){
            printDt2();
        }
        // Envia os pacotes para os outros nós que possuem caminho direto (custo minimo < 999)
        if(dirCost2[0] < 999){
            rpkt.setSourceid(2);
            rpkt.setDestid(0);
            RIP.toLayer2(rpkt);
        }

        if(dirCost2[1] < 999){
            rpkt.setSourceid(2);
            rpkt.setDestid(1);
            RIP.toLayer2(rpkt);
        }
        
        if(dirCost2[3] < 999){
            rpkt.setSourceid(2);
            rpkt.setDestid(3);
            RIP.toLayer2(rpkt);
        }      
    
    }
    
    public void printDt2(){//imprime a tabela de distancias
        
        System.out.printf("\n");
        System.out.printf("   N2 | 0\t1\t3\n");
        System.out.printf("  ----|----------------------------\n");
        System.out.printf("    0 | " + this.costs.getCost(0, 0) +"\t" + this.costs.getCost(0, 1)+ "\t" + this.costs.getCost(0, 3)+"\n");
        System.out.printf("    1 | "+ this.costs.getCost(1, 0) + "\t" + this.costs.getCost(1, 1)+ "\t" + this.costs.getCost(1, 3)+"\n");
        System.out.printf("    3 | "+ this.costs.getCost(3, 0) + "\t" + this.costs.getCost(3, 1)+ "\t" + this.costs.getCost(3, 3)+"\n");
        System.out.printf("\n");
    }
    
    
    public void rtupdate2(RoutinePacket rcvdpkt){//atualiza os valores com base no pacote recebido
        
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
            if(RIP.TRACE>0){
                System.out.println("\n\n-------------------------------------------------------------------------------------------------");
                System.out.print("DEBUG: Updating Node2...\n");
            }
            if(RIP.TRACE>2){
                System.out.print("DEBUG: Significant Routine Packet Received from layer2. Source: "+rcvdpkt.getSourceid()+" Destination: " +rcvdpkt.getDestID());
                System.out.println(" Data: [ "+ rcvdpkt.getMinCost()[0]+" "+ rcvdpkt.getMinCost()[1]+" "+ rcvdpkt.getMinCost()[2]+" "+ rcvdpkt.getMinCost()[3]+" ]");
            }
            if(RIP.TRACE==1||RIP.TRACE>2){
                printDt2();
            }
            //caso tenha atualizado envia os pacotes para todos os nós que possuem ligação
            if(dirCost2[0] < 999){
                rpkt.setSourceid(2);
                rpkt.setDestid(0);
                RIP.toLayer2(rpkt);
            }

            if(dirCost2[1] < 999){
                rpkt.setSourceid(2);
                rpkt.setDestid(1);
                RIP.toLayer2(rpkt);
            }

            if(dirCost2[3] < 999){
                rpkt.setSourceid(2);
                rpkt.setDestid(3);
                RIP.toLayer2(rpkt);
            }
          
        }
        
    }
    
}
