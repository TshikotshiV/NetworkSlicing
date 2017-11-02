/*
 * Author:TSHIKOTSHI VHUTALI
 * EEE4022S : FINAL YEAR PROJECT
 * BANDWIDTH MANAGEMENT SCHEME IN 5G WIRELES NETWORK
 * SUPERVISOR : DR OLABISI FALOWO
 */
package Slice;
public class CalcBlockingDropping {
	public double [] calcBlockingProb(UseCaseGroup s,String callType){
		
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
		double GG22 [] =  new double[10];
		//intializing arrays
		for(int i=0;i<10;i++){
			qn11[i] = 0;qh11[i] = 0;
			G[i]=0;GG1[i]=0;GG1[i]=0;GG22[i]=0;
		}
		if (slice.length ==1 ||  callType.equals("video")){
			int capacity = slice[0].__get_Capacity();
			int threshold = slice[0].__get_Threshold();
			int threshold2 = slice[0].__get_Threshold2();
		 	int bbu = slice[0].__getUseCase().get(0).__getBBU(callType);
			int bbu2 = slice[0].__getUseCase().get(0).__getBBU2(callType);
			
			double h=0.5;// Constant handoff rate
			double [] CH1 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2}; 
			double [] CH2 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2};
			//double CH1=0.5+h; //mean channel holding time for class-1 call
			//double CH2=0.5+h; //mean channel holding time for class-2 call
			
			//-----Call Arrivals-----------------
			//New call arrival rate (non-adaptive)
			//double [] Xc1 = {0.5,1,1.5,2,2.5,3,3.5,4,4.5,5}; // New Call Arrival Rate for Class 1
			//double [] Xc2 = {0.5,1,1.5,2,2.5,3,3.5,4,4.5,5}; // New Call Arrival Rate for Class 2
			 
			double Xc1 =  0.5;
			double Xc2 =  0.5;
			
			//double [] Xc1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//double [] Xc2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; 
		 
			//Handoff arrival Rate (adaptive)
			double Xh1 [] = new double[10];
			double Xh2 [] = new double[10];
			
			for(int i=0;i<CH1.length;i++){
				Xh1[i] = 0.5;//Handoff Arrival Rate for Class 1
				Xh2[i] = 0.5; // new call Arrival Rate for Class 2
			} 
			//The residual available bandwidth
			double P11 [] = new double[10];
			double P21 [] = new double[10];
			double PH11 [] = new double[10];
			 
			for(int i=0;i<10;i++){
				P11[i] =((capacity)/(capacity))*0.5; // new call use-case1 
				P21[i] =((capacity)/(capacity))*0.5; //Handoff call use case 1	
				PH11[i]=((capacity)/(capacity))*0.5; // new call use case 2
			} 
			//The generated load calculation
			double pn11 [] = new double[P11.length];
			double ph11 [] = new double[P11.length];
			double pn21 [] = new double[P11.length];
			//double ph21 [] = new double[P11.length];
			
