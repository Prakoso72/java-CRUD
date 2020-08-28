package CRUD;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operasi {

    public static void tampilkanData(boolean isTambahDataKosong,boolean isTampilkan)throws IOException {

        if(isTampilkan) {
            Utility.clearScreen();
            System.out.println("\n=================");
            System.out.println("LIST SELURUH BUKU");
            System.out.println("=================\n");
        }

        FileReader fileInput;
        BufferedReader bufferedFileInput;
        boolean tambahData;

        try{
            fileInput = new FileReader("database.txt");
            bufferedFileInput = new BufferedReader(fileInput);
        } catch(IOException e){
            System.err.println("\nFILE INPUT TIDAK DITEMUKAN");
            tambahData = Utility.getYesNo("Tambah Data?");

            if(tambahData) {
                tambahData(true);
            }

            return;
        }

        int nomor = 0;
        String data = bufferedFileInput.readLine();

        if(data == null && isTambahDataKosong){
            tambahData = Utility.getYesNo("Tidak ada data buku sama sekali\nTambah Data?");

            if(tambahData) {
                tambahData(true);
            }

        } else if(data != null) {
            Utility.templateTabelAtas();

            while (data != null) {
                nomor++;
                Utility.daftarData(data, nomor);
                data = bufferedFileInput.readLine();
            }
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }

        bufferedFileInput.close();
        fileInput.close();
    }

    public static void cariData()throws IOException{

        Utility.clearScreen();
        Scanner terminalInput = new Scanner(System.in);
        boolean lanjutCari = true;
        boolean isExist,tambahData;

        try {
            File file = new File("database.txt");
        } catch (Exception e){
            System.err.println("file tidak ditemukan!");
            tambahData = Utility.getYesNo("Tambah Data?");

            if(tambahData) {
                tambahData(true);
            }
        }

        while(lanjutCari) {
            Utility.clearScreen();
            System.out.println("\n=========");
            System.out.println("CARI BUKU");
            System.out.println("=========");

            System.out.print("\nCari(judul/penulis/penerbit/tahun tebit): ");
            String inputan = terminalInput.nextLine();
            System.out.println();
            String[] keywords = inputan.split("\\s");

            Utility.cekDatabase(keywords, true);
            lanjutCari = Utility.getYesNo("Cari lagi?");
        }

    }

    public static void tambahData(boolean isFromMenu)throws  IOException{

        if(isFromMenu) {
            Utility.clearScreen();
            System.out.println("\n================");
            System.out.println("TAMBAH DATA BUKU");
            System.out.println("================");
        }

        String judul,penulis,penerbit,tahunTerbit;
        FileWriter outputTambahData = new FileWriter("database.txt",true);
        BufferedWriter bufferTambahData = new BufferedWriter(outputTambahData);

        System.out.println();
        judul = Utility.notNullStringIn("Judul              : ");
        penulis = Utility.notNullStringIn("Penulis            : ");
        penerbit = Utility.notNullStringIn("Penerbit           : ");
        System.out.print("Tahun Terbit(YYYY) : ");
        tahunTerbit = Utility.ambilTahun();
        System.out.println();

        String[] keywords = {judul,penulis,penerbit,tahunTerbit};
        boolean cek = Utility.cekDatabase(keywords,false);
        boolean isRewrite;

        if(!cek){
            long nomorEntry = 1 + Utility.nomorEntryPerTahun(penulis,tahunTerbit);
            String key;

            key = penulis.replaceAll("\\s","")+"_"+tahunTerbit+"_"+nomorEntry;

            System.out.println("---------");
            System.out.println("DATA BUKU");
            System.out.println("---------");

            System.out.println("Key Database       : " + key);
            System.out.println("Judul              : " + judul);
            System.out.println("Penulis            : " + penulis);
            System.out.println("Penerbit           : " + penerbit);
            System.out.println("Tahun Terbit       : " + tahunTerbit);

            boolean konfirmasi = Utility.getYesNo("Konfirmasi?");

            if (konfirmasi){
                bufferTambahData.write(key+","+tahunTerbit+","+penulis+","+penerbit+","+judul);
                bufferTambahData.newLine();
                bufferTambahData.flush();
                outputTambahData.close();
                bufferTambahData.close();
                isRewrite = Utility.getYesNo("Tambah data buku lagi?");
                if(isRewrite){
                    tambahData(true);
                }
            }

        } else{
            outputTambahData.close();
            bufferTambahData.close();
            Utility.cekDatabase(keywords,true);
            isRewrite = Utility.getYesNo("Ulangi lagi?");
            if(isRewrite){
                tambahData(true);
            }
        }

    }

    public static void updateData() throws IOException{

        Utility.clearScreen();
        System.out.println("\n==============");
        System.out.println("UBAH DATA BUKU");
        System.out.println("==============");

        int nomorUpdate,indeksData;
        boolean konfirmasiUpdate,ulangi,notExist,outOfTheNumber;
        String databaseString;

        File databse = new File("database.txt");
        FileReader databaseReader = new FileReader(databse);
        BufferedReader buferDBReader = new BufferedReader(databaseReader);

        File tempDB = new File("tempDB.txt");
        FileWriter tempDBWriter = new FileWriter(tempDB);
        BufferedWriter bufferTempDBWriter = new BufferedWriter(tempDBWriter);

        tampilkanData(false,false);
        System.out.print("Nomor data yang ingin dirubah?: ");
        Scanner terminalInput = new Scanner (System.in);
        indeksData = 0;
        nomorUpdate = 0;
        databaseString = buferDBReader.readLine();
        notExist = true;
        outOfTheNumber = true;

        try {
            nomorUpdate = terminalInput.nextInt(10);
        } catch (Exception e) {
            System.out.println("tidak sesuai!");
            return;
        }

        while(databaseString != null) {
            indeksData++;

            if (indeksData == nomorUpdate) {
                StringTokenizer dataDatabase = new StringTokenizer(databaseString, ",");
                String judul, penulis, penerbit, tahunTerbit, key;
                key = dataDatabase.nextToken();
                tahunTerbit = dataDatabase.nextToken();
                penulis = dataDatabase.nextToken();
                penerbit = dataDatabase.nextToken();
                judul = dataDatabase.nextToken();
                System.out.println("\n---------");
                System.out.println("DATA BUKU");
                System.out.println("---------");

                System.out.println("Key Database       : " + key);
                System.out.println("Judul              : " + judul);
                System.out.println("Penulis            : " + penulis);
                System.out.println("Penerbit           : " + penerbit);
                System.out.println("Tahun Terbit       : " + tahunTerbit);

                konfirmasiUpdate = Utility.getYesNo("Konfirmasi untuk update data tersebut?");
                if(konfirmasiUpdate){
                    Utility.clearScreen();
                    outOfTheNumber = false;
                    System.out.println("\n---------");
                    System.out.println("DATA BUKU");
                    System.out.println("---------");

                    System.out.println("Key Database       : " + key);
                    System.out.println("Judul              : " + judul);
                    System.out.println("Penulis            : " + penulis);
                    System.out.println("Penerbit           : " + penerbit);
                    System.out.println("Tahun Terbit       : " + tahunTerbit);

                    System.out.println("\n---------------");
                    System.out.println("Rubah Data Buku");
                    System.out.println("---------------");

                    konfirmasiUpdate = Utility.getYesNo("Apakah Anda ingin merubah judul?");
                    if(konfirmasiUpdate) {
                        judul = Utility.notNullStringIn("Judul              : ");
                    }
                    konfirmasiUpdate = Utility.getYesNo("Apakah Anda ingin merubah penulis?");
                    if(konfirmasiUpdate) {
                        penulis = Utility.notNullStringIn("Penulis            : ");
                    }
                    konfirmasiUpdate = Utility.getYesNo("Apakah Anda ingin merubah penerbit?");
                    if(konfirmasiUpdate) {
                        penerbit = Utility.notNullStringIn("Penerbit           : ");
                    }
                    konfirmasiUpdate = Utility.getYesNo("Apakah Anda ingin merubah tahun terbit?");
                    if(konfirmasiUpdate) {
                        System.out.print("Tahun Terbit(YYYY) : ");
                        tahunTerbit = Utility.ambilTahun();
                    }

                    long nomorEntry = 1 + Utility.nomorEntryPerTahun(penulis,tahunTerbit);
                    key = penulis.replaceAll("\\s","")+"_"+tahunTerbit+"_"+nomorEntry;
                    String[] keywords = {judul,penulis,penerbit,tahunTerbit};
                    boolean cek = Utility.cekDatabase(keywords,false);

                    if(!cek) {
                        System.out.println("\n---------");
                        System.out.println("DATA BUKU");
                        System.out.println("---------");

                        System.out.println("Key Database       : " + key);
                        System.out.println("Judul              : " + judul);
                        System.out.println("Penulis            : " + penulis);
                        System.out.println("Penerbit           : " + penerbit);
                        System.out.println("Tahun Terbit       : " + tahunTerbit);

                        konfirmasiUpdate = Utility.getYesNo("Konfirmasi untuk update data?");
                        if (konfirmasiUpdate) {
                            bufferTempDBWriter.write(key + "," + tahunTerbit + "," + penulis + "," + penerbit + "," + judul);
                        } else {
                            bufferTempDBWriter.write(databaseString);
                        }
                    } else {
                        bufferTempDBWriter.write(databaseString);
                        System.out.println("Buku sudah ada!");
                    }
                } else{
                    bufferTempDBWriter.write(databaseString);
                }
            } else{
                bufferTempDBWriter.write(databaseString);
            }

            bufferTempDBWriter.newLine();
            notExist = false;
            databaseString = buferDBReader.readLine();
        }

        bufferTempDBWriter.flush();
        databaseReader.close();
        buferDBReader.close();
        tempDBWriter.close();
        bufferTempDBWriter.close();

        databse.delete();
        tempDB.renameTo(databse);


        if(!outOfTheNumber){
            ulangi = Utility.getYesNo("Apakah Anda ingin merubah data lagi?");
            if(ulangi) {
                updateData();
            }
        } else if(notExist){
            System.out.println("Tidak ada data sama sekali!");
        } else if(outOfTheNumber){
            System.out.println("Data buku tidak dapat ditemukan1");
            ulangi = Utility.getYesNo("Apakah Anda ingin merubah data lagi?");
            if(ulangi){
                updateData();
            }
        }

    }

    public static void hapusData()throws Exception{

        Utility.clearScreen();
        System.out.println("\n===============");
        System.out.println("HAPUS DATA BUKU");
        System.out.println("===============");

        File databaseFile = new File("Database.txt");
        FileReader inputDatabse = new FileReader(databaseFile);
        BufferedReader bufferDatabase = new BufferedReader(inputDatabse);

        File tempDB = new File("tempDB.txt");
        FileWriter outTempDB = new FileWriter(tempDB);
        BufferedWriter bufferTempDB = new BufferedWriter(outTempDB);

        tampilkanData(false,false);

        Scanner terminalInput = new Scanner(System.in);
        String databaseString = bufferDatabase.readLine();
        boolean konfirmasi,cekNotExistBuku,ulangi,notExist;
        int noData = 0;
        cekNotExistBuku = true;
        int noDataDihapus = 0;
        notExist = true;
        System.out.print("Nomor yang dihapus: ");

        try {
            noDataDihapus = terminalInput.nextInt(10);
        } catch (Exception e) {
            System.out.println("tidak sesuai!");
            return;
        }


        while(databaseString != null){
            notExist = false;
            noData++;
            konfirmasi = false;

            if(noData == noDataDihapus){
                StringTokenizer dataDatabase = new StringTokenizer(databaseString,",");
                String judul,penulis,penerbit,tahunTerbit,key;
                key = dataDatabase.nextToken();
                tahunTerbit = dataDatabase.nextToken();
                penulis = dataDatabase.nextToken();
                penerbit = dataDatabase.nextToken();
                judul = dataDatabase.nextToken();
                System.out.println("---------");
                System.out.println("DATA BUKU");
                System.out.println("---------");

                System.out.println("Key Database       : " + key);
                System.out.println("Judul              : " + judul);
                System.out.println("Penulis            : " + penulis);
                System.out.println("Penerbit           : " + penerbit);
                System.out.println("Tahun Terbit       : " + tahunTerbit);

                cekNotExistBuku = false;
                konfirmasi = Utility.getYesNo("Yakin untuk menghapus?");

            }

            if(konfirmasi){
                System.out.println("Data berhasil dihapus!");
            } else{
                bufferTempDB.write(databaseString);
                bufferTempDB.newLine();
            }

            databaseString = bufferDatabase.readLine();
        }

        bufferTempDB.flush();

        bufferDatabase.close();
        bufferTempDB.close();
        inputDatabse.close();
        outTempDB.close();

        System.gc();
        databaseFile.delete();
        tempDB.renameTo(databaseFile);

        if (cekNotExistBuku){
            System.out.println("\nData buku tidak dapat ditemukan!");
            ulangi = Utility.getYesNo("Ulangi hapus data lagi?");
            if(ulangi){
                hapusData();
            }
        } else if(!cekNotExistBuku){
            ulangi = Utility.getYesNo("Apakah Anda ingin menghapus lagi?");
            if(ulangi){
                hapusData();
            }
        }  else if(notExist){
            System.out.println("Tidak ada data sama sekali!");
        }

    }

}
