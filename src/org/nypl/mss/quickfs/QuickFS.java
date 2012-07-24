/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nypl.mss.quickfs;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.commons.cli.*;

/**
 *
 * @author dm
 */
public class QuickFS {
    private final String[] arguments;
    private Options options;
    private File path;
    private List<File> files;
    private Map<String, String> filesystems = new HashMap<String, String>();
    private List<String> filenames = new ArrayList<String>();
    private String filter;
    private boolean hasFilter;
    
    QuickFS(String[] args) throws ParseException, IOException, InterruptedException{
        this.arguments = args;
        options = createOptions();
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse( options, arguments);
        path = new File(cmd.getOptionValue("d"));
        if(cmd.hasOption("f")){
            hasFilter = true;
            filter = cmd.getOptionValue("f");
        }
        
        listFileSystems();
    }
    
    
    private Options createOptions() {
        Options mOptions = new Options();
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(true);
        OptionBuilder.withDescription("list only images of a specific filesystem");
        OptionBuilder.isRequired(false);
        mOptions.addOption(OptionBuilder.create("f"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(true);
        OptionBuilder.withDescription("path to the directory");
        OptionBuilder.isRequired(true);
        mOptions.addOption(OptionBuilder.create("d"));
        
        return mOptions;
    }


    private void listFileSystems() throws ParseException, IOException, InterruptedException {
        if(hasFilter){
            System.out.println("listing files in directory: " + path + " of filesystem: " + filter);
        }
        else
            System.out.println("listing files  and filesystems in directory: " + path);
        
        files = Arrays.asList(path.listFiles());

        for(File file: files){
            if(file.getName().toLowerCase().endsWith("001") && !file.isHidden()){
                QuickMD q = new QuickMD(file);
                filesystems.put(file.getName(), q.getFSSTring());
                filenames.add(file.getName());
            }
        }

        Collections.sort(filenames);
        
        for(Object filename : filenames){
            if(hasFilter){
                if(filesystems.get(filename).equals(filter)){
                    System.out.println(filename);
                }
            } else {
                System.out.println(filename + "\t" + filesystems.get(filename));
            }
            
        }
    }
    
        
    public static void main(String[] args) throws ParseException, IOException, InterruptedException{
        QuickFS qfs = new QuickFS(args);
    }

}
