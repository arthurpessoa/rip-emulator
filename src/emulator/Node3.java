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

public class Node3 {

    private RoutinePacket rpkt = new RoutinePacket();
    private DistanceTable costs = new DistanceTable();
    private int dirCost3[] = new int[4];

    public Node3() {

        //inicializa o custo minimo de cada como infinito
        rpkt.setMincost(0, 999);
        rpkt.setMincost(1, 999);
        rpkt.setMincost(2, 999);
        rpkt.setMincost(3, 999);

        //inicializa o custo de cada nó como infinito
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                costs.setCost(i, j, 999);
            }
        }

        //insere o custo direto do nó 1 para os demais
        costs.setCost(0, 0, 7);
        costs.setCost(1, 1, 999);
        costs.setCost(2, 2, 2);
        costs.setCost(3, 3, 0);

        dirCost3[0] = 7;
        dirCost3[1] = 999;
        dirCost3[2] = 2;
        dirCost3[3] = 0;

        //se o custo mínimo for maior do que o custo direto atualiza o custo minimo
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (rpkt.getMinCost(i) > costs.getCost(i, j)) {
                    rpkt.setMincost(i, costs.getCost(i, j));
                }
            }
        }
    }

    public void rtinit3() {//inicializa o nó
        if (RIP.TRACE > 0) {
            System.out.println("\n\n-------------------------------------------------------------------------------------------------");
            System.out.print("DEBUG: Updating Node3...\n");
        }

        if (RIP.TRACE == 1 || RIP.TRACE > 2) {
            printDt3();
        }
        // Envia os pacotes para os outros nós que possuem caminho direto (custo minimo < 999)
        if (dirCost3[0] < 999) {
            rpkt.setSourceid(3);
            rpkt.setDestid(0);
            RIP.toLayer2(rpkt);
        }

        if (dirCost3[1] < 999) {
            rpkt.setSourceid(3);
            rpkt.setDestid(1);
            RIP.toLayer2(rpkt);
        }

        if (dirCost3[2] < 999) {
            rpkt.setSourceid(3);
            rpkt.setDestid(2);
            RIP.toLayer2(rpkt);
        }

    }

    public void printDt3() {//imprimi a tabela de distancias
        System.out.printf("\n");
        System.out.printf("   N3 | 0\t1\t2\n");
        System.out.printf("  ----|----------------------------\n");
        System.out.printf("    0 | " + this.costs.getCost(0, 0) + "\t" + this.costs.getCost(0, 1) + "\t" + this.costs.getCost(0, 2) + "\n");
        System.out.printf("    1 | " + this.costs.getCost(1, 0) + "\t" + this.costs.getCost(1, 1) + "\t" + this.costs.getCost(1, 2) + "\n");
        System.out.printf("    2 | " + this.costs.getCost(2, 0) + "\t" + this.costs.getCost(2, 1) + "\t" + this.costs.getCost(2, 2) + "\n");
        System.out.printf("\n");
    }

    public void rtupdate3(RoutinePacket rcvdpkt) {//atualiza os valores com base no pacote recebido
        //inicializa uma falg como falso
        boolean flag = false;
        int i, j;

        //print tempo 
        //caso o custo atual for maior do que a soma do custo atualizado com o valor para chegar a tal nó
        //atualiza a tabela de custos e marca a flag como verdadeiro
        for (i = 0; i < 4; i++) {
            //se custo atual for maior do que o custo minimo + o custo direto do nó atual para o nó destino atualiza custo
            if (costs.getCost(i, rcvdpkt.getSourceid()) > (rcvdpkt.getMinCost(i) + costs.getCost(rcvdpkt.getSourceid(), rcvdpkt.getSourceid()))) {
                costs.setCost(i, rcvdpkt.getSourceid(), rcvdpkt.getMinCost(i) + costs.getCost(rcvdpkt.getSourceid(), rcvdpkt.getSourceid()));
                flag = true;
            }
        }

        //caso a flag seja verdadeira e o custo minimo for maior do que o custo, atualiza o custo minimo
        if (flag) {
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    if (rpkt.getMinCost(i) > costs.getCost(i, j)) {
                        rpkt.setMincost(i, costs.getCost(i, j));
                    }
                }
            }
            if (RIP.TRACE > 0) {
                System.out.println("\n\n-------------------------------------------------------------------------------------------------");
                System.out.print("DEBUG: Updating Node3...\n");
            }
            if (RIP.TRACE > 2) {
                System.out.print("DEBUG: Significant Routine Packet Received from layer2. Source: " + rcvdpkt.getSourceid() + " Destination: " + rcvdpkt.getDestID());
                System.out.println(" Data: [ " + rcvdpkt.getMinCost()[0] + " " + rcvdpkt.getMinCost()[1] + " " + rcvdpkt.getMinCost()[2] + " " + rcvdpkt.getMinCost()[3] + " ]");
            }
            if (RIP.TRACE == 1 || RIP.TRACE > 2) {
                printDt3();
            }

            //caso tenha atualizado envia os pacotes para todos os nós que possuem ligação
            if (dirCost3[0] < 999) {
                rpkt.setSourceid(3);
                rpkt.setDestid(0);
                RIP.toLayer2(rpkt);
            }

            if (dirCost3[1] < 999) {
                rpkt.setSourceid(3);
                rpkt.setDestid(1);
                RIP.toLayer2(rpkt);
            }

            if (dirCost3[2] < 999) {
                rpkt.setSourceid(3);
                rpkt.setDestid(2);
                RIP.toLayer2(rpkt);

            }

        }
    }
}