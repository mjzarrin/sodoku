/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysodoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author vahid
 */
public class Mysodoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mysodoku s = new Mysodoku();
        Stack sodostack = new Stack();
        
        boolean[][][] sodobool = new boolean[9][9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    sodobool[i][j][k] = true;
                }
            }
        }

        boolean[][] newfind = new boolean[9][9];

        int[][] sodoku = s.reader();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sodoku[i][j] != 0) {
                    newfind[i][j] = true;
                } else {
                    newfind[i][j] = false;
                }
            }
        }

//        s.sodokusolver(sodoku, newfind, sodobool, false);
        sodostack.push(sodobool);
        sodostack.push(newfind);
        sodostack.push(sodoku);
        int x=0;
        int stackcount;
        int m=0;
        boolean turn =false;
        while(sodostack.size()>0){
            int[][] sodokut = (int[][])sodostack.pop();
            boolean[][] newfindt = (boolean[][])sodostack.pop();
            turn = true;
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                     if(newfindt[i][j] == true){
                         turn = false;
                         break;                      
                     }
                }
            }
            sodostack.push(newfindt);
            sodostack.push(sodokut);
            
            stackcount = sodostack.size();
            
            sodostack =  s.sodokusolver(sodostack, turn);
           
          x++;
          System.out.println(x);
        
        }
        System.out.println("Soleved");

    }
    
    public Stack sodokusolver(Stack st, boolean turn){
        
        int[][] sodoku = (int[][])st.pop();
        boolean[][] newfind = (boolean[][])st.pop();
        boolean[][][] sodobool= (boolean[][][])st.pop();
        
      
        
        boolean first;
        boolean second;
        if (turn) {
            first = true;
            second = false;
        } else {
            first = false;
            second = true;
        }
//        first = true;
//        second= true;
        boolean stop = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (newfind[i][j] == true) {
                    stop = false;
                }

            }
        }

        if (stop == true) {
            if (finish(sodoku)) {
                System.out.println("Sodoku solved");
                return new Stack();
            } else {
                int[][] count = new int[9][9];
                System.out.println("Solver Needs to guess");
                
                int cancontinue = 0;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (sodoku[i][j] == 0) {
                            int q = 0;
                            for (int k = 0; k < 9; k++) {
                                if (sodobool[i][j][k] == true) {
                                    cancontinue++;
                                    q = q + 1;
                                }

                            }
                            count[i][j] = q;
                        }

                    }
                }
                if(cancontinue>0){
                    int min=10;
                    int x=100;
                    int y=100;
                    for(int i=0;i<9;i++){
                        for(int j=0;j<9;j++){
                            if(sodoku[i][j] == 0){
                                if (count[i][j] < min){
                                    min = count[i][j];
                                    x=i;
                                    y=j;
                                }
                            }
                        }
                    }
                    for(int k=0;k<9;k++){
                        if(sodobool[x][y][k] == true){
                            int[][] sodostackitem = new int[9][9];
                            boolean[][] newfindstackitem= new boolean[9][9];
                            boolean[][][] sodoboolstackitem= new boolean[9][9][9];
                            for(int i=0;i<9;i++){
                                for(int j=0;j<9;j++){
                                    for(int m=0; m<9;m++){
                                        sodostackitem[i][j] = sodoku[i][j];
//                                        newfindstack[i][j] = newfind[i][j];
                                        sodoboolstackitem[i][j][m]= sodobool[i][j][m];
                                    }
                                }
                            }
                            sodostackitem[x][y] = k+1;
                            newfindstackitem[x][y] = true;

//                            sodoboolStack.push(sodoboolstackitem);
//                            newfindStack.push(newfindstackitem);
//                            sodokuStack.push(sodostackitem);
                             st.push(sodoboolstackitem);
                             st.push(newfindstackitem);
                             st.push(sodostackitem);
                             
//                            sodokusolver(sodostackitem, newfindstackitem,sodoboolstackitem, false);
                            
                        }
                        
                    }
                    return st;
                }else{
//                     sodokusolver((int[][])sodokuStack.pop(), (boolean[][])newfindStack.pop(),(boolean[][][]) sodoboolStack.pop(), false);
                       return st;     
                }
            }
        }
        // row col squ update sodobool
        for (int m = 0; m < 9; m++) {
            for (int n = 0; n < 9; n++) {
                if (newfind[m][n] == true) {
//                    row update and col
                    for (int j = 0; j < 9; j++) {

                        sodobool[m][j][sodoku[m][n] - 1] = false;
                        sodobool[j][n][sodoku[m][n] - 1] = false;
                        sodobool[m][n][j] = false;

                    }

                    int p = (m / 3) * 3;
                    int l = (n / 3) * 3;
                    for (int x = p; x < p + 3; x++) {
                        for (int y = l; y < l + 3; y++) {
//                           if( x==m && y==n){
//                               
//                           }else{
                            if (x == 0 && y == 4 && sodoku[m][n] == 6) {
                                System.out.println();
                            }
                            sodobool[x][y][sodoku[m][n] - 1] = false;
//                           }

                        }
                    }

                    sodobool[m][n][sodoku[m][n] - 1] = true;
                }
            }
        }

        // from sodobool find new digits
        boolean[][] newnew = new boolean[9][9];

        if (first) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    newnew[i][j] = false;
                }
            }

            int[][] counter = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (sodoku[i][j] == 0) {
                        // first law
                        int q = 0;
                        int index = 0;
                        for (int k = 0; k < 9; k++) {
                            if (sodobool[i][j][k] == true) {
                                index = k + 1;
                                q = q + 1;
                            }

                        }
                        counter[i][j] = q;
                        if (q == 1) {
                            if (i == 3 && j == 8) {
                                sodoku[i][j] = index;
                            }
                            sodoku[i][j] = index;
                            newnew[i][j] = true;
                        }
                    // end of first law

                    }
                }
            }
        }
        // second law              
        if (second) {
            int[][] rowCounter = new int[9][9];
            int[][] colCounter = new int[9][9];
            int[][] seqCounter = new int[9][9];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (sodoku[i][j] == 0) {
                        for (int k = 0; k < 9; k++) {
                            if (sodobool[i][j][k] == true) {
                                rowCounter[i][k] = rowCounter[i][k] + 1;
                                colCounter[j][k] = colCounter[j][k] + 1;
                                seqCounter[(i / 3) * 3 + (j / 3)][k] = seqCounter[(i / 3) * 3 + (j / 3)][k] + 1;

                            }
                        }
                    }
                }
            }
            for (int y = 0; y < 9; y++) {
                for (int k = 0; k < 9; k++) {
                    if (rowCounter[y][k] == 1) {
                        for (int j = 0; j < 9; j++) {
                            if (sodobool[y][j][k] == true) {
                                sodoku[y][j] = k + 1;
                                newnew[y][j] = true;
                            }
                        }
                    }
                    if (colCounter[y][k] == 1) {
                        for (int i = 0; i < 9; i++) {
                            if (sodobool[i][y][k] == true) {
                                sodoku[i][y] = k + 1;
                                newnew[i][y] = true;
                            }
                        }
                    }
                    if (seqCounter[y][k] == 1) {
                        int p = (y / 3) * 3;
                        int w = (y % 3) * 3;
                        for (int v = p; v < p + 3; v++) {
                            for (int c = w; c < w + 3; c++) {
                                if (sodobool[v][c][k] == true && sodoku[v][c] == 0) {
                                    sodoku[v][c] = k + 1;
                                    newnew[v][c] = true;
                                }
                            }
                        }
                    }

                }
            }
        }
        // end of second law
        System.out.println("*******************************");
        System.out.println(Arrays.deepToString(sodoku));
        if(cheching(sodoku)){
        st.push(sodobool);
        st.push(newnew);
        st.push(sodoku);
        }
        return st;
    }
    
    public boolean cheching(int[][] sodoku){
        int[][] row= new int[9][9];
        int[][] col= new int[9][9];
        int[][] squ= new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
               if(sodoku[i][j] != 0){
                   row[i][sodoku[i][j]-1]++;
                   col[j][sodoku[i][j]-1]++;           
                   squ[(i / 3) * 3 + (j/3)][sodoku[i][j]-1]++;

               }

            }
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(row[i][j]>1 ||col[i][j]>1 || squ[i][j] >1){
                    return false;
                }
            }
        }
        
        
        
        return true;
    }
    
    public int[][] reader() {
        int[][] sudoku = new int[9][9];

        File file = new File("src\\data\\data12.txt");

        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 9; i++) {
                String line = scanner.nextLine();
                char[] cArray = line.toCharArray();

                int element = cArray.length - 9;
                for (int j = 0; j < 9; j++) {

                    int t = Character.getNumericValue(cArray[j + element]);
                    System.out.print(t + " ");
                    sudoku[i][j] = t;

                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }

//        try {
//            Scanner scanner = new Scanner(file);
//            
//            for (int i = 0; i < 9; i++) {
//                String line = scanner.nextLine();
//                System.out.println(line);
//                for (int j = 0; j <9; j++) {
//                    int t = Character.getNumericValue(line.charAt(j)); 
//                    if(t == -1){
//                        j=0;
//                        reserve = Character.getNumericValue(line.charAt(9));
//                        continue;
//                    }
//                    System.out.print(t+ " ");
//                    sudoku[i][j] = t;
//                    
//                }
//            }
//            scanner.close();
//        } catch (FileNotFoundException e) {
//        }
        System.out.println(Arrays.deepToString(sudoku));

        return sudoku;
    }
    
    public boolean finish(int[][] sodoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sodoku[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;

    }
    
}
