package com.garraio.cordoba.application.core.utils;

public class StringUtils {
	
	private StringUtils() {
		// Private constructor to prevent instantiation
	}
	
	public static String formatCamelCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        // This regex adds a space in two situations:
        // 1. Before an uppercase letter that follows a lowercase letter
        // 2. Before a lowercase letter that follows multiple uppercase letters (for acronyms)
        return text.replaceAll("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])", " ");
    }

}
