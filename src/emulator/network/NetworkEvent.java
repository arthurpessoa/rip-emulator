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

package emulator.network;

public class NetworkEvent {
           
    private float evtime; /* event time */
    private int evtype; /* event type code */
    private int eventity; /* entity where event occurs */
    private RoutinePacket rtpkt; /* ptr to packet (if any) assoc w/ this event */
    
    public NetworkEvent(float time, int type, int entity, RoutinePacket rt){
     this.evtime = time;   
     this.evtype = type;   
     this.eventity = entity;   
     this.rtpkt = rt;   
    }
    
    public void setEvtime(float ev){
        this.evtime =  ev;
    }
    
     public float getEvtime(){
        return evtime;
    }
     
     public void setEvtype(int ev){
        this.evtype =  ev;
    }
    
     public int getEvtype(){
        return evtype;
    }
   
     public void setEventity(int ev){
        this.eventity =  ev;
    }
    
     public int getEventity(){
        return eventity;
    }
     
    public void setRtpkt(RoutinePacket rt){
        this.rtpkt =  rt;
    }
     
    public RoutinePacket getRtpkt(){
        return rtpkt;
    }

}
