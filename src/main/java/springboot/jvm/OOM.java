package springboot.jvm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/*
 * oom常见的内存溢出问题
 * 
 * */
public class OOM {

	public static void main(String[] args) {
		
		//线程过多引起oom
		URL url = null;
        List<ClassLoader> classLoaderList = new ArrayList<ClassLoader>();
        try {
            url = new File("/tmp").toURI().toURL();
            URL[] urls = {url};
            while (true){
                ClassLoader loader = new URLClassLoader(urls);
                classLoaderList.add(loader);
                loader.loadClass("springboot.nio.NewioClient");
                loader.loadClass("springboot.nio.NewioServer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
