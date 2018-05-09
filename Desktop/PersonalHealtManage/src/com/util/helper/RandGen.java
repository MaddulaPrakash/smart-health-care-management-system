package com.util.helper;
import java.util.Random;

public class RandGen {

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return (int)(Math.random() * ((max - min) + 1)) + min;
	}

   

public static void main(String[]Args){
   System.out.println(RandGen.getRandomNumberInRange(0,3));
}
}