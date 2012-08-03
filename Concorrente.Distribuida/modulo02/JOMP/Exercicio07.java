package JOMP;

import jomp.runtime.OMP;

public class Exercicio07 {


    public static void main(String[] args) {
        int[][] matrizA = new int[10][10];
        int[][] matrizB = new int[10][10];
        int[][] matrizC = new int[10][10];

        // insere valores na matriz
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrizA[i][j] = (int) (Math.random() * 5);
                matrizB[i][j] = (int) (Math.random() * 5);
            }
        }

        OMP.setNumThreads(10);
        int linhaGrupo1;
        int linhaGrupo2;
        int limite1 = (int) (matrizA.length / 2);
        int limite2 = matrizA.length;

        int myid;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.limite2 = limite2;
  __omp_Object0.limite1 = limite1;
  __omp_Object0.matrizC = matrizC;
  __omp_Object0.matrizB = matrizB;
  __omp_Object0.matrizA = matrizA;
  __omp_Object0.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  limite2 = __omp_Object0.limite2;
  limite1 = __omp_Object0.limite1;
  linhaGrupo2 = __omp_Object0.linhaGrupo2;
  linhaGrupo1 = __omp_Object0.linhaGrupo1;
  matrizC = __omp_Object0.matrizC;
  matrizB = __omp_Object0.matrizB;
  matrizA = __omp_Object0.matrizA;
  args = __omp_Object0.args;
}
// OMP PARALLEL BLOCK ENDS

        
        // Verifica os valores no final
        for (int linha = 0; linha < matrizC.length; linha++) {
            for (int coluna = 0; coluna < matrizC[0].length; coluna++) {
                System.out.println("[" + linha + "]" + "[" + coluna + "] = " + matrizC[linha][coluna]);
            }
        }
    }

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  int limite2;
  int limite1;
  int linhaGrupo2;
  int linhaGrupo1;
  int [ ] [ ] matrizC;
  int [ ] [ ] matrizB;
  int [ ] [ ] matrizA;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int myid;
  // reduction variables, init to default
    // OMP USER CODE BEGINS

                  { // OMP SECTIONS BLOCK BEGINS
                  // copy of firstprivate variables, initialized
                  // copy of lastprivate variables
                  // variables to hold result of reduction
                  boolean amLast=false;
                  {
                    // firstprivate variables + init
                    // [last]private variables
                    // reduction variables + init to default
                    // -------------------------------------
                    __ompName_1: while(true) {
                    switch((int)jomp.runtime.OMP.getTicket(__omp_me)) {
                    // OMP SECTION BLOCK 0 BEGINS
                      case 0: {
                    // OMP USER CODE BEGINS

            {
                myid = OMP.getThreadNum();
            
                for (linhaGrupo1 = 0; linhaGrupo1 < limite1; linhaGrupo1++) {
                    for (int c = 0; c < matrizA[0].length; c++) {
                        matrizC[linhaGrupo1][c] = matrizA[linhaGrupo1][c] + matrizB[linhaGrupo1][c];
                        System.out.println("Linha " + linhaGrupo1 + ", coluna " + c + ". (" + matrizA[linhaGrupo1][c] + "+" + matrizB[linhaGrupo1][c] + "). Myid: " + myid);
                    }
                }
            }
                    // OMP USER CODE ENDS
                      };  break;
                    // OMP SECTION BLOCK 0 ENDS
                    // OMP SECTION BLOCK 1 BEGINS
                      case 1: {
                    // OMP USER CODE BEGINS

            {
                myid = OMP.getThreadNum();
                for (linhaGrupo2 = limite1; linhaGrupo2 < limite2; linhaGrupo2++) {
                    for (int c = 0; c < matrizA[0].length; c++) {
                        matrizC[linhaGrupo2][c] = matrizA[linhaGrupo2][c] + matrizB[linhaGrupo2][c];
                        System.out.println("Linha " + linhaGrupo2 + ", coluna " + c + ". (" + matrizA[linhaGrupo2][c] + "+" + matrizB[linhaGrupo2][c] + "). Myid: " + myid);
                    }
                }
            }
                    // OMP USER CODE ENDS
                    amLast = true;
                      };  break;
                    // OMP SECTION BLOCK 1 ENDS

                      default: break __ompName_1;
                    } // of switch
                    } // of while
                    // call reducer
                    jomp.runtime.OMP.resetTicket(__omp_me);
                    jomp.runtime.OMP.doBarrier(__omp_me);
                    // copy lastprivate variables out
                    if (amLast) {
                    }
                  }
                  // update lastprivate variables
                  if (amLast) {
                  }
                  // update reduction variables
                  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                  }
                  } // OMP SECTIONS BLOCK ENDS

    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

