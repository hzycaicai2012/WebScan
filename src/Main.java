import client.Client;

/**
 * Project Name:WebScan
 * File Name:Main.java
 * Package Name:
 * Date:2014年1月10日下午5:15:14
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
 */

/**
 * ClassName:Main
 *
 * @author   hzycaicai
 * @version  	 
 */
public class Main {

	public static void main(String[] args) {
		Client client = Client.getClient();
		client.start();
	}

}
