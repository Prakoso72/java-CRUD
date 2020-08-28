package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {

    public static boolean getYesNo(String massage){

        Scanner getYesNoInput = new Scanner (System.in);
        String pilihanUser;
        boolean pilihan;

        System.out.print("\n" + massage + " y/n : ");
        pilihanUser = getYesNoInput.next();

        while (!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")){
            System.err.println("Wrong Keyword!");
            System.out.print("\n" + massage + " y/n : ");
            pilihanUser = getYesNoInput.next();
        }

        pilihan = pilihanUser.equalsIgnoreCase("y");
        System.out.println();

        return pilihan;
    }

    public static void clearScreen(){

        try{
            if(System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else{
                System.out.print("\033\143");
            }
        } catch (Exception e){
            System.err.println("tidak bisa clear screen");
        }
    }

    static void daftarData(String input,int nomor){

        StringTokenizer dataToken;
        dataToken = new StringTokenizer(input, ",");
        dataToken.nextToken();

        System.out.printf("| %-6d", nomor);
        System.out.printf("| %-10s", dataToken.nextToken());
        System.out.printf("| %-22s", dataToken.nextToken());
        System.out.printf("| %-22s", dataToken.nextToken());
        System.out.printf("| %-34s", dataToken.nextToken());
        System.out.println("|");

    }

    static void templateTabelAtas(){

        String no = " NO";
        String tahun = " TAHUN";
        String penulis = " PENULIS";
        String penerbit = " PENERBIT";
        String judul = " JUDUL";
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-7s",no);
        System.out.printf("|%-11s",tahun);
        System.out.printf("|%-23s",penulis);
        System.out.printf("|%-23s",penerbit);
        System.out.printf("|%-35s",judul);
        System.out.println("|");
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    static boolean cekDatabase (String[] key, boolean isDisplay)throws IOException {

        FileReader databaseReader = new FileReader("database.txt");
        BufferedReader buffDatabaseReader = new BufferedReader(databaseReader);

        String databaseString = buffDatabaseReader.readLine();
        boolean cek = false;
        int nomor = 0;

        if(isDisplay) {
            templateTabelAtas();
        }

        while(databaseString != null){
            cek = true;
            for(String keyword:key){
                cek = cek && databaseString.toLowerCase().contains(keyword.toLowerCase());
            }
            if(isDisplay) {
                if (cek) {
                    nomor++;
                    daftarData(databaseString, nomor);
                }
            } else if(cek){
                break;
            }
            databaseString = buffDatabaseReader.readLine();
        }

        if(isDisplay) {
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }

        databaseReader.close();
        buffDatabaseReader.close();
        return cek;

    }

    static String ambilTahun(){

        Scanner terminalInput = new Scanner(System.in);
        String tahun;
        boolean tahunValid = false;
        tahun = terminalInput.nextLine();

        while(!tahunValid) {

            try {
                Year.parse(tahun);
                tahunValid = true;
            } catch (Exception e) {
                System.err.println("tahun tidak valid");
                System.out.print("Tahun Terbit(YYYY) : ");
                tahun = terminalInput.nextLine();
            }
        }
        return tahun;

    }

    static String notNullStringIn(String massage){

        Scanner terminalInput = new Scanner(System.in);
        boolean tesNull = true;
        String hasilInput = null;

        while(tesNull) {
            System.out.print(massage);
            hasilInput = terminalInput.nextLine();

            if (hasilInput.length() > 0) {
                tesNull = false;
            } else{
                System.err.println("MASUKAN DATA TERLEBIH DAHULU!");
            }

        }

        return hasilInput;
    }

    static long nomorEntryPerTahun(String penulis, String tahunTerbit) throws IOException{

        FileReader database = new FileReader("database.txt");
        BufferedReader buffDatabase = new BufferedReader(database);
        String databaseString,primaryKey;
        Scanner inputDatabase;
        long noEntry = 0;
        databaseString = buffDatabase.readLine();

        while(databaseString != null) {
            inputDatabase = new Scanner(databaseString);
            inputDatabase = inputDatabase.useDelimiter(",");
            primaryKey = inputDatabase.next();
            inputDatabase = new Scanner(primaryKey);
            inputDatabase.useDelimiter("_");
            String tes = penulis.replaceAll("\\s", "");


            if(tes.equalsIgnoreCase(inputDatabase.next()) && tahunTerbit.equalsIgnoreCase(inputDatabase.next())){
                noEntry = inputDatabase.nextLong();
            }

            databaseString = buffDatabase.readLine();
        }

        database.close();
        buffDatabase.close();
        return noEntry;
    }

}
