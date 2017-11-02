/*
 * Author:TSHIKOTSHI VHUTALI
 * EEE4022S : FINAL YEAR PROJECT
 * BANDWIDTH MANAGEMENT SCHEME IN 5G WIRELES NETWORK
 * SUPERVISOR : DR OLABISI FALOWO
 */
package Slice;
public class UseCaseGroup {
	/**
	 * 
	 * class UseCaseGroup specifies a group of use cases
	 * and the slices they can be assigned to
	 * 
	 **/
		private SLICE[] slices;
		public int calc(int nn) {
	        if (nn <= 1)
	            return 1;
	        else
	            return nn * calc(nn - 1);
	    }
		 public double myPow(double base, int exp) {
		        if(base == 0) return 0;
		        if(exp == 0) return 1;
		        int absexp = (exp < 0)? exp * -1 : exp;
		        double output = base;
		        for(int i = 1; i < absexp; i++) {
		            output *= base;
		        }
		        if(exp < 1) output = 1 / output;
		        return output;
		 }
		public UseCaseGroup(SLICE[] slice){
			this.slices = slice;
		}
		public UseCaseGroup(){
			//this.slices = slice;
		}
		public UseCaseGroup(SLICE slice){
			
			this.slices = new SLICE[]{
					slice
			};
		}
		
		public SLICE[] __getSlices() {
			return slices;
		};
		
}
