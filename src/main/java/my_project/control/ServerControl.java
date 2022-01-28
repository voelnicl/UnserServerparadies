package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.netz.Server;
import my_project.model.Ding;

import java.awt.event.KeyEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ServerControl extends Server {

    //Attribute


    // Referenzen
    private final ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Ding ding1;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ServerControl(ViewController viewController){
        super(6698);
        // 10.17.128.89
        this.viewController = viewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {
        // Erstelle ein Objekt der Klasse Ball und lasse es zeichnen
        ding1 = new Ding(300,300);
        viewController.draw(ding1);
    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        if(ViewController.isKeyDown(KeyEvent.VK_W)) System.out.println("Test");
        if(ViewController.isKeyDown(KeyEvent.VK_0));
        if(ViewController.isKeyDown(KeyEvent.VK_1)) ;
        if(ViewController.isKeyDown(KeyEvent.VK_2)) ;
        if(ViewController.isKeyDown(KeyEvent.VK_3)) ;
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        this.send(pClientIP,pClientPort,"Willkommen zu unserem Projekt. Starte das Programm mit\"deinem Namen\"!");
        System.out.println(pClientIP);
    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        if(pMessage.equalsIgnoreCase("deinem Namen")) {
            System.out.println("Von " + pClientIP + " aus wurde das Programm erfolgreich gestartet.");
        } else {
            printError(pMessage,pClientIP);
        }
    }

    private void printError(String pMessage, String pClientIP){
        System.out.println("Falsche Antwort: "+pMessage+" from: "+pClientIP);
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        this.send(pClientIP,pClientPort,"Tschüssi!");
    }
}

