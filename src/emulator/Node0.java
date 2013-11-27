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

public class Node0 {

    private RoutinePacket RoutinePacket = new RoutinePacket();
    private DistanceTable costs = new DistanceTable();
    private int dirCost0[] = new int[4];

    public Node0() {

        //inicializa o custo minimo de cada como infinito
        RoutinePacket.setMincost(0, 999);
        RoutinePacket.setMincost(1, 999);
        RoutinePacket.setMincost(2, 999);
        RoutinePacket.setMincost(3, 999);

        //inicializa o custo de cada nó como infinito
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                costs.setCost(i, j, 999);
            }
        }

        //insere o custo direto do nó 0 para os demais


        costs.setCost(0, 0, 0);
        costs.setCost(1, 1, 1);
        costs.setCost(2, 2, 3);
        costs.setCost(3, 3, 7);

        dirCost0[0] = 0;
        dirCost0[1] = 1;
        dirCost0[2] = 3;
        dirCost0[3] = 7;

        //se o custo mínimo for maior do que o custo direto atualiza o custo minimo
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (RoutinePacket.getMinCost(i) > costs.getCost(i, j)) {
                    RoutinePacket.setMincost(i, costs.getCost(i, j));
                }
            }
        }

    }

    public void rtinit0() {//inicializa o nó
        if(RIP.TRACE>0){
            System.out.println("\n\n-------------------------------------------------------------------------------------------------");
            System.out.print("DEBUG: Inserting Node0...\n");
        } 
        if(RIP.TRACE==1||RIP.TRACE>2){
            printDt0();
        } 
        // Envia os pacotes para os outros nós que possuem caminho direto (custo minimo < 999)
        if (dirCost0[1] < 999) {
            RoutinePacket.setSourceid(0);
            RoutinePacket.setDestid(1);
            RIP.toLayer2(RoutinePacket);
        }

        if (dirCost0[2] < 999) {
            RoutinePacket.setSourceid(0);
            RoutinePacket.setDestid(2);
            RIP.toLayer2(RoutinePacket);
        }

        if (dirCost0[3] < 999) {
            RoutinePacket.setSourceid(0);
            RoutinePacket.setDestid(3);
            RIP.toLayer2(RoutinePacket);
        }

    }

    public void printDt0() {//imprime a tabela de distancias
        
        System.out.printf("\n");
        System.out.printf("   N0 | 1\t2\t3\n");
        System.out.printf("  ----|----------------------------\n");
        System.out.printf("    1 | " + this.costs.getCost(1, 1) + "\t" + this.costs.getCost(1, 2) + "\t" + this.costs.getCost(1, 3) + "\n");
        System.out.printf("    2 | " + this.costs.getCost(2, 1) + "\t" + this.costs.getCost(2, 2) + "\t" + this.costs.getCost(2, 3) + "\n");
        System.out.printf("    3 | " + this.costs.getCost(3, 1) + "\t" + this.costs.getCost(3, 2) + "\t" + this.costs.getCost(3, 3) + "\n");
        System.out.printf("\n");

    }

    public void rtupdate0(RoutinePacket rcvdpkt) {//atualiza os valores com base no pacote recebido
        //inicializa uma flag como falso
        boolean flag = false;
        int i, j;

        //print tempo 
        //caso o custo atual for maior do que a soma do custo atualizado com o valor para chegar a tal nó
        //atualiza a tabela de custos e marca a flag como verdadeiro
        for (i = 0; i < 4; i++) {
            if (costs.getCost(i, rcvdpkt.getSourceid()) > rcvdpkt.getMinCost(i) + costs.getCost(rcvdpkt.getSourceid(), rcvdpkt.getSourceid())) {
                costs.setCost(i, rcvdpkt.getSourceid(), rcvdpkt.getMinCost(i) + costs.getCost(rcvdpkt.getSourceid(), rcvdpkt.getSourceid()));
                flag = true;
            }
        }

        //caso a flag seja verdadeira e o custo minimo for maior do que o custo, atualiza o custo minimo
        if (flag) {
            if(RIP.TRACE>0){
                System.out.println("\n\n-------------------------------------------------------------------------------------------------");
                System.out.print("DEBUG: Updating Node0...\n");
            }
            if(RIP.TRACE>2){
                System.out.print("DEBUG: Significant Routine Packet Received from layer2. Source: "+rcvdpkt.getSourceid()+" Destination: " +rcvdpkt.getDestID());
                System.out.println(" Data: [ "+ rcvdpkt.getMinCost()[0]+" "+ rcvdpkt.getMinCost()[1]+" "+ rcvdpkt.getMinCost()[2]+" "+ rcvdpkt.getMinCost()[3]+" ]");
            }
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    if (RoutinePacket.getMinCost(i) > costs.getCost(i, j)) {
                        RoutinePacket.setMincost(i, costs.getCost(i, j));
                    }
                }
            }
            
            
            if(RIP.TRACE==1||RIP.TRACE>2){
                printDt0();
            }
            //caso tenha atualizado envia os pacotes para todos os nós que possuem ligação
            if (dirCost0[1] != 999) {
                RoutinePacket.setSourceid(0);
                RoutinePacket.setDestid(1);
                RIP.toLayer2(RoutinePacket);
            }

            if (dirCost0[2] != 999) {
                RoutinePacket.setSourceid(0);
                RoutinePacket.setDestid(2);

                RIP.toLayer2(RoutinePacket);
            }

            if (dirCost0[3] != 999) {
                RoutinePacket.setSourceid(0);
                RoutinePacket.setDestid(3);
                RIP.toLayer2(RoutinePacket);
            }            
        }
    }
}
