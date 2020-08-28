package com.latihan;

import java.util.Scanner;
import CRUD.Operasi;
import CRUD.Utility;

public class Main {

    public static void main(String[] args)throws Exception{

        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while (isLanjutkan){
            Utility.clearScreen();
            System.out.println("DATABASE BUKU(prosedural)\n");
            System.out.println("1.\tLihat seluruh buku");
            System.out.println("2.\tCari data buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");

            System.out.print("\n\nPilihan anda: ");
            pilihanUser = terminalInput.next();

            switch (pilihanUser) {
                case "1":
                    Operasi.tampilkanData(true,true);
                    isLanjutkan = Utility.getYesNo("Lanjutkan?");
                    break;
                case "2":
                    Operasi.cariData();
                    isLanjutkan = Utility.getYesNo("Lanjutkan?");
                    break;
                case "3":
                    Operasi.tambahData(true);
                    isLanjutkan = Utility.getYesNo("Lanjutkan?");
                    break;
                case "4":
                    Operasi.updateData();
                    isLanjutkan = Utility.getYesNo("Lanjutkan?");
                    break;
                case "5":
                    Operasi.hapusData();
                    isLanjutkan = Utility.getYesNo("Lanjutkan?");
                    break;
                default:
                    System.err.println("Input tidak ditemukan\n");
                    isLanjutkan = Utility.getYesNo("Pilih Kembali?");
            }

        }

    }

}
