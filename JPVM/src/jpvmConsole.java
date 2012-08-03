


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public 
class jpvmConsole {
	private static jpvmEnvironment	jpvm;
	public static BufferedReader 	user;

	public static void main(String args[]) {
	    try {
		jpvm  = new jpvmEnvironment("jpvm console");
		InputStreamReader userIn = new InputStreamReader(System.in);
		user = new BufferedReader(userIn);
		while(true) {
			System.out.print("jpvm> ");
			try {
				System.out.flush();
                    		String command = user.readLine();
				if(command.equalsIgnoreCase("quit") ||
				   command.equalsIgnoreCase("q") ) {
					Quit();
				}
				else if (command.equalsIgnoreCase("help") ||
					 command.equals("?")) {
					 Help();
				}
				else if (command.equalsIgnoreCase("conf")) {
					Conf();
				}
				else if (command.equalsIgnoreCase("halt")) {
					Halt();
				}
				else if (command.equalsIgnoreCase("add")) {
					Add();
				}
				else if (command.equalsIgnoreCase("ps")) {
					Ps();
				}
				else {
					System.out.println(command+
						": not found");
				}
			}
			catch (IOException ioe) {
				System.err.println("jpvm console: i/o " +
					"exception.");
				System.exit(1);
			}
		}
	    }
	    catch (jpvmException jpe) {
		perror("internal jpvm error - "+jpe.toString());
	    }
	}

	private static void Quit() throws jpvmException {
		System.out.println("jpvm still running.");
		jpvm.pvm_exit();
		System.exit(0);
	}

	private static void Help() throws jpvmException {
		System.out.println("Commands are:");
		System.out.println("  add\t- Add a host to the virtual "+
					"machine");
		System.out.println("  halt\t- Stop jpvm daemons");
		System.out.println("  help\t- Print helpful information " +
					"about commands");
		System.out.println("  ps\t- List tasks");
		System.out.println("  quit\t- Exit console");
	}

	private static void Conf() throws jpvmException {
		jpvmConfiguration conf = jpvm.pvm_config();
		System.out.println(""+conf.numHosts+" hosts:");
		for(int i=0;i<conf.numHosts;i++)
			System.out.println("\t"+conf.hostNames[i]);
	}

	private static void Ps() throws jpvmException {
		jpvmConfiguration conf = jpvm.pvm_config();
		for(int i=0;i<conf.numHosts;i++) {
			jpvmTaskStatus ps = jpvm.pvm_tasks(conf,i);
			System.out.println(ps.hostName+", "+ps.numTasks+
				" tasks:");
			for(int j=0;j<ps.numTasks;j++)
			    System.out.println("\t"+ps.taskNames[j]);
		}
	}

	private static void Halt() throws jpvmException {
		jpvm.pvm_halt();
		try {
                        Thread.sleep(2000);
                }
                catch (InterruptedException ie) {
                }
		System.exit(0);
	}

	private static void Add() {
		String host = null;
		int    port = 0;
		try {
			System.out.print("\tHost name   : ");
			System.out.flush();
			host = user.readLine();
		    	System.out.print("\tPort number : ");
		    	System.out.flush();
		    	String port_str = user.readLine();
		    try {
		    	port = Integer.valueOf(port_str).intValue();
		    }
		    catch (NumberFormatException nfe) {
			System.out.println("Bad port.");
			return;
		    }
		}
		catch (IOException e) {
		 	System.out.println("i/o exception");
			try {
				Quit();
			}
			catch (jpvmException jpe) {
				System.exit(0);
			}
		}
		jpvmTaskId tid = new jpvmTaskId(host,port);
		String h[] = new String[1];
		jpvmTaskId t[] = new jpvmTaskId[1];
		h[0] = host;
		t[0] = tid;
		try {
		  jpvm.pvm_addhosts(1,h,t);
		}
		catch (jpvmException jpe) {
		  perror("error - couldn't add host " + host);
		}
	}

 	private static void perror(String message) {
                System.err.println("jpvm console: "+ message);
                System.err.flush();
        }
};
