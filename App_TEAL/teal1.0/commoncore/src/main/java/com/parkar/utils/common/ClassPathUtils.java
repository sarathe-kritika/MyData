package com.parkar.utils.common;

/**
 * Created by abhishek.awale on 31-03-2016.
 */
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.parkar.exception.ParkarCoreCommonException;
import com.parkar.logging.ParkarLogger;

/**
 * list resources available from the classpath @ *
 */
public class ClassPathUtils {
	/**
     * for all elements of java.class.path get a Collection of resources Pattern
     * pattern = Pattern.compile(".*"); gets all resources
     *
     * @param pattern
     *            the pattern to match
     * @return the resources in the order they are found
     */
	
	final  static Logger logger =Logger.getLogger(ClassPathUtils.class);
    public static Collection<String> getResources(
            final Pattern pattern)throws ParkarCoreCommonException{
        final ArrayList<String> retval = new ArrayList<String>();
        final String classPath = System.getProperty("java.class.path", ".");
        final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
        for(final String element : classPathElements){
            retval.addAll(getResources(element, pattern));
        }
        return retval;
    }
    /**
     * Get resources that matches the pattern.
     *
     * @param element: String
     *  @param : String
     * @return the Collection of resources that matches the pattern
     */
    private static Collection<String> getResources (
            final String element,
            final Pattern pattern)throws ParkarCoreCommonException{
        final ArrayList<String> retval = new ArrayList<String>();
        final File file = new File(element);
        if(file.isDirectory()){
            retval.addAll(getResourcesFromDirectory(file, pattern));
        } else{
            retval.addAll(getResourcesFromJarFile(file, pattern));
        }
        return retval;
    }
    /**
     *Get the resources from the jar files.
     *
     *@exception
     *@param file: File
     *@param pattern: Pattern
     *@throws ParkarCoreCommonException
     *		: customized Parkar core common exception
     *@return the Collection of resources that matches the pattern
     */
    private static Collection<String> getResourcesFromJarFile(final File file, final Pattern pattern)throws ParkarCoreCommonException{
    	
    	ParkarLogger.traceEnter();
        final ArrayList<String> retval = new ArrayList<String>();
        ZipFile zf;
        try{
            zf = new ZipFile(file);
        } catch(final ZipException e){
        	String msg="ZIP Exception occured";
        	logger.error(msg, e);
        	throw new ParkarCoreCommonException(msg,e);
           
        } catch(final IOException e){
        	String msg="While zip the file ,IO Exception has been catched";
        	logger.error(msg, e);
        	throw new  ParkarCoreCommonException(msg,e);
        }
        final Enumeration e = zf.entries();
        while(e.hasMoreElements()){
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = pattern.matcher(fileName).matches();
            if(accept){
                retval.add(fileName);
            }
        }
        try{
            zf.close();
        } catch(final IOException ex){
        	String msg="While close ZipFile ,IO Exception has been catched";
        	logger.error(msg, ex);
        	throw new  ParkarCoreCommonException(msg,ex);
        }
        ParkarLogger.traceLeave();
        return retval;
    }
    /**
     * Get the resources from the Directory
     *
     * @param directory: File
     * @param pattern: Pattern
     * @throws ParkarCoreCommonException
     * 		: customized Parkar core common exception
     * @return the Collection of resources that matches the pattern
     */
    private static Collection<String> getResourcesFromDirectory(final File directory,final Pattern pattern) throws ParkarCoreCommonException{
    	ParkarLogger.traceEnter();
        final ArrayList<String> retval = new ArrayList<String>();
        final File[] fileList = directory.listFiles();
        for(final File file : fileList){
            if(file.isDirectory()){
                retval.addAll(getResourcesFromDirectory(file, pattern));
            } else{
                try{
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if(accept){
                        retval.add(fileName);
                    }
                } catch(final IOException e){
                	String errorMsg ="While get Resource from directory ,IO Exception happened";
                	logger.error(errorMsg, e);
                	throw new  ParkarCoreCommonException(errorMsg,e);
                }
            }
        }
        ParkarLogger.traceLeave();
        return retval;
    }
    /**
     * Loads all the files from the class path
     * 
     * @throws ParkarCoreCommonException
     * 		: customized Parkar core common exception
     * @return URLClassLoader
     */
    public static URLClassLoader createURLClassLoader() throws ParkarCoreCommonException {
    	ParkarLogger.traceEnter();
        Collection<String> resources = ClassPathUtils.getResources(Pattern.compile(".*"));
        Collection<URL> urls = new ArrayList<URL>();
        for (String resource : resources) {
            File file = new File(resource);
            // Ensure that the JAR exists
            // and is in the globalclasspath directory.
            if (file.isFile() && "globalclasspath".equals(file.getParentFile().getName())) {
                try {
                    urls.add(file.toURI().toURL());
                } catch (MalformedURLException e) {
                    // This should never happen.
                	String errorMsg="Invalid URL Input";
                	logger.error(errorMsg, e);
                	throw new  ParkarCoreCommonException(errorMsg,e);
                }
            }
        }
        ParkarLogger.traceLeave();
        return new URLClassLoader(urls.toArray(new URL[urls.size()]));
    }


    /**
     * list the resources that match args[0]
     *
     * @param args
     *            args[0] is the pattern to match, or list all resources if
     *            there are no args
     */
   /* public static void main(final String[] args){
        Pattern pattern;
        if(args.length < 1){
            pattern = Pattern.compile(".*");
        } else{
            pattern = Pattern.compile(args[0]);
        }
        Collection<String> list=null;
		try {
			list = ClassPathUtils.getResources(pattern);
		} catch (ParkarCoreCommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(final String name : list){
            System.out.println(name);
        }
    }*/
}
