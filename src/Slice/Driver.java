/*
 * Author:TSHIKOTSHI VHUTALI
 * EEE4022S : FINAL YEAR PROJECT
 * BANDWIDTH MANAGEMENT SCHEME IN 5G WIRELES NETWORK
 * SUPERVISOR : DR OLABISI FALOWO
 */
package Slice;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class Driver {
	public static void main(String[] args) throws IOException {
		
		
		BufferedWriter output = null;
		output = new BufferedWriter(new FileWriter("experiment22.txt"));
		BandwidthAdaptationController obj = new BandwidthAdaptationController ();
		 
		double[] block = new double[10];
		double[] block1 = new double[10];
		double[] block2 = new double[10];
		double drop  [] = new double[10];
		double dropAdapted  [] = new double[10];


		SLICE eMBB = new SLICE(30,30,30,"enhanced Mobile Broad Band");
		SLICE mMTC =new SLICE(30,30,"massive Machine Type Communication");
		SLICE uRLLC =new SLICE(30,30,"Ultra low latency communication");
		
		CalcBlockingDropping prob= new CalcBlockingDropping();
		SLICE[] infrastructure = {mMTC,eMBB,uRLLC}; 
		
		int capacity1 = eMBB.__get_Capacity();
		int threshold1 = eMBB.__get_Threshold();
		int capacity2 = mMTC.__get_Capacity();
		int threshold2 = mMTC.__get_Threshold();
		int capacity3 = uRLLC.__get_Capacity();
		int thr4 = uRLLC.__get_Threshold();
		System.out.println("capacity eMBB: "+capacity1);
		System.out.println("Threshold eMBB:" + threshold1);
		System.out.println("capacity mMTC  = Threshold mMTC: "+capacity2+"");
		System.out.println("capacity uRLLC = Threshold uRLLC: "+capacity3);
		System.out.println("  ");
		 
		UseCaseGroup A = new UseCaseGroup(eMBB);
		UseCaseGroup A1 = new UseCaseGroup(mMTC);
		UseCaseGroup A2 = new UseCaseGroup(uRLLC);
		
		UseCaseGroup A4 = new UseCaseGroup( infrastructure);
	 	
		//Blocking probabilities
		System.out.println("---------------Blocking/Dropping probabilities----------------");
		System.out.println("  ");
		
		System.out.println("Dropping probability of a new use_case A call (HD Video) : ");
		output.write("Dropping probability of a new use_case A call (HD Video) : ");
		output.newLine();
		output.append(" ");
		output.newLine();
		//output.write("nnnnnn");
		System.out.println("  ");
		 
		
		drop = prob.calcDroppingProb(A,"video");
		dropAdapted = obj.BandwidthAdaptation(A, 50, "video");
		//int count = 0;
		for(int i=0;i<block.length;i++){
			//if(drop[i]> 0.2){ 
				//System.out.println("Adaptation triggered "+count);
				//count++;
			//}
			System.out.println(drop[i]); 
			output.write(Double.toString(drop[i]));
			output.newLine();
		}
		 
		
		System.out.println("  "); 
		//output.append(" ");output.newLine();
		//output.write("Dropping probability of a new use_case A call (HD Video)(adapted threshold) : ");
		//output.newLine();
		//output.append(" ");
		//output.newLine();

		for(int i=0;i<block.length;i++){
				//System.out.println(dropAdapted[i]);
			  // output.write(Double.toString(dropAdapted[i]));
			   //output.newLine();
		}
		
		System.out.println("  ");
		System.out.println("Blocking probability of a new use_case A call (HD Video) : ");
		System.out.println("  ");
		
		output.append(" ");output.newLine();
		output.write("Blocking probability of a new use_case A call (HD Video) : ");
		output.newLine();
		output.append(" ");
		output.newLine();
		
		
		block = prob.calcBlockingProb(A,"video");
		for(int i=0;i<block.length;i++){
			System.out.println(block[i]); 
			output.write(Double.toString(block[i]));
			output.newLine();
		}
		
			
		System.out.println("Blocking probability of a new use_case B call (HomeAuto) : ");
		System.out.println("  ");
		output.append(" ");output.newLine();
		output.write("Blocking probability of a new use_case B call (HomeAuto) : ");
		output.newLine();
		output.append(" ");
		output.newLine();
		
		block1 = prob.calcBlockingProb(A1,"HomeAuto");
		for(int i=0;i<block.length;i++){
			System.out.println(block1[i]); 
			output.write(Double.toString(block1[i]));
			output.newLine();
		}
		
		System.out.println("Blocking probability of a new use_case C call  (eHealth) : ");
		System.out.println("  ");
		output.append(" ");output.newLine();
		output.write("Blocking probability of a new use_case C call  (eHealth) : ");
		output.newLine();
		output.append(" ");
		output.newLine();
		block2 = prob.calcBlockingProb(A2,"eHealth");
		for(int i=0;i<block.length;i++){
			System.out.println(block2[i]); 
			output.write(Double.toString(block2[i]));
			output.newLine();
		}
		output.flush();  
		output.close();	
	}
}
