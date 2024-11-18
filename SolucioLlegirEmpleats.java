import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SolucioLlegirEmpleats {

    public static void main(String[] args) throws FileNotFoundException, IOException  {

        System.out.println("Opcions disponibles");
        System.out.println("");
        System.out.println("1. Cercar un empleat");
        System.out.println("2. Modificar un departament");
        System.out.print("Indicar l'opció: ");

        try (BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in))) {

            int opcio = Integer.parseInt(br1.readLine());

            String nomFit = "Empleats.dat";

            System.out.print("Introduir el nom a localitzar al fitxer d'empleats: ");
            String nom_empleat = br1.readLine();

            boolean validOpt = true;
            int nou_dept = 0;
            String mode = "";

            while (validOpt) {

                if (opcio == 1) {

                    mode = "r";

                    validOpt = false;
    
                } else if (opcio == 2) {
    
                    System.out.print("Introduir el nou departament: ");

                    nou_dept = Integer.parseInt(br1.readLine());
    
                    mode = "rw";
                    
                    validOpt = false;
    
                } else {
                    System.out.println("Opció no vàlida, ficar una opció correcta");
                }
        
            }

            TractarAleatori(opcio, nomFit, mode ,nom_empleat, nou_dept);

        }

    }

    public static void TractarAleatori(int opcio, String nomFitxer, String mode, String nom_empleat, int nou_dept) throws IOException {
            
        RandomAccessFile raf1 = new RandomAccessFile(nomFitxer, mode);

        //Declarem la variable que ens permetrà posicionar-nos al fitxer
        int posicio=0; 

        //Declarem la variable auxiliar que emmagatzemarà cada caràcter
        //de l'array de caràcters del nom
        char aux;

        //Declarem les variables dels camps que anem a llegir
        char nomEmpleat[] = new char[20];
        int departament;
        double salari;
        boolean EmpTrobat = false;

        //condició d'iteració: fins a la longitud màxima de fitxer
        while(raf1.getFilePointer() != raf1.length()){
            
            //ens posicionem d'acord al valor de la variable posició
            raf1.seek(posicio);

            //iterem caràcter a caràcter i els anem ficant a nomEmpleat
            for (int i=0;i<nomEmpleat.length;i++) {
                aux = raf1.readChar();
                nomEmpleat[i] = aux;
            }

            //Reconstruïm en un únic String
            String nomCompletEmpleat = new String(nomEmpleat);

            if (nomCompletEmpleat.trim().equals(nom_empleat)) {

                EmpTrobat = true;

                if (opcio == 1) {
                    //lectura i mostra de les dades de l'empleat

                    departament = raf1.readInt();
                    salari = raf1.readDouble();

                    System.out.printf("Nom empleat: %s, Departament: %d, Salari: %.2f %n",
                    nomCompletEmpleat.trim(),departament,salari);
                    
                } else if (opcio == 2) {
                    //modificació del departament de l'empleat

                    raf1.writeInt(nou_dept);

                    raf1.skipBytes(8);
                }

            } else {
                // Si no hi ha coincidència fem skip dels bytes de departament i salari (4 + 8 = 12)
                raf1.skipBytes(12);

            }

            //movem el punter
            posicio = posicio + 52;
        }

//Si no es troba l'empleat donem un missatge
        if (!EmpTrobat) {
            System.out.println("Empleat no localitzat a l'arxiu d'empleats");
        }

        raf1.close();

    }
}