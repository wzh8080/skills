package demo;

import java.util.concurrent.ThreadLocalRandom;

public class Test {
	public static void main(String[] args) {
		System.out.println(ThreadLocalRandom.current().nextInt(140)+80);
	}
}
