package JOMP;

import jomp.runtime.OMP;

public class MultiplicaMatrizOMP {


	public static void main(String[] args) {

		int matriz[][] = new int[3][3];
		int i, j;
		int myid = 0;
		int soma = 1;
		OMP.setNumThreads(10);

		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				matriz[i][j] = 2;
			}
		}

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.matriz = matriz;
  __omp_Object0.args = args;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  soma  *= __omp_Object0._rd_soma;
  // shared variables
  matriz = __omp_Object0.matriz;
  args = __omp_Object0.args;
}
// OMP PARALLEL BLOCK ENDS

		System.out.println("RESULTADO: " + soma);

	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  int [ ] [ ] matriz;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction
  int _rd_soma;

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int myid;
  int i;
  int j;
  // reduction variables, init to default
  int soma = 1;
    // OMP USER CODE BEGINS

		{

			myid = OMP.getThreadNum();
                         { // OMP FOR BLOCK BEGINS
                         // copy of firstprivate variables, initialized
                         // copy of lastprivate variables
                         // variables to hold result of reduction
                         boolean amLast=false;
                         {
                           // firstprivate variables + init
                           // [last]private variables
                           // reduction variables + init to default
                           // -------------------------------------
                           jomp.runtime.LoopData __omp_WholeData2 = new jomp.runtime.LoopData();
                           jomp.runtime.LoopData __omp_ChunkData1 = new jomp.runtime.LoopData();
                           __omp_WholeData2.start = (long)( 0);
                           __omp_WholeData2.stop = (long)( 3);
                           __omp_WholeData2.step = (long)(1);
                           jomp.runtime.OMP.setChunkStatic(__omp_WholeData2);
                           while(!__omp_ChunkData1.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData2, __omp_ChunkData1)) {
                           for(;;) {
                             if(__omp_WholeData2.step > 0) {
                                if(__omp_ChunkData1.stop > __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                if(__omp_ChunkData1.start >= __omp_WholeData2.stop) break;
                             } else {
                                if(__omp_ChunkData1.stop < __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                if(__omp_ChunkData1.start > __omp_WholeData2.stop) break;
                             }
                             for( i = (int)__omp_ChunkData1.start; i < __omp_ChunkData1.stop; i += __omp_ChunkData1.step) {
                               // OMP USER CODE BEGINS
 {
				for (j = 0; j < 3; j++) {
					soma *= matriz[i][j];
				}
			}
                               // OMP USER CODE ENDS
                               if (i == (__omp_WholeData2.stop-1)) amLast = true;
                             } // of for 
                             if(__omp_ChunkData1.startStep == 0)
                               break;
                             __omp_ChunkData1.start += __omp_ChunkData1.startStep;
                             __omp_ChunkData1.stop += __omp_ChunkData1.startStep;
                           } // of for(;;)
                           } // of while
                           // call reducer
                           jomp.runtime.OMP.doBarrier(__omp_me);
                           // copy lastprivate variables out
                           if (amLast) {
                           }
                         }
                         // set global from lastprivate variables
                         if (amLast) {
                         }
                         // set global from reduction variables
                         if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                         }
                         } // OMP FOR BLOCK ENDS


			System.out.println("MYID: " + myid);

		}
    // OMP USER CODE ENDS
  // call reducer
  soma = (int) jomp.runtime.OMP.doMultReduce(__omp_me, soma);
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
    _rd_soma = soma;
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

