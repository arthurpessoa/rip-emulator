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


public class RoutinePacket {
  private int sourceID;     /* id of sending router sending this pkt */
  private int destID;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  private int mincost[] = new int[4];  /* min cost to node 0 ... 3 */

  public RoutinePacket(){
      
  }
  
  public void setSourceid(int id){
      this.sourceID = id;
  }
  
  public void setDestid(int id){
      this.destID = id;
  }
  
  public void setMincost(int pos, int cost){
      this.mincost[pos] = cost;
  }

  public int getSourceid(){
      return sourceID;
  }
  
  public int getDestID(){
      return destID;
  }
  
  public int getMinCost(int pos){
      return mincost[pos];      
  }
  
  public int[] getMinCost(){ //sobrecarga que retorna todos os custos
      return mincost;      
  }
  
  public void create(int srcid, int destidr, int[] mincosts){
     this.sourceID = srcid;
     this.destID = destidr;     
     this.mincost = mincosts.clone();
  }  
}