package my_project.control;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.abitur.datenstrukturen.List;
import KAGO_framework.model.abitur.netz.Server;
import my_project.model.Ding;

import java.awt.event.KeyEvent;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController extends Server {

    //Attribute


    // Referenzen
    private final ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Ding ding1;
    private List<String[]> cl;


    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        super(6698);
        cl = new List<>();
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
        System.out.println("Willkommen zu unserem Projekt. Starte das Programm mit\"deinem Namen\"!");
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
            String[] strings = new String[3];
            strings[0] = pClientIP;
            strings[2] = "0";
            cl.append(strings);
            System.out.println(pClientIP + ": Neu verbunden");
    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        boolean onequestion = false;
        String[] strings = iPSearch(pClientIP);
            if (strings[2].equals("0")) {
                if(pMessage.equalsIgnoreCase("deinem Namen")) {
                    strings[2] = "1";
                    send(pClientIP, pClientPort, "Was ist denn nun dein Name?");
                    System.out.println(pClientIP + ": hat noch keinen Namen, aber schonmal begonnen!");
                    onequestion = true;
                } else {
                    printError(pMessage, pClientIP);
                    send(pClientIP, pClientPort, "Versuch es nochmal mit\"deinem Namen\"! (Tipp: Um die Ecke denken!)");
                }
            }
            if (strings[2].equals("1") && onequestion == false) {
                strings[2] = "2";
                strings[1] = pMessage;
                System.out.println(pMessage + ": hat einen Namen festgelegt!");
                send(pClientIP, pClientPort, ": Nun können wir weiter machen, " + pMessage + "!");
                send(pClientIP,pClientPort, "Vor dir steht ein Kuchen!");
                send(pClientIP,pClientPort, "*a* Kuchen \"essen\"! *b* Kuchen nehmen!");
                onequestion = true;
            }
            if(strings[2].equals("2") && onequestion == false) {
                if(pMessage.equalsIgnoreCase("a")) {
                    send(pClientIP,pClientPort, "Jetzt ist der Kuchen weg! Es soll aber einer da bleiben!");
                    System.out.println(strings[1] + ": Versucht sich mit dem Kuchen!");
                }
                else if(pMessage.equalsIgnoreCase("b")) {
                    send(pClientIP,pClientPort, "Jetzt hat der Nächste keinen Kuchen mehr! Er soll aber da bleiben!");
                    System.out.println(strings[1] + ": Versucht sich mit dem Kuchen!");
                }
                else if(pMessage.equalsIgnoreCase("c")) {
                    send(pClientIP,pClientPort, "C ist immer richtig! Nun hat der nächste auch noch einen Kuchen, weil du ihn stehen gelassen hast!");
                    System.out.println(strings[1] + ": Hat die Kuchen-Aufgabe gelöst!");
                    send(pClientIP,pClientPort, "Du hast jetzt aber Lust auf Kuchen und möchtest einen \"backen\"! Aber wie?");
                    strings[2] = "3";
                } else {
                    send(pClientIP,pClientPort, "So kannst du nicht antworten! (Tipp: Alle guten Dinge sind drei!)");
                    System.out.println(strings[1] + ": Versucht sich mit dem Kuchen!");
                }
                onequestion = true;
            }
        if(strings[2].equals("3") && onequestion == false) {
            if(pMessage.equalsIgnoreCase("backen")) {
                send(pClientIP,pClientPort, "Das wäre zu einfach! Denk mal zweifach!");
                System.out.println(strings[1] + ": Backt den Kuchen falsch!");
            } else if (pMessage.equalsIgnoreCase("backenbacken")) {
                send(pClientIP,pClientPort, "Richtig! Nun musst du nur noch eine Sache machen!");
                System.out.println(strings[1] + ": Hat einen Kuchen gebacken!");
                strings[2] = "4";
            } else {
                send(pClientIP,pClientPort, "Manchmal muss man Wörter duplizieren!");
                System.out.println(strings[1] + ": Backt den Kuchen falsch!");
            }
            onequestion = true;
        }
        if(strings[2].equals("4") && onequestion == false) {
            if(pMessage.equalsIgnoreCase("essen")) {
                send(pClientIP, pClientPort, "Fertig!");
                System.out.println(strings[1] + ": Hat das Rätsel komplett gelöst!");
                strings[2] = "5";
            }else {
                send(pClientIP,pClientPort, "Was war denn gerade Antwort a?");
                System.out.println(strings[1] + ": Weiß nicht, wie er das Rätsel fertig löst!");
            }
            onequestion = true;
        }
    }

    private void printError(String pMessage, String pClientIP){
        System.out.println("Falsche Antwort: "+pMessage+" von: "+pClientIP);
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        this.send(pClientIP,pClientPort,"Tschüssi!");
        iPRemove(pClientIP);
        System.out.println(pClientIP + ": Verbindung getrennt!");
    }

    public String[] iPSearch(String iP) {
        cl.toFirst();
        if(!cl.getContent()[0].equals(iP)) {
            cl.next();
        }
        return cl.getContent();
    }

    public void iPRemove(String iP) {
        iPSearch(iP);
        cl.remove();
    }

}

