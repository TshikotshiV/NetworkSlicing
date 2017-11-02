/*
 * Author:TSHIKOTSHI VHUTALI
 * EEE4022S : FINAL YEAR PROJECT
 * BANDWIDTH MANAGEMENT SCHEME IN 5G WIRELES NETWORK
 * SUPERVISOR : DR OLABISI FALOWO
 */
package Slice;
import java.util.ArrayList;
/**
 * Class SLICE specifies a SLICE in terms of:
 * its name, capacity, threshold and use case
 * */

public class SLICE {
	
	private String slice_name;
	private int slice_capacity;
	private int threshold;
	private int threshold2;
	private UseCase _case1;
	private UseCase _case2;
	private UseCase _case3;
	private ArrayList <UseCase> use_case;
	 
	public SLICE(int c,int t, String n){
		use_case = new ArrayList <UseCase>() ;
		this.slice_name = n;
		this.slice_capacity = c;
		this.threshold = t;
		
		//initialize use cases
		_case1 = new UseCase("video");
		_case2 = new UseCase("HomeAuto");
		_case3 = new UseCase("eHealth");
		
		this.use_case.add(_case1);
		if(n.equals("mMTC")){
			this.use_case.add(_case2);
		}
		else{
			if(n.equals("uRLLC")){
				this.use_case.add(_case3);
			}
		}
	}
	public SLICE(int c,int t,int t2, String n){
		use_case = new ArrayList <UseCase>() ;
		this.slice_name = n;
		this.slice_capacity = c;
		this.threshold = t;
		this.threshold2 = t2;
		//initialize use cases
		_case1 = new UseCase("video");
		this.use_case.add(_case1);
	}
	public String __get_SliceName(){
		return this.slice_name;
	}
	public ArrayList<UseCase> __getUseCase(){
		return this.use_case;
	}
	public int __get_Capacity(){
		return this.slice_capacity;
	}
	
	public void __set_Capacity(int c){
		this.slice_capacity = c;
	}
	
	public int __get_Threshold(){
		return this.threshold;
	}
	public int __get_Threshold2(){
		return this.threshold2;
	}
	public void __set_Threshold(int t){
		this.threshold = t;
	}
	public void __set_Threshold2(int t){
		this.threshold2 = t;
	}
}
