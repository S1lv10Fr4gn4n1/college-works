package JOMP;

import jomp.runtime.OMP;

public class Exercicio04 {

	//Como fazer a soma de uma matriz usando
	// omp parallel shared(x,y,n)
	// sem for omp
	
	//10 x 10
	
	public static void main(String[] args) {
		int myid = 0; 
		int b = 0;
		int i = 0;
		int[][] matriz  = {	{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}, 
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
					{1, 2, 3, 4, 5, 6, 7, 8, 9, 0},
				 };
		
		OMP.setNumThreads(10);

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
  b  += __omp_Object0._rd_b;
  // shared variables
  matriz = __omp_Object0.matriz;
  args = __omp_Object0.args;
}
// OMP PARALLEL BLOCK ENDS

		
		System.out.println("Valor Total: "+ b);
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private static class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  int [ ] [ ] matriz;
  String [ ] args;
  // firstprivate variables
  // variables to hold results of reduction
  int _rd_b;

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int myid;
  int i;
  // reduction variables, init to default
  int b = 0;
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
                           __omp_WholeData2.stop = (long)( matriz.length);
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
				b += matriz[myid][i];
				System.out.println("Thread: " + myid + ", Valor: " + matriz[myid][i]);
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

			
		}
    // OMP USER CODE ENDS
  // call reducer
  b = (int) jomp.runtime.OMP.doPlusReduce(__omp_me, b);
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
    _rd_b = b;
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

