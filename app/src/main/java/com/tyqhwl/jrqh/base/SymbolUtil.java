package com.tyqhwl.jrqh.base;

public class SymbolUtil {
        public static  String getSymbol(String symbol){

            String strK = symbol;
            if (symbol.equals("BP")) {
                strK = "CMEGBP";
            } else if (symbol.equals("CD")) {
                strK = "";
            } else if (symbol.equals("DXF")) {
                strK = "CMEDX";
            } else if (symbol.equals("EC")) {
                strK = "CMEEC";
            } else if (symbol.equals("JY")) {
                strK = "CMEJY";
            } else if (symbol.equals("SF")) {
                strK = "";
            }
            return strK;

        }
}
