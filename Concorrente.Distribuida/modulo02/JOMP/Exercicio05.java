package JOMP;

import jomp.runtime.OMP;

public class Exercicio05 {


    public static void main(String[] args) {
        boolean passouS1 = false;
        boolean passouS2 = false;
       	int myid;
	 
        OMP.setNumThreads(3);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.passouS2 = passouS2;
  __omp_Object0.passouS1 = passouS1;
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
  passouS2 = __omp_Object0.passouS2;
  passouS1 = __omp_Object0.passouS1;
  args = __omp_Object0.args;
}
// OMP PARALLEL BLOCK ENDS

    }

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  boolean passouS2;
  boolean passouS1;
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
                
                 System.out.println("Thread " + myid + ": se\u00e7\u00e3o 1");
                 passouS1 = true;
            }
                    // OMP USER CODE ENDS
                      };  break;
                    // OMP SECTION BLOCK 0 ENDS
                    // OMP SECTION BLOCK 1 BEGINS
                      case 1: {
                    // OMP USER CODE BEGINS

            {
                myid = OMP.getThreadNum();
                
                while (!passouS1) {
                    System.out.println("Thread " + myid + ": se\u00e7\u00e3o 2 aguardando se\u00e7\u00e3o 1!");
                }                
                
                System.out.println("Thread " + myid + ": se\u00e7\u00e3o 2 liberada!");
                passouS2 = true;
            }
                    // OMP USER CODE ENDS
                      };  break;
                    // OMP SECTION BLOCK 1 ENDS
                    // OMP SECTION BLOCK 2 BEGINS
                      case 2: {
                    // OMP USER CODE BEGINS

            {
                myid = OMP.getThreadNum();

                while (!passouS2) {
                    System.out.println("Thread " + myid + ": se\u00e7\u00e3o 3 aguardando se\u00e7\u00e3o 2!");
                }

                System.out.println("Thread " + myid + ": se\u00e7\u00e3o 3 liberada!");
            }
                    // OMP USER CODE ENDS
                    amLast = true;
                      };  break;
                    // OMP SECTION BLOCK 2 ENDS

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

