/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nypl.mss.quickfs;

import java.io.IOException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author dm
 */
public class QuickFS {
    private final String[] arguments;
    private Options options;
    
    QuickFS(String[] args){
        this.arguments = args;
        options = createOptions();
    }
    
    
    private Options createOptions() {
        Options mOptions = new Options();
        
        return mOptions;
    }
    
    public static void main(String[] args) throws ParseException, IOException, InterruptedException{
        QuickFS qfs = new QuickFS(args);
    }

}