			for(int i=0;i<P11.length;i++){
				pn11[i] = P11[i]/CH1[i];
				ph11[i]=PH11[i]/CH1[i];
				pn21[i]=P21[i]/CH2[i];
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
		}
		else if (slice.length ==2 ||  callType.equals("HomeAuto")){
			String slice2 =slice[1].__get_SliceName();
			int capacity2 = slice[1].__get_Capacity();
			int threshold22 = slice[1].__get_Threshold();
			int index2 = slice2.equals("mMTC") ? 1:0;
			int bbu21 = slice[1].__getUseCase().get(index2).__getBBU(callType);
	
			double h=0.5;// Constant handoff rate
			//channel holding time
			double [] CH1 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2}; 
			double [] CH2 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2};
			//double CH1=0.5+h; 
			//double CH2=0.5+h;  
			
			 //call arrival rates
			double Xc1 = 0.5; 
			double Xc2 = 0.5;
			
			//double [] Xc1 = { 1, 1.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5};  
			//double [] Xc2 = { 1, 1.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5};
			
			//double [] Xc1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//double [] Xc2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		 
			double Xh1 [] = new double[10];
			double Xh2 [] = new double[10];
			for(int i=0;i<10;i++){
				Xh1[i] = 0.5;//Handoff Arrival Rate for Class 1
				Xh2[i] = 0.5; // new call Arrival Rate for use_case 1,2 and 3
			} 
			//The residual available bandwidth
			double P11 [] = new double[10];
			double P21 [] = new double[10];
			double PH11 [] = new double[10];
			 
			for(int i=0;i<10;i++){
				P11[i] =((capacity2)/(capacity2))*0.5; // new call use-case1 
				P21[i] =((capacity2)/(capacity2))*0.5; //Handoff call use case 1	
				PH11[i]=((capacity2)/(capacity2))*0.5; // new call use case 2
			} 
			//The generated load calculation
			double pn11 [] = new double[P11.length];
			double ph11 [] = new double[P11.length];
			
			for(int i=0;i<P11.length;i++){
				pn11[i] = P11[i]/CH1[i];
				ph11[i]=PH11[i]/CH1[i];
			}
			int dm11=Math.round(threshold22/bbu21)+1;//new calls possible in slice 2
			UseCaseGroup obj = new UseCaseGroup();
			for(int i=1;i<=10;i++){//for each arrival rate
				for(int m11=1;m11<=dm11;m11++){
						if (((bbu21*(m11-1)) <=capacity2)&&((m11-1)>=0) && (((bbu21*(m11-1)) <=threshold22))){ 
							for(int z=0;z<10;z++){
								qn11[z] = obj.myPow(pn11[z],(m11 - 1))/obj.calc(m11 - 1);
								G[z]=G[z]+(qn11[z]);//normalizing constant G
								//Conditions for a new use_case-1 call to be blocked
	                            if ( (bbu21*(m11-1))>capacity2 ||(bbu21+(bbu21*(m11-1))>threshold22)){
	                            	GG1[z]=GG1[z]+(qn11[z]); //normalizing constant G 
	                            }
							}
						}
				}
			}
		}
		
		else if (slice.length ==3 ||  callType.equals("eHealth")){
			
			String slice3 =slice[2].__get_SliceName();
			int capacity3 = slice[2].__get_Capacity();
			int threshold3 = slice[2].__get_Threshold();
			int index3 = slice3.equals("uRLLC") ? 1:0;
			int bbu3 = slice[2].__getUseCase().get(index3).__getBBU(callType);
			
			// Constant handoff rate
			double h=0.5;
			//channel holding time
			double [] CH1 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2}; 
			double [] CH2 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2};
			//double CH1=0.5+h;  
			//double CH2=0.5+h;  
			
			//-----Call Arrivals-----------------
			//new call arrival rate for all use cases
			double Xc1 = 0.5;  
			double Xc2 = 0.5; 
			//double [] Xc1 = { 1, 1.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5};  
			//double [] Xc2 = { 1, 1.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5};
			
			//double [] Xc1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//double [] Xc2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//Handoff arrival Rate (adaptive)
			double Xh1 [] = new double[10];
			double Xh2 [] = new double[10];
			for(int i=0;i<10;i++){
				Xh1[i] = 0.5;//Handoff Arrival Rate for use_case 1
				Xh2[i] = 0.5; // new call Arrival Rate for use_case 1,2 and 3 
			} 
			//The residual available bandwidth
			double P11 [] = new double[10];
			double P21 [] = new double[10];
			double PH11 [] = new double[Xh1.length];
			 
			for(int i=0;i<10;i++){
				P11[i] =((capacity3)/(capacity3))*0.5; // new call use-case1 
				P21[i] =((capacity3)/(capacity3))*0.5; //Handoff call use case 1	
				PH11[i]=((capacity3)/(capacity3))*0.5; // new call use case 2
			} 
			//The generated load calculation
			double pn11 [] = new double[P11.length];
			double ph11 [] = new double[P11.length];
			 
			for(int i=0;i<P11.length;i++){
				pn11[i] = P11[i]/CH1[i];
				ph11[i]=PH11[i]/CH1[i];
			}
			int dm11=Math.round(threshold3/bbu3)+1;//new calls possible in slice 3
			UseCaseGroup obj = new UseCaseGroup();
			for(int i=1;i<=10;i++){//for each arrival rate
				for(int m11=1;m11<=dm11;m11++){
						if (((bbu3*(m11-1)) <=capacity3) && (((bbu3*(m11-1)) <=threshold3)) &&((m11-1)>=0)){ 
							for(int z=0;z<10;z++){
								qn11[z] = obj.myPow(pn11[z],(m11 - 1))/obj.calc(m11 - 1);
								G[z]=G[z]+(qn11[z]);//normalizing constant G
								//Conditions for a new use_case-1 call to be blocked
	                            if ( (bbu3*(m11-1))>capacity3 ||(bbu3+(bbu3*(m11-1))>threshold3) ){
	                            	GG1[z]=GG1[z]+(qn11[z]); //normalizing constant G 
	                            }
							}
						}
				}
			} 
		}
		
		for(int x=0;x<10;x++){
			Bp[x] = GG1[x]/G[x];
	    }
		for(int x=0;x<10;x++){
			//Bp[x] = Math.round(Bp[x] * 1000000);
			//Bp[x] = Bp[x]/1000000;
		}
		return Bp;
	}
