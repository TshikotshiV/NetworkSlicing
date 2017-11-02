/*
 * Author:TSHIKOTSHI VHUTALI
 * EEE4022S : FINAL YEAR PROJECT
 * BANDWIDTH MANAGEMENT SCHEME IN 5G WIRELES NETWORK
 * SUPERVISOR : DR OLABISI FALOWO
 */
package Slice;
public class UseCase {
		private int _bbu_video = 12;
		private int _bbu_video2 = 8;
		private int _bbu_homeAutomation = 8 ;
		private int _bbu_ehealth = 18;
		private String _use_name;
	 	
		public UseCase(int bbuV,int bbuH,int bbuE){
			this._bbu_video = bbuV;
			this._bbu_homeAutomation = bbuH;
			this._bbu_ehealth = bbuE;
		}
		public UseCase(int bbuV){
			this._bbu_video2 = bbuV; 
		}
		public UseCase(String name){
			this._use_name = name;
		}
		public void __allocate_videoBBU(int hdVideo){
			this._bbu_video = hdVideo; 
		}
		public void __allocate_videoBBU2(int hdVideo){
			this._bbu_video2 = hdVideo; 
		}
		public void __allocate_homeAutomationBBU(int homeAuto){
			this._bbu_homeAutomation = homeAuto; 
		}
		public void __allocate_ehealthBBU(int health){
			this._bbu_ehealth = health; 
		}
		//get the bandwidth corresponding to the given name
		public int __getBBU(String name){
			int _bbu = 1;
			if(name.equalsIgnoreCase("video")){
				_bbu = _bbu_video;
			}
			else if (name.equalsIgnoreCase("HomeAuto")){
				_bbu = _bbu_homeAutomation; 
			}
			else{
				if(name.equalsIgnoreCase("eHealth")){
					_bbu = _bbu_ehealth;
				}
			}
			return _bbu;
		}
		public int __getBBU2(String name){
			int _bbu = 1;
			if(name.equalsIgnoreCase("video")){
				_bbu = _bbu_video2;
			}
			return _bbu;
		}
		//allocate bandwidth units to new calls
		public void __allocate_BBU(String name,int bbu){
			if(name.equalsIgnoreCase("video")){
				this._bbu_video = bbu;
			}
			else if (name.equalsIgnoreCase("HomeAuto")){
				this._bbu_homeAutomation = bbu;
			}
			else{
				if(name.equalsIgnoreCase("eHealth")){
					this._bbu_ehealth= bbu;
				}
			}
		}
		//allocate bandwidth units to handoff calls
		public void __allocate_BBU2(String name,int bbu){
			if(name.equalsIgnoreCase("video")){
				this._bbu_video2 = bbu;
			}
			 
		}
}
 