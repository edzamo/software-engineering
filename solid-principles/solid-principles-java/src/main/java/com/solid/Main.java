package com.solid;

import com.solid.principle.dip.DipMain;
import com.solid.principle.lsp.LspMain;
import com.solid.principle.ocp.OcpMain;
import com.solid.principle.srp.SrpMain;

public class Main {
    public static void main(String[] args) {
        System.out.println("Applying SOLID principles");
        

        SrpMain.main();
        OcpMain.main();
        LspMain.main();
        DipMain.main();
        
    }
}
