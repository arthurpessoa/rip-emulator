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

public class DistanceTable {
    private int costs[][] = new int[4][4];
    
    public int getCost(int x, int y){
        return costs[x][y];
    }

    public void setCost(int x, int y, int cost){
        this.costs[x][y] = cost;
    }
}
