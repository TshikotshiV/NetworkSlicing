/*
 * Author:TSHIKOTSHI VHUTALI
 * EEE4022S : FINAL YEAR PROJECT
 * BANDWIDTH MANAGEMENT SCHEME IN 5G WIRELES NETWORK
 * SUPERVISOR : DR OLABISI FALOWO
 */
package Slice;

public class BandwidthAdaptationController {
//	private ;
	
	public double [] BandwidthAdaptation (UseCaseGroup s,int percent,String callType){
		SLICE[] slice = s.__getSlices();
		//Probabilities
		double Bp [] =  new double[10];//bloking probabilities
		double Dp [] =  new double[10];//Dropping probabilities
		double qn11 [] = new double[10];
		double qh11 [] = new double[10];
		//Normalization Constant
		double G   [] =  new double[10];
		double GG1 [] =  new double[10];
		double GG2 [] =  new double[10];
		//intializing arrays
		float percentage = percent/100;
		if (slice.length ==1 ||  callType.equals("video")){
			int capacity = slice[0].__get_Capacity();
			//int threshold = (slice[0].__get_Threshold()) - (int) ((slice[0].__get_Threshold())*percentage);
			int threshold = (int) (percentage*capacity);
			//int threshold2 = slice[0].__get_Threshold2();
			int threshold2 = capacity;
		 	int bbu = slice[0].__getUseCase().get(0).__getBBU(callType);
			int bbu2 = slice[0].__getUseCase().get(0).__getBBU2(callType);
			
			double h=0.5;// Constant handoff rate
			double CH1=0.5+h; //mean channel holding time for class-1 call
			double CH2=0.5+h; //mean channel holding time for class-2 call
			
			//-----Call Arrivals-----------------
			//New call arrival rate (non-adaptive)
			double [] Xc1 = {.5,1,1.5,2,2.5,3,3.5,4,4.5,5}; // New Call Arrival Rate for Class 1
			double [] Xc2 = {.5,1,1.5,2,2.5,3,3.5,4,4.5,5}; // New Call Arrival Rate for Class 2
			//Handoff arrival Rate (adaptive)
			double Xh1 [] = new double[Xc1.length];
			double Xh2 [] = new double[Xc1.length];
			for(int i=0;i<Xc1.length;i++){
				Xh1[i] = (Xc1[i]*h)/0.5;//Handoff Arrival Rate for Class 1
				Xh2[i] = (h*Xc2[i])/0.5; // new call Arrival Rate for Class 2
			} 
			//The residual available bandwidth
			double P11 [] = new double[Xc1.length];
			double P21 [] = new double[Xc1.length];
			double PH11 [] = new double[Xh1.length];
			 
			for(int i=0;i<Xc1.length;i++){
				P11[i] =((capacity)/(capacity))*Xc1[i]; // new call use-case1 
				P21[i] =((capacity)/(capacity))*Xc2[i]; //Handoff call use case 1	
				PH11[i]=((capacity)/(capacity))*Xh1[i]; // new call use case 2
			} 
			//The generated load calculation
			double pn11 [] = new double[P11.length];
			double ph11 [] = new double[P11.length];
			double pn21 [] = new double[P11.length];
			//double ph21 [] = new double[P11.length];
			
			for(int i=0;i<P11.length;i++){
				pn11[i] = P11[i]/CH1;
				ph11[i]=PH11[i]/CH1;
				pn21[i]=P21[i]/CH2;
			}
			int dm11=Math.round(threshold/bbu)+1;//new calls possible in slice 1
			int dn11=Math.round(threshold2/bbu2)+1;// Handoff calls possible in slice1 
		
			UseCaseGroup obj = new UseCaseGroup();
			for(int i=1;i<=10;i++){
				for(int m11=1;m11<=dm11;m11++){
					for(int n11=1;n11<=dn11;n11++){
						if (((bbu*(m11-1)) +(bbu2*(n11-1))<=capacity) && (((bbu*(m11-1)) <=threshold)) &&(((bbu2*(n11-1))<=threshold2)) &&((m11-1)>=0)&&((n11-1)>=0)){ 
							for(int z=0;z<10;z++){
								qn11[z] = obj.myPow(pn11[z],(m11 - 1))/obj.calc(m11 - 1);  
								qh11[z] = obj.myPow(ph11[z],(n11 - 1))/obj.calc(n11 - 1);
								G[z]=G[z]+(qn11[z]*qh11[z]);//normalizing constant G 
								
								//Conditions for a new use_case-1 call to be blocked
	                            if (((bbu+(bbu*(m11-1))+(bbu2*(n11-1))>capacity)|| (bbu+(bbu*(m11-1))>threshold))){
	                            	GG1[z]=GG1[z]+(qn11[z]*qh11[z]); //normalizing constant G 
	                            }
	                            //Conditions for Handoff use_case-1 to be dropped
	                            if (((bbu2+(bbu*(m11-1))+(bbu2*(n11-1))>capacity)|| (bbu2+(bbu2*(n11-1))>threshold2) )){
	                            	GG2[z]=GG2[z]+(qn11[z]*qh11[z]); //normalizing constant G 
	                            }
							}
						}
					}
				}
			}
			for(int x=0;x<10;x++){
				Dp[x] = GG2[x]/G[x];
			}
		}
		return Dp; 
	}
}
