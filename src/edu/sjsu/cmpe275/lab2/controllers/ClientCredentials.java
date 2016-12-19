package edu.sjsu.cmpe275.lab2.controllers;

public class ClientCredentials {

	  /** Value of the "API key" shown under "Simple API Access". */
	  static final String API_KEY =
	      "Enter API Key from https://code.google.com/apis/console/?api=books into API_KEY in "
	      + ClientCredentials.class;

	  static void errorIfNotSpecified() {
	    if (API_KEY.startsWith("Enter ")) {
	      System.err.println(API_KEY);
	      System.exit(1);
	      }
	    }
}