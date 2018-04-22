package gammaSupport;

import MDELite.ErrInt;


public enum GammaDebug implements ErrInt<GammaDebug> {
    
    addingThread("Adding thread NAME"),
    filter4Connector("Filter for connector NAME is STRING"),
    hjRead1("HJoin read1 TUPLE"),
    settingBit("Setting TUPLE bitmap entry for KEY with hash VALUE on connector NAME"),
    startingThread("Starting thread NAME"),
    streamOutput("Stream NUMBER gets TUPLE"),
    tupleFiltered("Filtered tuple TUPLE on connector NAME on field FIELD with hash HASH"), 
    
    ;
    
    String msg;
    
    GammaDebug(String msg) { 
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public ErrInt<GammaDebug>[] vals() {
        return GammaDebug.values();
    }
    
    public static void report(GammaDebug error, Object... args) {
        if (GammaConstants.debug) {
        System.err.println(EString(error,args));
        }
    }
    
    public static void AlwaysReport(GammaDebug error, Object... args) {
        System.err.println(EString(error,args));
    }
    
    public static String EString(GammaDebug error, Object... args) {
        return MDELite.Utils.makeString(error.msg,args);
    }
    
}