public double [] calcDroppingProb(UseCaseGroup s,String callType){		
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
		double GG22 [] =  new double[10];
		//intializing arrays
		for(int i=0;i<10;i++){
			qn11[i] = 0;qh11[i] = 0;
			G[i]=0;GG1[i]=0;GG1[i]=0;GG22[i]=0;
		}
		if (slice.length ==1 ||  callType.equals("video")){
			int capacity = slice[0].__get_Capacity();
			int threshold = slice[0].__get_Threshold();
			int threshold2 = slice[0].__get_Threshold2();
		 	int bbu = slice[0].__getUseCase().get(0).__getBBU(callType);
			int bbu2 = slice[0].__getUseCase().get(0).__getBBU2(callType);
			
			double h=0.5;// Constant handoff rate
			double [] CH1 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2}; 
			double [] CH2 = {0.2,0.4,0.6,0.8,1,1.2,1.4,1.6,1.8,2};
			//double CH1=0.5+h; //mean channel holding time for class-1 call
			//double CH2=0.5+h; //mean channel holding time for class-2 call
			
			//-----Call Arrivals-----------------
			//New call arrival rate (non-adaptive)
			double Xc1 = 0.5; // New Call Arrival Rate for Class 1
			double Xc2 = 0.5; // New Call Arrival Rate for Class 2
			
			//double [] Xc1 = { 1, 1.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5};  
			//double [] Xc2 = { 1, 1.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5}; 
			
			//double [] Xc1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//double [] Xc2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			//Handoff arrival Rate (adaptive)
			double Xh1 [] = new double[10];
			double Xh2 [] = new double[10];
			for(int i=0;i<10;i++){
				Xh1[i] = 0.5;//Handoff Arrival Rate for Class 1
				Xh2[i] = 0.5; // new call Arrival Rate for Class 2
			} 
			//The residual available bandwidth
			double P11 [] = new double[10];
			double P21 [] = new double[10];
			double PH11 [] = new double[10];
			 
			for(int i=0;i<10;i++){
				P11[i] =((capacity)/(capacity))*0.5; // new call use-case1 
				P21[i] =((capacity)/(capacity))*0.5; //Handoff call use case 1	
				PH11[i]=((capacity)/(capacity))*0.5; // new call use case 2
			} 
			//The generated load calculation
			double pn11 [] = new double[P11.length];
			double ph11 [] = new double[P11.length];
			double pn21 [] = new double[P11.length];
			//double ph21 [] = new double[P11.length];
			
			for(int i=0;i<P11.length;i++){
				pn11[i] = P11[i]/CH1[i];
				ph11[i]=PH11[i]/CH1[i];
				pn21[i]=P21[i]/CH2[i];
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
		}
		for(int x=0;x<10;x++){
			Dp[x] = GG2[x]/G[x];
	    }
		for(int x=0;x<10;x++){
			//Dp[x] = Math.round(Bp[x] * 1000000000);
			//Dp[x] = Bp[x]/1000000000;
	    }
		return Dp;
	}
	
}