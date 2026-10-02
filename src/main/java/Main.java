import nu.pattern.OpenCV;

public class Main {

    public static void main(String[] args){
        OpenCV.loadLocally();
        // Abstractions or superclasses not required here.
        var aruRead = new AruCodeRead();
        aruRead.aruRead();

    }

}
