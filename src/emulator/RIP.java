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

import emulator.network.NetworkEvent;
import emulator.network.RoutinePacket; 

import java.util.ArrayList;
import java.util.Random;

public class RIP {
    
    private static Node0 node0 = new Node0();
    private static Node1 node1 = new Node1();
    private static Node2 node2 = new Node2();
    private static Node3 node3 = new Node3();   
    
    //gambis
    public static int TRACE = 3;    
    public static int FROM_LAYER2 = 2;
    
    
    private static ArrayList<NetworkEvent> NetworkEventList = new ArrayList<>();
    private static RoutinePacket myRoutinePacket = new RoutinePacket();
    private static float lastime;
         
    public static void main(String[] args) {
    
        NetworkEvent networkEvent;
        //inicia os nós
        init();
        //enquanto a lista de eventos não está vazia permanece no loop 
        while (!NetworkEventList.isEmpty()){                      
            networkEvent = NetworkEventList.get(NetworkEventList.size()-1);  // pega o próximo evento
            NetworkEventList.remove(NetworkEventList.size()-1); //remove primeiro elemento da lista

            if(networkEvent == null){
                break;
            }    
            
            /*
            if(TRACE>2){
                System.out.print("DEBUG: Routine Packet Received from layer2. Source: "+networkEvent.getRtpkt().getSourceid()+" Destination: " +networkEvent.getRtpkt().getDestID());
                System.out.println(" Data: [ "+ myRoutinePacket.getMinCost()[0]+" "+ myRoutinePacket.getMinCost()[1]+" "+ myRoutinePacket.getMinCost()[2]+" "+ myRoutinePacket.getMinCost()[3]+" ]" + " Timestamp: "+networkEvent.getEvtime());
            } 
            */
            
            //faz o update dos eventos
            if (networkEvent.getEvtype() == FROM_LAYER2){
                if (networkEvent.getEventity() == 0){
                    node0.rtupdate0(networkEvent.getRtpkt());
                }else if(networkEvent.getEventity() == 1){
                    node1.rtupdate1(networkEvent.getRtpkt());
                }else if(networkEvent.getEventity() == 2){
                    node2.rtupdate2(networkEvent.getRtpkt());              
                }else if(networkEvent.getEventity() == 3){
                    node3.rtupdate3(networkEvent.getRtpkt());              
                }else{
                    System.out.print("Panic: unknown event entity");
                }
            }else{
                System.out.print("Panic: unknown event type");
            }           
            
        }
        
        System.out.print("Final Result: \n");
        node0.printDt0();
        node1.printDt1();
        node2.printDt2();
        node3.printDt3();
                
        System.out.print("\nSimulator terminated, no packets in medium\n");
    }
    
    public static void init(){
                
        //Inicialização dos nós
        node0.rtinit0();
        node1.rtinit1();
        node2.rtinit2();
        node3.rtinit3();
    }
    
    
    /* 
     * Essa Função é responsável por enviar um pacote de rotina para a 
     * layer2 (camada de enlace)
     */
    public static void toLayer2(RoutinePacket pkt){
        
        Random rn = new Random();
        float timestamp = lastime + 2 * (rn.nextFloat() % 1);
        boolean directConnected[][] = new boolean[4][4];
        //Verificando na layer2 quem está conectado diretamente a quen
        //Conectividade do nó 0 para os demais      
        directConnected[0][0]=true;
        directConnected[0][1]=true;  
        directConnected[0][2]=true;
        directConnected[0][3]=true;
        
        
        //Conectividade do nó 1 para os demais
        directConnected[1][0]=true;  
        directConnected[1][1]=true; 
        directConnected[1][2]=true;
        directConnected[1][3]=false; //não ligados diretamente
        
        //Conectividade do nó 2 para os demais
        directConnected[2][0]=true; 
        directConnected[2][1]=true;  
        directConnected[2][2]=true;
        directConnected[2][3]=true;
        
        //Conectividade do nó 3 para os demais        
        directConnected[3][0]=true;  
        directConnected[3][1]=false; 
        directConnected[3][2]=true;
        directConnected[3][3]=true;
        
        //  verifica se não tem nenhum problema com a origem e destino dos pacotes 
        //Caso os pacotes de fonte estejam fora do intervalo (0~3)
        if (pkt.getSourceid() <0 || pkt.getSourceid() >3)
        {
          System.out.printf("WARNING: illegal source id in your packet, ignoring packet!\n");
          return;
        }
        
        //Caso os pacotes de destino estejam fora do intervalo (0~3)
        if (pkt.getDestID() <0 || pkt.getDestID() >3) 
        {
          System.out.printf("WARNING: illegal dest id in your packet, ignoring packet!\n");
          return;
        }
        
        //caso destino e fonte sejam o mesmo
        if (pkt.getSourceid() == pkt.getDestID())  {
          System.out.printf("WARNING: source and destination id's the same, ignoring packet!\n");
          return;
        }
        
        //Caso fonte e destino não estejam conectados
        if(directConnected[pkt.getSourceid()][pkt.getDestID()] == false)
        {
          System.out.printf("WARNING: source and destination not connected, ignoring packet!\n");  
          return;
        }
        
       //Inicializa os valores ao pacote que irá para a lista de eventos
       myRoutinePacket.create(pkt.getSourceid(), pkt.getDestID(), pkt.getMinCost());         
       //adiciona o evento para a lista de eventos
              
       NetworkEventList.add(new NetworkEvent(timestamp, FROM_LAYER2, myRoutinePacket.getDestID(), myRoutinePacket));
       
       
       //Debug
       if(TRACE>=2){
           System.out.print("DEBUG: Routine Packet sent to layer2. Source: "+myRoutinePacket.getSourceid()+" Destination: " +myRoutinePacket.getDestID());
           System.out.println(" Data: [ "+ myRoutinePacket.getMinCost()[0]+" "+ myRoutinePacket.getMinCost()[1]+" "+ myRoutinePacket.getMinCost()[2]+" "+ myRoutinePacket.getMinCost()[3]+" ]" + " Timestamp: "+timestamp);
       }    
    }
}